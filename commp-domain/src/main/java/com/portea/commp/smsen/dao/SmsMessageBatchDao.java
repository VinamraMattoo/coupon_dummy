package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsLot;
import com.portea.commp.smsen.domain.SmsMessageBatch;
import com.portea.commp.smsen.domain.SmsType;
import com.portea.dao.Dao;

public interface SmsMessageBatchDao extends Dao<Integer, SmsMessageBatch> {

    SmsMessageBatch create(SmsLot smsLot, SmsType smsType);
}
