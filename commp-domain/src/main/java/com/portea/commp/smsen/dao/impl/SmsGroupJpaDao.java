package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsGroupDao;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;
import com.portea.commp.smsen.domain.SmsGroup;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
        Query query = entityManager.createQuery("SELECT smsGroup FROM SmsGroup smsGroup WHERE name = :name");
        query.setParameter("name", name);

        return (SmsGroup) query.getSingleResult();
    }
}
