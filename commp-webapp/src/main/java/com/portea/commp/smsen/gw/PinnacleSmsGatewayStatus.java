package com.portea.commp.smsen.gw;

public enum PinnacleSmsGatewayStatus implements GatewaySmsStatus {

    //success
    DELIVERED("DELIVRD", true, GssSuccessType.SUCCESS_COMPLETED),

    //intermediate
    PENDING("PENDING", false, GssSuccessType.SUCCESS_IN_PROGRESS),
    HTTP_NOT_OK("Http not ok", false, GssFailureType.FAILURE_CARRIER),
    INVALID_SENDER_ID("Invalid senderid", false, GssFailureType.FAILURE_SYNTAX),
    INVALID_USER_PASSWORD("Authentication Failed", false, GssFailureType.FAILURE_SYNTAX),
    INVALID_ACCOUNT_TYPE("invalid account type", false, GssFailureType.FAILURE_SYNTAX),

    //failure
    DND("NCPR", true, GssFailureType.FAILURE_DND),
    EXPIRED("EXPIRED", true, GssFailureType.FAILURE_EXPIRED),
    UNDELIVERED("UNDELIV", true, GssFailureType.FAILURE_UNDELIVERED),
    NO_PRIVILEGE("No Privilege", true, GssFailureType.FAILURE_REJECTED),
    UNKNOWN("Sorry unable to process", true, GssFailureType.FAILURE_CARRIER),
    ACCOUNT_EXPIRED("Account Expired", true, GssFailureType.FAILURE_REJECTED),
    ACCOUNT_DEACTIVATED("account deactivated", true, GssFailureType.FAILURE_REJECTED),
    IN_SUFFICIENT_BALANCE("Insufficent balance", true, GssFailureType.FAILURE_REJECTED),

    ;

    private final String name;
    private final boolean terminal;
    private final GatewaySmsStatusType type;

    PinnacleSmsGatewayStatus(String name, boolean terminal, GatewaySmsStatusType type) {
        this.name = name;
        this.terminal = terminal;
        this.type = type;
    }

    @Override
    public boolean isTerminal() {
        return terminal;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSmsGatewayName() {
        return PinnacleSmsGatewayHandler.SMS_GATEWAY_NAME;
    }

    public GatewaySmsStatusType getType(){
        return type;
    }

    @Override
    public boolean isSuccess() {
        return getType().isSuccess();
    }
}
