package com.portea.commp.web.rapi.domain;

import java.util.Date;

public class DailySentSmsData {
    private Integer delivered;
    private Integer failed;
    private Integer pending;
    private String day;
    private Date date;

    public void setDelivered(Integer delivered) {
        this.delivered = delivered;
    }

    public Integer getDelivered() {
        return delivered;
    }

    public void setFailed(Integer failed) {
        this.failed = failed;
    }

    public Integer getFailed() {
        return failed;
    }

    public void setPending(Integer pending) {
        this.pending = pending;
    }

    public Integer getPending() {
        return pending;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
