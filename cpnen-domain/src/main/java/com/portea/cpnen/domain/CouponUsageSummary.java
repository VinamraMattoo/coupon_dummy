package com.portea.cpnen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "coupon_usage_summary")
public class CouponUsageSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(name = "total_use_cnt")
    private Integer totalUserCount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "first_use_date")
    private Date firstUseDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "latest_use_date")
    private Date latestUseDate;

    @Column(name = "trans_val_avg")
    private Integer transactionValAvg;

    @Column(name = "trans_val_highest")
    private Integer transactionValHighest;

    @Column(name = "trans_val_lowest")
    private Integer transactionValLowest;

    public CouponUsageSummary() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Integer getTotalUserCount() {
        return totalUserCount;
    }

    public void setTotalUserCount(Integer totalUserCount) {
        this.totalUserCount = totalUserCount;
    }

    public Date getFirstUseDate() {
        return firstUseDate;
    }

    public void setFirstUseDate(Date firstUseDate) {
        this.firstUseDate = firstUseDate;
    }

    public Date getLatestUseDate() {
        return latestUseDate;
    }

    public void setLatestUseDate(Date latestUseDate) {
        this.latestUseDate = latestUseDate;
    }

    public Integer getTransactionValAvg() {
        return transactionValAvg;
    }

    public void setTransactionValAvg(Integer transactionValAvg) {
        this.transactionValAvg = transactionValAvg;
    }

    public Integer getTransactionValHighest() {
        return transactionValHighest;
    }

    public void setTransactionValHighest(Integer transactionValHighest) {
        this.transactionValHighest = transactionValHighest;
    }

    public Integer getTransactionValLowest() {
        return transactionValLowest;
    }

    public void setTransactionValLowest(Integer transactionValLowest) {
        this.transactionValLowest = transactionValLowest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CouponUsageSummary that = (CouponUsageSummary) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
