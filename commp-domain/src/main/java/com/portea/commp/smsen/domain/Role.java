package com.portea.commp.smsen.domain;

import javax.persistence.*;

/**
 * A read-only implementation for an existing table, as sms engine system is not expected to write to the
 * underlying table.
 */
@Entity
@Table(name = "auth_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "name", columnDefinition = "varchar(64)")
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Integer getParentId() {
        return parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return id.equals(role.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
