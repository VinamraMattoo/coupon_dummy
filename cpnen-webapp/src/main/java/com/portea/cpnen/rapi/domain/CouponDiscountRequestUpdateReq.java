package com.portea.cpnen.rapi.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CouponDiscountRequestUpdateReq {

    private Double totalCost;
    private List<SelectedProduct> products;
    private String codes[];

    public CouponDiscountRequestUpdateReq() {
        products = Collections.emptyList();
    }

    public List<SelectedProduct> getProducts() {
        return products;
    }

    public void setProducts(List<SelectedProduct> products) {
        this.products = products;
    }

    public String[] getCodes() {
        return codes;
    }

    public void setCodes(String[] codes) {
        this.codes = codes;
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

        if (this.codes == null) {
            return "codes";
        }

        if (this.products == null) {
            return "products";
        }

        return null;
    }

    @Override
    public String toString() {
        return "CouponDiscountRequestUpdateReq{" +
                " products=" + products +
                ", codes=" + Arrays.toString(codes) +
                ", totalCost=" + totalCost +
                '}';
    }
}