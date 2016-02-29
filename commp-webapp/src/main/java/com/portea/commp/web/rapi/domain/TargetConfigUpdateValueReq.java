package com.portea.commp.web.rapi.domain;

public class TargetConfigUpdateValueReq {

    private Integer targetId;
    private Integer configParamId;
    private String value;

    public Integer getConfigParamId() {
        return configParamId;
    }

    public void setConfigParamId(Integer configParamId) {
        this.configParamId = configParamId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
