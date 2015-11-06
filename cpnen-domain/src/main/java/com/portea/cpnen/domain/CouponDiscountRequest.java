package com.portea.cpnen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "coupon_discount_req")
public class CouponDiscountRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@JoinColumn(name = "user_id")
    @ManyToOne
	private User user;
	
	@Column(name = "user_phone")
	private String userPhone;
	
	@Column(name = "latest_updated_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date latestUpdatedOn;

    @Column(name = "completed")
	private Boolean completed;

    public CouponDiscountRequest() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getLatestUpdatedOn() {
        return latestUpdatedOn;
    }

    public void setLatestUpdatedOn(Date latestUpdatedOn) {
        this.latestUpdatedOn = latestUpdatedOn;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
