package com.portea.commp.smsen.dao.impl;

import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;
import com.portea.commp.smsen.dao.SmsAuditDao;
import com.portea.commp.smsen.domain.SmsAudit;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
