package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponDiscountingRuleDao;
import com.portea.cpnen.domain.Coupon;
import com.portea.cpnen.domain.CouponDiscountingRule;
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
public class CouponDiscountingRuleJpaDao extends BaseJpaDao<Integer, CouponDiscountingRule> implements CouponDiscountingRuleDao {

    public CouponDiscountingRuleJpaDao() {
        super(CouponDiscountingRule.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public CouponDiscountingRule getRule(Coupon coupon) throws NoResultException{
        Query query = entityManager.createNamedQuery("getDiscountRule", CouponDiscountingRule.class);
        query.setParameter("coupon", coupon);
        return (CouponDiscountingRule) query.getSingleResult();
    }

    @Override
    public void delete(Coupon coupon) {
        Query query = entityManager.createNamedQuery("deleteCouponRule");
        query.setParameter("coupon", coupon);
        query.executeUpdate();
    }
}
