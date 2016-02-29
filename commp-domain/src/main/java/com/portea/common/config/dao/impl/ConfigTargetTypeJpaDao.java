package com.portea.common.config.dao.impl;

import com.portea.common.config.dao.ConfigTargetTypeDao;
import com.portea.common.config.domain.ConfigTargetType;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

    @Override
    public Integer getIdForTargetType(com.portea.commp.smsen.domain.ConfigTargetType targetType) throws NoResultException{
        Query query = entityManager.createNamedQuery("getTargetTypeId");
        query.setHint("org.hibernate.cacheable", true);
        query.setParameter("targetType", targetType);
        Integer targetTypeId = (Integer) query.getSingleResult();
        return targetTypeId;
    }
}
