package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsAuditDao;
import com.portea.commp.smsen.domain.SmsAudit;
import com.portea.commp.smsen.domain.SmsRecord;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;

@JpaDao
@Dependent
public class SmsAuditJpaDao extends BaseJpaDao<Integer, SmsAudit> implements SmsAuditDao {

    public SmsAuditJpaDao() {
        super(SmsAudit.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public SmsAudit create(SmsRecord record) {
        SmsAudit smsAudit = new SmsAudit();

        smsAudit.setCreatedOn(new Date());
        smsAudit.setSmsPrimaryProcessingState(record.getSmsPrimaryProcessingState());
        smsAudit.setSmsSecondaryProcessingState(record.getSmsSecondaryProcessingState());

        smsAudit.setSmsRecord(record);

        smsAudit.setGatewayStatus(record.getGatewayStatus());
        smsAudit.setSmsGateway(record.getSmsGateway());
        smsAudit.setStatusReason(record.getStatusReason());

        smsAudit.setStatusRemarks(record.getStatusRemarks());
        smsAudit.setCorrelationId(record.getCorrelationId());

        return create(smsAudit);
    }

    @Override
    public SmsAudit create(SmsRecord record, String responseCode, String responseMessage) {
        SmsAudit smsAudit = new SmsAudit();

        smsAudit.setCreatedOn(new Date());
        smsAudit.setSmsPrimaryProcessingState(record.getSmsPrimaryProcessingState());
        smsAudit.setSmsSecondaryProcessingState(record.getSmsSecondaryProcessingState());

        smsAudit.setSmsRecord(record);

        smsAudit.setGatewayStatus(record.getGatewayStatus());
        smsAudit.setSmsGateway(record.getSmsGateway());
        smsAudit.setStatusReason(record.getStatusReason());

        smsAudit.setStatusRemarks(record.getStatusRemarks());
        smsAudit.setCorrelationId(record.getCorrelationId());

        smsAudit.setResponseCode(responseCode);
        smsAudit.setResponseMessage(responseMessage);

        return create(smsAudit);
    }

    @Override
    public Long getSubmittedSms(Date fromDate, Date tillDate) {
        Query query = entityManager.createNamedQuery("getSubmittedSms");
        query.setParameter("fromDate", fromDate);
        query.setParameter("tillDate", tillDate);
        return (Long) query.getSingleResult();
    }

    @Override
    public Object[] getSubmittedSmsStartingTime(Date from, Date till) {
        Query query = entityManager.createNamedQuery("getSubmittedSmsStartingTime");
        query.setParameter("fromDate", from);
        query.setParameter("tillDate", till);
        return (Object[]) query.getSingleResult();
    }

    @Override
    public Object[] getSubmittedSmsEndingTime(Date from, Date till) {
        Query query = entityManager.createNamedQuery("getSubmittedSmsEndingTime");
        query.setParameter("fromDate", from);
        query.setParameter("tillDate", till);
        return (Object[]) query.getSingleResult();
    }
}
