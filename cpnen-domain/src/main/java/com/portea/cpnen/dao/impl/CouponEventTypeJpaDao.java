package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponEventTypeDao;
import com.portea.cpnen.domain.CouponEventType;
import com.portea.dao.impl.BaseJpaDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class CouponEventTypeJpaDao extends BaseJpaDao<Integer, CouponEventType> implements CouponEventTypeDao {

    public CouponEventTypeJpaDao() {
        super(CouponEventType.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        //System.out.println("Successfully set the entity manager = " + entityManager);
        this.entityManager = entityManager;
    }

}
