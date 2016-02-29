package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponAreaMappingAuditDao;
import com.portea.cpnen.dao.CouponReferrerMappingAuditDao;
import com.portea.cpnen.dao.CouponReferrerMappingDao;
import com.portea.cpnen.domain.CouponAreaMappingAudit;
import com.portea.cpnen.domain.CouponAudit;
import com.portea.cpnen.domain.CouponReferrerMappingAudit;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@JpaDao
@Dependent
public class CouponReferrerMappingAuditJpaDao extends BaseJpaDao<Integer, CouponReferrerMappingAudit> implements CouponReferrerMappingAuditDao {

    public CouponReferrerMappingAuditJpaDao() {
        super(CouponReferrerMappingAudit.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void delete(CouponAudit couponAudit) {
        Query query = entityManager.createNamedQuery("deleteCouponReferrerMappingAudit");
        query.setParameter("couponAudit", couponAudit);
        query.executeUpdate();
    }
}
