package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.BrandDao;
import com.portea.cpnen.domain.Brand;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@JpaDao
@Dependent
public class BrandJpaDao extends BaseJpaDao<Integer, Brand> implements BrandDao {

    public BrandJpaDao() {
        super(Brand.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Brand> getBrands() {
        Query query = entityManager.createNamedQuery("getBrands", Brand.class);
        return query.getResultList();
    }
}
