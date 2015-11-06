package com.portea.common.config.domain;

import javax.persistence.*;

@Entity
@Table(name = "cmn_target_config_value")
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
