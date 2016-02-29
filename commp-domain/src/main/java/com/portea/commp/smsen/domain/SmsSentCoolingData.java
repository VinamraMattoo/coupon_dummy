package com.portea.commp.smsen.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Once a sms is sent to a user it's cooling period details are stored here. This data will be useful in determining
 * if a future sms being sent to a user is within the cooling period or not.
 */
@Entity
@Table(name = "smsen_sms_sent_cooling_data")
public class SmsSentCoolingData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Column(name = "mobile_number" , columnDefinition = "varchar(15)")
    private String mobileNumber;

    @Column(name = "message_hash")
    private Integer messageHash;

    @Column(name = "message" , columnDefinition = "varchar(1024)")
    private String message;

    @Column(name = "sms_type_name", columnDefinition = "varchar(32)")
    private String smsTypeName;

    @Column(name = "time_zone", columnDefinition = "varchar(32)")
    private String timeZone;

    @Column(name = "sent_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt;

    @Column(name = "sms_type_expires")
    @Temporal(TemporalType.TIMESTAMP)
    private Date smsTypeExpires;

    @Column(name = "msg_content_expires")
    @Temporal(TemporalType.TIMESTAMP)
    private Date msgContentExpires;

    public SmsSentCoolingData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Integer getMessageHash() {
        return messageHash;
    }

    public void setMessageHash(Integer messageHash) {
        this.messageHash = messageHash;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSmsTypeName() {
        return smsTypeName;
    }

    public void setSmsTypeName(String smsTypeName) {
        this.smsTypeName = smsTypeName;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getSmsTypeExpires() {
        return smsTypeExpires;
    }

    public void setSmsTypeExpires(Date smsTypeExpires) {
        this.smsTypeExpires = smsTypeExpires;
    }

    public Date getMsgContentExpires() {
        return msgContentExpires;
    }

    public void setMsgContentExpires(Date msgContentExpires) {
        this.msgContentExpires = msgContentExpires;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsSentCoolingData that = (SmsSentCoolingData) o;

        return !(getId() != null ? !getId().equals(that.getId()) : that.getId() != null);

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}
