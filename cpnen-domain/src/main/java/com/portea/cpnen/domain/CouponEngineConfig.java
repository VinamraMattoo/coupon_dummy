package com.portea.cpnen.domain;

import javax.persistence.*;

@Entity
@Table(name = "coupon_engine_config")
public class CouponEngineConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name", columnDefinition = "varchar(256)")
	private String name;
	
	@Column(name = "value")
	private String value;

    public CouponEngineConfig() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
