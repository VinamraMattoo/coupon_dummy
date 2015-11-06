package com.portea.cpnen.web.rapi.domain;

import java.util.ArrayList;
import java.util.List;

public class CouponListResponse {

    private List<CouponDataVO> rows = new ArrayList<>();
    private Long total;

    public List<CouponDataVO> getRows() {
        return rows;
    }

    public void setRows(List<CouponDataVO> rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}

