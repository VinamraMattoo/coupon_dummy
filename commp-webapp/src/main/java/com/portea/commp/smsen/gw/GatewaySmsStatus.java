package com.portea.commp.smsen.gw;

/**
 * This interface is implemented by all Status Enums for various SMS gateways
 */
public interface GatewaySmsStatus {

    /**
     * Returns whether this status denotes a terminal state such as failure or a success
     */
    boolean isTerminal();

    /**
     * Returns the name of this status
     */
    String getName();

    /**
     * Returns the name of the gateway which will use this status
     */
    String getSmsGatewayName();

}
