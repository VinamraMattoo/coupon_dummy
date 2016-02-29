package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponDiscountProductDao;
import com.portea.cpnen.domain.CouponDiscountProduct;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JpaDao
@Dependent
public class CouponDiscountProductJpaDao extends BaseJpaDao<Integer, CouponDiscountProduct> implements CouponDiscountProductDao {

    public CouponDiscountProductJpaDao() {
        super(CouponDiscountProduct.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        //System.out.println("Successfully set the entity manager = " + entityManager);
        this.entityManager = entityManager;
    }

}
