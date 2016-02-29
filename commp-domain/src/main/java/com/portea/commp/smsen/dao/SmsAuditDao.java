package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsAudit;
import com.portea.commp.smsen.domain.SmsRecord;
import com.portea.dao.Dao;

import java.util.Date;

public interface SmsAuditDao extends Dao<Integer, SmsAudit> {

    SmsAudit create(SmsRecord record);

    SmsAudit create(SmsRecord record, String responseCode, String responseMessage);

    Long getSubmittedSms(Date fromDate, Date tillDate);

    Object[] getSubmittedSmsStartingTime(Date from, Date till);

    Object[] getSubmittedSmsEndingTime(Date from, Date till);
}
