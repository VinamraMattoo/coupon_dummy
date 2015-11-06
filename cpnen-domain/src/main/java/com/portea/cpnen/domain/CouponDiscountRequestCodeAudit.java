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
	
	@Column(name = "created_on")
	private Date createdOn;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private CouponDiscountRequestStatus status;

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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public CouponDiscountRequestStatus getStatus() {
		return status;
	}

	public void setStatus(CouponDiscountRequestStatus status) {
		this.status = status;
	}
}