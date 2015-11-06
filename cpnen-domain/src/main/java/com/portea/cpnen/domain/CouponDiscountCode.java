package com.portea.cpnen.domain;

import javax.persistence.*;

@Entity
@Table(name = "coupon_discount_code")
public class CouponDiscountCode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @ManyToOne
    @JoinColumn(name = "coupon_disc_id")
    private CouponDiscount couponDiscount;

	@ManyToOne
	@JoinColumn(name = "code_id")
	private CouponCode couponCode;

    public CouponDiscountCode() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CouponDiscount getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(CouponDiscount couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public CouponCode getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(CouponCode couponCode) {
        this.couponCode = couponCode;
    }
}