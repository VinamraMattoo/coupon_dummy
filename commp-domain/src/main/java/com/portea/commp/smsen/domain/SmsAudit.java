package com.portea.commp.smsen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "smsen_sms_audit")
public class SmsAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "record_id")
    @ManyToOne
    private SmsRecord smsRecord;

    @Column(name = "correlation_id", columnDefinition = "varchar(200)")
    private String correlationId;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

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

    @Column(name = "gateway_status", columnDefinition = "varchar(32)")
    private String gatewayStatus;

    @JoinColumn(name = "gateway_id")
    @ManyToOne
    private SmsGateway smsGateway;

    @Column(name = "response_code", columnDefinition = "varchar(128)")
    private String responseCode;

    @Column(name = "response_message", columnDefinition = "varchar(255)")
    private String responseMessage;

    public SmsAudit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SmsRecord getSmsRecord() {
        return smsRecord;
    }

    public void setSmsRecord(SmsRecord smsRecord) {
        this.smsRecord = smsRecord;
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

    public String getGatewayStatus() {
        return gatewayStatus;
    }

    public void setGatewayStatus(String gatewayStatus) {
        this.gatewayStatus = gatewayStatus;
    }

    public SmsGateway getSmsGateway() {
        return smsGateway;
    }

    public void setSmsGateway(SmsGateway smsGateway) {
        this.smsGateway = smsGateway;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsAudit smsAudit = (SmsAudit) o;

        return !(getId() != null ? !getId().equals(smsAudit.getId()) : smsAudit.getId() != null);

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
