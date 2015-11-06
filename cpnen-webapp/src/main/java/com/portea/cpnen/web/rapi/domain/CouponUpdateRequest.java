package com.portea.cpnen.web.rapi.domain;

import com.portea.cpnen.domain.CouponApplicationType;

import java.util.Date;

public class CouponUpdateRequest {

    private String name;
    private CouponApplicationType applicationType;
    private Date applicableFrom;
    private Date applicableTill;
    private Integer transactionMaxVal;
    private Integer transactionMinVal;
    private String description;
    private Boolean inclusive;
    private String channelName;
    private Integer applicableUseCount;
    private String type;
    private Boolean published;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CouponApplicationType getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(CouponApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public Date getApplicableFrom() {
        return applicableFrom;
    }

    public void setApplicableFrom(Date applicableFrom) {
        this.applicableFrom = applicableFrom;
    }

    public Date getApplicableTill() {
        return applicableTill;
    }

    public void setApplicableTill(Date applicableTill) {
        this.applicableTill = applicableTill;
    }

    public Integer getTransactionMaxVal() {
        return transactionMaxVal;
    }

    public void setTransactionMaxVal(Integer transactionMaxVal) {
        this.transactionMaxVal = transactionMaxVal;
    }

    public Integer getTransactionMinVal() {
        return transactionMinVal;
    }

    public void setTransactionMinVal(Integer transactionMinVal) {
        this.transactionMinVal = transactionMinVal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getInclusive() {
        return inclusive;
    }

    public void setInclusive(Boolean inclusive) {
        this.inclusive = inclusive;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getApplicableUseCount() {
        return applicableUseCount;
    }

    public void setApplicableUseCount(Integer applicableUseCount) {
        this.applicableUseCount = applicableUseCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }
}
