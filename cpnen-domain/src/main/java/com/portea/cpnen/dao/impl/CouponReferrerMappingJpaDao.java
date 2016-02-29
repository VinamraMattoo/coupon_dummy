package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponAreaMappingDao;
import com.portea.cpnen.dao.CouponReferrerMappingDao;
import com.portea.cpnen.domain.*;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@JpaDao
@Dependent
public class CouponReferrerMappingJpaDao extends BaseJpaDao<Integer, CouponReferrerMapping> implements CouponReferrerMappingDao {

    public CouponReferrerMappingJpaDao() {
        super(CouponReferrerMapping.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public CouponReferrerMapping find(Coupon coupon, Referrer referrer) throws NoResultException {
        Query query = entityManager.createNamedQuery("findCouponReferrerMapping", CouponReferrerMapping.class);
        query.setParameter("coupon", coupon);
        query.setParameter("referrer", referrer);
        return (CouponReferrerMapping) query.getSingleResult();
    }

    @Override
    public void delete(Coupon coupon) {
        Query query = entityManager.createNamedQuery("deleteCouponReferrerMapping");
        query.setParameter("coupon", coupon);
        query.executeUpdate();
    }

    @Override
    public List<CouponReferrerMapping> getMappings(Coupon coupon) {
        Query query = entityManager.createNamedQuery("getReferrerMapping", CouponReferrerMapping.class);
        query.setParameter("coupon", coupon);
        return query.getResultList();
    }
}
