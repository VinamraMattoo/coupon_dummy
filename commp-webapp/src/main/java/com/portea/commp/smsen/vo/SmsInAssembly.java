package com.portea.commp.smsen.vo;

import com.portea.commp.smsen.domain.SmsAssembly;
import com.portea.commp.smsen.domain.SmsGroupGatewayMapping;
import com.portea.commp.smsen.engine.SmsQueuePriority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class captures information about an SMS, the handling for which is under progress.
 */
public class SmsInAssembly implements Comparable<SmsInAssembly> {

    private SmsAssembly smsAssembly;

    private List<SmsGroupGatewayMapping> usedGatewayMappings;

    private SmsQueuePriority currentSmsQueuePriority;

    public SmsInAssembly() {
        /*
          A small number '3' is used as it is not expected that on an average more than 3
          gateways will be used in the life of an SMS
         */
        usedGatewayMappings = new ArrayList<>(3);
    }

    /**
     * Any server component that updates SmsAssembly should also update the reference in this
     * object. This is to ensure that the SmsAssembly data is always up-to-date and can be used
     * by a server component
     * @param smsAssembly the reference of the updated SmsAssembly object which is in sync with the
     *                    database
     */
    public void updateSmsAssembly(SmsAssembly smsAssembly) {
        this.smsAssembly = smsAssembly;
    }

    public SmsAssembly getSmsAssembly() {
        return smsAssembly;
    }


    @Override
    public int compareTo(SmsInAssembly o) {
        return -999; // TODO Implement this method
    }

    /**
     * Returns the name of the latest used gateway
     * @return Gateway name
     */
    public SmsGroupGatewayMapping getLatestUsedGatewayMapping() {
        return usedGatewayMappings.isEmpty() ? null :
                usedGatewayMappings.get(usedGatewayMappings.size() - 1);
    }

    /**
     * Adds name of a gateway to the list
     * @param smsGroupGatewayMapping the sms-group gateway mapping to be used
     */
    public void addUsedGatewayMapping(SmsGroupGatewayMapping smsGroupGatewayMapping) {
        usedGatewayMappings.add(smsGroupGatewayMapping);
    }

    /**
     * Returns unmodifiable version of the current list of names of used gateways
     * @return list of gateway names
     */
    public List<SmsGroupGatewayMapping> getUsedGatewayMappings() {
        return Collections.unmodifiableList(usedGatewayMappings);
    }
}