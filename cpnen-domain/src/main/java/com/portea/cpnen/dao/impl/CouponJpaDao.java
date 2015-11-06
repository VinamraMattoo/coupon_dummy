package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponDao;
import com.portea.cpnen.domain.Coupon;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@JpaDao
@Dependent
public class CouponJpaDao extends BaseJpaDao<Integer, Coupon> implements CouponDao {

    public CouponJpaDao() {
        super(Coupon.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Coupon> getCoupons(Integer startIndex, Integer requestedCount) {
        Query query = entityManager.createNamedQuery("getCoupons", Coupon.class);
        query.setFirstResult(startIndex);
        query.setMaxResults(requestedCount);
        List<Coupon> coupons = query.getResultList();
        return coupons;
    }

    @Override
    public List<Coupon> getCoupons(Integer maxRecords) {
        Query query = entityManager.createNamedQuery("getCoupons", Coupon.class);
        query.setMaxResults(maxRecords);
        List<Coupon> coupons = query.getResultList();
        return coupons;
    }

    @Override
    public Long getCouponCount() {
        Query query = entityManager.createNamedQuery("getCouponCount");
        return (Long) query.getSingleResult();
    }

}
