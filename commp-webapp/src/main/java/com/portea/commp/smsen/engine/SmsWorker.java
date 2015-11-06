package com.portea.commp.smsen.engine;

import com.portea.commp.smsen.dao.SmsAssemblyDao;
import com.portea.commp.smsen.domain.SmsAssembly;
import com.portea.commp.smsen.domain.SmsGroupGatewayMapping;
import com.portea.commp.smsen.domain.SmsSecondaryProcessingState;
import com.portea.commp.smsen.gw.GatewaySmsStatus;
import com.portea.commp.smsen.gw.SmsGatewayHandler;
import com.portea.commp.smsen.gw.SmsGatewayManager;
import com.portea.commp.smsen.vo.SmsInAssembly;
import com.portea.dao.JpaDao;
import com.portea.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.transaction.*;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A worker that processes a given SMS using an handler for an appropriate gateway.
 * Instances of worker are executed through Executor Service.
 */
@ManagedBean
public class SmsWorker implements Runnable {

    private final Date createdAt;

    private Logger LOG;

    @Inject
    private SmsQueueManager smsQueueManager;

    @Inject
    private SmsGatewayManager smsGatewayManager;

    @Resource
    private ManagedExecutorService executorService;

    @Resource
    private UserTransaction transaction;

    @Inject @JpaDao
    private SmsAssemblyDao smsAssemblyDao;

    private SmsInAssembly smsInAssembly;

    private String name = null;

    public SmsWorker() {
        createdAt = new Date();
    }

    public static AtomicInteger workerCounter = new AtomicInteger(0);

    public static AtomicInteger loadCounter = new AtomicInteger(0);

    @Override
    public void run() {

        LOG = LoggerFactory.getLogger(
                getName() != null ? getName() :
                SmsWorker.class.getSimpleName() + "." + UUID.randomUUID());

        LogUtil.entryTrace("run", LOG);

        try {
            doWork();
        } catch (InterruptedException e) {
            // Clean up status and close
            // Maybe intimate someone
            e.printStackTrace();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        int currentCount = workerCounter.incrementAndGet();

        if (currentCount % 1000 == 0) {
            if (LOG.isDebugEnabled()) LOG.debug("Worker number " + currentCount + " finished.");
        }

        LogUtil.exitTrace("run", LOG);
    }

    private void doWork() throws InterruptedException, SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        LogUtil.entryTrace("doWork", LOG);

        SmsAssembly currSms = smsAssemblyDao.find(smsInAssembly.getSmsAssembly().getId());

        if (currSms.isQueued()) {
            transaction.begin();

            currSms.setSmsSecondaryProcessingState(SmsSecondaryProcessingState.LOADED);
            smsAssemblyDao.update(currSms);

            transaction.commit();
        }
        else {
            // Probably another worker is processing this SMS
            LOG.warn("Returning, as received SMS is not queued, possibly being handled by another worker , id=" + currSms.getId());
            return;
        }

        smsInAssembly.updateSmsAssembly(currSms);

        LOG.debug("::::::Sms Secondary Status is " + currSms.getSmsSecondaryProcessingState() + " " + loadCounter.incrementAndGet());

        if (false == validateSms()) {
            // TODO LOG the validation failure
            return;
        }

        if (checkDndForRecipient()) {
            // TODO LOG the DnD event
            return;
        }

        SmsGroupGatewayMapping mapping = smsGatewayManager.getSmsGroupGatewayMapping(smsInAssembly);

        if (mapping == null) {
            checkAndSubmitSmsForRetrial();
            return;
        }

        smsInAssembly.addUsedGatewayMapping(mapping);

        SmsGatewayHandler smsGwHandler = smsGatewayManager.getGatewayHandler(mapping);

        String correlationId = submitSmsForSending(smsGwHandler);

        if (correlationId == null) {
            checkAndSubmitSmsForRetrial();
            return;
        }

        boolean smsSentSuccessfully = trackSmsStatus(correlationId, smsGwHandler);

        if (false == smsSentSuccessfully) {
            checkAndSubmitSmsForRetrial();
            return;
        }


        LogUtil.exitTrace("doWork", LOG);
    }

    /**
     * Return the date and time when this worker was created
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (this.name != null) {
            throw new IllegalStateException("Name cannot be changed. Existing name="
            + this.name + ",new name=" + name);
        }

        if (name == null || 0 == name.trim().length()) {
            throw new IllegalArgumentException("Name to be set cannot be null or empty");
        }

        this.name = name;
    }

    public SmsInAssembly getSmsInAssembly() {
        return smsInAssembly;
    }

    public void setSmsInAssembly(SmsInAssembly smsInAssembly) {
        this.smsInAssembly = smsInAssembly;
    }

    private boolean validateSms() {
        // Check whether SMS has all required data
        // Check whether SMS has expired or not
        return false; // TODO Complete the implementation
    }

    private boolean checkDndForRecipient() {
        return false; // TODO Complete the implementation
    }

    private String submitSmsForSending(final SmsGatewayHandler smsGatewayHandler) throws InterruptedException {

        Future<String> result = executorService.submit(() ->
                smsGatewayHandler.submitSMS(smsInAssembly.getSmsAssembly()));

        String correlationId = null;

        try {
            correlationId = result.get(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw e;
        } catch (ExecutionException e) {
            e.printStackTrace(); // TODO Handle in a better way
        } catch (TimeoutException e) {
            // TODO Report gateway failure
            correlationId = null;
        }

        return correlationId;
    }

    private boolean trackSmsStatus(final String correlationId,
                                   final SmsGatewayHandler smsGatewayHandler) throws InterruptedException {

        int counter = 0;
        int timeoutCounter = 0;

        while(false == Thread.currentThread().isInterrupted()) {

            Future<GatewaySmsStatus> result = executorService.submit(() ->
                    smsGatewayHandler.querySmsStatus(correlationId));

            GatewaySmsStatus smsStatus = null;

            try {
                ++counter;
                smsStatus = result.get(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw e;
            } catch (ExecutionException e) {
                e.printStackTrace(); // TODO Handle in a better way
            } catch (TimeoutException e) {
                ++timeoutCounter;
                smsStatus = null;
            }

            if (smsStatus != null && smsStatus.isTerminal()) {
                return true;
            }
            else if (smsStatus != null && false == smsStatus.isTerminal()
                    && counter <= 3) {
                // TODO we can ask handler for the total number of possible intermediate statuses
                // Empty block to let the loop run, to retry status check
            }
            else if (smsStatus == null && timeoutCounter > 2) {
                // Treat this as failure to send SMS
                return false;
            }
            else {
                // Catch-all else block for any situation not considered
                return false;
            }
        }

        return false;
    }

    private void checkAndSubmitSmsForRetrial() {
        // TODO Implement the logic to check if retry should be done
        smsQueueManager.queueSmsToBeRetried(smsInAssembly);
    }
}