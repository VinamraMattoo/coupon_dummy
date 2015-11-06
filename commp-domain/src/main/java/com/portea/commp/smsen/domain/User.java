package com.portea.commp.smsen.domain;

import javax.persistence.*;

@Entity
@Table(name = "auth_user")
public class User {

    @Id
    @Column(name = "id")
    private Integer id;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return !(getId() != null ? !getId().equals(user.getId()) : user.getId() != null);

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}
