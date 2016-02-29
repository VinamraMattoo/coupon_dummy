package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.BrandDao;
import com.portea.commp.smsen.domain.Brand;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@JpaDao
@Dependent
public class BrandJpaDao extends BaseJpaDao<Integer, Brand> implements BrandDao{

    public BrandJpaDao() {
        super(Brand.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Brand find(String brandName) {
        Query query = entityManager.createNamedQuery("getBrand", Brand.class);
        query.setParameter("name", brandName);
        return (Brand) query.getSingleResult();
    }
}
