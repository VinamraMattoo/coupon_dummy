package com.portea.common.config.dao.impl;

import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;
import com.portea.common.config.dao.TargetConfigAuditDao;
import com.portea.common.config.domain.TargetConfigAudit;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JpaDao
@Dependent
public class TargetConfigAuditJpaDao extends BaseJpaDao<Integer, TargetConfigAudit> implements TargetConfigAuditDao {

    public TargetConfigAuditJpaDao() {
        super(TargetConfigAudit.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
