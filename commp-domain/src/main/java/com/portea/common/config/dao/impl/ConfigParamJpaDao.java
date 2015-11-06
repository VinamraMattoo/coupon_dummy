package com.portea.common.config.dao.impl;

import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;
import com.portea.common.config.dao.ConfigParamDao;
import com.portea.common.config.domain.ConfigParam;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JpaDao
@Dependent
public class ConfigParamJpaDao extends BaseJpaDao<Integer, ConfigParam> implements ConfigParamDao {

    public ConfigParamJpaDao() {
        super(ConfigParam.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
