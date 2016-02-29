package com.portea.cpnen.web.rapi.domain;

import com.portea.cpnen.domain.ProductType;

public class ProductVO {
    private Integer id;
    private ProductType type;
    private String name;

    public ProductVO() {}

    public ProductVO(Integer id, String name, String type){
        this.id = id;
        this.name = name;
        ProductType[] productTypes = ProductType.values();

        for(ProductType productType : productTypes){
            if(productType.name().equals(type)){
                this.type = productType;
                break;
            }
        }

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
