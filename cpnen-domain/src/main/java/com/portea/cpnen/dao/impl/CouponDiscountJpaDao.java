package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponDiscountDao;
import com.portea.cpnen.domain.CouponDiscount;
import com.portea.dao.impl.BaseJpaDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class CouponDiscountJpaDao extends BaseJpaDao<Integer, CouponDiscount> implements CouponDiscountDao {

    public CouponDiscountJpaDao() {
        super(CouponDiscount.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        //System.out.println("Successfully set the entity manager = " + entityManager);
        this.entityManager = entityManager;
    }

}
