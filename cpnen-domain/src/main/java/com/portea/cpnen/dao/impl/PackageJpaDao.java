package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.PackageDao;
import com.portea.cpnen.domain.Package;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@JpaDao
@Dependent
public class PackageJpaDao extends BaseJpaDao<Integer, Package> implements PackageDao {

    public PackageJpaDao() {
        super(Package.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Package> getPackages(List<Integer> ids) {
        Query query = entityManager.createNamedQuery("getPackages", Package.class);
        query.setParameter("ids", ids);
        return query.getResultList();
    }
}
