package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsTypeAuditDao;
import com.portea.commp.smsen.domain.SmsType;
import com.portea.commp.smsen.domain.SmsTypeAudit;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@JpaDao
@Dependent
public class SmsTypeAuditJpaDao extends BaseJpaDao<Integer, SmsTypeAudit> implements SmsTypeAuditDao{

    public SmsTypeAuditJpaDao() {
        super(SmsTypeAudit.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public SmsTypeAudit create(SmsType smsType) {
        SmsTypeAudit smsTypeAudit = new SmsTypeAudit();
        smsTypeAudit.setTypeMatchCoolingPeriod(smsType.getTypeMatchCoolingPeriod());
        smsTypeAudit.setCreatedOn(new Date());
        smsTypeAudit.setCreatedBy(smsType.getLastUpdatedBy());
        smsTypeAudit.setContentMatchCoolingPeriod(smsType.getContentMatchCoolingPeriod());
        smsTypeAudit.setExpiresIn(smsType.getExpiresIn());
        smsTypeAudit.setSmsType(smsType);
        return create(smsTypeAudit);
    }
}
