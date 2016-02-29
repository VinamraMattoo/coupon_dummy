package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsTypeDao;
import com.portea.commp.smsen.domain.SmsType;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@JpaDao
@Dependent
public class SmsTypeJpaDao extends BaseJpaDao<Integer, SmsType> implements SmsTypeDao {

    public SmsTypeJpaDao() {
        super(SmsType.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<SmsType> getSmsTypes() {
        Query query = entityManager.createNamedQuery("getSmsTypes", SmsType.class);
        return query.getResultList();
    }

    @Override
    public SmsType find(String name) {
        Query query = entityManager.createNamedQuery("getSmsTypeForName", SmsType.class);
        query.setParameter("name", name);
        return (SmsType) query.getSingleResult();
    }
}
