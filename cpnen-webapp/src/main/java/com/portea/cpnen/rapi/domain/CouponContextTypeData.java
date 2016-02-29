package com.portea.cpnen.rapi.domain;

import com.portea.cpnen.domain.ContextType;

public class CouponContextTypeData {

    private ContextType contextType;

    private Long count;

    public ContextType getContextType() {
        return contextType;
    }

    public void setContextType(ContextType contextType) {
        this.contextType = contextType;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
