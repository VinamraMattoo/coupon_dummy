package com.portea.commp.web.rapi.domain;


import com.portea.commp.smsen.domain.SmsGroup;
import com.portea.commp.smsen.domain.SmsType;

import java.util.Date;

public class SmsGroupVo {

    private Integer id;
    private SmsTypeData smsTypeData;
    private String name;
    private String description;
    private Integer priority;
    private Boolean isBulk;
    private CoolingPeriodVo contentMatchCoolingPeriod;
    private CoolingPeriodVo typeMatchCoolingPeriod;
    private String lastUpdatedBy;
    private Date lastUpdatedOn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsBulk() {
        return isBulk;
    }

    public void setIsBulk(Boolean isBulk) {
        this.isBulk = isBulk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public SmsTypeData getSmsTypeData() {
        return smsTypeData;
    }

    public void setSmsTypeData(SmsTypeData smsTypeData) {
        this.smsTypeData = smsTypeData;
    }

    public CoolingPeriodVo getTypeMatchCoolingPeriod() {
        return typeMatchCoolingPeriod;
    }

    public void setTypeMatchCoolingPeriod(CoolingPeriodVo typeMatchCoolingPeriod) {
        this.typeMatchCoolingPeriod = typeMatchCoolingPeriod;
    }

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

    public static SmsGroupVo build(SmsGroup smsGroup) {
        SmsGroupVo smsGroupVo = new SmsGroupVo();
        smsGroupVo.setId(smsGroup.getId());
        smsGroupVo.setSmsTypeData(SmsTypeData.build(smsGroup.getSmsType()));
        smsGroupVo.setContentMatchCoolingPeriod(CoolingPeriodVo.build(smsGroup.getContentMatchCoolingPeriod()));
        smsGroupVo.setDescription(smsGroup.getDescription());
        smsGroupVo.setIsBulk(smsGroup.isBulk());
        smsGroupVo.setName(smsGroup.getName());
        smsGroupVo.setPriority(smsGroup.getPriority());
        smsGroupVo.setTypeMatchCoolingPeriod(CoolingPeriodVo.build(smsGroup.getTypeMatchCoolingPeriod()));
        smsGroupVo.setLastUpdatedOn(smsGroup.getLastUpdatedOn());
        String username = (smsGroup.getLastUpdatedBy() == null) ? null : smsGroup.getLastUpdatedBy().getLogin();
        smsGroupVo.setLastUpdatedBy(username);
        return smsGroupVo;
    }

    public static class SmsTypeData {
        private Integer id;
        private String name;
        private String description;

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getId() {
            return id;
        }

        public SmsTypeData() {
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static SmsTypeData build(SmsType smsType) {
            SmsTypeData smsTypeData = new SmsTypeData();
            smsTypeData.setId(smsType.getId());
            smsTypeData.setName(smsType.getName());
            smsTypeData.setDescription(smsType.getDescription());
            return smsTypeData;
        }
    }
}
