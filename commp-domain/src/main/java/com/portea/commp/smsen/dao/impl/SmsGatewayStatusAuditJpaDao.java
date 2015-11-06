package com.portea.commp.smsen.dao.impl;

import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;
import com.portea.commp.smsen.dao.SmsGatewayStatusAuditDao;
import com.portea.commp.smsen.domain.SmsGatewayStatusAudit;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JpaDao
@Dependent
public class SmsGatewayStatusAuditJpaDao extends BaseJpaDao<Integer, SmsGatewayStatusAudit> implements SmsGatewayStatusAuditDao {

    public SmsGatewayStatusAuditJpaDao() {
        super(SmsGatewayStatusAudit.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
