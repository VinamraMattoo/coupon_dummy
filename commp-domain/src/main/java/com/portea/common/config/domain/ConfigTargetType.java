package com.portea.common.config.domain;

import javax.persistence.*;

@Entity
@Table(name = "cmn_config_target_type")
@Cacheable
public class ConfigTargetType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "type_name", columnDefinition = "varchar(128)")
    @Enumerated(value = EnumType.STRING)
    private com.portea.commp.smsen.domain.ConfigTargetType targetType;

    @Column(name = "description", columnDefinition = "varchar(512)")
    private String description;

    public ConfigTargetType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public com.portea.commp.smsen.domain.ConfigTargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(com.portea.commp.smsen.domain.ConfigTargetType targetType) {
        this.targetType = targetType;
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

        ConfigTargetType that = (ConfigTargetType) o;

        return !(getId() != null ? !getId().equals(that.getId()) : that.getId() != null);

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}
