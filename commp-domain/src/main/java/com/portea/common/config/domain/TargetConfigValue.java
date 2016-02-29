package com.portea.common.config.domain;

import com.portea.commp.smsen.domain.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cmn_target_config_value",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"target_config_id"})})
@Cacheable
public class TargetConfigValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "target_config_id")
    @ManyToOne
    private TargetConfig targetConfig;

    @Column(name = "value", columnDefinition = "varchar(256)")
    private String value;

    @Column(name = "last_updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedOn;

    @JoinColumn(name = "last_updated_by")
    @ManyToOne
    private User lastUpdatedBy;

    public TargetConfigValue() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TargetConfig getTargetConfig() {
        return targetConfig;
    }

    public void setTargetConfig(TargetConfig targetConfig) {
        this.targetConfig = targetConfig;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

        TargetConfigValue that = (TargetConfigValue) o;

        return !(getId() != null ? !getId().equals(that.getId()) : that.getId() != null);

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}
