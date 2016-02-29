package com.portea.commp.web.rapi.domain;

import com.portea.commp.smsen.domain.SmsSender;

import java.util.Date;

public class SmsSenderVo {

    private Integer id;
    private String name;
    private String description;
    private String email;
    private Boolean active;
    private Date registeredOn;
    private Date lastUpdatedOn;
    private String lastUpdatedBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Date registeredOn) {
        this.registeredOn = registeredOn;
    }

    public Date getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Date lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public static SmsSenderVo build(SmsSender smsSender) {
        SmsSenderVo smsSenderVo = new SmsSenderVo();
        smsSenderVo.setId(smsSender.getId());
        smsSenderVo.setName(smsSender.getName());
        smsSenderVo.setDescription(smsSender.getDescription());

        smsSenderVo.setEmail(smsSender.getEmail());
        smsSenderVo.setActive(smsSender.getActive());
        String updatedBy = (smsSender.getLastUpdatedBy() == null) ? null : smsSender.getLastUpdatedBy().getLogin();

        smsSenderVo.setLastUpdatedBy(updatedBy);
        smsSenderVo.setLastUpdatedOn(smsSender.getLastUpdatedOn());
        smsSenderVo.setRegisteredOn(smsSender.getRegisteredOn());
        return smsSenderVo;
    }
}
