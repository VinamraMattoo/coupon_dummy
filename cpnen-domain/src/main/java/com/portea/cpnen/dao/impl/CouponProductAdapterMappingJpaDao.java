package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponProductAdapterMappingDao;
import com.portea.cpnen.domain.Coupon;
import com.portea.cpnen.domain.CouponProductAdapterMapping;
import com.portea.cpnen.domain.ProductAdapter;
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
public class CouponProductAdapterMappingJpaDao extends BaseJpaDao<Integer, CouponProductAdapterMapping> implements CouponProductAdapterMappingDao{

    public CouponProductAdapterMappingJpaDao() {
        super(CouponProductAdapterMapping.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<CouponProductAdapterMapping> getMappings(Coupon coupon) {
        Query query = entityManager.createNamedQuery("getProductAdapterMapping", CouponProductAdapterMapping.class);
        query.setParameter("coupon", coupon);
        return query.getResultList();
    }

    @Override
    public void delete(Coupon coupon) {
        Query query = entityManager.createNamedQuery("deleteCouponProdMapping");
        query.setParameter("coupon", coupon);
        query.executeUpdate();
    }

    @Override
    public CouponProductAdapterMapping find(Coupon coupon, ProductAdapter product) throws NoResultException {
        Query query = entityManager.createNamedQuery("findCouponProductMapping", CouponProductAdapterMapping.class);
        query.setParameter("coupon", coupon);
        query.setParameter("product", product);
        return (CouponProductAdapterMapping) query.getSingleResult();
    }
}
