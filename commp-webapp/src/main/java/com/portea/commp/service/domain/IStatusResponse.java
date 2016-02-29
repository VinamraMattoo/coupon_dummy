package com.portea.commp.service.domain;

public interface IStatusResponse {

    public Integer getDelivered();
    public Integer getTotal();
    public Integer getFailed();
    public Integer getPending();
    public void setDelivered(Integer delivered);
    public void setTotal(Integer total);
    public void setFailed(Integer failure);
    public void setPending(Integer pending);
}
