package com.portea.commp.service.ejb;

import com.portea.commp.smsen.domain.SmsAssembly;
import com.portea.commp.smsen.domain.SmsPrimaryProcessingState;
import com.portea.commp.smsen.domain.SmsSecondaryProcessingState;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.util.Collection;

@Stateless
public class SmsQueueManagerHelper {

    @PersistenceUnit(unitName = "commpPU")
    EntityManagerFactory entityManagerFactory;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void moveSmsAssemblyToQueuedStatus(final Collection<SmsAssembly> smsAssemblyCollection) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createNamedQuery("updateSmsAssemblyStatus");

        for (SmsAssembly smsAssembly : smsAssemblyCollection) {
            // TODO Improve the code by doing bulk update instead of individual update
            updateSmsAssemblyToQueuedStatus(query, smsAssembly);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void moveSmsAssemblyToQueuedStatus(final SmsAssembly smsAssembly) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createNamedQuery("updateSmsAssemblyStatus");

        updateSmsAssemblyToQueuedStatus(query, smsAssembly);
    }

    private void updateSmsAssemblyToQueuedStatus(final Query query, final SmsAssembly smsAssembly) {
        query.setParameter("id", smsAssembly.getId());
        query.setParameter("primaryStatus", SmsPrimaryProcessingState.UNDER_PROCESS);
        query.setParameter("secondaryStatus", SmsSecondaryProcessingState.QUEUED);
        query.executeUpdate();
    }
}