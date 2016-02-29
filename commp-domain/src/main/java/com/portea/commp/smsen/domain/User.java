package com.portea.commp.smsen.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "auth_users")
public class User {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "login", columnDefinition = "varchar(128)")
    private String login;

    @Column(name = "password", columnDefinition = "varchar(128)")
    private String password;

    @Column(name = "firstName", columnDefinition = "varchar(45)")
    private String firstName;

    @Column(name = "middleName", columnDefinition = "varchar(45)")
    private String middleName;

    @Column(name = "lastName", columnDefinition = "varchar(45)")
    private String lastName;

    @Column(name = "mobileNumber", columnDefinition = "varchar(15)")
    private String mobileNumber;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getPassword() {
        return password;
    }

    public String getMobileNumber() {
        return mobileNumber;
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
