package com.portea.cpnen.domain;

import javax.persistence.*;

/**
 * A read-only implementation for an existing table, as coupon engine system is not expected to write to the
 * underlying table.
 */
@Entity
@Table(name = "packages")
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(64)")
    private String name;

    @Column(name = "description", columnDefinition = "varchar(512)")
    private String description;

    @Column(name = "deleted")
    private Boolean deleted;

    public Package() {}

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Package aPackage = (Package) o;

        return !(id != null ? !id.equals(aPackage.id) : aPackage.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
