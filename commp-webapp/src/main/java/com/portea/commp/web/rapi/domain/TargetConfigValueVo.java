package com.portea.commp.web.rapi.domain;

import com.portea.common.config.domain.TargetConfigValue;

public class TargetConfigValueVo {

    private ConfigTargetTypeVo configTargetTypeVo;
    private ConfigParamVo configParamVo;
    private String targetName;
    private String value;
    private Integer targetId;

    public ConfigParamVo getConfigParamVo() {
        return configParamVo;
    }

    public void setConfigParamVo(ConfigParamVo configParamVo) {
        this.configParamVo = configParamVo;
    }

    public ConfigTargetTypeVo getConfigTargetTypeVo() {
        return configTargetTypeVo;
    }

    public void setConfigTargetTypeVo(ConfigTargetTypeVo configTargetTypeVo) {
        this.configTargetTypeVo = configTargetTypeVo;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public static TargetConfigValueVo build(TargetConfigValue targetConfigValue, String targetName) {
        TargetConfigValueVo targetConfigValueVo = new TargetConfigValueVo();
        targetConfigValueVo.setConfigParamVo(ConfigParamVo.build(targetConfigValue.getTargetConfig().getConfigParam()));
        targetConfigValueVo.setConfigTargetTypeVo(ConfigTargetTypeVo.build(targetConfigValue.getTargetConfig().getConfigParam().getConfigTargetType()));
        targetConfigValueVo.setTargetName(targetName);
        targetConfigValueVo.setTargetId(targetConfigValue.getTargetConfig().getTargetId());
        targetConfigValueVo.setValue(targetConfigValue.getValue());
        return targetConfigValueVo;
    }
}
