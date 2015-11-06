package com.portea.cpnen.web.rapi.domain;

import com.portea.cpnen.domain.CouponApplicationType;

import javax.persistence.*;

public class CouponVO {

    private Integer id;
    private String name;
    private String description;
    private String createdOn;
    private Integer createdBy;
    private String deactivatedOn;
    private Integer deactivatedBy;
    private Boolean inclusive;
    private String channelName;
    private CouponApplicationType applicationType;
    private String applicableFrom;
    private String applicableTill;
    private Integer applicableUseCount;
    private Integer transactionMinValue;
    private Integer transactionMaxValue;
    private Integer publishedBy;
    private String publishedOn;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getDeactivatedOn() {
        return deactivatedOn;
    }

    public void setDeactivatedOn(String deactivatedOn) {
        this.deactivatedOn = deactivatedOn;
    }

    public Integer getDeactivatedBy() {
        return deactivatedBy;
    }

    public void setDeactivatedBy(Integer deactivatedBy) {
        this.deactivatedBy = deactivatedBy;
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

    public CouponApplicationType getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(CouponApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public String getApplicableFrom() {
        return applicableFrom;
    }

    public void setApplicableFrom(String applicableFrom) {
        this.applicableFrom = applicableFrom;
    }

    public String getApplicableTill() {
        return applicableTill;
    }

    public void setApplicableTill(String applicableTill) {
        this.applicableTill = applicableTill;
    }

    public Integer getApplicableUseCount() {
        return applicableUseCount;
    }

    public void setApplicableUseCount(Integer applicableUseCount) {
        this.applicableUseCount = applicableUseCount;
    }

    public Integer getTransactionMinValue() {
        return transactionMinValue;
    }

    public void setTransactionMinValue(Integer transactionMinValue) {
        this.transactionMinValue = transactionMinValue;
    }

    public Integer getTransactionMaxValue() {
        return transactionMaxValue;
    }

    public void setTransactionMaxValue(Integer transactionMaxValue) {
        this.transactionMaxValue = transactionMaxValue;
    }

    public Integer getPublishedBy() {
        return publishedBy;
    }

    public void setPublishedBy(Integer publishedBy) {
        this.publishedBy = publishedBy;
    }

    public String getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(String publishedOn) {
        this.publishedOn = publishedOn;
    }
}
