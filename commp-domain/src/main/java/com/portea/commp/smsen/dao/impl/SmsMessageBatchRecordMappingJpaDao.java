package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsMessageBatchRecordMappingDao;
import com.portea.commp.smsen.domain.SmsMessageBatch;
import com.portea.commp.smsen.domain.SmsMessageBatchRecordMapping;
import com.portea.commp.smsen.domain.SmsRecord;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@JpaDao
@Dependent
public class SmsMessageBatchRecordMappingJpaDao
        extends BaseJpaDao<Long, SmsMessageBatchRecordMapping> implements SmsMessageBatchRecordMappingDao{

    public SmsMessageBatchRecordMappingJpaDao() {
        super(SmsMessageBatchRecordMapping.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public SmsMessageBatchRecordMapping create(SmsMessageBatch messageBatch, SmsRecord smsRecord) {
        SmsMessageBatchRecordMapping smsMessageBatchRecordMapping = new SmsMessageBatchRecordMapping();
        smsMessageBatchRecordMapping.setSmsMessageBatch(messageBatch);
        smsMessageBatchRecordMapping.setSmsRecord(smsRecord);
        return create(smsMessageBatchRecordMapping);
    }

    @Override
    public List<SmsMessageBatchRecordMapping> find(List<Integer> messageBatchIds) {
        if (messageBatchIds.isEmpty()) {
            return new ArrayList<>();
        }
        Query query = entityManager.createNamedQuery("getMessageBatchForIds", SmsMessageBatchRecordMapping.class);
        query.setParameter("ids", messageBatchIds);
        return query.getResultList();
    }

    @Override
    public List<SmsMessageBatchRecordMapping> getMappings(Integer lotId) {
        Query query = entityManager.createNamedQuery("getMessageBatchForLotId", SmsMessageBatchRecordMapping.class);
        query.setParameter("id", lotId);
        return query.getResultList();
    }
}
