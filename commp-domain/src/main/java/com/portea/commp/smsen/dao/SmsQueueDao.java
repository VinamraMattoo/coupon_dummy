package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsQueue;
import com.portea.dao.Dao;

import java.util.List;

public interface SmsQueueDao extends Dao<Long, SmsQueue> {

    /**
     * Returns list containing SMS which need to be sent on or before specified milliseconds from now.
     * resultSize determines maximum results that can be fetched in one go.
     */
    List<SmsQueue> getNextBatchFromQueue(int milliSec, int resultSize);

}
