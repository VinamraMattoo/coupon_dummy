package com.portea.commp.smsen.gw;

public enum MgageSmsGatewayStatus implements GatewaySmsStatus {

    //success
    DELIVERED("DELIVRD", true, GssSuccessType.SUCCESS_COMPLETED),

    //intermediate
    UNKNOWN("UNKNOWN", false, GssUnknownType.UNKNOWN),
    NOT_APPLICABLE("N/A", false, GssUnknownType.UNKNOWN),
    ACCEPTED("ACCEPTD", false, GssSuccessType.SUCCESS_IN_PROGRESS),
    HTTP_NOT_OK("Http not ok", false, GssFailureType.FAILURE_CARRIER),
    DOMAIN_NAME_NOT_FOUND("1004", false, GssFailureType.FAILURE_CARRIER),
    INVALID_USER_PASSWORD("1001", false, GssFailureType.FAILURE_SYNTAX),
    INVALID_QUERY_PARAMETERS("1005", false, GssFailureType.FAILURE_SYNTAX),
    INVALID_ACKNOWLEDGEMENT_ID("1000", false, GssFailureType.FAILURE_SYNTAX),
    SYSTEM_ERROR("1002", false, GssFailureType.FAILURE_CARRIER),

    //failure
    EXPIRED("EXPIRED", true, GssFailureType.FAILURE_EXPIRED),
    DELETED("DELETED", true, GssFailureType.FAILURE_DELETED),
    REJECTED("REJECTD", true, GssFailureType.FAILURE_REJECTED),
    UNDELIVERED("UNDELIV", true, GssFailureType.FAILURE_UNDELIVERED),

    ;

    private final String name;
    private final boolean terminal;
    private final GatewaySmsStatusType type;

    MgageSmsGatewayStatus(String name, Boolean terminal, GatewaySmsStatusType type){
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
        return MgageSmsGatewayHandler.SMS_GATEWAY_NAME;
    }

    @Override
    public GatewaySmsStatusType getType(){
        return type;
    }

    @Override
    public boolean isSuccess() {
        return getType().isSuccess();
    }
}
