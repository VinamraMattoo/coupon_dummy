package com.portea.cpnen.web.rapi.domain;

import com.portea.cpnen.domain.CouponApplicationType;

import java.util.Date;

public class CouponCreationRequest {
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


    public CouponCreationRequest() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setApplicableUseCount(Integer applicableUseCount) {
        this.applicableUseCount = applicableUseCount;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setInclusive(Boolean inclusive) {
        this.inclusive = inclusive;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void setTransactionMinVal(Integer transactionMinVal) {
        this.transactionMinVal = transactionMinVal;
    }

    public void setTransactionMaxVal(Integer transactionMaxVal) {
        this.transactionMaxVal = transactionMaxVal;
    }

    public void setApplicableTill(Date applicableTill) {
        this.applicableTill = applicableTill;
    }

    public void setApplicableFrom(Date applicableFrom) {
        this.applicableFrom = applicableFrom;
    }

    public void setApplicationType(CouponApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public CouponApplicationType getApplicationType() {
        return applicationType;
    }

    public Date getApplicableFrom() {
        return applicableFrom;
    }

    public Date getApplicableTill() {
        return applicableTill;
    }

    public Integer getTransactionMaxVal() {
        return transactionMaxVal;
    }

    public Integer getTransactionMinVal() {
        return transactionMinVal;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getInclusive() {
        return inclusive;
    }

    public String getChannelName() {
        return channelName;
    }

    public Integer getApplicableUseCount() {
        return applicableUseCount;
    }
}
