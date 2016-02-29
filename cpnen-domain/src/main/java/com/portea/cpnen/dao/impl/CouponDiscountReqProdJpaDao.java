package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponDiscountReqProdDao;
import com.portea.cpnen.domain.CouponDiscountRequest;
import com.portea.cpnen.domain.CouponDiscountRequestProduct;
import com.portea.cpnen.domain.ProductType;
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
public class CouponDiscountReqProdJpaDao extends BaseJpaDao<Integer, CouponDiscountRequestProduct> implements CouponDiscountReqProdDao{

    public CouponDiscountReqProdJpaDao() {
        super(CouponDiscountRequestProduct.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public CouponDiscountRequestProduct getRequestProductById(Integer cdrId, Integer productId, ProductType productType) throws NoResultException{
        Query query = entityManager.createNamedQuery("getProductById", CouponDiscountRequestProduct.class).setParameter("cdrId",cdrId).setParameter("productId", productId)
                .setParameter("productType", productType);
        return (CouponDiscountRequestProduct)query.getSingleResult();
    }

    @Override
    public List<CouponDiscountRequestProduct> getProducts(CouponDiscountRequest couponDiscountRequest) {
        Query query = entityManager.createNamedQuery("getProductsForDiscountRequest", CouponDiscountRequestProduct.class);
        query.setParameter("cdr", couponDiscountRequest);
        return query.getResultList();
    }
}
