package com.portea.commp.smsen.gw;

public enum PMockSmsGatewayStatus implements GatewaySmsStatus {

    PMOCK_ACCEPTED("PMock-In-accepted", false, GssSuccessType.SUCCESS_IN_PROGRESS),
    PMOCK_IN_PROCESS("PMock-In-Process", false, GssSuccessType.SUCCESS_IN_PROGRESS),
    PMOCK_FAILED("PMock-Failed", true, GssFailureType.FAILURE_GENERAL),
    PMOCK_DELIVERED("PMock-Completed", true, GssSuccessType.SUCCESS_COMPLETED),
    PMOCK_HTTP_NOT_OK("PMock-Http-not-ok", false, GssFailureType.FAILURE_CARRIER);

    private final String name;
    private final boolean terminal;
    private final GatewaySmsStatusType type;

    PMockSmsGatewayStatus(String name, boolean terminal, GatewaySmsStatusType type) {
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
        return MockSmsGatewayHandler.GATEWAY_NAME;
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