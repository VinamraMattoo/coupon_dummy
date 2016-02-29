package com.portea.cpnen.rapi.domain;

import com.portea.cpnen.domain.ContextType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CouponDiscountRequestCreateReq {

    private Integer requesterId;
    private Integer beneficiaryId;
    private Integer areaId;
    private Integer referrerId;
    private Integer patientBrandId;
    private ContextType contextType;
    private List<SelectedProduct> products;
    private String codes[];
    private Double totalCost;
    private Boolean withinSubscription;
    private String sourceName;

    public CouponDiscountRequestCreateReq() {
        products = Collections.emptyList();
    }

    public Integer getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(Integer requesterId) {
        this.requesterId = requesterId;
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

    public Integer getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(Integer beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public ContextType getContextType() {
        return contextType;
    }

    public void setContextType(ContextType contextType) {
        this.contextType = contextType;
    }

    public Boolean getWithinSubscription() {
        return withinSubscription;
    }

    public void setWithinSubscription(Boolean withinSubscription) {
        this.withinSubscription = withinSubscription;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Integer getReferrerId() {
        return referrerId;
    }

    public void setReferrerId(Integer referrerId) {
        this.referrerId = referrerId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getPatientBrandId() {
        return patientBrandId;
    }

    public void setPatientBrandId(Integer patientBrandId) {
        this.patientBrandId = patientBrandId;
    }

    public String inspectNullParameters() {

        if (this.beneficiaryId == null) {
            return "beneficiaryId";
        }

        if (this.requesterId == null) {
            return "requesterId";
        }

        if (this.referrerId == null) {
            return "referrerId";
        }

        if (this.areaId == null) {
            return "areaId";
        }

        if (this.patientBrandId == null) {
            return "patientBrandId";
        }

        if (this.contextType == null) {
            return "contextType";
        }

        if (this.products == null) {
            return "products";
        }

        if (this.codes == null) {
            return "codes";
        }

        if (this.totalCost == null) {
            return "totalCost";
        }

        if (this.withinSubscription == null) {
            return "withinSubscription";
        }

        if (this.sourceName == null) {
            return "sourceName";
        }
        return null;
    }

    @Override
    public String toString() {
        return "CouponDiscountRequestCreateReq{" +
                "beneficiaryId=" + beneficiaryId +
                ", requesterId=" + requesterId +
                ", contextType=" + contextType +
                ", products=" + products +
                ", codes=" + Arrays.toString(codes) +
                ", totalCost=" + totalCost +
                ", withinSubscription=" + withinSubscription +
                ", sourceName='" + sourceName + '\'' +
                '}';
    }
}