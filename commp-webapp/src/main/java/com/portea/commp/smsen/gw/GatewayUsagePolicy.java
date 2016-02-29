package com.portea.commp.smsen.gw;

public enum GatewayUsagePolicy {

    CYCLE_AVAILABLE,

    RETRY_UNTIL_UNAVAILABLE

    ;


    /**
     * Finds a gateway policy that matches the input name. If it the name does not match
     * with any of the existing policies by default cycle available is returned
     */
    public static GatewayUsagePolicy find (String name) {
        GatewayUsagePolicy[] gatewayUsagePolicies = GatewayUsagePolicy.values();
        for (GatewayUsagePolicy gatewayUsagePolicy : gatewayUsagePolicies) {
            if (gatewayUsagePolicy.name().equals(name)) {
                return gatewayUsagePolicy;
            }
        }
        return CYCLE_AVAILABLE;
    }
}
