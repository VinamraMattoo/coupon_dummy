package com.portea.commp.smsen.dao.impl;

import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;
import com.portea.commp.smsen.dao.SmsNotificationDao;
import com.portea.commp.smsen.domain.SmsNotification;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JpaDao
@Dependent
public class SmsNotificationJpaDao extends BaseJpaDao<Integer, SmsNotification> implements SmsNotificationDao {

    public SmsNotificationJpaDao() {
        super(SmsNotification.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
