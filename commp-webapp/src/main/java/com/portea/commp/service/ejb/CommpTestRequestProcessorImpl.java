package com.portea.commp.service.ejb;

import com.portea.commp.smsen.dao.*;
import com.portea.commp.smsen.domain.*;
import com.portea.dao.JpaDao;
import com.portea.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

@Stateless
public class CommpTestRequestProcessorImpl implements CommpTestRequestProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(CommpTestRequestProcessorImpl.class);

    @Inject @JpaDao
    private SmsQueueDao smsQueueDao;

    @Inject @JpaDao
    private BrandDao brandDao;

    @Inject @JpaDao
    private UserDao userDao;

    @Inject @JpaDao
    private SmsGroupDao smsGroupDao;

    @Inject @JpaDao
    private SmsTemplateDao smsTemplateDao;

    @Override
    public void createSmsBatch(final int count) {

        LogUtil.entryTrace("createSmsBatch", LOG);

        long start = System.currentTimeMillis();

        int applicableCount = 10; // init to default
        int APPLICABLE_COUNT_MIN = 1;
        int APPLICABLE_COUNT_MAX = 50000;

        if (count >= APPLICABLE_COUNT_MIN && count <= APPLICABLE_COUNT_MAX) {
            applicableCount = count;
        }

        if (LOG.isDebugEnabled()) LOG.debug("Creating " + applicableCount + " test SMS.");

        Brand testBrand = brandDao.find(new Integer(1));
        User testUser = userDao.find(new Integer(1));

        SmsGroup dummySmsGroup1 = smsGroupDao.findByName("DummyGroup1");
        SmsGroup dummySmsGroup2 = smsGroupDao.findByName("DummyGroup2");

        SmsGroup[] smsGroups = {dummySmsGroup1, dummySmsGroup2};

        SmsTemplate dummySmsTemplate1 = smsTemplateDao.findByName("DummyTemplate1");
        SmsTemplate dummySmsTemplate2 = smsTemplateDao.findByName("DummyTemplate2");

        Random random = new Random();

        SmsTemplate[] smsTemplates = {dummySmsTemplate1, dummySmsTemplate2};

        LocalDateTime ldt = LocalDateTime.now();

        Date now = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        ldt = ldt.plusSeconds((long)30);

        Date afterSomeTime = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        for (int i = 0; i < applicableCount; i++) {

            SmsQueue smsQueue = new SmsQueue();

            smsQueue.setCountryCode("IN");
            smsQueue.setCreatedOn(now);
            smsQueue.setMessage("A message created at " + now);

            smsQueue.setBrand(testBrand);
            smsQueue.setMobileNumber("0000000000");
            smsQueue.setUser(testUser);
            smsQueue.setReceiverType("SampleReceiverType");

            smsQueue.setScheduledId("SampleScheduleId");
            smsQueue.setScheduledTimeZone("+5.5");
            smsQueue.setScheduledTime(afterSomeTime);
            smsQueue.setScheduledType("SampleScheduleType");

            smsQueue.setSendBefore(afterSomeTime);

            int randomIndex = random.nextInt(smsTemplates.length);

            smsQueue.setSmsGroup(smsGroups[randomIndex]);
            smsQueue.setSmsTemplate(smsTemplates[randomIndex]);

            smsQueueDao.create(smsQueue);
        }

        long end = System.currentTimeMillis();

        // TODO Implement AOP interceptor to measure method run-time
        if (LOG.isDebugEnabled()) LOG.debug("Execution time for test SMS creation : " +
                ((end-start) / 1000) + " seconds.");

        LogUtil.exitTrace("createSmsBatch", LOG);
    }
}
