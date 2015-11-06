package com.portea.cpnen.domain;

import javax.persistence.*;

@Entity
@Table(name = "test_product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name", columnDefinition = "varchar(128)")
	private String name;
	
	@JoinColumn(name = "parent_product_id")
    @ManyToOne
	private Product parent;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "unit_price")
	private Integer unitPrice;

    public Product() {}

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

    public Product getParent() {
        return parent;
    }

    public void setParent(Product parent) {
        this.parent = parent;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }
}
