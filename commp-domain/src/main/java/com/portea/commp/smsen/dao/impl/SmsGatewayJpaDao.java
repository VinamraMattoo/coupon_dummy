package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsGatewayDao;
import com.portea.commp.smsen.domain.SmsGateway;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JpaDao
@Dependent
public class SmsGatewayJpaDao extends BaseJpaDao<Integer, SmsGateway> implements SmsGatewayDao {

    public SmsGatewayJpaDao() {
        super(SmsGateway.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
