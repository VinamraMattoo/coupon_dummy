package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsTemplateDao;
import com.portea.commp.smsen.domain.SmsTemplate;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@JpaDao
@Dependent
public class SmsTemplateJpaDao extends BaseJpaDao<Integer, SmsTemplate> implements SmsTemplateDao {

    public SmsTemplateJpaDao() {
        super(SmsTemplate.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public SmsTemplate findByName(String name) {
        Query query = entityManager.createQuery("SELECT smsTemplate FROM SmsTemplate smsTemplate WHERE name = :name");
        query.setParameter("name", name);

        return (SmsTemplate) query.getSingleResult();
    }
}
