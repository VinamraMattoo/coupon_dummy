package com.portea.commp.smsen.gw;

import com.portea.commp.smsen.domain.SmsGroupGatewayMapping;
import com.portea.commp.smsen.vo.SmsInAssembly;

import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * A manager to manage the collection of available gateways. This manager can be used to retrieve
 * an available gateway
 */
@Startup
@Singleton
public class SmsGatewayManager {

    public SmsGatewayManager() {}

    /**
     * Returns an SmsGatewayHandler that can be used by an SmsWorker. This method filters the gateways
     * currently reported as unavailable.
     *
     * @param smsGroupGatewayMapping the mapping based on which handler needs to be created
     * @return a SmsGatewayHandler
     */
    public SmsGatewayHandler getGatewayHandler(SmsGroupGatewayMapping smsGroupGatewayMapping) {
        // TODO This method creates a new instance of a SMS gateway handler based upon the mapping
        // provided
        return null;
    }

    public SmsGroupGatewayMapping getSmsGroupGatewayMapping(SmsInAssembly smsInAssembly) {
        // TODO Query the database and return a mapping that can be used
        // Filter out gateways which are current reported as not available
        return null;
    }
}
