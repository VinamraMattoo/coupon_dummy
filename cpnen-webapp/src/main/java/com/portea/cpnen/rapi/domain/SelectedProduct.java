package com.portea.cpnen.rapi.domain;

import com.portea.cpnen.domain.ProductType;

public class SelectedProduct {

    private Integer id;
    private ProductType productType;
    private Integer count;
    private Integer purchaseCount;
    private Double unitCost;
    private String remarks;

    public SelectedProduct() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }


    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(Integer purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String inspectNullParameters() {

        if (this.id == null) {
            return "products.id";
        }

        if (this.count == null) {
            return "products.count";
        }

        if (this.productType == null) {
            return "products.productType";
        }

        if (this.purchaseCount == null) {
            return "products.purchaseCount";
        }

        if (this.unitCost == null) {
            return "products.unitCost";
        }

        return null;

    }

    @Override
    public String toString() {
        return "SelectedProduct{" +
                "count=" + count +
                ", id=" + id +
                ", productType=" + productType +
                ", purchaseCount=" + purchaseCount +
                ", unitCost=" + unitCost +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}