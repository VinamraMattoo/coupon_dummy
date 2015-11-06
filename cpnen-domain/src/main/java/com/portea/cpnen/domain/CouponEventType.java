package com.portea.cpnen.domain;

import javax.persistence.*;

// TODO Map this class with CouponCodeReservation
@Entity
@Table(name = "coupon_event_type")
public class CouponEventType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name", columnDefinition = "varchar(128)")
	private String name;

    public CouponEventType() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
