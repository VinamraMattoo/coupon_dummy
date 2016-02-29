package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsUserThrottlingDataDao;
import com.portea.commp.smsen.domain.SmsUserThrottlingData;
import com.portea.commp.smsen.domain.User;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;

@JpaDao
@Dependent
public class SmsUserThrottlingDataJpaDao extends BaseJpaDao<Long, SmsUserThrottlingData> implements SmsUserThrottlingDataDao {

    public SmsUserThrottlingDataJpaDao() {
        super(SmsUserThrottlingData.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public SmsUserThrottlingData create(User user, String mobileNumber, String smsTypeName,
                                        Date currDate, Date startOfDay, Date endOfDay, Integer count) {
        SmsUserThrottlingData smsUserThrottlingData = new SmsUserThrottlingData();
        smsUserThrottlingData.setSmsTypeName(smsTypeName);
        smsUserThrottlingData.setBeginDuration(startOfDay);
        smsUserThrottlingData.setEndDuration(endOfDay);
        smsUserThrottlingData.setLastSentAt(currDate);
        smsUserThrottlingData.setMobileNumber(mobileNumber);
        smsUserThrottlingData.setSentCount(count);
        smsUserThrottlingData.setUser(user);
        return create(smsUserThrottlingData);
    }

    @Override
    public SmsUserThrottlingData findMatchingRecord(String mobileNumber, String smsTypeName, Date currDate) {
        Query query = entityManager.createNamedQuery("smsUserThrottlingDataFindMatchingRecord", SmsUserThrottlingData.class);
        query.setParameter("mobileNumber", mobileNumber);
        query.setParameter("smsTypeName", smsTypeName);
        query.setParameter("currDate", currDate);
        return (SmsUserThrottlingData) query.getSingleResult();
    }
}
