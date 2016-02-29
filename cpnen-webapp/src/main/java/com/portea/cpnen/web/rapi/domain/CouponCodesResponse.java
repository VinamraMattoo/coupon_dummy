package com.portea.cpnen.web.rapi.domain;

import java.util.ArrayList;
import java.util.List;

public class CouponCodesResponse {
    private List<CouponCodeVO> rows = new ArrayList<>();
    private Long total;

    public List<CouponCodeVO> getRows() {
        return rows;
    }

    public void setRows(List<CouponCodeVO> rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
