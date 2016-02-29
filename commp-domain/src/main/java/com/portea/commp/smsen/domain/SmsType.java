package com.portea.commp.smsen.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * SMS type has been modeled from the 'category' attribute of existing table 'sms_template'. However instead of SMS
 * templates types are SMS groups are associated with types. This provide cleaner hierarchy for SMS
 *
 * With SMS type being an independent table, it affords greater flexibility in defining configuration for SMS types.
 */
@Entity
@Table(name = "smsen_sms_type")
public class SmsType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(128)")
    private String name;

    @Column(name = "description",columnDefinition = "varchar(512)")
    private String description;

    @Column(name = "expires_in")
    private Integer expiresIn;

    @Column(name = "last_updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedOn;

    @JoinColumn(name = "last_updated_by")
    @ManyToOne
    private User lastUpdatedBy;

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

    public SmsType() {
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

        SmsType smsType = (SmsType) o;

        return !(getId() != null ? !getId().equals(smsType.getId()) : smsType.getId() != null);

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}

