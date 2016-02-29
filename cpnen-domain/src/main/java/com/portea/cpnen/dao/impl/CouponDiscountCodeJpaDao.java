package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponDiscountCodeDao;
import com.portea.cpnen.domain.Coupon;
import com.portea.cpnen.domain.CouponDiscountCode;
import com.portea.cpnen.domain.User;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@JpaDao
@Dependent
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

    @Override
    public Long getCouponAppliedCount(Coupon coupon) {
        Query query = entityManager.createNamedQuery("getCouponAppliedCount");
        query.setParameter("coupon", coupon);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long getCouponAppliedCount(Coupon coupon, User user) {
        Query query = entityManager.createNamedQuery("getUserCouponAppliedCount");
        query.setParameter("coupon", coupon);
        query.setParameter("user", user);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long getCodeAppliedCount(String code) {
        Query query = entityManager.createNamedQuery("getCodeAppliedCount");
        query.setParameter("code", code);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long getCodeAppliedCount(String code, User user) {
        Query query = entityManager.createNamedQuery("getUserCodeAppliedCount");
        query.setParameter("code", code);
        query.setParameter("user", user);
        return (Long) query.getSingleResult();
    }

}
