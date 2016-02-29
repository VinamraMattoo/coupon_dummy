package com.portea.commp.smsen.gw;

import java.util.Map;

public class MgagePrioritySmsGatewayHandler extends MgageSmsGatewayHandler {

    public static final String SMS_GATEWAY_NAME = "MGAGE_PRIORITY";

    public MgagePrioritySmsGatewayHandler(Map<String, String> config) {
        super(config);
    }

    @Override
    public String getSmsGatewayName() {
        return SMS_GATEWAY_NAME;
    }

    @Override
    protected GatewaySmsStatus findGatewaySmsStatus(String statusName) {
        for(GatewaySmsStatus gatewaySmsStatus : MgagePrioritySmsGatewayStatus.values()){
            if(gatewaySmsStatus.getName().equals(statusName)){
                return gatewaySmsStatus;
            }
        }
        return null;
    }

    @Override
    public Integer getSmsSubmissionCount() {
        return null;
    }

}