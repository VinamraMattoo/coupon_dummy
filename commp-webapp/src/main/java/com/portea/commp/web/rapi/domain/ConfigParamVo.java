package com.portea.commp.web.rapi.domain;

import com.portea.common.config.domain.ConfigParam;
import com.portea.common.config.domain.ConfigParamValueDataType;

public class ConfigParamVo {

    private Integer id;
    private String name;
    private String description;
    private ConfigParamValueDataType valueDataType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConfigParamValueDataType getValueDataType() {
        return valueDataType;
    }

    public void setValueDataType(ConfigParamValueDataType valueDataType) {
        this.valueDataType = valueDataType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static ConfigParamVo build(ConfigParam configParam) {
        ConfigParamVo configParamVo = new ConfigParamVo();
        configParamVo.setId(configParam.getId());
        configParamVo.setName(configParam.getName());
        configParamVo.setValueDataType(configParam.getValueDataType());
        configParamVo.setDescription(configParam.getDescription());
        return configParamVo;
    }
}
