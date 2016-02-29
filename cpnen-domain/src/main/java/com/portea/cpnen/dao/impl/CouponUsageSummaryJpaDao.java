package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponUsageSummaryDao;
import com.portea.cpnen.domain.CouponUsageSummary;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JpaDao
@Dependent
public class CouponUsageSummaryJpaDao extends BaseJpaDao<Integer, CouponUsageSummary> implements CouponUsageSummaryDao {

    public CouponUsageSummaryJpaDao() {
        super(CouponUsageSummary.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {

    }
}
