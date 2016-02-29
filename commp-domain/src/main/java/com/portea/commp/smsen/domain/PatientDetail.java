package com.portea.commp.smsen.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "patient_details")
public class PatientDetail {

    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * This is a logical reference to id in user table.
     */
    @Column(name = "loginId")
    private Integer loginId;

    @Column(name = "sendSmsAlert")
    private Boolean sendSmsAlert;

    /**
     * This is a logical reference to id in brand table.
     */
    @Column(name = "brandId")
    private Integer brandId;

    public PatientDetail() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getLoginId() {
        return loginId;
    }

    public Boolean getSendSmsAlert() {
        return sendSmsAlert;
    }


    public Integer getBrandId() {
        return brandId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PatientDetail that = (PatientDetail) o;

        return !(getId() != null ? !getId().equals(that.getId()) : that.getId() != null);

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}
