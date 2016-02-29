package com.portea.commp.smsen.gw;

/**
 * An interface to capture common attributes of Gateway Status
 */
public interface GatewaySmsStatusType {

    /**
     * Returns whether the type is a 'success' type.  If <code>false</code> then it is 'error/failure' type
     */
    boolean isSuccess();

}
