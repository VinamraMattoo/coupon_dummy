package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsGroup;
import com.portea.commp.smsen.domain.SmsGroupAudit;
import com.portea.dao.Dao;

public interface SmsGroupAuditDao extends Dao<Integer, SmsGroupAudit> {

    SmsGroupAudit create(SmsGroup smsGroup);

}
