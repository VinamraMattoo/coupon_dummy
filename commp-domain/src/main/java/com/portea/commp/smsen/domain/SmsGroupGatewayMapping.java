package com.portea.commp.smsen.domain;

import javax.persistence.*;

@Entity
@Table(name = "sms_group_gateway_mapping",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"sms_group_id", "gateway_id", "priority"})})
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

}
