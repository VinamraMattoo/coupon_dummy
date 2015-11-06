package com.portea.common.config.dao.impl;

import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;
import com.portea.common.config.dao.ConfigTargetTypeDao;
import com.portea.common.config.domain.ConfigTargetType;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JpaDao
@Dependent
public class ConfigTargetTypeJpaDao extends BaseJpaDao<Integer, ConfigTargetType> implements ConfigTargetTypeDao {

    public ConfigTargetTypeJpaDao() {
        super(ConfigTargetType.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
