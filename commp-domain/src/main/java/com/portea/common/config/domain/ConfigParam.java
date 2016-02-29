package com.portea.common.config.domain;

import javax.persistence.*;

@Entity
@Table(name = "cmn_config_param",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"config_target_type_id", "name"})})
@Cacheable
public class ConfigParam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "config_target_type_id")
    @ManyToOne
    private ConfigTargetType configTargetType;

    @Column(name = "name", columnDefinition = "varchar(128)")
    private String name;

    @Column(name = "description", columnDefinition = "varchar(1024)")
    private String description;

    @Column(name = "value_data_type", columnDefinition = "varchar(32)")
    @Enumerated(value = EnumType.STRING)
    private ConfigParamValueDataType valueDataType;

    public ConfigParam() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConfigParamValueDataType getValueDataType() {
        return valueDataType;
    }

    public void setValueDataType(ConfigParamValueDataType valueDataType) {
        this.valueDataType = valueDataType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfigParam that = (ConfigParam) o;

        return !(getId() != null ? !getId().equals(that.getId()) : that.getId() != null);

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}
