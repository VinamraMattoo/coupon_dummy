package com.portea.commp.smsen.gw;

public enum MockSmsGatewayStatus implements GatewaySmsStatus {

    MOCK_IN_PROCESS("Mock In Process", false, GssSuccessType.SUCCESS_IN_PROGRESS),
    MOCK_FAILED("Mock Failed", true, GssFailureType.FAILURE_GENERAL),
    MOCK_DELIVERED("Mock Completed", true, GssSuccessType.SUCCESS_COMPLETED);

    private final String name;
    private final boolean terminal;
    private final GatewaySmsStatusType type;

    MockSmsGatewayStatus(String name, boolean terminal, GatewaySmsStatusType type) {
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