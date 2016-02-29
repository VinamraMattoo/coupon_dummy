package com.portea.cpnen.rapi.domain;

import com.portea.cpnen.domain.*;

import java.util.ArrayList;
import java.util.List;

public class CouponInfoResponse {

    private Integer couponId;
    private String name;
    private String description;
    private Boolean inclusive;
    private CouponApplicationType applicationType;
    private ActorType actorType;
    private ContextType contextType;
    private Integer applicableUseCount;
    private Boolean global;
    private Integer nthTime;
    private Boolean nthTimeRecurring;
    private List<ProductMapping> productMapping = new ArrayList<>();

    public CouponInfoResponse() {}

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getInclusive() {
        return inclusive;
    }

    public void setInclusive(Boolean inclusive) {
        this.inclusive = inclusive;
    }

    public CouponApplicationType getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(CouponApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public ActorType getActorType() {
        return actorType;
    }

    public void setActorType(ActorType actorType) {
        this.actorType = actorType;
    }

    public ContextType getContextType() {
        return contextType;
    }

    public void setContextType(ContextType contextType) {
        this.contextType = contextType;
    }

    public Integer getApplicableUseCount() {
        return applicableUseCount;
    }

    public void setApplicableUseCount(Integer applicableUseCount) {
        this.applicableUseCount = applicableUseCount;
    }

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public Integer getNthTime() {
        return nthTime;
    }

    public void setNthTime(Integer nthTime) {
        this.nthTime = nthTime;
    }

    public Boolean getNthTimeRecurring() {
        return nthTimeRecurring;
    }

    public void setNthTimeRecurring(Boolean nthTimeRecurring) {
        this.nthTimeRecurring = nthTimeRecurring;
    }

    public void setProductMapping(List<ProductMapping> productMapping) {
        this.productMapping = productMapping;
    }

    public void addProductMapping(Integer productId, ProductType type, String name) {

        ProductMapping productMapping = new ProductMapping();
        productMapping.setProductId(productId);
        productMapping.setType(type);

        productMapping.setName(name);
        this.productMapping.add(productMapping);
    }

    public static class ProductMapping {

        private Integer productId;
        private ProductType type;
        private String name;

        public ProductMapping(){}

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public ProductType getType() {
            return type;
        }

        public void setType(ProductType type) {
            this.type = type;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
