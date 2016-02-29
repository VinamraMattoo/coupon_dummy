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

    @Enumerated(EnumType.STRING)
	@Column(name = "reservation_type", columnDefinition = "varchar(128)")
	private EventType reservationType;
	
	@Column(name = "reservation_from")
	@Temporal(TemporalType.TIMESTAMP)
	private Date reservationFrom;
	
	@Column(name = "reservation_till")
	@Temporal(TemporalType.TIMESTAMP)
	private Date reservationTill;
	
	@OneToOne
	@JoinColumn(name = "code_id")
	private CouponCode couponCode;

    @Column(name = "remarks", columnDefinition = "varchar(256)")
    private String remarks;
	
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

    public EventType getReservationType() {
        return reservationType;
    }

    public void setReservationType(EventType reservationType) {
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CouponCodeReservation that = (CouponCodeReservation) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
