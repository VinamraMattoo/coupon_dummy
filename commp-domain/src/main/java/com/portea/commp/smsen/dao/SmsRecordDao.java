package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsAssembly;
import com.portea.commp.smsen.domain.SmsPrimaryProcessingState;
import com.portea.commp.smsen.domain.SmsRecord;
import com.portea.commp.smsen.domain.SmsSecondaryProcessingState;
import com.portea.dao.Dao;

import java.util.Date;
import java.util.List;

public interface SmsRecordDao extends Dao<Long, SmsRecord> {

    void updateSmsStatus(List<Long> smsAssemblyIds, SmsPrimaryProcessingState primaryProcessingState,
                         SmsSecondaryProcessingState secondaryProcessingState);

    void updateGatewayStatus(SmsRecord smsRecord, String name);

    /**
     * Returns an aggregate information about source usage.
     */
    List<Object[]> getSmsSourceUsage();

    /**
     * Returns an aggregate information about gateway usage.
     */
    List<Object[]> getSmsGatewayUsage();


    List<SmsRecord> getRecords(Date fromDate, Date toDate);
}
