package com.portea.common.config.domain;

public enum ConfigSmsTypeParam implements ConfigTargetParam {

    DEFAULT_GROUP_NAME("DummyGroup4"),

    SMS_MAX_THROTTLING_COUNT("-1");

    private final String defaultValue;

    ConfigSmsTypeParam(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }
}
