package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsMessageBatchDao;
import com.portea.commp.smsen.domain.SmsLot;
import com.portea.commp.smsen.domain.SmsMessageBatch;
import com.portea.commp.smsen.domain.SmsType;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JpaDao
@Dependent
public class SmsMessageBatchJpaDao extends BaseJpaDao<Integer, SmsMessageBatch> implements SmsMessageBatchDao {

    public SmsMessageBatchJpaDao() {
        super(SmsMessageBatch.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public SmsMessageBatch create(SmsLot smsLot, SmsType smsType) {
        SmsMessageBatch smsMessageBatch = new SmsMessageBatch();
        smsMessageBatch.setSmsLot(smsLot);
        smsMessageBatch.setSmsType(smsType);
        return create(smsMessageBatch);
    }
}
