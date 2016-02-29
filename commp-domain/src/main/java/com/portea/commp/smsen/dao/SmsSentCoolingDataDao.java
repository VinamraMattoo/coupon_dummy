package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsSentCoolingData;
import com.portea.dao.Dao;

import java.util.List;

public interface SmsSentCoolingDataDao extends Dao<Integer, SmsSentCoolingData> {

    List<SmsSentCoolingData> getCoolingPeriodData(Integer userId, String mobileNumber, Integer messageHash, String smsTypeName);

    Integer createWhenNotExists(SmsSentCoolingData smsSentCoolingData, String sentAt, String smsTypeExpires, String msgContentExpires);
}
