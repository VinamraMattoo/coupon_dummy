package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsGatewayDao;
import com.portea.commp.smsen.domain.SmsGateway;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

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

    @Override
    public SmsGateway find(String gatewayName) throws NoResultException{
        Query query = entityManager.createNamedQuery("getGatewayForName", SmsGateway.class);
        query.setParameter("name", gatewayName);
        return (SmsGateway) query.getSingleResult();
    }

    @Override
    public List<SmsGateway> getGateways() {
        Query query = entityManager.createNamedQuery("getGateways", SmsGateway.class);
        return query.getResultList();
    }
}
