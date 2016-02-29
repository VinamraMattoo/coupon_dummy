package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsAssembly;
import com.portea.commp.smsen.domain.SmsPrimaryProcessingState;
import com.portea.commp.smsen.domain.SmsSecondaryProcessingState;
import com.portea.dao.Dao;

import java.util.List;

public interface SmsAssemblyDao extends Dao<Long, SmsAssembly> {

    /**
     * Returns list containing SMS which need to be sent on or before specified milli seconds from now.
     * resultSize determines maximum results that can be fetched in one go.
     */
    List<SmsAssembly> getNextBatchFromAssembly(int milliSec, int resultSize);

    void updateSmsStatus(List<Long> smsAssemblyIds, SmsPrimaryProcessingState primaryProcessingState, SmsSecondaryProcessingState secondaryProcessingState);

    /**
     * Returns a list of sms that are to be loaded for status check.
     * @limit defines maximum sms that can be loaded.
     */
    List<SmsAssembly> getNextSmsBatchForStatusCheck(Integer limit);

}
