package com.portea.cpnen.web.rapi.domain;

public class CouponListRequest {

    private Integer startIndex;
    private Integer requestedCount;


    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getRequestedCount() {
        return requestedCount;
    }

    public void setRequestedCount(Integer requestedCount) {
        this.requestedCount = requestedCount;
    }

}
