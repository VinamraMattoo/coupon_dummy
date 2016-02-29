package com.portea.commp.smsen.gw;

import com.portea.commp.smsen.domain.SmsAssembly;
import com.portea.commp.smsen.util.ConnectionHolder;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * The interface implemented by all SMS Gateway handlers
 */
public interface SmsGatewayHandler {

    /**
     * Submits the SMS to the gateway to be sent.
     * Returns a connection holder, which contains
     * connection object and the input stream
     * inside buffered reader
     */
    ConnectionHolder getSubmissionConnectionHolder(SmsAssembly smsAssembly);

    ConnectionHolder getStatusCheckConnectionHolder(String correlationId);

    /**
     * Queries the gateway for the status of an SMS already submitted
     */
    default GatewaySmsStatusCheckResponse readStatusResponse(ConnectionHolder holder) throws IOException {
        HttpURLConnection connection = holder.getConnection();

        int responseCode = connection.getResponseCode();
        if(responseCode != HttpURLConnection.HTTP_OK) {

            GatewaySmsStatusCheckResponse response = new GatewaySmsStatusCheckResponse();
            response.setResponseCode(String.valueOf(connection.getResponseCode()));

            return response;
        }

        return null;
    }

    /**
     * Returns the name of the gateway that this handler works with
     */
    String getSmsGatewayName();

    /**
     * Returns sms submission response.
     */
    default GatewaySmsSubmissionResponse readSubmissionResponse(ConnectionHolder holder) throws IOException {

        HttpURLConnection connection = holder.getConnection();

        if(connection.getResponseCode() != HttpURLConnection.HTTP_OK) {

            GatewaySmsSubmissionResponse gatewaySmsSubmissionResponse = new GatewaySmsSubmissionResponse();
            gatewaySmsSubmissionResponse.setFailureType(GssFailureType.FAILURE_GENERAL);
            gatewaySmsSubmissionResponse.setError(true);

            gatewaySmsSubmissionResponse.setResponseCode(String.valueOf(connection.getResponseCode()));

            return gatewaySmsSubmissionResponse;
        }

        return null;
    }

    void resetSmsSubmissionCount();

    Integer getSmsSubmissionCount();
}
