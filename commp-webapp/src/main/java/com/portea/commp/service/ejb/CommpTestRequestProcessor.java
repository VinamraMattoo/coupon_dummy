package com.portea.commp.service.ejb;

import com.portea.commp.service.domain.SubmittedSmsVo;

import javax.ejb.Local;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Local
public interface CommpTestRequestProcessor {

    /**
     * Creates a batch of test SmsQueue objects. This method attempts to create SMSs of various
     * @param count the number of SMS to be created
     * @param groupName the name of the group to which the generated SMS should belong to
     * @param phoneNumber the phone number to which these SMS will be delivered
     * @param receiverType
     * @param message
     * @param brandName
     * @param appendTimeStampToMessage
     * @param login
     */
    Response createSmsBatch(final int count, String groupName, String phoneNumber,
                            Integer userId, String templateName, String receiverType,
                            String message, String brandName, Boolean appendTimeStampToMessage, String login);

    String getTargetConfigValue(String targetTypeName, String configParam, Integer targetId);

    String submitPMockSms(Integer count, Boolean error);

    String getPMockSmsStatus(String status, Boolean error);

    List<String> getSmsGroupForNameMatch(String name, Integer limit);

    List<String> getUserForLoginNameMatch(String name, Integer limit);

    SubmittedSmsVo getSubmittedSms(Long fromDate, Long tillDate, Boolean detailed);
}
