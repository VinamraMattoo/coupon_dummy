package com.portea.cpnen.rapi.domain;


import java.util.Collections;
import java.util.List;

public class ProductUpdateReq {

    private Double totalCost;
    private List<SelectedProduct> products;

    public ProductUpdateReq() {
        products = Collections.emptyList();
    }

    public List<SelectedProduct> getProducts() {
        return products;
    }

    public void setProducts(List<SelectedProduct> products) {
        this.products = products;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String inspectNullParameters() {

        if (this.totalCost == null) {
            return "totalCost";
        }

        if (this.products == null) {
            return "products";
        }

        return null;
    }

    @Override
    public String toString() {
        return "CouponDiscountRequestUpdateReq{" +
                "products=" + products +
                ", totalCost=" + totalCost +
                '}';
    }
}