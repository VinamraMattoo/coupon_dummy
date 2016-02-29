package com.portea.commp.service.domain;

public class LotStatusResponse implements IStatusResponse {

    private Integer failed;
    private Integer pending;
    private Integer delivered;
    private Integer total;

    public LotStatusResponse() {
    }

    public Integer getDelivered() {
        return delivered;
    }

    public void setDelivered(Integer delivered) {
        this.delivered = delivered;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public Integer getFailed() {
        return failed;
    }

    @Override
    public void setFailed(Integer failed) {
        this.failed = failed;
    }

    public Integer getPending() {
        return pending;
    }

    public void setPending(Integer pending) {
        this.pending = pending;
    }

    @Override
    public String toString() {
        return "LotStatusResponse{" +
                "failed=" + failed +
                ", pending=" + pending +
                ", delivered=" + delivered +
                ", total=" + total +
                '}';
    }
}
