package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponAreaMappingDao;
import com.portea.cpnen.dao.CouponBrandMappingDao;
import com.portea.cpnen.domain.*;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@JpaDao
@Dependent
public class CouponAreaMappingJpaDao extends BaseJpaDao<Integer, CouponAreaMapping> implements CouponAreaMappingDao {

    public CouponAreaMappingJpaDao() {
        super(CouponAreaMapping.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public CouponAreaMapping find(Coupon coupon, Area area) throws NoResultException {
        Query query = entityManager.createNamedQuery("findCouponAreaMapping", CouponAreaMapping.class);
        query.setParameter("coupon", coupon);
        query.setParameter("area", area);
        return (CouponAreaMapping) query.getSingleResult();
    }

    @Override
    public void delete(Coupon coupon) {
        Query query = entityManager.createNamedQuery("deleteCouponAreaMapping");
        query.setParameter("coupon", coupon);
        query.executeUpdate();
    }

    @Override
    public List<CouponAreaMapping> getMappings(Coupon coupon) {
        Query query = entityManager.createNamedQuery("getAreaMapping", CouponAreaMapping.class);
        query.setParameter("coupon", coupon);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getCouponAreaDetails(Date date) {
        Query query = entityManager.createNamedQuery("getCouponAreaDetails");
        query.setParameter("date", date);
        return query.getResultList();
    }
}
