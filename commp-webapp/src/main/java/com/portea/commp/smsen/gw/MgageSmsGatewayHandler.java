package com.portea.commp.smsen.gw;

import com.portea.commp.smsen.domain.SmsAssembly;

public class MgageSmsGatewayHandler implements SmsGatewayHandler {

    @Override
    public String submitSMS(SmsAssembly smsAssembly) {
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public GatewaySmsStatus querySmsStatus(String correlationId) {
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public String getSmsGatewayName() {
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public boolean isSmsStatusTerminal(String smsStatusName) {
        throw new RuntimeException("Not implemented yet.");
    }
}
