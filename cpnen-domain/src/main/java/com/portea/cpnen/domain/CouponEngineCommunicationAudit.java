package com.portea.cpnen.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * This entity captures all in/out communication with the coupon engine
 */
@Entity
@Table(name = "coupon_engine_comm_audit")
public class CouponEngineCommunicationAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ip4", length = 15)
    private String ip4;

    @Column(name = "request_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;

    @Column(name = "request_url",  length = 1024)
    private String requestUrl;

    @Column(name = "request_data", columnDefinition = "TEXT")
    @Lob
    private String requestData;

    @Column(name = "response_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date responseDate;

    @Column(name = "response_data", columnDefinition = "TEXT")
    @Lob
    private String responseData;

    public CouponEngineCommunicationAudit() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp4() {
        return ip4;
    }

    public void setIp4(String ip4) {
        this.ip4 = ip4;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CouponEngineCommunicationAudit that = (CouponEngineCommunicationAudit) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}