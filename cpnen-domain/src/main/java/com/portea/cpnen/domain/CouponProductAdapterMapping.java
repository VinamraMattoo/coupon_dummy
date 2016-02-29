package com.portea.cpnen.domain;


import javax.persistence.*;

@Entity
@Table(name = "coupon_product_adapter_mapping")
public class CouponProductAdapterMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "coupon_product_adapter_id")
    private ProductAdapter productAdapter;

    @Column(name = "applicable")
    private Boolean applicable;

    public CouponProductAdapterMapping() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
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

        CouponProductAdapterMapping that = (CouponProductAdapterMapping) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
