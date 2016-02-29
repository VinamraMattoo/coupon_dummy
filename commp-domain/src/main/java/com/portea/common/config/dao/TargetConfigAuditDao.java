package com.portea.common.config.dao;

import com.portea.common.config.domain.TargetConfigAudit;
import com.portea.common.config.domain.TargetConfigValue;
import com.portea.dao.Dao;

public interface TargetConfigAuditDao extends Dao<Integer,TargetConfigAudit> {

    TargetConfigAudit create(TargetConfigValue targetConfigValue, String preValue);
}
