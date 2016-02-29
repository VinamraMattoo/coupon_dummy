package com.portea.commp.smsen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "smsen_sms_type_audit")
public class SmsTypeAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @JoinColumn(name = "sms_type_id")
    @ManyToOne
    private SmsType smsType;

    @Column(name = "expires_in")
    private Integer expiresIn;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "unit", column = @Column(name = "clng_pd_content_match_type", columnDefinition = "varchar(32)")),
            @AttributeOverride(name = "value", column = @Column(name = "clng_pd_content_match_val"))})
    private CoolingPeriod contentMatchCoolingPeriod;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "unit", column = @Column(name = "clng_pd_type_match_type", columnDefinition = "varchar(32)")),
            @AttributeOverride(name = "value", column = @Column(name = "clng_pd_type_match_val"))})
    private CoolingPeriod typeMatchCoolingPeriod;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @JoinColumn(name = "created_by")
    @ManyToOne
    private User createdBy;

    public SmsTypeAudit() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SmsType getSmsType() {
        return smsType;
    }

    public void setSmsType(SmsType smsType) {
        this.smsType = smsType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public CoolingPeriod getContentMatchCoolingPeriod() {
        return contentMatchCoolingPeriod;
    }

    public void setContentMatchCoolingPeriod(CoolingPeriod contentMatchCoolingPeriod) {
        this.contentMatchCoolingPeriod = contentMatchCoolingPeriod;
    }

    public CoolingPeriod getTypeMatchCoolingPeriod() {
        return typeMatchCoolingPeriod;
    }

    public void setTypeMatchCoolingPeriod(CoolingPeriod typeMatchCoolingPeriod) {
        this.typeMatchCoolingPeriod = typeMatchCoolingPeriod;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsTypeAudit that = (SmsTypeAudit) o;

        return !(getId() != null ? !getId().equals(that.getId()) : that.getId() != null);

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}
