package com.portea.cpnen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "coupon_discount_req_code")
public class CouponDiscountRequestCode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "coupon_disc_req_id")
	private CouponDiscountRequest couponDiscountRequest;
	
	@ManyToOne
	@JoinColumn(name = "code_id")
	private CouponCode couponCode;

    public CouponDiscountRequestCode() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CouponDiscountRequest getCouponDiscountRequest() {
        return couponDiscountRequest;
    }

    public void setCouponDiscountRequest(CouponDiscountRequest couponDiscountRequest) {
        this.couponDiscountRequest = couponDiscountRequest;
    }

    public CouponCode getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(CouponCode couponCode) {
        this.couponCode = couponCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CouponDiscountRequestCode that = (CouponDiscountRequestCode) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}