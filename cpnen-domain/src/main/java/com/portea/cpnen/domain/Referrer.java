package com.portea.cpnen.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="referrers")
public class Referrer {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(64)")
    private String name;

    @Column(name = "brandId")
    private Integer brandId;

    @Column(name = "referrerType", columnDefinition = "enum('B2B', 'B2C')")
    private String referrerType;

    public Referrer() {}

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

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getReferrerType() {
        return referrerType;
    }

    public void setReferrerType(String referrerType) {
        this.referrerType = referrerType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Referrer referrer = (Referrer) o;

        return id.equals(referrer.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
