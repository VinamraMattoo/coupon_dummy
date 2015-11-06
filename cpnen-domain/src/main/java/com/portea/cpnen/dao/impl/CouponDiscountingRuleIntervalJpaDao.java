package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponDiscountingRuleIntervalDao;
import com.portea.cpnen.domain.CouponDiscountingRuleInterval;
import com.portea.cpnen.domain.IntervalType;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@JpaDao
@Dependent
public class CouponDiscountingRuleIntervalJpaDao extends BaseJpaDao<Integer, CouponDiscountingRuleInterval> implements CouponDiscountingRuleIntervalDao {

    public CouponDiscountingRuleIntervalJpaDao() {
        super(CouponDiscountingRuleInterval.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Integer getDiscount(int couponDiscountingRuleId, int totalCost, int productCount) {
        Query query = entityManager.createNamedQuery("getDiscount").setParameter("id", couponDiscountingRuleId);
        query.setParameter("prodType", IntervalType.PRODUCT_COUNT);
        query.setParameter("transType", IntervalType.TRANSACTION_VALUE);
        query.setParameter("prodCount",productCount);
        query.setParameter("transVal", totalCost);
        Integer discount = (Integer) query.getSingleResult();
        return discount;
    }

    @Override
    public Integer getIntervalCount(Integer cdrId) {
        Query query = entityManager.createNamedQuery("getIntervalCount").setParameter("id", cdrId);
        Integer intervalCount = ((Long) query.getSingleResult()).intValue();
        return intervalCount;
    }

}
