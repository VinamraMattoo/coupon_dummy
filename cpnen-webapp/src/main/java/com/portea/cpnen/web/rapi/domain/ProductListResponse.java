package com.portea.cpnen.web.rapi.domain;


import com.portea.cpnen.domain.ProductType;

import java.util.ArrayList;
import java.util.List;

public class ProductListResponse {

    private Long total;
    private Integer offset;
    private List<ProductVO> rows = new ArrayList<>();

    public ProductListResponse() {}

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<ProductVO> getRows() {
        return rows;
    }

    public void setRows(List<ProductVO> rows) {
        this.rows = rows;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
