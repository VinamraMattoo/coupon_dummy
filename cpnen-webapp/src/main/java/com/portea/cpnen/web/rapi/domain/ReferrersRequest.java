package com.portea.cpnen.web.rapi.domain;


import com.portea.cpnen.domain.Brand;

import java.util.List;

public class ReferrersRequest {

    private List<BrandMapping> brands;

    private Boolean isB2B;

    private Boolean isB2C;

    public List<BrandMapping> getBrands() {
        return brands;
    }

    public void setBrands(List<BrandMapping> brands) {
        this.brands = brands;
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
}
