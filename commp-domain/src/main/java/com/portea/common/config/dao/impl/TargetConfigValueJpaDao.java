package com.portea.common.config.dao.impl;

import com.portea.common.config.dao.TargetConfigValueDao;
import com.portea.common.config.domain.ConfigParam;
import com.portea.common.config.domain.TargetConfigValue;
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
public class TargetConfigValueJpaDao extends BaseJpaDao<Integer,TargetConfigValue> implements TargetConfigValueDao {

    public TargetConfigValueJpaDao() {
        super(TargetConfigValue.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public String getTargetConfigValue(Integer configParamId, Integer targetId) throws NoResultException {

        Query query;
        if(targetId == null || targetId == -1){
            query = entityManager.createNamedQuery("getTargetConfigValueWithNoTargetId");
        }else{
            query = entityManager.createNamedQuery("getTargetConfigValue");
            query.setParameter("targetId", targetId);
        }

        query.setHint("org.hibernate.cacheable", true);
        query.setParameter("configParamId", configParamId);

        String value = (String) query.getSingleResult();
        return value;
    }

    @Override
    public TargetConfigValue getTargetConfig(Integer configParamId, Integer targetId) throws NoResultException {

        Query query;
        if (targetId == null) {

            query = entityManager.createNamedQuery("getNullTargetConfig", TargetConfigValue.class);
        }
        else {

            query = entityManager.createNamedQuery("getTargetConfig", TargetConfigValue.class);
            query.setParameter("targetId", targetId);
        }
        query.setHint("org.hibernate.cacheable", true);
        query.setParameter("configParamId", configParamId);

        TargetConfigValue configValue = (TargetConfigValue) query.getSingleResult();
        return configValue;
    }

    @Override
    public List<TargetConfigValue> getTargetConfigValues() {
        Query query = entityManager.createNamedQuery("getTargetConfigValues", TargetConfigValue.class);
        query.setHint("org.hibernate.cacheable", true);
        return query.getResultList();
    }
}
