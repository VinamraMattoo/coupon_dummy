package com.portea.cpnen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "coupon_disc_req_code_audit")
public class CouponDiscountRequestCodeAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "cdr_audit_id")
	private CouponDiscountRequestAudit couponDiscReqAudit;
	
	@ManyToOne
	@JoinColumn(name = "code_id")
	private CouponCode couponCode;

	public CouponDiscountRequestCodeAudit() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CouponDiscountRequestAudit getCouponDiscReqAudit() {
		return couponDiscReqAudit;
	}

	public void setCouponDiscReqAudit(CouponDiscountRequestAudit couponDiscReqAudit) {
		this.couponDiscReqAudit = couponDiscReqAudit;
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

        CouponDiscountRequestCodeAudit that = (CouponDiscountRequestCodeAudit) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}