package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsGroupGatewayMappingAuditDao;
import com.portea.commp.smsen.domain.SmsGroupGatewayMapping;
import com.portea.commp.smsen.domain.SmsGroupGatewayMappingAudit;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Dependent
@JpaDao
public class SmsGroupGatewayMappingAuditJpaDao extends
        BaseJpaDao<Integer, SmsGroupGatewayMappingAudit> implements SmsGroupGatewayMappingAuditDao {

    public SmsGroupGatewayMappingAuditJpaDao() {
        super(SmsGroupGatewayMappingAudit.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public SmsGroupGatewayMappingAudit create(SmsGroupGatewayMapping mapping, Integer preValue) {
        SmsGroupGatewayMappingAudit smsGroupGatewayMappingAudit = new SmsGroupGatewayMappingAudit();
        smsGroupGatewayMappingAudit.setCreatedBy(mapping.getLastUpdatedBy());
        smsGroupGatewayMappingAudit.setSmsGateway(mapping.getSmsGateway());
        smsGroupGatewayMappingAudit.setSmsGroup(mapping.getSmsGroup());
        smsGroupGatewayMappingAudit.setCreatedOn(new Date());
        smsGroupGatewayMappingAudit.setNewPriority(mapping.getPriority());
        smsGroupGatewayMappingAudit.setOldPriority(preValue);
        return create(smsGroupGatewayMappingAudit);
    }
}
