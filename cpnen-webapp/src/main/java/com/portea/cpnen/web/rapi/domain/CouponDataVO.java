package com.portea.cpnen.web.rapi.domain;

import com.portea.cpnen.domain.CouponApplicationType;

import java.util.Date;
import java.util.List;

public class CouponDataVO {

    private Integer id;
    private String name;
    private String channel;
    private CouponApplicationType applicationType;
    private Integer appUseCount;
    private String status;
    private Integer codes;
    private String description;
    private Date from;
    private Date till;
    private Boolean inclusive;
    private Date createdOn;
    private Boolean global;
    private Date deactivatedOn;
    private Integer deactivatedBy;
    private Date publishedOn;
    private Integer publishedBy;
    private Date lastUpdatedOn;
    private Integer lastUpdatedBy;
    private Integer createdBy;
    private List<BrandVo> brandVos;


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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public CouponApplicationType getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(CouponApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public Integer getAppUseCount() {
        return appUseCount;
    }

    public void setAppUseCount(Integer appUseCount) {
        this.appUseCount = appUseCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCodes() {
        return codes;
    }

    public void setCodes(Integer codes) {
        this.codes = codes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTill() {
        return till;
    }

    public void setTill(Date till) {
        this.till = till;
    }

    public Boolean getInclusive() {
        return inclusive;
    }

    public void setInclusive(Boolean inclusive) {
        this.inclusive = inclusive;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public Date getDeactivatedOn() {
        return deactivatedOn;
    }

    public void setDeactivatedOn(Date deactivatedOn) {
        this.deactivatedOn = deactivatedOn;
    }

    public Integer getDeactivatedBy() {
        return deactivatedBy;
    }

    public void setDeactivatedBy(Integer deactivatedBy) {
        this.deactivatedBy = deactivatedBy;
    }

    public Date getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public Integer getPublishedBy() {
        return publishedBy;
    }

    public void setPublishedBy(Integer publishedBy) {
        this.publishedBy = publishedBy;
    }

    public Date getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Date lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public List<BrandVo> getBrandVos() {
        return brandVos;
    }

    public void setBrandVos(List<BrandVo> brandVos) {
        this.brandVos = brandVos;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

}
