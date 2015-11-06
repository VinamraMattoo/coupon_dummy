package com.portea.cpnen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "coupon_disc_req_audit")
public class CouponDiscountRequestAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "coupon_disc_req_id")
	private CouponDiscountRequest couponDiscountRequest;
	
	@JoinColumn(name = "user_id")
	@ManyToOne
	private User user;
	
	@Column(name  = "user_phone")
	private String userPhone;
	
	@Column(name = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	public CouponDiscountRequestAudit() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CouponDiscountRequest getCouponDiscountRequest() {
        return couponDiscountRequest;
    }

    public void setCouponDiscountRequest(CouponDiscountRequest couponDiscountRequest) {
        this.couponDiscountRequest = couponDiscountRequest;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
