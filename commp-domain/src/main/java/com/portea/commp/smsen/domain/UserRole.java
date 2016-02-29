package com.portea.commp.smsen.domain;

public enum UserRole {

    SMS_MANAGER("Sms manager");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
