package com.portea.cpnen.rapi.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CouponDiscountRequestUpdateReq {

    private String userId;
    private List<SelectedProduct> products;
    private String codes[];
    private CouponDiscountRequestStatus status;

    public CouponDiscountRequestUpdateReq() {
        products = Collections.emptyList();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public CouponDiscountRequestStatus getStatus() {
        return status;
    }

    public void setStatus(CouponDiscountRequestStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CouponDiscountRequestUpdateReq{" +
                "userId='" + userId + '\'' +
                ", products=" + products +
                ", codes=" + Arrays.toString(codes) +
                ", status=" + status +
                '}';
    }
}