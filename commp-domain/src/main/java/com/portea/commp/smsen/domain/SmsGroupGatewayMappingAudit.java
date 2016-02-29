package com.portea.commp.smsen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "smsen_sms_group_gateway_mapping_audit")
public class SmsGroupGatewayMappingAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * If old priority is null for a gateway mapping audit that implies that the mapping is new.
     */
    @Column(name = "old_priority")
    private Integer oldPriority;

    /**
     * If new priority is null for a gateway mapping audit that implies that the mapping is deleted.
     */
    @Column(name = "new_priority")
    private Integer newPriority;

    @JoinColumn(name = "gateway_id")
    @ManyToOne
    private SmsGateway smsGateway;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @JoinColumn(name = "created_by")
    @ManyToOne
    private User createdBy;

    @JoinColumn(name = "sms_group_id")
    @ManyToOne
    private SmsGroup smsGroup;

    public SmsGroupGatewayMappingAudit() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getNewPriority() {
        return newPriority;
    }

    public void setNewPriority(Integer newPriority) {
        this.newPriority = newPriority;
    }

    public Integer getOldPriority() {
        return oldPriority;
    }

    public void setOldPriority(Integer oldPriority) {
        this.oldPriority = oldPriority;
    }

    public SmsGateway getSmsGateway() {
        return smsGateway;
    }

    public void setSmsGateway(SmsGateway smsGateway) {
        this.smsGateway = smsGateway;
    }

    public SmsGroup getSmsGroup() {
        return smsGroup;
    }

    public void setSmsGroup(SmsGroup smsGroup) {
        this.smsGroup = smsGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsGroupGatewayMappingAudit that = (SmsGroupGatewayMappingAudit) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
