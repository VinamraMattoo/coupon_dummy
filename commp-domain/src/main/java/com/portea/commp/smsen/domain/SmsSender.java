package com.portea.commp.smsen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "smsen_sms_sender",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class SmsSender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(32)")
    private String name;

    @Column(name = "password", columnDefinition = "varchar(128)")
    private String password;

    @Column(name = "description", columnDefinition = "varchar(128)")
    private String description;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "email", columnDefinition = "varchar(64)")
    private String email;

    @JoinColumn(name = "created_by")
    @ManyToOne
    private User createdBy;

    @Column(name = "last_updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedOn;

    @JoinColumn(name = "last_updated_by")
    @ManyToOne
    private User lastUpdatedBy;

    @Column(name = "registered_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredOn;

    public SmsSender() {
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String key) {
        this.password = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Date registeredOn) {
        this.registeredOn = registeredOn;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(User lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Date lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsSender smsSender = (SmsSender) o;

        return id.equals(smsSender.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
