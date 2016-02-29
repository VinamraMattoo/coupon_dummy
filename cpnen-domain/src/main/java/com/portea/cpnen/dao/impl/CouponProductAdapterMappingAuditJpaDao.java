package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponProductAdapterMappingAuditDao;
import com.portea.cpnen.domain.CouponAudit;
import com.portea.cpnen.domain.CouponProductAdapterMapping;
import com.portea.cpnen.domain.CouponProductAdapterMappingAudit;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@JpaDao
@Dependent
public class CouponProductAdapterMappingAuditJpaDao extends BaseJpaDao<Integer, CouponProductAdapterMappingAudit> implements CouponProductAdapterMappingAuditDao{

    public CouponProductAdapterMappingAuditJpaDao() {
        super(CouponProductAdapterMappingAudit.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void delete(CouponAudit couponAudit) {
        Query query = entityManager.createNamedQuery("deleteProdAdapterMappingAudit");
        query.setParameter("couponAudit", couponAudit);
        query.executeUpdate();
    }
}
