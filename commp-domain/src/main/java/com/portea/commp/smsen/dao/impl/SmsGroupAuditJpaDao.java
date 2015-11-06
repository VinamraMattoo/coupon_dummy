package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsGroupAuditDao;
import com.portea.commp.smsen.domain.SmsGroupAudit;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
