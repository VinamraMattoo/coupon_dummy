package com.portea.commp.smsen.gw;

/**
 * Captures a response received from the gateway
 */
public abstract class AGatewayResponse {

    private String responseCode;
    private String responseMessage;
    private boolean error;

    public AGatewayResponse() {}

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
