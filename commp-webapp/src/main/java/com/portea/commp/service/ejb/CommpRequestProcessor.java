package com.portea.commp.service.ejb;

import com.portea.commp.service.domain.*;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CommpRequestProcessor {

    void queueDirectSms(SmsAssemblyVo smsAssembly);

    void resetSmsSubmissionCount(String gatewayName);

    Integer getSmsSubmissionCount(String gatewayName);

    SendSmsResponse queueSms(SendSmsRequest sendSmsRequest);

    SmsStatusResponse getSmsStatus(String id);

    BatchStatusResponse getBatchStatus(String id);

    LotStatusResponse getLotStatus(String id);

    BatchFailureResponse getBatchFailureStatus(String id);
}
