package com.portea.commp.smsen.gw;

public enum MockSmsGatewayStatus implements GatewaySmsStatus {

    IN_PROCESS("In Process", "DUMMY_GATEWAY_1", false),
    FAILED("Failed", "DUMMY_GATEWAY_1", true),
    DELIVERED("Completed", "DUMMY_GATEWAY_1", true);

    private final String name;
    private final String smsGatewayName;
    private final boolean terminal;

    MockSmsGatewayStatus(String name, String smsGatewayName, boolean terminal) {
        this.name = name;
        this.smsGatewayName = smsGatewayName;
        this.terminal = terminal;
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
        return smsGatewayName;
    }
}