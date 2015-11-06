package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsRecordDao;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;
import com.portea.commp.smsen.domain.SmsRecord;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JpaDao
@Dependent
public class SmsRecordJpaDao extends BaseJpaDao<Integer, SmsRecord> implements SmsRecordDao {

    public SmsRecordJpaDao() {
        super(SmsRecord.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}