package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsType;
import com.portea.commp.smsen.domain.SmsTypeAudit;
import com.portea.dao.Dao;

public interface SmsTypeAuditDao extends Dao<Integer, SmsTypeAudit> {

    SmsTypeAudit create(SmsType smsType);
}
