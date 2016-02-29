package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsSender;
import com.portea.commp.smsen.domain.SmsSenderAudit;
import com.portea.dao.Dao;

public interface SmsSenderAuditDao extends Dao<Integer, SmsSenderAudit> {

    SmsSenderAudit create(SmsSender smsSender);
}
