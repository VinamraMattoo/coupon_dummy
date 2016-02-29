package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponAuditDao;
import com.portea.cpnen.domain.Coupon;
import com.portea.cpnen.domain.CouponAudit;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@JpaDao
@Dependent
public class CouponAuditJpaDao extends BaseJpaDao<Integer, CouponAudit> implements CouponAuditDao{


    public CouponAuditJpaDao() {
        super(CouponAudit.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<CouponAudit> find(Coupon coupon) {
        Query query = entityManager.createNamedQuery("getCouponAudit", CouponAudit.class);
        query.setParameter("coupon", coupon);
        return query.getResultList();
    }

    @Override
    public void delete(Coupon coupon) {
        Query query = entityManager.createNamedQuery("deleteCouponAudit");
        query.setParameter("coupon", coupon);
        query.executeUpdate();
    }
}
