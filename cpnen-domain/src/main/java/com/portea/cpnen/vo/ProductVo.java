package com.portea.cpnen.vo;

import com.portea.cpnen.domain.ProductType;

public class ProductVo {

    private Integer id;
    private ProductType productType;
    private Integer count;
    private Integer purchaseCount;
    private Double unitCost;
    private String remarks;
    private Integer productBrand;

    public ProductVo() {}

    public Integer getId() {
        return id;
    }


    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
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

    public Integer getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(Integer productBrand) {
        this.productBrand = productBrand;
    }
}
