package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponDiscountingRuleDao;
import com.portea.cpnen.domain.CouponDiscountingRule;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
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
    public List<Integer> getRuleIds(int couponId) {
        Query query = entityManager.createNamedQuery("findListRulesOrderByPriority").setParameter("id", couponId);
        List<Integer> list = query.getResultList();
        return list;
    }

}
