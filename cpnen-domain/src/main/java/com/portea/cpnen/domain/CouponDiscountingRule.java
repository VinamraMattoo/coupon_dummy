package com.portea.cpnen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "coupon_discounting_rule")
public class CouponDiscountingRule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
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
	
	@Column(name = "priority")
	private Integer priority;

    public CouponDiscountingRule() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
