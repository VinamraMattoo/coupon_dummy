package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsSenderDao;
import com.portea.commp.smsen.domain.SmsSender;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Dependent
@JpaDao
public class SmsSenderJpaDao extends BaseJpaDao<Integer, SmsSender> implements SmsSenderDao {

    public SmsSenderJpaDao() {
        super(SmsSender.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<SmsSender> getSmsSenders() {
        Query query = entityManager.createNamedQuery("getSmsSenders", SmsSender.class);
        return query.getResultList();
    }

    @Override
    public SmsSender find(String name) throws NoResultException{
        Query query = entityManager.createNamedQuery("getSmsSender", SmsSender.class);
        query.setParameter("name", name);
        return (SmsSender) query.getSingleResult();
    }
}
