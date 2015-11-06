package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponEngineConfigAuditDao;
import com.portea.cpnen.domain.CouponEngineConfigAudit;
import com.portea.dao.impl.BaseJpaDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class CouponEngineConfigAuditJpaDao extends BaseJpaDao<Integer, CouponEngineConfigAudit> implements CouponEngineConfigAuditDao {

    public CouponEngineConfigAuditJpaDao() {
        super(CouponEngineConfigAudit.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        //System.out.println("Successfully set the entity manager = " + entityManager);
        this.entityManager = entityManager;
    }

}
