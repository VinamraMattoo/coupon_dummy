package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsQueue;
import com.portea.dao.Dao;

import java.util.List;

public interface SmsQueueDao extends Dao<Integer, SmsQueue> {

    /**
     * Returns list containing SMS which need to be sent on or before specified seconds from now.
     * resultSize determines maximum results that can be fetched in one go.
     */
    List<SmsQueue> getNextBatchFromQueue(int seconds, int resultSize);

}
