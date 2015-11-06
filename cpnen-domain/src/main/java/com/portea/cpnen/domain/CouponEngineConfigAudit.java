package com.portea.cpnen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "coupon_engine_config_audit")
public class CouponEngineConfigAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "cpn_engine_conf_id")
	private CouponEngineConfig couponEngineConfig;
	
	@Column(name = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	@Column(name = "name", columnDefinition  = "varchar(256)")
	private String name;
	
	@Column(name = "old_value", columnDefinition  = "varchar(256)")
	private String oldValue;
	
	@Column(name = "new_value", columnDefinition  = "varchar(256)")
	private String newValue;

    public CouponEngineConfigAudit() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CouponEngineConfig getCouponEngineConfig() {
        return couponEngineConfig;
    }

    public void setCouponEngineConfig(CouponEngineConfig couponEngineConfig) {
        this.couponEngineConfig = couponEngineConfig;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
