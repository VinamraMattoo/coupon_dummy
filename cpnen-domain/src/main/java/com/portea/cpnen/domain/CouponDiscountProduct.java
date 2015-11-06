package com.portea.cpnen.domain;

import javax.persistence.*;

@Entity
@Table(name = "coupon_discount_product")
public class CouponDiscountProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "coupon_disc_id")
	private CouponDiscount couponDiscount;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@Column(name = "product_count")
	private Integer productCount;
	
	@Column(name = "product_unit_price")
	private Integer productUnitPrice;

    public CouponDiscountProduct() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CouponDiscount getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(CouponDiscount couponDiscount) {
        this.couponDiscount = couponDiscount;
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
}
