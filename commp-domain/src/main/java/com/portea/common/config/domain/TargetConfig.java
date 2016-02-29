package com.portea.common.config.domain;

import javax.persistence.*;

@Entity
@Table(name = "cmn_target_config",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"config_param_id", "target_id"})})
@Cacheable
public class TargetConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "target_id")
    private Integer targetId;

    @JoinColumn(name = "config_param_id")
    @ManyToOne
    private ConfigParam configParam;

    @Column(name = "active")
    private Boolean active;

    public TargetConfig() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public ConfigParam getConfigParam() {
        return configParam;
    }

    public void setConfigParam(ConfigParam configParam) {
        this.configParam = configParam;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TargetConfig that = (TargetConfig) o;

        return !(getId() != null ? !getId().equals(that.getId()) : that.getId() != null);

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}
