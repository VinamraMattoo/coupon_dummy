package com.portea.commp.service.domain;

import java.util.Date;

public class SmsAssemblyVo {
    private String receiverType;
    private Integer userId;
    private Integer smsTemplate;
    private Integer smsGroup;
    private String mobileNumber;
    private String countryCode;
    private String message;
    private Date sendBefore;
    private Integer brand;
    private String scheduledId;
    private String scheduledType;
    private Date scheduledTime;
    private String scheduledTimeZone;

    public SmsAssemblyVo() {}

    public String getReceiverType() {
        return receiverType;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getSmsTemplate() {
        return smsTemplate;
    }

    public Integer getSmsGroup() {
        return smsGroup;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getMessage() {
        return message;
    }

    public Date getSendBefore() {
        return sendBefore;
    }

    public Integer getBrand() {
        return brand;
    }

    public String getScheduledId() {
        return scheduledId;
    }

    public String getScheduledType() {
        return scheduledType;
    }

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public String getScheduledTimeZone() {
        return scheduledTimeZone;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setSmsTemplate(Integer smsTemplate) {
        this.smsTemplate = smsTemplate;
    }

    public void setSmsGroup(Integer smsGroup) {
        this.smsGroup = smsGroup;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSendBefore(Date sendBefore) {
        this.sendBefore = sendBefore;
    }

    public void setBrand(Integer brand) {
        this.brand = brand;
    }

    public void setScheduledId(String scheduledId) {
        this.scheduledId = scheduledId;
    }

    public void setScheduledType(String scheduledType) {
        this.scheduledType = scheduledType;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public void setScheduledTimeZone(String scheduledTimeZone) {
        this.scheduledTimeZone = scheduledTimeZone;
    }
}
