package com.portea.common.config.dao.impl;

import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;
import com.portea.common.config.dao.TargetConfigValueDao;
import com.portea.common.config.domain.TargetConfigValue;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JpaDao
@Dependent
public class TargetConfigValueJpaDao extends BaseJpaDao<Integer,TargetConfigValue> implements TargetConfigValueDao {

    public TargetConfigValueJpaDao() {
        super(TargetConfigValue.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
