package com.portea.commp.smsen.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * This entity keeps track of how many sms are being sent in a duration.
 * This data will be useful in determining, if a future sms being sent to a user
 * is within the acceptable number of sms that can be sent per a given duration.
 */
@Entity
@Table(name = "smsen_sms_user_throttling_data",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"mobile_number", "sms_type_name", "begin_duration", "end_duration"})})
public class SmsUserThrottlingData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Column(name = "mobile_number" , columnDefinition = "varchar(15)")
    private String mobileNumber;

    @Column(name = "sms_type_name", columnDefinition = "varchar(32)")
    private String smsTypeName;

    @Column(name = "last_sent_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSentAt;

    @Column(name = "begin_duration")
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginDuration;

    @Column(name = "end_duration")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDuration;

    @Column(name = "sent_count")
    private Integer sentCount;

    @Version
    private Long version;

    public SmsUserThrottlingData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getSmsTypeName() {
        return smsTypeName;
    }

    public void setSmsTypeName(String smsTypeName) {
        this.smsTypeName = smsTypeName;
    }

    public Date getLastSentAt() {
        return lastSentAt;
    }

    public void setLastSentAt(Date lastSentAt) {
        this.lastSentAt = lastSentAt;
    }

    public Date getBeginDuration() {
        return beginDuration;
    }

    public void setBeginDuration(Date beginDuration) {
        this.beginDuration = beginDuration;
    }

    public Date getEndDuration() {
        return endDuration;
    }

    public void setEndDuration(Date endDuration) {
        this.endDuration = endDuration;
    }

    public Integer getSentCount() {
        return sentCount;
    }

    public void setSentCount(Integer sentCount) {
        this.sentCount = sentCount;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsUserThrottlingData that = (SmsUserThrottlingData) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
