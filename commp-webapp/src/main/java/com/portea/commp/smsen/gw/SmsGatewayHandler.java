package com.portea.commp.smsen.gw;

import com.portea.commp.smsen.domain.SmsAssembly;

/**
 * The interface implemented by all SMS Gateway handlers
 */
public interface SmsGatewayHandler {

    /**
     * Submits the SMS to the gateway to be sent
     * @return the correlation id of accepted SMS request
     */
    String submitSMS(SmsAssembly smsAssembly);

    /**
     * Queries the gateway for the status of an SMS already submitted
     * @return returns the status string
     */
    GatewaySmsStatus querySmsStatus(String correlationId);

    /**
     * Returns the name of the gateway that this handler works with
     */
    String getSmsGatewayName();

    /**
     * Returns whether sms status is final
     */
    boolean isSmsStatusTerminal(String smsStatusName);
}
