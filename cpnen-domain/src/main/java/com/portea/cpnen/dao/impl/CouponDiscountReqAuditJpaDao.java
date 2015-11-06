package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponDiscountRequestAuditDao;
import com.portea.cpnen.domain.CouponDiscountRequestAudit;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JpaDao
@Dependent
public class CouponDiscountReqAuditJpaDao extends BaseJpaDao<Integer, CouponDiscountRequestAudit> implements CouponDiscountRequestAuditDao {

    public CouponDiscountReqAuditJpaDao() {
        super(CouponDiscountRequestAudit.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        //System.out.println("Successfully set the entity manager = " + entityManager);
        this.entityManager = entityManager;
    }

}
