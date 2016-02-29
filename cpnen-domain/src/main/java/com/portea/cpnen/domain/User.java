package com.portea.cpnen.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * A read-only implementation for an existing table, as coupon engine system is not expected to write to the
 * underlying table.
 */
@Entity()
@Table(name = "auth_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login", columnDefinition = "varchar(128)")
    private String login;

    @Column(name = "password", columnDefinition = "varchar(128)")
    private String password;

    @Column(name = "name", columnDefinition = "varchar(128)")
    private String name;

    @Column(name = "firstName", columnDefinition = "varchar(45)")
    private String firstName;

    @Column(name = "middleName", columnDefinition = "varchar(45)")
    private String middleName;

    @Column(name = "lastName", columnDefinition = "varchar(45)")
    private String lastName;

    @Column(name = "phoneNumber", columnDefinition = "varchar(25)")
    private String phoneNumber;

    @Column(name = "mobileNumber", columnDefinition = "varchar(15)")
    private String mobileNumber;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "type", columnDefinition = "enum('staff', 'health_professional', 'patient')")
    private String type;

    public User() {}

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
