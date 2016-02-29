package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsGroupAuditDao;
import com.portea.commp.smsen.domain.SmsGroup;
import com.portea.commp.smsen.domain.SmsGroupAudit;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@JpaDao
@Dependent
public class SmsGroupAuditJpaDao extends BaseJpaDao<Integer, SmsGroupAudit> implements SmsGroupAuditDao {

    public SmsGroupAuditJpaDao() {
        super(SmsGroupAudit.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public SmsGroupAudit create(SmsGroup smsGroup) {
        SmsGroupAudit smsGroupAudit = new SmsGroupAudit();
        smsGroupAudit.setSmsGroup(smsGroup);
        smsGroupAudit.setPriority(smsGroup.getPriority());
        smsGroupAudit.setContentMatchCoolingPeriod(smsGroup.getContentMatchCoolingPeriod());
        smsGroupAudit.setCreatedOn(new Date());
        smsGroupAudit.setCreatedBy(smsGroup.getLastUpdatedBy());
        smsGroupAudit.setTypeMatchCoolingPeriod(smsGroup.getTypeMatchCoolingPeriod());
        return create(smsGroupAudit);
    }
}
