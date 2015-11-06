package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponEngineConfigDao;
import com.portea.cpnen.domain.CouponEngineConfig;
import com.portea.dao.impl.BaseJpaDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class CouponEngineConfigJpaDao extends BaseJpaDao<Integer, CouponEngineConfig> implements CouponEngineConfigDao {

    public CouponEngineConfigJpaDao() {
        super(CouponEngineConfig.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        //System.out.println("Successfully set the entity manager = " + entityManager);
        this.entityManager = entityManager;
    }

}
