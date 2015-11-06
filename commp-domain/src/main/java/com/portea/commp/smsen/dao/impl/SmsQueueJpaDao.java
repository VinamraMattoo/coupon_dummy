package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsQueueDao;
import com.portea.commp.smsen.domain.SmsQueue;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@JpaDao
@Dependent
public class SmsQueueJpaDao extends BaseJpaDao<Integer, SmsQueue> implements SmsQueueDao {

    public SmsQueueJpaDao() {
        super(SmsQueue.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<SmsQueue> getNextBatchFromQueue(int seconds, int resultSize) {
        Query query = entityManager.createNamedQuery("getNextBatchFromQueue");
        query.setMaxResults(resultSize);

        LocalDateTime ldt = LocalDateTime.now();
        ldt.plusSeconds((long) seconds);

        Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        query.setParameter("scheduledTime", date);

        return query.getResultList();
    }
}
