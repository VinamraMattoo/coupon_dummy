package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsQueueDao;
import com.portea.commp.smsen.domain.SmsQueue;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.concurrent.TimeUnit;

@JpaDao
@Dependent
public class SmsQueueJpaDao extends BaseJpaDao<Long, SmsQueue> implements SmsQueueDao {

    public SmsQueueJpaDao() {
        super(SmsQueue.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<SmsQueue> getNextBatchFromQueue(int milliSec, int resultSize) {

        Long seconds = TimeUnit.MILLISECONDS.toSeconds(milliSec);
        Query query = entityManager.createNativeQuery("" +
                "SELECT * FROM `smsen_sms_queue` WHERE scheduledTime < " +
                "DATE_ADD(CONVERT_TZ(now(), (SELECT @@time_zone), scheduled_time_zone), INTERVAL "+seconds+" SECOND)",
                SmsQueue.class);
        query.setMaxResults(resultSize);

        return query.getResultList();
    }
}
