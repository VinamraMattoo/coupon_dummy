package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsRecordDao;
import com.portea.commp.smsen.domain.SmsPrimaryProcessingState;
import com.portea.commp.smsen.domain.SmsRecord;
import com.portea.commp.smsen.domain.SmsSecondaryProcessingState;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@JpaDao
@Dependent
public class SmsRecordJpaDao extends BaseJpaDao<Long, SmsRecord> implements SmsRecordDao {

    public SmsRecordJpaDao() {
        super(SmsRecord.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void updateSmsStatus(List<Long> smsAssemblyIds, SmsPrimaryProcessingState primaryProcessingState,
                                SmsSecondaryProcessingState secondaryProcessingState) {
        Query query = entityManager.createNamedQuery("updateSmsRecordStatus");
        query.setParameter("ids", smsAssemblyIds);
        query.setParameter("primaryStatus", primaryProcessingState);
        query.setParameter("secondaryStatus", secondaryProcessingState);
        query.executeUpdate();
    }

    @Override
    public void updateGatewayStatus(SmsRecord smsRecord, String status) {
        Query query = entityManager.createNamedQuery("updateGatewayStatus");
        query.setParameter("record", smsRecord);
        query.setParameter("status", status);
        query.executeUpdate();
    }

    @Override
    public List<Object[]> getSmsSourceUsage() {
        Query query = entityManager.createNamedQuery("getSmsSourceUsage");
        return query.getResultList();
    }

    @Override
    public List<Object[]> getSmsGatewayUsage() {
        Query query = entityManager.createNamedQuery("getSmsGatewayUsage");
        return query.getResultList();
    }

    @Override
    public List<SmsRecord> getRecords(Date fromDate, Date toDate) {
        Query query = entityManager.createNamedQuery("getRecordsInBetweenDays", SmsRecord.class);
        query.setParameter("from", fromDate);
        query.setParameter("to", toDate);
        return query.getResultList();
    }
}