package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponDiscountRequestDao;
import com.portea.cpnen.domain.CouponDiscountRequest;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JpaDao
@Dependent
public class CouponDiscountReqJpaDao extends BaseJpaDao<Integer, CouponDiscountRequest> implements CouponDiscountRequestDao {

    public CouponDiscountReqJpaDao() {
        super(CouponDiscountRequest.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        //System.out.println("Successfully set the entity manager = " + entityManager);
        this.entityManager = entityManager;
    }

}
