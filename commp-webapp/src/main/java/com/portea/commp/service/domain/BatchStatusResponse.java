package com.portea.commp.service.domain;

public class BatchStatusResponse implements IStatusResponse {

    private Integer delivered;
    private Integer total;
    private Integer failed;
    private Integer pending;

    public BatchStatusResponse() {
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

    public Integer getFailed() {
        return failed;
    }

    public void setFailed(Integer failed) {
        this.failed = failed;
    }

    public void setPending(Integer pending) {
        this.pending = pending;
    }

    public Integer getPending() {
        return pending;
    }

    @Override
    public String toString() {
        return "BatchStatusResponse{" +
                "delivered=" + delivered +
                ", total=" + total +
                ", failed=" + failed +
                ", pending=" + pending +
                '}';
    }
}
