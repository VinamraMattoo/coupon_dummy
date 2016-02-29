package com.portea.common.config.domain;

public enum  ConfigGatewayParam implements ConfigTargetParam{

    USERNAME,   // change to gw access username ..

    /**
     * Password identifier used to send the message.
     */
    USER_CREDENTIAL_PWD,  // change to gw access password ..

    /**
     * url for sending sms.
     */
    ENDPOINT_URL_SUBMISSION,

    /**
     * url for getting status of sms that is been sent to a gateway.
     */
    ENDPOINT_URL_POLLING,

    INACTIVE_PERIOD_END

}
