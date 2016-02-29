package com.portea.commp.smsen.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "smsen_sms_sender_audit")
public class SmsSenderAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "sender_id")
    @ManyToOne
    private SmsSender smsSender;

    @Column(name = "password", columnDefinition = "varchar(128)")
    private String password;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @JoinColumn(name = "created_by")
    @ManyToOne
    private User createdBy;

    @Column(name = "active")
    private Boolean active;

    public SmsSenderAudit() {
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User lastUpdatedBy) {
        this.createdBy = lastUpdatedBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date lastUpdatedOn) {
        this.createdOn = lastUpdatedOn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SmsSender getSmsSender() {
        return smsSender;
    }

    public void setSmsSender(SmsSender smsSender) {
        this.smsSender = smsSender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsSenderAudit that = (SmsSenderAudit) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

