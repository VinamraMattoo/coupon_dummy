package com.portea.commp.smsen.domain;

/**
 * Defines constants to depict various states of a gateway.
 */
public enum GatewayStatus {

    /**
     * Gateway is available and reachable.
     */
    ACTIVE,

    /**
     * Gateway is available but not reachable.
     */
    UNREACHABLE,

    /**
     * Gateway is not available and not reachable.
     */
    INACTIVE

}
