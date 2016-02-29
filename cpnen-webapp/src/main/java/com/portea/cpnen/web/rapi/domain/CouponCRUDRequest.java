package com.portea.cpnen.web.rapi.domain;

import com.portea.cpnen.domain.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CouponCRUDRequest {
    private String name;
    private String description;
    private Boolean inclusive;
    private CouponCategory category;
    private CouponApplicationType applicationType;
    private ActorType actorType;
    private ContextType contextType;
    private Date applicableFrom;
    private Date applicableTill;
    private Integer applicableUseCount;
    private Integer transactionValMin;
    private Integer transactionValMax;
    private Integer discountAmountMin;
    private Integer discountAmountMax;
    private Boolean global;
    private Boolean isAllProducts;
    private Boolean isAllAreas;
    private Boolean isAllBrands;
    private Boolean isB2B;
    private Boolean isB2C;
    private Integer nthTime;
    private Boolean nthTimeRecurring;
    private Date lastUpdatedOn;
    private List<ProductMapping> productMapping;
    private List<BrandMapping> brandMapping;
    private List<AreaMapping> areaMapping;
    private List<ReferrerMapping> referrerMapping;
    private DiscountRule discountRule;
    private Boolean published;

    public CouponCRUDRequest() {
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

    public CouponCategory getCategory() {
        return category;
    }

    public void setCategory(CouponCategory category) {
        this.category = category;
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

    public Date getApplicableFrom() {
        return applicableFrom;
    }

    public void setApplicableFrom(Date applicableFrom) {
        this.applicableFrom = applicableFrom;
    }

    public Date getApplicableTill() {
        return applicableTill;
    }

    public void setApplicableTill(Date applicableTill) {
        this.applicableTill = applicableTill;
    }

    public Integer getApplicableUseCount() {
        return applicableUseCount;
    }

    public void setApplicableUseCount(Integer applicableUseCount) {
        this.applicableUseCount = applicableUseCount;
    }

    public Integer getTransactionValMin() {
        return transactionValMin;
    }

    public void setTransactionValMin(Integer transactionValMin) {
        this.transactionValMin = transactionValMin;
    }

    public Integer getTransactionValMax() {
        return transactionValMax;
    }

    public void setTransactionValMax(Integer transactionValMax) {
        this.transactionValMax = transactionValMax;
    }

    public Integer getDiscountAmountMin() {
        return discountAmountMin;
    }

    public void setDiscountAmountMin(Integer discountAmountMin) {
        this.discountAmountMin = discountAmountMin;
    }

    public Integer getDiscountAmountMax() {
        return discountAmountMax;
    }

    public void setDiscountAmountMax(Integer discountAmountMax) {
        this.discountAmountMax = discountAmountMax;
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

    public Date getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Date lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
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

    public List<ProductMapping> getProductMapping() {
        return productMapping;
    }

    public List<BrandMapping> getBrandMapping() {
        return brandMapping;
    }

    public DiscountRule getDiscountRule() {
        return discountRule;
    }

    public void setProductMapping(List<ProductMapping> productMapping) {
        this.productMapping = productMapping;
    }

    public void setBrandMapping(List<BrandMapping> brandMapping) {
        this.brandMapping = brandMapping;
    }

    public void setDiscountRule(DiscountRule discountRule) {
        this.discountRule = discountRule;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Boolean getIsAllAreas() {
        return isAllAreas;
    }

    public void setIsAllAreas(Boolean isAllAreas) {
        this.isAllAreas = isAllAreas;
    }

    public Boolean getIsAllBrands() {
        return isAllBrands;
    }

    public void setIsAllBrands(Boolean isAllBrands) {
        this.isAllBrands = isAllBrands;
    }

    public Boolean getIsB2B() {
        return isB2B;
    }

    public void setIsB2B(Boolean isB2B) {
        this.isB2B = isB2B;
    }

    public Boolean getIsB2C() {
        return isB2C;
    }

    public void setIsB2C(Boolean isB2C) {
        this.isB2C = isB2C;
    }

    public Boolean getIsAllProducts() {
        return isAllProducts;
    }

    public void setIsAllProducts(Boolean isAllProducts) {
        this.isAllProducts = isAllProducts;
    }

    public List<AreaMapping> getAreaMapping() {
        return areaMapping;
    }

    public void setAreaMapping(List<AreaMapping> areaMapping) {
        this.areaMapping = areaMapping;
    }

    public List<ReferrerMapping> getReferrerMapping() {
        return referrerMapping;
    }

    public void setReferrerMapping(List<ReferrerMapping> referrerMapping) {
        this.referrerMapping = referrerMapping;
    }

    public String inspectNullParameters() {
        if (name == null) {
            return "name";
        }
        if (actorType == null) {
            return "actorType";
        }
        if (inclusive == null) {
            return "inclusive";
        }
        if (category == null) {
            return "category";
        }
        if (applicationType == null) {
            return "applicationType";
        }
        if (contextType == null) {
            return "contextType";
        }
        if (applicableFrom == null) {
            return "applicableFrom";
        }
        if (applicableTill == null) {
            return "applicableTill";
        }
        if (global == null) {
            return "global";
        }
        if (productMapping == null) {
            return "productMapping";
        }
        if (brandMapping == null) {
            return "brandMapping";
        }
        if (discountRule == null) {
            return "discountRule";
        }
        if (published == null) {
            return "published";
        }

        return null;
    }

    @Override
    public String toString() {
        return "CouponCRUDRequest{" +
                "actorType=" + actorType +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", inclusive=" + inclusive +
                ", category=" + category +
                ", applicationType=" + applicationType +
                ", contextType=" + contextType +
                ", applicableFrom=" + applicableFrom +
                ", applicableTill=" + applicableTill +
                ", applicableUseCount=" + applicableUseCount +
                ", transactionValMin=" + transactionValMin +
                ", transactionValMax=" + transactionValMax +
                ", discountAmountMin=" + discountAmountMin +
                ", discountAmountMax=" + discountAmountMax +
                ", global=" + global +
                ", nthTime=" + nthTime +
                ", nthTimeRecurring=" + nthTimeRecurring +
                ", lastUpdatedOn=" + lastUpdatedOn +
                ", published=" + published +
                '}';
    }

    public static class ProductMapping {

        private Integer productId;
        private ProductType type;
        private String name;

        public ProductMapping() {
        }

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String inspectNullParameters() {

            if (productId == null) {
                return "productMapping.productId";
            }

            if (type == null) {
                return "productMapping.type";
            }

            if (name == null) {
                return "productMapping.name";
            }

            return null;
        }
    }

    public static class BrandMapping {

        private Integer brandId;

        public BrandMapping() {
        }

        public Integer getBrandId() {
            return brandId;
        }

        public void setBrandId(Integer brandId) {
            this.brandId = brandId;
        }

        public String inspectNullParameters() {

            if (brandId == null) {
                return "BrandMapping.brandId";
            }

            return null;
        }
    }

    public static class AreaMapping {

        private Integer areaId;

        public AreaMapping() {
        }

        public Integer getAreaId() {
            return areaId;
        }

        public void setAreaId(Integer areaId) {
            this.areaId = areaId;
        }

        public String inspectNullParameters() {

            if (areaId == null) {
                return "areaMapping.areaId";
            }

            return null;
        }
    }

    public static class ReferrerMapping {

        private Integer referrerId;
        private ReferrerType type;
        private String name;

        public ReferrerMapping() {
        }

        public Integer getReferrerId() {
            return referrerId;
        }

        public void setReferrerId(Integer referrerId) {
            this.referrerId = referrerId;
        }

        public ReferrerType getType() {
            return type;
        }

        public void setType(ReferrerType type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String inspectNullParameters() {

            if (referrerId == null) {
                return "referrerMapping.referrerId";
            }

            if (type == null) {
                return "referrerMapping.type";
            }

            if (name == null) {
                return "referrerMapping.name";
            }

            return null;
        }
    }

    public static class DiscountRule {

        private String description;
        private CouponDiscountingRuleType ruleType;
        private Integer discountFlatAmount;
        private Integer discountPercentage;

        public DiscountRule() {
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public CouponDiscountingRuleType getRuleType() {
            return ruleType;
        }

        public void setRuleType(CouponDiscountingRuleType ruleType) {
            this.ruleType = ruleType;
        }

        public Integer getDiscountFlatAmount() {
            return discountFlatAmount;
        }

        public void setDiscountFlatAmount(Integer discountFlatAmount) {
            this.discountFlatAmount = discountFlatAmount;
        }

        public Integer getDiscountPercentage() {
            return discountPercentage;
        }

        public void setDiscountPercentage(Integer discountPercentage) {
            this.discountPercentage = discountPercentage;
        }


        public String inspectNullParameters() {
            if (ruleType == null) {
                return "discountRule.ruleType";
            }
            if (ruleType.equals(CouponDiscountingRuleType.PERCENTAGE) && discountPercentage == null) {
                return "discountPercentage";
            }
            if (ruleType.equals(CouponDiscountingRuleType.FLAT) && discountFlatAmount == null) {
                return "discountFlatAmount";
            }
            return null;
        }
    }
}
