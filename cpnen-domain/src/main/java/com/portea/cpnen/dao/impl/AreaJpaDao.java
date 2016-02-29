package com.portea.cpnen.dao.impl;


import com.portea.cpnen.dao.AreaDao;
import com.portea.cpnen.domain.Area;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@JpaDao
@Dependent
public class AreaJpaDao extends BaseJpaDao<Integer, Area> implements AreaDao {

    public AreaJpaDao() {
        super(Area.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Area> getAreas() {
        Query query = entityManager.createNamedQuery("getAreas", Area.class);
        return query.getResultList();
    }
}
