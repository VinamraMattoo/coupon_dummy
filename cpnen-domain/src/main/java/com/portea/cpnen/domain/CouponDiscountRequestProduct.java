package com.portea.cpnen.domain;

import javax.persistence.*;

@Entity
@Table(name = "coupon_discount_req_prod")
public class CouponDiscountRequestProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "coupon_disc_req_id")
	private CouponDiscountRequest couponDiscountRequest;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@Column(name = "product_count")
	private Integer productCount;
	
	@Column(name = "product_unit_price")
	private Integer productUnitPrice;
	
	@Column(name = "remarks", columnDefinition = "varchar(256)")
	private String remarks;

    public CouponDiscountRequestProduct() {}

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public int getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(int productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
