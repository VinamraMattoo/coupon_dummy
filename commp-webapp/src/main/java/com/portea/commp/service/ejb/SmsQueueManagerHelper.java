package com.portea.commp.service.ejb;

import com.portea.commp.smsen.dao.SmsAssemblyDao;
import com.portea.commp.smsen.dao.SmsAuditDao;
import com.portea.commp.smsen.dao.SmsRecordDao;
import com.portea.commp.smsen.domain.*;
import com.portea.dao.JpaDao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Stateless
public class SmsQueueManagerHelper {

    @Inject @JpaDao
    SmsAssemblyDao smsAssemblyDao;

    @Inject @JpaDao
    SmsRecordDao smsRecordDao;

    @Inject @JpaDao
    SmsAuditDao smsAuditDao;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void moveSmsAssemblyToQueuedStatus(final Collection<SmsAssembly> smsAssemblyCollection,
                                              SmsPrimaryProcessingState primaryProcessingState,
                                              SmsSecondaryProcessingState secondaryProcessingState) {

        List<Long> smsAssemblyIds = new ArrayList<>();
        List<Long> smsRecordIds = new ArrayList<>();

        smsAssemblyCollection.forEach((smsAssembly) ->{
            smsAssemblyIds.add(smsAssembly.getId());
            smsRecordIds.add(smsAssembly.getSmsRecord().getId());
        });

        smsAssemblyDao.updateSmsStatus(smsAssemblyIds,
                primaryProcessingState, secondaryProcessingState);

        smsRecordDao.updateSmsStatus(smsRecordIds,
                primaryProcessingState, secondaryProcessingState);

        smsAssemblyCollection.forEach((smsAssembly) ->{

            SmsAudit smsAudit = new SmsAudit();
            smsAudit.setCreatedOn(new Date());
            SmsRecord smsRecord = smsAssembly.getSmsRecord();

            smsAudit.setSmsRecord(smsRecord);
            smsAudit.setCorrelationId(smsRecord.getCorrelationId());
            smsAudit.setSmsGateway(smsRecord.getSmsGateway());

            smsAudit.setSmsPrimaryProcessingState(primaryProcessingState);
            smsAudit.setSmsSecondaryProcessingState(secondaryProcessingState);
            smsAuditDao.create(smsAudit);
        });
    }

}