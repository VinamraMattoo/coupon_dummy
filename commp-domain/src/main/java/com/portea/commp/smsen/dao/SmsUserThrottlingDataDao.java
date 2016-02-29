package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsUserThrottlingData;
import com.portea.commp.smsen.domain.User;
import com.portea.dao.Dao;

import java.util.Date;

public interface SmsUserThrottlingDataDao extends Dao<Long, SmsUserThrottlingData> {

    SmsUserThrottlingData create(User user, String mobileNumber, String smsTypeName,
                                 Date currDate, Date startOfDay, Date endOfDay, Integer count);

    SmsUserThrottlingData findMatchingRecord(String mobileNumber, String smsTypeName, Date currDate);
}
