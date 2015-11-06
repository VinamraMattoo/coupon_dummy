package com.portea.common.config.dao.impl;

import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;
import com.portea.common.config.dao.TargetConfigDao;
import com.portea.common.config.domain.TargetConfig;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JpaDao
@Dependent
public class TargetConfigJpaDao extends BaseJpaDao<Integer,TargetConfig> implements TargetConfigDao {

    public TargetConfigJpaDao() {
        super(TargetConfig.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
