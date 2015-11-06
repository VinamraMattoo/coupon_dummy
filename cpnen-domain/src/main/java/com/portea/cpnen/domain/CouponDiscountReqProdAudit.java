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
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@Column(name = "product_count")
	private Integer productCount;
	
	@Column(name = "product_uint_price")
	private Integer productUnitPrice;
	
	@Column(name = "remarks", columnDefinition = "varchar(256)")
	private String remarks;

	public CouponDiscountReqProdAudit() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CouponDiscountRequestAudit getCouponDiscReqAudit() {
        return couponDiscReqAudit;
    }

    public void setCouponDiscReqAudit(CouponDiscountRequestAudit couponDiscReqAudit) {
        this.couponDiscReqAudit = couponDiscReqAudit;
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
