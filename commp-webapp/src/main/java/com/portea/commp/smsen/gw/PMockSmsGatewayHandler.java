package com.portea.commp.smsen.gw;

import com.portea.commp.smsen.domain.SmsAssembly;
import com.portea.commp.smsen.util.ConnectionHolder;
import com.portea.commp.smsen.util.ConnectionUtil;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A Http connection mock handler that can be used for testing the SMS engine flow.
 */
public class PMockSmsGatewayHandler extends PinnacleSmsGatewayHandler implements SmsGatewayHandler {

    public static final String SMS_GATEWAY_NAME = "DUMMY_GATEWAY_2";

    private static AtomicInteger submissionCount = new AtomicInteger(0);

    public PMockSmsGatewayHandler(Map<String, String> config) {
        super(config);
    }

    @Override
    public ConnectionHolder getSubmissionConnectionHolder(SmsAssembly smsAssembly) {
        Integer currentCount = submissionCount.incrementAndGet();

        try {
            return ConnectionUtil.getConnectionReader(sendSmsUrl + "&count=" + currentCount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ConnectionHolder getStatusCheckConnectionHolder(String correlationId) {

        String smsStatusURL = pollUrl;
        try {
            return ConnectionUtil.getConnectionReader(smsStatusURL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected GatewaySmsStatus findGatewayStatus(String statusResponse) {
        for (GatewaySmsStatus gatewaySmsStatus : PMockSmsGatewayStatus.values()) {
            if (statusResponse.contains(gatewaySmsStatus.getName())) {
                return gatewaySmsStatus;
            }
        }
        return null;
    }

    @Override
    public String getSmsGatewayName() {
        return SMS_GATEWAY_NAME;
    }

    @Override
    public void resetSmsSubmissionCount() {
        submissionCount.set(0);
    }

    @Override
    public Integer getSmsSubmissionCount() {
        return submissionCount.intValue();
    }
}
