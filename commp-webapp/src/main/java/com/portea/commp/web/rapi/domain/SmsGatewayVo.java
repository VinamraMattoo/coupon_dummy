package com.portea.commp.web.rapi.domain;

import com.portea.commp.smsen.domain.GatewayStatus;
import com.portea.commp.smsen.domain.SmsGateway;

public class SmsGatewayVo {
    private Integer id;
    private String name;
    private GatewayStatus status;

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

    public GatewayStatus getStatus() {
        return status;
    }

    public void setStatus(GatewayStatus status) {
        this.status = status;
    }

    public static SmsGatewayVo build(SmsGateway smsGateway) {
        SmsGatewayVo smsGatewayVo = new SmsGatewayVo();
        smsGatewayVo.setId(smsGateway.getId());
        smsGatewayVo.setName(smsGateway.getName());
        smsGatewayVo.setStatus(smsGateway.getStatus());
        return smsGatewayVo;
    }
}
