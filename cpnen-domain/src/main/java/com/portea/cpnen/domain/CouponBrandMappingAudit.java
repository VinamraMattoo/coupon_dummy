package com.portea.cpnen.domain;

import javax.persistence.*;

@Entity
@Table(name = "coupon_brand_mapping_audit")
public class CouponBrandMappingAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "coupon_audit_id")
    @ManyToOne
    private CouponAudit couponAudit;

    @JoinColumn(name = "brand_id")
    @ManyToOne
    private Brand brand;

    @Column(name = "applicable")
    private Boolean applicable;

    public CouponBrandMappingAudit() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CouponAudit getCouponAudit() {
        return couponAudit;
    }

    public void setCouponAudit(CouponAudit couponAudit) {
        this.couponAudit = couponAudit;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Boolean getApplicable() {
        return applicable;
    }

    public void setApplicable(Boolean applicable) {
        this.applicable = applicable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CouponBrandMappingAudit that = (CouponBrandMappingAudit) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
