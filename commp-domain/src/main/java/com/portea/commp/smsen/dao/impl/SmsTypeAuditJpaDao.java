package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.domain.SmsTypeAudit;
import com.portea.commp.smsen.dao.SmsTypeAuditDao;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
