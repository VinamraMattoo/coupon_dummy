package com.portea.cpnen.domain;

import javax.persistence.*;

@Entity
@Table(name = "coupon_disc_req_prod_audit")
public class CouponDiscountReqProdAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "cdr_audit_id")
	private CouponDiscountRequestAudit couponDiscReqAudit;

    /**
     * The product id should map to an existing record in one of the product tables
     */
    @Column(name = "product_id")
    private Integer productId;
	
	@Column(name = "product_count")
	private Integer productCount;

    @Column(name = "product_unit_price")
    private Double productUnitPrice;

    @Column(name = "purchase_instance_count")
    private Integer purchaseInstanceCount;

	@Column(name = "remarks", columnDefinition = "varchar(256)")
	private String remarks;

	public CouponDiscountReqProdAudit() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CouponDiscountRequestAudit getCouponDiscReqAudit() {
        return couponDiscReqAudit;
    }

    public void setCouponDiscReqAudit(CouponDiscountRequestAudit couponDiscReqAudit) {
        this.couponDiscReqAudit = couponDiscReqAudit;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

        CouponDiscountReqProdAudit that = (CouponDiscountReqProdAudit) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
