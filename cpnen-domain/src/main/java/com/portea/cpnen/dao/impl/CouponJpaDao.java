package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponDao;
import com.portea.cpnen.domain.Coupon;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.*;
import java.util.Date;
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
    public List<Coupon> getCoupons(Integer startIndex, Integer requestedCount, String name,
                                   Boolean published, Boolean draft, Boolean deactivated){


        return null;
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

    @Override
    public Integer getCouponId(String name) throws NoResultException, NonUniqueResultException {
        Query query = entityManager.createNamedQuery("getCouponIdForName");
        query.setParameter("name", name);
        return (Integer) query.getSingleResult();
    }

    @Override
    public List<Coupon> find(String name) {

        Query query = entityManager.createNamedQuery("getCouponsForName", Coupon.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getCouponActorDetails(Date date) {
        Query query = entityManager.createNamedQuery("getCouponActorDetails");
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getCouponCategoryDetails(Date date) {
        Query query = entityManager.createNamedQuery("getCouponCategoryDetails");
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getCouponContextTypeDetails(Date date) {
        Query query = entityManager.createNamedQuery("getCouponContextTypeDetails");
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Override
    public Long getCouponDiscountDetails(Date date, Integer startRange, Integer endRange) throws NoResultException {
        Query query = entityManager.createNamedQuery("getCouponDiscountDetails");
        query.setParameter("date", date);
        query.setParameter("startRange", startRange);
        query.setParameter("endRange", endRange);
        return (Long) query.getSingleResult();
    }

    public List<Object[]> getCouponExpiryDetails(Date startDate, Date endDate) {
        Query query = entityManager.createNamedQuery("getCouponValidityDetails");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

}
