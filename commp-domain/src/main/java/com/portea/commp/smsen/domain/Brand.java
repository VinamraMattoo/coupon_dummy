package com.portea.commp.smsen.domain;

import javax.persistence.*;

@Entity
@Table(name = "brands")
public class Brand {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "canSendSms")
    private Boolean canSendSms;

    public Brand() {
    }

    public Integer getId() {
        return id;
    }

    public Boolean getCanSendSms() {
        return canSendSms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brand brand = (Brand) o;

        return !(getId() != null ? !getId().equals(brand.getId()) : brand.getId() != null);

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}
