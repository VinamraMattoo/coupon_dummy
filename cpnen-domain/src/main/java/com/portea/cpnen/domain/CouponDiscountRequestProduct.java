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

    /**
     * The product id should map to an existing record in one of the product tables
     */
	@Column(name = "product_id")
	private Integer productId;

    @Column(name = "product_type")
    @Enumerated(EnumType.STRING)
    private ProductType productType;
	
	@Column(name = "product_count")
	private Integer productCount;
	
	@Column(name = "product_unit_price")
	private Double productUnitPrice;

    @Column(name = "purchase_instance_count")
    private Integer purchaseInstanceCount;
	
	@Column(name = "remarks", columnDefinition = "varchar(256)")
	private String remarks;

    public CouponDiscountRequestProduct() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CouponDiscountRequest getCouponDiscountRequest() {
        return couponDiscountRequest;
    }

    public void setCouponDiscountRequest(CouponDiscountRequest couponDiscountRequest) {
        this.couponDiscountRequest = couponDiscountRequest;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
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

        CouponDiscountRequestProduct that = (CouponDiscountRequestProduct) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
