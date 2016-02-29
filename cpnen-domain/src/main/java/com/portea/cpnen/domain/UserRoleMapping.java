package com.portea.cpnen.domain;

import javax.persistence.*;

/**
 * A read-only implementation for an existing table, as coupon engine system is not expected to write to the
 * underlying table.
 */
@Entity
@Table(name = "auth_user_role_mapping")
public class UserRoleMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "role_id")
    private Integer roleId;

    public Integer getId() {
        return id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRoleMapping that = (UserRoleMapping) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
