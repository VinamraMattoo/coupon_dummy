package com.portea.commp.smsen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "smsen_sms_record")
public class SmsRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "receiver_type", columnDefinition = "varchar(128)")
    private String receiverType;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "sms_group_id")
    @ManyToOne
    private SmsGroup smsGroup;

    @JoinColumn(name = "template_id")
    @ManyToOne
    private SmsTemplate smsTemplate;

    @Column(name = "mobile_number" , columnDefinition = "varchar(15)")
    private String mobileNumber;

    @Column(name = "country_code", columnDefinition = "varchar(8)")
    private String countryCode;

    @Column(name = "message" , columnDefinition = "varchar(1024)")
    private String message;

    @Column(name = "correlation_id", columnDefinition = "varchar(200)")
    private String correlationId;

    @Column(name = "send_before")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendBefore;

    @Column(name = "last_updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedOn;

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

    @Column(name = "status_reason", columnDefinition = "varchar(256)")
    private String statusReason;

    @Column(name = "status_remarks", columnDefinition = "varchar(512)")
    private String statusRemarks;

    @Column(name = "message_hash")
    private Integer messageHash;

    @Column(name = "scheduled_id" , columnDefinition = "varchar(32)")
    private String scheduledId;

    @Column(name = "scheduled_type", columnDefinition = "varchar(32)")
    private String scheduledType;

    @Column(name = "scheduled_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledTime;

    @Column(name = "scheduled_time_zone", columnDefinition = "varchar(32)")
    private String scheduledTimeZone;

    @JoinColumn(name = "gateway_id")
    @ManyToOne
    private SmsGateway smsGateway;

    /**
     * Denotes the status response sent by a gateway, Combination of gatewayId and
     * gatewayStatus can be used to find the implementation of GatewaySmsStatus.
     */
    @Column(name = "gateway_status", columnDefinition = "varchar(32)")
    private String gatewayStatus;

    @Column(name = "sms_requested_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date smsRequestedAt;

    @Column(name = "correlation_id_check_count")
    private Integer correlationIdCheckTrialCount;

    @Column(name = "gateway_finding_failure_count")
    private Integer gatewayFindingFailureCount;

    @Column(name = "received_correlation_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivedCorrelationIdAt;

    @JoinColumn(name = "copy_of")
    @ManyToOne
    private SmsRecord copyOf;

    @Column(name ="status_check_trial_count")
    private Integer statusCheckTrialCount;

    @Column(name = "source_name", columnDefinition = "varchar(64)")
    @Enumerated(value = EnumType.STRING)
    private SmsSource sourceName;

    public SmsRecord() {
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

    public SmsGroup getSmsGroup() {
        return smsGroup;
    }

    public void setSmsGroup(SmsGroup smsGroup) {
        this.smsGroup = smsGroup;
    }

    public SmsTemplate getSmsTemplate() {
        return smsTemplate;
    }

    public void setSmsTemplate(SmsTemplate smsTemplate) {
        this.smsTemplate = smsTemplate;
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

    public Date getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Date lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
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

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public String getStatusRemarks() {
        return statusRemarks;
    }

    public void setStatusRemarks(String statusRemarks) {
        this.statusRemarks = statusRemarks;
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

    public SmsGateway getSmsGateway() {
        return smsGateway;
    }

    public void setSmsGateway(SmsGateway smsGateway) {
        this.smsGateway = smsGateway;
    }

    public String getGatewayStatus() {
        return gatewayStatus;
    }

    public void setGatewayStatus(String gatewayStatus) {
        this.gatewayStatus = gatewayStatus;
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

    public Date getSmsRequestedAt() {
        return smsRequestedAt;
    }

    public void setSmsRequestedAt(Date smsRequestedAt) {
        this.smsRequestedAt = smsRequestedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsRecord smsRecord = (SmsRecord) o;

        return !(getId() != null ? !getId().equals(smsRecord.getId()) : smsRecord.getId() != null);

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    public void setCorrelationIdCheckTrialCount(Integer submissionCheckTrialCount) {
        this.correlationIdCheckTrialCount = submissionCheckTrialCount;
    }

    public Integer getCorrelationIdCheckTrialCount() {
        return correlationIdCheckTrialCount;
    }

    public void setGatewayFindingFailureCount(Integer gatewayFindingFailureCount) {
        this.gatewayFindingFailureCount = gatewayFindingFailureCount;
    }

    public Integer getGatewayFindingFailureCount() {
        return gatewayFindingFailureCount;
    }

    public Date getReceivedCorrelationIdAt() {
        return receivedCorrelationIdAt;
    }

    public void setReceivedCorrelationIdAt(Date receivedCorrelationIdAt) {
        this.receivedCorrelationIdAt = receivedCorrelationIdAt;
    }

    public void setCopyOf(SmsRecord copyOf) {
        this.copyOf = copyOf;
    }

    public SmsRecord getCopyOf() {
        return copyOf;
    }

    public Integer getStatusCheckTrialCount() {
        return statusCheckTrialCount;
    }

    public void setStatusCheckTrialCount(Integer statusCheckTrialCount) {
        this.statusCheckTrialCount = statusCheckTrialCount;
    }

    public SmsSource getSourceName() {
        return sourceName;
    }

    public void setSourceName(SmsSource sourceName) {
        this.sourceName = sourceName;
    }
}
