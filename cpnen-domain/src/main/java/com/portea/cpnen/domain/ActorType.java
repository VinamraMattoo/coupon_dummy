package com.portea.cpnen.domain;

public enum ActorType {

    /**
     * Staff denotes a registered user who is a portea employee. Coupon with this actor type can only be applied by
     * staff members.
     */
    STAFF("staff"),

    /**
     * Customer denotes a registered user who purchases services from portea.
     */
    CUSTOMER("customer"),

    ;

    /**
     * This attribute is mapped to the value given in existing database table (auth_users -> type).
     */
    private final String name;

    ActorType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
