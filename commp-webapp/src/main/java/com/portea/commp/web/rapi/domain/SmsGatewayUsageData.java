package com.portea.commp.web.rapi.domain;

public class SmsGatewayUsageData {

    private String gatewayName;
    private Long count;

    public SmsGatewayUsageData() {
    }

    public String getGatewayName() {
        return gatewayName;
    }

    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
