package com.portea.cpnen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "coupon_code_reservation")
public class CouponCodeReservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "user_id")
	@ManyToOne
	private User user;

	/**
	 * TODO Event Type should be an entity as new kinds of events can be defined
	 */
	@Column(name = "event_type", columnDefinition = "varchar(128)")
	private String reservationType;
	
	@Column(name = "reservation_from")
	@Temporal(TemporalType.TIMESTAMP)
	private Date reservationFrom;
	
	@Column(name = "reservation_till")
	@Temporal(TemporalType.TIMESTAMP)
	private Date reservationTill;
	
	@OneToOne
	@JoinColumn(name = "code_id")
	private CouponCode couponCode;
	
    public CouponCodeReservation() {}

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

    public String getReservationType() {
        return reservationType;
    }

    public void setReservationType(String reservationType) {
        this.reservationType = reservationType;
    }

    public Date getReservationFrom() {
        return reservationFrom;
    }

    public void setReservationFrom(Date reservationFrom) {
        this.reservationFrom = reservationFrom;
    }

    public Date getReservationTill() {
        return reservationTill;
    }

    public void setReservationTill(Date reservationTill) {
        this.reservationTill = reservationTill;
    }

    public CouponCode getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(CouponCode couponCode) {
        this.couponCode = couponCode;
    }
}
