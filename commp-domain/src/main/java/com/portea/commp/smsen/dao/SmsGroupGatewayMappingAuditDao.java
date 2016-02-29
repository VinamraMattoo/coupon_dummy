package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsGroupGatewayMapping;
import com.portea.commp.smsen.domain.SmsGroupGatewayMappingAudit;
import com.portea.dao.Dao;

public interface SmsGroupGatewayMappingAuditDao extends Dao<Integer, SmsGroupGatewayMappingAudit> {

    SmsGroupGatewayMappingAudit create(SmsGroupGatewayMapping mapping, Integer priority);
}
