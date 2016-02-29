package com.portea.cpnen.rapi.domain;


import java.util.List;

public class CouponCategoriesResponse {

    private List<String> categoryNames;

    public List<String> getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(List<String> categoryNames) {
        this.categoryNames = categoryNames;
    }

}
