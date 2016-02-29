package com.portea.cpnen.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Information related to update in coupon core data, coupon-brand mapping data or
 * coupon-product mapping data can be obtained here.
 */
@Entity
@Table(name = "coupon_audit")
public class CouponAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(name = "core_update")
    private Boolean coreUpdate;

    @Column(name = "brands_update")
    private Boolean brandsUpdate;

    @Column(name = "areas_update")
    private Boolean areasUpdate;

    @Column(name = "referrers_update")
    private Boolean referrerUpdate;

    @Column(name = "products_update")
    private Boolean productsUpdate;

    @Column(name = "discounting_rule_update")
    private Boolean discountingRuleUpdate;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    public CouponAudit() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Boolean getCoreUpdate() {
        return coreUpdate;
    }

    public void setCoreUpdate(Boolean coresUpdate) {
        this.coreUpdate = coresUpdate;
    }

    public Boolean getBrandsUpdate() {
        return brandsUpdate;
    }

    public void setBrandsUpdate(Boolean brandupdate) {
        this.brandsUpdate = brandupdate;
    }

    public Boolean getProductsUpdate() {
        return productsUpdate;
    }

    public void setProductsUpdate(Boolean productsUpdate) {
        this.productsUpdate = productsUpdate;
    }

    public Boolean getDiscountingRuleUpdate() {
        return discountingRuleUpdate;
    }

    public void setDiscountingRuleUpdate(Boolean discountingRuleUpdate) {
        this.discountingRuleUpdate = discountingRuleUpdate;
    }

    public Boolean getAreasUpdate() {
        return areasUpdate;
    }

    public void setAreasUpdate(Boolean areasUpdate) {
        this.areasUpdate = areasUpdate;
    }

    public Boolean getReferrerUpdate() {
        return referrerUpdate;
    }

    public void setReferrerUpdate(Boolean referrerUpdate) {
        this.referrerUpdate = referrerUpdate;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CouponAudit that = (CouponAudit) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
