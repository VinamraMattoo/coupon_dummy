package com.portea.cpnen.rapi.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CouponDiscountRequestCreateReq {

    int userId;
    List<SelectedProduct> products;
    String codes[];

    public CouponDiscountRequestCreateReq() {
        products = Collections.emptyList();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "CouponDiscountRequestCreateReq{" +
                "userId='" + userId + '\'' +
                ", products=" + products +
                ", codes=" + Arrays.toString(codes) +
                '}';
    }
}