package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.domain.SmsGatewayLog;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;
import com.portea.commp.smsen.dao.SmsGatewayLogDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JpaDao
@Dependent
public class SmsGatewayLogJpaDao extends BaseJpaDao<Integer, SmsGatewayLog> implements SmsGatewayLogDao {

    public SmsGatewayLogJpaDao() {
        super(SmsGatewayLog.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
