package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponDiscountCodeDao;
import com.portea.cpnen.domain.CouponDiscountCode;
import com.portea.dao.impl.BaseJpaDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class CouponDiscountCodeJpaDao extends BaseJpaDao<Integer, CouponDiscountCode> implements CouponDiscountCodeDao {

    public CouponDiscountCodeJpaDao() {
        super(CouponDiscountCode.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        //System.out.println("Successfully set the entity manager = " + entityManager);
        this.entityManager = entityManager;
    }

}
