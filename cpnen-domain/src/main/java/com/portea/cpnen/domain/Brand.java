package com.portea.cpnen.domain;

import javax.persistence.*;

@Entity
@Table(name = "brands")
public class Brand {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(64)")
    private String name;

    public Brand() {}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brand brand = (Brand) o;

        return id.equals(brand.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
