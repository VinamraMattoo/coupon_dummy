package com.portea.cpnen.domain;

import javax.persistence.*;

@Entity
@Table(name = "coupon_product_adapter_mapping_audit")
public class CouponProductAdapterMappingAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "coupon_audit_id")
    @ManyToOne
    private CouponAudit couponAudit;

    @JoinColumn(name = "coupon_product_adapter_id")
    @ManyToOne
    private ProductAdapter productAdapter;

    @Column(name = "applicable")
    private Boolean applicable;

    public CouponProductAdapterMappingAudit() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CouponAudit getCouponAudit() {
        return couponAudit;
    }

    public void setCouponAudit(CouponAudit couponAudit) {
        this.couponAudit = couponAudit;
    }

    public ProductAdapter getProductAdapter() {
        return productAdapter;
    }

    public void setProductAdapter(ProductAdapter productAdapter) {
        this.productAdapter = productAdapter;
    }

    public Boolean getApplicable() {
        return applicable;
    }

    public void setApplicable(Boolean applicable) {
        this.applicable = applicable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CouponProductAdapterMappingAudit that = (CouponProductAdapterMappingAudit) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
