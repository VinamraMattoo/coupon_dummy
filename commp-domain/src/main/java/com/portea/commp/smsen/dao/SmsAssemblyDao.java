package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsAssembly;
import com.portea.dao.Dao;

import java.util.List;

public interface SmsAssemblyDao extends Dao<Long, SmsAssembly> {

    /**
     * Returns list containing SMS which need to be sent on or before specified seconds from now.
     * resultSize determines maximum results that can be fetched in one go.
     */
    List<SmsAssembly> getNextBatchFromAssembly(int seconds, int resultSize);
}
