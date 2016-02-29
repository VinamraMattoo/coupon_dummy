package com.portea.commp.web.rapi.domain;

import com.portea.commp.smsen.domain.SmsType;

import java.util.Date;

public class SmsTypeVo {

    private Integer id;
    private String name;
    private String description;
    private CoolingPeriodVo contentMatchCoolingPeriod;
    private CoolingPeriodVo typeMatchCoolingPeriod;
    private String lastUpdatedBy;
    private Date lastUpdatedOn;

    public CoolingPeriodVo getContentMatchCoolingPeriod() {
        return contentMatchCoolingPeriod;
    }

    public void setContentMatchCoolingPeriod(CoolingPeriodVo contentMatchCoolingPeriod) {
        this.contentMatchCoolingPeriod = contentMatchCoolingPeriod;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoolingPeriodVo getTypeMatchCoolingPeriod() {
        return typeMatchCoolingPeriod;
    }

    public void setTypeMatchCoolingPeriod(CoolingPeriodVo typeMatchCoolingPeriod) {
        this.typeMatchCoolingPeriod = typeMatchCoolingPeriod;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Date lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public static SmsTypeVo build(SmsType smsType) {
        SmsTypeVo smsTypeVo = new SmsTypeVo();
        smsTypeVo.setId(smsType.getId());
        smsTypeVo.setTypeMatchCoolingPeriod(CoolingPeriodVo.build(smsType.getTypeMatchCoolingPeriod()));
        smsTypeVo.setName(smsType.getName());
        smsTypeVo.setContentMatchCoolingPeriod(CoolingPeriodVo.build(smsType.getContentMatchCoolingPeriod()));
        smsTypeVo.setDescription(smsType.getDescription());
        String username = (smsType.getLastUpdatedBy() == null) ? null : smsType.getLastUpdatedBy().getLogin();
        smsTypeVo.setLastUpdatedBy(username);
        smsTypeVo.setLastUpdatedOn(smsType.getLastUpdatedOn());
        return smsTypeVo;
    }
}
