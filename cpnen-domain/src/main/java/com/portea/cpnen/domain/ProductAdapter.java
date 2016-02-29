package com.portea.cpnen.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * An adapter for various different products which are captured elsewhere; for e.g.in services and packages.
 * During coupon creation adapter records for all the applicable products are registered here if it not already present.
 */
@Entity
@Table(name = "coupon_product_adapter")
public class ProductAdapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(64)")
    private String name;

    /**
     * This is a logical reference to id in services or id in packages table. In future, any other product table
     * can also be mapped to this attribute.
     */
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "product_type", columnDefinition = "varchar(32)")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private Date createdOn;

    public ProductAdapter() {}

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

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductAdapter that = (ProductAdapter) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
