package com.portea.commp.smsen.gw;

/**
 * Response from gateway when status check is requested for a submitted SMS
 */
public class GatewaySmsStatusCheckResponse extends AGatewayResponse {

    private GatewaySmsStatus status;

    public GatewaySmsStatusCheckResponse() {}

    public GatewaySmsStatus getStatus() {
        return status;
    }

    public void setStatus(GatewaySmsStatus status) {
        this.status = status;
    }
}
