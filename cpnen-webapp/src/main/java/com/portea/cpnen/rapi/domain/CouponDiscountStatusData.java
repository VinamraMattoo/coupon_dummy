package com.portea.cpnen.rapi.domain;

import com.portea.cpnen.domain.CouponDiscountRequestStatus;

public class CouponDiscountStatusData {

    private Long requested;

    private Long cancelled;

    private Long applied;

    private String dayOfWeek;

    public Long getRequested() {
        return requested;
    }

    public void setRequested(Long requested) {
        this.requested = requested;
    }

    public Long getCancelled() {
        return cancelled;
    }

    public void setCancelled(Long cancelled) {
        this.cancelled = cancelled;
    }

    public Long getApplied() {
        return applied;
    }

    public void setApplied(Long applied) {
        this.applied = applied;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

}
