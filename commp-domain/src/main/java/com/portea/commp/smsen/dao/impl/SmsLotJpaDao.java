package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsLotDao;
import com.portea.commp.smsen.domain.SmsLot;
import com.portea.commp.smsen.domain.SmsSender;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@JpaDao
@Dependent
public class SmsLotJpaDao extends BaseJpaDao<Integer, SmsLot> implements SmsLotDao {

    public SmsLotJpaDao() {
        super(SmsLot.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public SmsLot create(SmsSender smsSender) {
        SmsLot smsLot = new SmsLot();
        smsLot.setCreatedOn(new Date());
        smsLot.setSmsSender(smsSender);
        return create(smsLot);
    }
}
