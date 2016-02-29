package com.portea.cpnen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "coupon_discounting_rule_audit")
public class CouponDiscountingRuleAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "coupon_audit_id")
    @ManyToOne
    private CouponAudit couponAudit;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(name = "description",columnDefinition = "varchar(512)")
    private String description;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @JoinColumn(name = "created_by")
    @ManyToOne
    private User createdBy;

    @Column(name = "deactivated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deactivatedOn;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CouponDiscountingRuleType couponDiscRuleType;

    @Column(name = "disc_flat_amt")
    private Integer discountFlatAmount;

    @Column(name = "disc_percentage")
    private Integer discountPercentage;

    public CouponDiscountingRuleAudit() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CouponAudit getCouponAudit() {
        return couponAudit;
    }

    public void setCouponAudit(CouponAudit couponAudit) {
        this.couponAudit = couponAudit;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getDeactivatedOn() {
        return deactivatedOn;
    }

    public void setDeactivatedOn(Date deactivatedOn) {
        this.deactivatedOn = deactivatedOn;
    }

    public CouponDiscountingRuleType getCouponDiscRuleType() {
        return couponDiscRuleType;
    }

    public void setCouponDiscRuleType(CouponDiscountingRuleType couponDiscRuleType) {
        this.couponDiscRuleType = couponDiscRuleType;
    }

    public Integer getDiscountFlatAmount() {
        return discountFlatAmount;
    }

    public void setDiscountFlatAmount(Integer discountFlatAmount) {
        this.discountFlatAmount = discountFlatAmount;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CouponDiscountingRuleAudit that = (CouponDiscountingRuleAudit) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
