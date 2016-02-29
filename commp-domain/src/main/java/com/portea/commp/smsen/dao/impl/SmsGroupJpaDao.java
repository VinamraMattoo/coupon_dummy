package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsGroupDao;
import com.portea.commp.smsen.domain.SmsGroup;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@JpaDao
@Dependent
public class SmsGroupJpaDao extends BaseJpaDao<Integer, SmsGroup> implements SmsGroupDao {

    public SmsGroupJpaDao() {
        super(SmsGroup.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public SmsGroup findByName(final String name) {
        Query query = entityManager.createNamedQuery("getSmsGroupForName", SmsGroup.class);
        query.setParameter("name", name);

        return (SmsGroup) query.getSingleResult();
    }

    @Override
    public List<SmsGroup> getSmsGroups() {
        Query query = entityManager.createNamedQuery("getSmsGroups", SmsGroup.class);
        return query.getResultList();
    }

    @Override
    public List<SmsGroup> getSmsGroupForNameMatch(String name, Integer limit) {
        Query query = entityManager.createNamedQuery("getSmsGroupForNameMatch", SmsGroup.class);
        query.setParameter("name", name);
        query.setMaxResults(limit);
        return query.getResultList();
    }
}
