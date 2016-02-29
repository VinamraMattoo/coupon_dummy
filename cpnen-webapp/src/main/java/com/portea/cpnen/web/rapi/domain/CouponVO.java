package com.portea.cpnen.web.rapi.domain;

import com.portea.cpnen.domain.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CouponVO {

    private Integer couponId;
    private String name;
    private String description;
    private Date createdOn;
    private String createdBy;
    private Date lastUpdatedOn;
    private String lastUpdatedBy;
    private Date deactivatedOn;
    private String deactivatedBy;
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
    private Date publishedOn;
    private String publishedBy;
    private List<ProductMapping> productMapping = new ArrayList<>();
    private List<BrandMapping> brandMapping = new ArrayList<>();
    private List<AreaMapping> areaMapping = new ArrayList<>();
    private List<ReferrerMapping> referrersMapping = new ArrayList<>();
    private DiscountRule discountRule = new DiscountRule();

    public CouponVO() {}

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

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Date lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getDeactivatedOn() {
        return deactivatedOn;
    }

    public void setDeactivatedOn(Date deactivatedOn) {
        this.deactivatedOn = deactivatedOn;
    }

    public String getDeactivatedBy() {
        return deactivatedBy;
    }

    public void setDeactivatedBy(String deactivatedBy) {
        this.deactivatedBy = deactivatedBy;
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

    public Boolean getIsAllProducts() {
        return isAllProducts;
    }

    public void setIsAllProducts(Boolean isAllProducts) {
        this.isAllProducts = isAllProducts;
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

    public Date getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public String getPublishedBy() {
        return publishedBy;
    }

    public void setPublishedBy(String publishedBy) {
        this.publishedBy = publishedBy;
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

    public List<AreaMapping> getAreaMapping() {
        return areaMapping;
    }

    public void setAreaMapping(List<AreaMapping> areaMapping) {
        this.areaMapping = areaMapping;
    }

    public List<ReferrerMapping> getReferrersMapping() {
        return referrersMapping;
    }

    public void setReferrersMapping(List<ReferrerMapping> referrersMapping) {
        this.referrersMapping = referrersMapping;
    }

    public void addProductMapping(Integer productId, ProductType type, String name) {

        ProductMapping productMapping = new ProductMapping();
        productMapping.setProductId(productId);
        productMapping.setType(type);

        productMapping.setName(name);
        this.productMapping.add(productMapping);
    }

    public void addReferrerMapping(Integer referrerId, String type, String name) {

        ReferrerMapping referrerMapping = new ReferrerMapping();
        referrerMapping.setReferrerId(referrerId);
        referrerMapping.setType(type);

        referrerMapping.setName(name);
        this.referrersMapping.add(referrerMapping);
    }

    public void addBrandMapping(Integer brandId, String name) {
        BrandMapping brandMapping = new BrandMapping();
        brandMapping.setBrandId(brandId);
        brandMapping.setName(name);
        this.brandMapping.add(brandMapping);
    }

    public void addAreaMapping(Integer areaId, String name) {
        AreaMapping areaMapping = new AreaMapping();
        areaMapping.setAreaId(areaId);
        areaMapping.setName(name);
        this.areaMapping.add(areaMapping);
    }

    public void addDiscountRule(String description, CouponDiscountingRuleType type, Integer flatAmount, Integer percent){
        discountRule = new DiscountRule();
        discountRule.setDescription(description);
        discountRule.setDiscountFlatAmount(flatAmount);
        discountRule.setDiscountPercentage(percent);
        discountRule.setRuleType(type);
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

    public static class ReferrerMapping {

        private Integer referrerId;
        private String type;
        private String name;

        public ReferrerMapping() {
        }

        public Integer getReferrerId() {
            return referrerId;
        }

        public void setReferrerId(Integer referrerId) {
            this.referrerId = referrerId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public static class BrandMapping {

        private Integer brandId;
        private String name;

        public BrandMapping(){}

        public Integer getBrandId() {
            return brandId;
        }

        public void setBrandId(Integer brandId) {
            this.brandId = brandId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static class AreaMapping {

        private Integer areaId;
        private String name;

        public AreaMapping(){}

        public Integer getAreaId() {
            return areaId;
        }

        public void setAreaId(Integer areaId) {
            this.areaId = areaId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static class DiscountRule {

        private String description;
        private CouponDiscountingRuleType ruleType;
        private Integer discountFlatAmount;
        private Integer discountPercentage;
        private Date createdOn;
        private String createdBy;
        private Integer id;

        public DiscountRule(){}

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

        public void setCreatedOn(Date createdOn) {
            this.createdOn = createdOn;
        }

        public Date getCreatedOn() {
            return createdOn;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getId() {
            return id;
        }


    }
}
