package com.portea.commp.service.domain;

import java.util.Date;

public class SubmittedSmsVo {
    private Long count;
    private Date startDate;
    private Date endDate;

    public SubmittedSmsVo() {
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
