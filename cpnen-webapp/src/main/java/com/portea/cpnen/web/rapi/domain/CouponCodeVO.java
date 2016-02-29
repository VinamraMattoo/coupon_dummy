package com.portea.cpnen.web.rapi.domain;

import java.util.Date;

public class CouponCodeVO {
    private Integer id;
    private String code;
    private String channelName;
    private Date createdOn;
    private String createdBy;
    private Date deactivatedOn;
    private String deactivatedBy;
    private Integer couponId;
    private Date applicableTill;
    private Date applicableFrom;
    private String couponName;
    private String categoryName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getDeactivatedOn() {
        return deactivatedOn;
    }

    public void setDeactivatedOn(Date deactivatedOn) {
        this.deactivatedOn = deactivatedOn;
    }

    public String getDeactivatedBy() {
        return deactivatedBy;
    }

    public void setDeactivatedBy(String deactivatedBy) {
        this.deactivatedBy = deactivatedBy;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setApplicableTill(Date applicableTill) {
        this.applicableTill = applicableTill;
    }

    public Date getApplicableTill() {
        return applicableTill;
    }

    public void setApplicableFrom(Date applicableFrom) {
        this.applicableFrom = applicableFrom;
    }

    public Date getApplicableFrom() {
        return applicableFrom;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponName() {
        return couponName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
