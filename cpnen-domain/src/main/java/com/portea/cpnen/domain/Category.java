package com.portea.cpnen.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "test_category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name", columnDefinition = "varchar(128)")
	private String name;
	
	@JoinColumn(name = "parent_category_id")
    @ManyToOne
	private Category parent;
	
	@OneToMany(mappedBy = "category")
	private Set<CouponCategoryMap> couponCategoryMappings = new HashSet<>();
	
	@OneToMany(mappedBy = "category")
	private Set<Product> listProducts = new HashSet<>();

	public Category() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<CouponCategoryMap> getCouponCategoryMappings() {
		return couponCategoryMappings;
	}

	public void setCouponCategoryMappings(Set<CouponCategoryMap> couponCategoryMappings) {
		this.couponCategoryMappings = couponCategoryMappings;
	}

	public Set<Product> getListProducts() {
		return listProducts;
	}

	public void setListProducts(Set<Product> listProducts) {
		this.listProducts = listProducts;
	}
}