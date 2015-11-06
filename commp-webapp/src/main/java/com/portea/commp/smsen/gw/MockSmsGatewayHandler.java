package com.portea.commp.smsen.gw;

import com.portea.commp.smsen.domain.SmsAssembly;

import java.util.Date;

/**
 * A mock handler that can be used for testing the SMS engine flow. This mock implementation
 * will never report failure
 */
public class MockSmsGatewayHandler implements SmsGatewayHandler {

    public final static String GATEWAY_NAME = "DUMMY_GATEWAY_1";

    @Override
    public String submitSMS(SmsAssembly smsAssembly) {
        return GATEWAY_NAME + (new Date()).toString();
    }

    @Override
    public GatewaySmsStatus querySmsStatus(String correlationId) {
        // TODO Make this implementation return status randomly, either success or failure
        return MockSmsGatewayStatus.DELIVERED;
    }

    @Override
    public String getSmsGatewayName() {
        return GATEWAY_NAME;
    }

    @Override
    public boolean isSmsStatusTerminal(String smsStatusName) {

        boolean statusNameRecognized = false;

        for (MockSmsGatewayStatus status : MockSmsGatewayStatus.values()) {
            if (status.getName().equals(smsStatusName)) {
                statusNameRecognized = true;
                return status.isTerminal();
            }
        }

        if (false == statusNameRecognized) {
            throw new IllegalArgumentException("Status name not recognized");
        }

        return false;
    }
}