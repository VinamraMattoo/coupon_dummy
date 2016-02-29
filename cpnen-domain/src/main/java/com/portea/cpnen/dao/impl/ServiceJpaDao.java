package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.ServiceDao;
import com.portea.cpnen.domain.Service;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@JpaDao
@Dependent
public class ServiceJpaDao extends BaseJpaDao<Integer, Service> implements ServiceDao {

    public ServiceJpaDao() {
        super(Service.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Service> getServices(List<Integer> ids) {
        Query query = entityManager.createNamedQuery("getServices", Service.class);
        query.setParameter("ids", ids);
        return query.getResultList();
    }
}
