package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsMessageBatch;
import com.portea.commp.smsen.domain.SmsMessageBatchRecordMapping;
import com.portea.commp.smsen.domain.SmsRecord;
import com.portea.dao.Dao;

import java.util.List;

public interface SmsMessageBatchRecordMappingDao extends Dao<Long, SmsMessageBatchRecordMapping> {

    SmsMessageBatchRecordMapping create(SmsMessageBatch messageBatch, SmsRecord smsRecord);

    List<SmsMessageBatchRecordMapping> find(List<Integer> messageBatchIds);

    List<SmsMessageBatchRecordMapping> getMappings(Integer lotId);
}
