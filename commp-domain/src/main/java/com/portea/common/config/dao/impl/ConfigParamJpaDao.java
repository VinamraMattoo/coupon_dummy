package com.portea.common.config.dao.impl;

import com.portea.common.config.dao.ConfigParamDao;
import com.portea.common.config.domain.ConfigParam;
import com.portea.common.config.domain.ConfigTargetParam;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

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

    @Override
    public ConfigParam getConfigParam(Integer targetTypeId,ConfigTargetParam configTargetParam) throws NoResultException {
        Query query = entityManager.createNamedQuery("getConfigParam");
        query.setHint("org.hibernate.cacheable", true);
        query.setParameter("name", configTargetParam.name());
        query.setParameter("id", targetTypeId);
        return (ConfigParam) query.getSingleResult();
    }

    /**
     * Returns corresponding config parameters for a given target type Id
     */
    @Override
    public List<ConfigParam> getConfigParams(Integer targetTypeId) throws NoResultException {
        Query query = entityManager.createNamedQuery("getConfigParamsForTargetId", ConfigParam.class);
        query.setParameter("id", targetTypeId);
        return query.getResultList();
    }

    /**
     * Returns corresponding config parameters for a given target type name
     */
    @Override
    public List<ConfigParam> getConfigParams(com.portea.commp.smsen.domain.ConfigTargetType targetTypeName) throws NoResultException {
        Query query = entityManager.createNamedQuery("getConfigParamsForTargetName", ConfigParam.class);
        query.setParameter("name",targetTypeName);
        return query.getResultList();
    }
}
