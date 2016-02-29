package com.portea.cpnen.domain;

public enum UserRole {

    COUPON_MANAGER_SALES("Coupon Manager Sales"),

    COUPON_MANAGER_OPS("Coupon Manager Ops"),

    COUPON_MANAGER_MARKETING("Coupon Manager Marketing"),

    COUPON_MANAGER_ENGAGEMENT("Coupon Manager Engagement"),

    COUPON_ADMIN("Coupon Admin");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
