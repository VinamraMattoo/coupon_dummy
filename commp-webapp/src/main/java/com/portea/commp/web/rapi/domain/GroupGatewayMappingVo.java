package com.portea.commp.web.rapi.domain;

import com.portea.commp.smsen.domain.SmsGroupGatewayMapping;

public class GroupGatewayMappingVo {

    private Integer id;
    private SmsGroupVo smsGroupVo;
    private SmsGatewayVo smsGatewayVo;
    private Integer priority;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public SmsGatewayVo getSmsGatewayVo() {
        return smsGatewayVo;
    }

    public void setSmsGatewayVo(SmsGatewayVo smsGatewayVo) {
        this.smsGatewayVo = smsGatewayVo;
    }

    public SmsGroupVo getSmsGroupVo() {
        return smsGroupVo;
    }

    public void setSmsGroupVo(SmsGroupVo smsGroupVo) {
        this.smsGroupVo = smsGroupVo;
    }

    public static GroupGatewayMappingVo build(SmsGroupGatewayMapping smsGroupGatewayMapping) {
        GroupGatewayMappingVo groupGatewayMappingVo = new GroupGatewayMappingVo();
        groupGatewayMappingVo.setId(smsGroupGatewayMapping.getId());
        groupGatewayMappingVo.setPriority(smsGroupGatewayMapping.getPriority());
        groupGatewayMappingVo.setSmsGatewayVo(SmsGatewayVo.build(smsGroupGatewayMapping.getSmsGateway()));
        groupGatewayMappingVo.setSmsGroupVo(SmsGroupVo.build(smsGroupGatewayMapping.getSmsGroup()));
        return groupGatewayMappingVo;
    }
}
