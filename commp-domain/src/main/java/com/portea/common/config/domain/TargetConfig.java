package com.portea.common.config.domain;

import javax.persistence.*;

@Entity
@Table(name = "cmn_target_config")
public class TargetConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "config_target_type_id")
    @ManyToOne
    private ConfigTargetType configTargetType;

    @Column(name = "target_id", columnDefinition = "varchar(128)")
    private String targetId;

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

    public ConfigTargetType getConfigTargetType() {
        return configTargetType;
    }

    public void setConfigTargetType(ConfigTargetType configTargetType) {
        this.configTargetType = configTargetType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
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
