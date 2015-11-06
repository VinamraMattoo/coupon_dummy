package com.portea.cpnen.domain;

import javax.persistence.*;

@Entity
@Table(name = "coupon_category_map",
		uniqueConstraints = @UniqueConstraint(columnNames = {"coupon_id", "category_id"}))
public class CouponCategoryMap {

	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "coupon_id")
	private Coupon coupon;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@Column(name = "applicable")
	private Boolean applicable;

	public CouponCategoryMap() {}

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Boolean isApplicable() {
		return applicable;
	}

	public void setApplicable(Boolean applicable) {
		this.applicable = applicable;
	}
}
