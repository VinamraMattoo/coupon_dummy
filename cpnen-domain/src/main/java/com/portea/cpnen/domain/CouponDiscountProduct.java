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
	@JoinColumn(name = "coupon_product_adapter_id")
	private ProductAdapter productAdapter;
	
	@Column(name = "product_count")
	private Integer productCount;
	
	@Column(name = "product_unit_price")
	private Double productUnitPrice;

    @Column(name = "purchase_instance_count")
    private Integer purchaseInstanceCount;

    public CouponDiscountProduct() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CouponDiscount getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(CouponDiscount couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public ProductAdapter getProductAdapter() {
        return productAdapter;
    }

    public void setProductAdapter(ProductAdapter productAdapter) {
        this.productAdapter = productAdapter;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Double getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(Double productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public Integer getPurchaseInstanceCount() {
        return purchaseInstanceCount;
    }

    public void setPurchaseInstanceCount(Integer purchaseInstanceCount) {
        this.purchaseInstanceCount = purchaseInstanceCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CouponDiscountProduct that = (CouponDiscountProduct) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
