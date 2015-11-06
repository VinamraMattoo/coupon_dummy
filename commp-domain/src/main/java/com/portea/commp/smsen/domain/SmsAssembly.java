package com.portea.commp.smsen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sms_assembly")
public class SmsAssembly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "receiver_type", columnDefinition = "varchar(128)")
    private String receiverType;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "template_id")
    @ManyToOne
    private SmsTemplate smsTemplate;

    @JoinColumn(name = "sms_group_id")
    @ManyToOne
    private SmsGroup smsGroup;

    @Column(name = "mobile_number" , columnDefinition = "varchar(15)")
    private String mobileNumber;

    @Column(name = "country_code", columnDefinition = "varchar(8)")
    private String countryCode;

    @Column(name = "message" , columnDefinition = "varchar(1024)")
    private String message;

    @Column(name = "correlation_id", columnDefinition = "varchar(200)")
    private String correlationId;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "send_before")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendBefore;

    @Column(name = "retry_count")
    private Integer retryCount;

    @JoinColumn(name = "brand_id")
    @ManyToOne
    private Brand brand;

    @Column(name = "primary_processing_status", columnDefinition = "varchar(32)")
    @Enumerated(value = EnumType.STRING)
    private SmsPrimaryProcessingState smsPrimaryProcessingState;

    @Column(name = "secondary_processing_status", columnDefinition = "varchar(32)")
    @Enumerated(value = EnumType.STRING)
    private SmsSecondaryProcessingState smsSecondaryProcessingState;

    @Column(name = "message_hash")
    private Integer messageHash;

    @Column(name = "scheduled_id", columnDefinition = "varchar(32)")
    private String scheduledId;

    @Column(name = "scheduled_type", columnDefinition = "varchar(32)")
    private String scheduledType;

    @Column(name = "scheduled_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledTime;

    @Column(name = "scheduled_time_zone", columnDefinition = "varchar(32)")
    private String scheduledTimeZone;

    public SmsAssembly() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SmsTemplate getSmsTemplate() {
        return smsTemplate;
    }

    public void setSmsTemplate(SmsTemplate smsTemplate) {
        this.smsTemplate = smsTemplate;
    }

    public SmsGroup getSmsGroup() {
        return smsGroup;
    }

    public void setSmsGroup(SmsGroup smsGroup) {
        this.smsGroup = smsGroup;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public SmsPrimaryProcessingState getSmsPrimaryProcessingState() {
        return smsPrimaryProcessingState;
    }

    public void setSmsPrimaryProcessingState(SmsPrimaryProcessingState smsPrimaryProcessingState) {
        this.smsPrimaryProcessingState = smsPrimaryProcessingState;
    }

    public SmsSecondaryProcessingState getSmsSecondaryProcessingState() {
        return smsSecondaryProcessingState;
    }

    public void setSmsSecondaryProcessingState(SmsSecondaryProcessingState smsSecondaryProcessingState) {
        this.smsSecondaryProcessingState = smsSecondaryProcessingState;
    }

    public Integer getMessageHash() {
        return messageHash;
    }

    public void setMessageHash(Integer messageHash) {
        this.messageHash = messageHash;
    }

    public String getScheduledId() {
        return scheduledId;
    }

    public void setScheduledId(String scheduledId) {
        this.scheduledId = scheduledId;
    }

    public String getScheduledType() {
        return scheduledType;
    }

    public void setScheduledType(String scheduledType) {
        this.scheduledType = scheduledType;
    }

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Date getSendBefore() {
        return sendBefore;
    }

    public void setSendBefore(Date sendBefore) {
        this.sendBefore = sendBefore;
    }

    public String getScheduledTimeZone() {
        return scheduledTimeZone;
    }

    public void setScheduledTimeZone(String scheduledTimeZone) {
        this.scheduledTimeZone = scheduledTimeZone;
    }

    /**
     * Utility method to check whether this SMS is currently under process and queued
     */
    public boolean isQueued() {

        SmsPrimaryProcessingState primary = this.smsPrimaryProcessingState;
        SmsSecondaryProcessingState secondary = this.smsSecondaryProcessingState;

        if ((primary != null && primary == SmsPrimaryProcessingState.UNDER_PROCESS)
                && (secondary != null &&
                (secondary == SmsSecondaryProcessingState.QUEUED ||
                secondary == SmsSecondaryProcessingState.QUEUED_IN_RETRY))) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsAssembly that = (SmsAssembly) o;

        return !(getId() != null ? !getId().equals(that.getId()) : that.getId() != null);

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}