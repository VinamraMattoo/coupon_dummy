package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponBrandMappingDao;
import com.portea.cpnen.domain.Brand;
import com.portea.cpnen.domain.Coupon;
import com.portea.cpnen.domain.CouponBrandMapping;
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
public class CouponBrandMappingJpaDao extends BaseJpaDao<Integer, CouponBrandMapping> implements CouponBrandMappingDao{

    public CouponBrandMappingJpaDao() {
        super(CouponBrandMapping.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<CouponBrandMapping> getMappings(Coupon coupon) {
        Query query = entityManager.createNamedQuery("getBrandMapping", CouponBrandMapping.class);
        query.setParameter("coupon", coupon);
        return query.getResultList();
    }

    @Override
    public void delete(Coupon coupon) {
        Query query = entityManager.createNamedQuery("deleteCouponBrandMapping");
        query.setParameter("coupon", coupon);
        query.executeUpdate();
    }

    @Override
    public CouponBrandMapping find(Coupon coupon, Brand brand) throws NoResultException {
        Query query = entityManager.createNamedQuery("findCouponBrandMapping", CouponBrandMapping.class);
        query.setParameter("coupon", coupon);
        query.setParameter("brand", brand);
        return (CouponBrandMapping) query.getSingleResult();
    }
}
