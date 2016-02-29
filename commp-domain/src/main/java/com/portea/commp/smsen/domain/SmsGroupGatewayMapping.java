package com.portea.commp.smsen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "smsen_sms_group_gateway_mapping",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"sms_group_id", "gateway_id"})})
public class SmsGroupGatewayMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "sms_group_id")
    @ManyToOne
    private SmsGroup smsGroup;

    @JoinColumn(name = "gateway_id")
    @ManyToOne
    private SmsGateway smsGateway;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "last_updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedOn;

    @JoinColumn(name = "last_updated_by")
    @ManyToOne
    private User lastUpdatedBy;

    public SmsGroupGatewayMapping() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SmsGroup getSmsGroup() {
        return smsGroup;
    }

    public void setSmsGroup(SmsGroup smsGroup) {
        this.smsGroup = smsGroup;
    }

    public SmsGateway getSmsGateway() {
        return smsGateway;
    }

    public void setSmsGateway(SmsGateway smsGateway) {
        this.smsGateway = smsGateway;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public User getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(User lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Date lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsGroupGatewayMapping that = (SmsGroupGatewayMapping) o;

        return !(getId() != null ? !getId().equals(that.getId()) : that.getId() != null);

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SmsGroupGatewayMapping{" +
                "id=" + id +
                ", smsGroup=" + smsGroup.getName() +
                ", smsGateway=" + smsGateway.getName() +
                ", priority=" + priority +
                '}';
    }
}
