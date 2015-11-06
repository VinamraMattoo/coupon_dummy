package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponDiscountReqProdAuditDao;
import com.portea.cpnen.domain.CouponDiscountReqProdAudit;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JpaDao
@Dependent
public class CouponDiscountReqProdAuditJpaDao extends BaseJpaDao<Integer, CouponDiscountReqProdAudit> implements CouponDiscountReqProdAuditDao {

    public CouponDiscountReqProdAuditJpaDao() {
        super(CouponDiscountReqProdAudit.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        //System.out.println("Successfully set the entity manager = " + entityManager);
        this.entityManager = entityManager;
    }

}
