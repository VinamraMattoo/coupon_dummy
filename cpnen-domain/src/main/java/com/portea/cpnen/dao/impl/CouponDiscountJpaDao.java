package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponDiscountDao;
import com.portea.cpnen.domain.CouponDiscount;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@JpaDao
@Dependent
public class CouponDiscountJpaDao extends BaseJpaDao<Integer, CouponDiscount> implements CouponDiscountDao {

    public CouponDiscountJpaDao() {
        super(CouponDiscount.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        //System.out.println("Successfully set the entity manager = " + entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<Object[]> getCouponDiscountDetailsByArea(Date startDate, Date endDate) {
        Query query = entityManager.createNamedQuery("getCouponDiscountDetailsByArea");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getCouponDiscountDetailsByBrand(Date startDate, Date endDate) {
        Query query = entityManager.createNamedQuery("getCouponDiscountDetailsByBrand");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getCouponDiscountDetailsByReferrerType(Date startDate, Date endDate) {
        Query query = entityManager.createNamedQuery("getCouponDiscountDetailsByReferrerType");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    public List<Object[]> getCouponDiscountDetails(Date startDate, Date endDate) {
        Query query = entityManager.createNamedQuery("getCouponDiscountRequestMinMax");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getCouponDiscountsGivenByArea(Date startDate, Date endDate) {
        Query query = entityManager.createNamedQuery("getCouponDiscountsGivenByArea");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getCouponDiscountsGivenByBrand(Date startDate, Date endDate) {
        Query query = entityManager.createNamedQuery("getCouponDiscountsGivenByBrand");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }
}
