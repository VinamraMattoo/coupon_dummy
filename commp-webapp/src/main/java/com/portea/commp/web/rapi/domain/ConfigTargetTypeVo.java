package com.portea.commp.web.rapi.domain;

import com.portea.commp.smsen.domain.ConfigTargetType;

public class ConfigTargetTypeVo {

    private Integer id;
    private ConfigTargetType targetType;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ConfigTargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(ConfigTargetType targetType) {
        this.targetType = targetType;
    }

    public static ConfigTargetTypeVo build(com.portea.common.config.domain.ConfigTargetType configTargetType) {
        ConfigTargetTypeVo configTargetTypeVo = new ConfigTargetTypeVo();
        configTargetTypeVo.setId(configTargetType.getId());
        configTargetTypeVo.setDescription(configTargetType.getDescription());
        configTargetTypeVo.setTargetType(configTargetType.getTargetType());
        return configTargetTypeVo;
    }
}
