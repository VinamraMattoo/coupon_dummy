package com.portea.commp.smsen.domain;

import javax.persistence.*;

/**
 * A read-only implementation for an existing table, as sms engine system is not expected to write to the
 * underlying table.
 */
@Entity
@Cacheable
@Table(name = "brands")
public class Brand {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(64)")
    private String name;

    @Column(name = "canSendSms")
    private Boolean canSendSms;

    public Brand() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getCanSendSms() {
        return canSendSms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brand brand = (Brand) o;

        return id.equals(brand.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
