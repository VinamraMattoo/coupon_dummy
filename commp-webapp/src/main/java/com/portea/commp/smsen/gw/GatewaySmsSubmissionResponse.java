package com.portea.commp.smsen.gw;

/**
 * Response from gateway when an SMS is submitted to be sent
 */
public class GatewaySmsSubmissionResponse extends AGatewayResponse {

    private String correlationId;
    private GssFailureType failureType;

    public GssFailureType getFailureType() {
        return failureType;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public void setFailureType(GssFailureType failureType) {
        this.failureType = failureType;
    }
}
