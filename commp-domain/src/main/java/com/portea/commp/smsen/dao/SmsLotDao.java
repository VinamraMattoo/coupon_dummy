package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsLot;
import com.portea.commp.smsen.domain.SmsSender;
import com.portea.dao.Dao;

public interface SmsLotDao extends Dao<Integer, SmsLot> {

    SmsLot create(SmsSender smsSender);
}
