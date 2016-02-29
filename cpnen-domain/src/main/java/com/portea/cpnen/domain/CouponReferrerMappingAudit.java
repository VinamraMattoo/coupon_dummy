package com.portea.cpnen.domain;

import javax.persistence.*;

@Entity
@Table(name = "coupon_referrer_mapping_audit")
public class CouponReferrerMappingAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "coupon_audit_id")
    @ManyToOne
    private CouponAudit couponAudit;

    @JoinColumn(name = "referrer_id")
    @ManyToOne
    private Referrer referrer;

    @Column(name = "applicable")
    private Boolean applicable;

    public CouponReferrerMappingAudit() {}

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

    public Referrer getReferrer() {
        return referrer;
    }

    public void setReferrer(Referrer referrer) {
        this.referrer = referrer;
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

        CouponReferrerMappingAudit that = (CouponReferrerMappingAudit) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
