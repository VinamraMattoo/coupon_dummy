package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponDiscountSummaryDao;
import com.portea.cpnen.domain.CouponDiscountSummary;
import com.portea.dao.impl.BaseJpaDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class CouponDiscountSummaryJpaDao extends BaseJpaDao<Integer, CouponDiscountSummary> implements CouponDiscountSummaryDao {

    public CouponDiscountSummaryJpaDao() {
        super(CouponDiscountSummary.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        //System.out.println("Successfully set the entity manager = " + entityManager);
        this.entityManager = entityManager;
    }

}
