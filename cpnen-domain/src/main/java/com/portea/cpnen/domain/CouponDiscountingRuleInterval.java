package com.portea.cpnen.domain;

import javax.persistence.*;

@Entity
@Table(name = "coupon_discounting_rule_interval")
public class CouponDiscountingRuleInterval {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "rule_id")
	private CouponDiscountingRule couponDiscRule;
	
	@Column(name = "type")
    @Enumerated(EnumType.STRING)
	private IntervalType type;
	
	@Column(name = "interval_start")
	private Integer intervalStart;
	
	@Column(name = "interval_end")
	private Integer intervalEnd;
	
	@Column(name = "value")
	private Integer value;

    public CouponDiscountingRuleInterval() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CouponDiscountingRule getCouponDiscRule() {
        return couponDiscRule;
    }

    public void setCouponDiscRule(CouponDiscountingRule couponDiscRule) {
        this.couponDiscRule = couponDiscRule;
    }

    public IntervalType getType() {
        return type;
    }

    public void setType(IntervalType type) {
        this.type = type;
    }

    public Integer getIntervalStart() {
        return intervalStart;
    }

    public void setIntervalStart(Integer intervalStart) {
        this.intervalStart = intervalStart;
    }

    public Integer getIntervalEnd() {
        return intervalEnd;
    }

    public void setIntervalEnd(Integer intervalEnd) {
        this.intervalEnd = intervalEnd;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}