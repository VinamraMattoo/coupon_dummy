package com.portea.commp.smsen.dao.impl;

import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;
import com.portea.commp.smsen.dao.SmsTypeDao;
import com.portea.commp.smsen.domain.SmsType;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
