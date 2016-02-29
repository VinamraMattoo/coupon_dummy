package com.portea.commp.smsen.domain;

import javax.persistence.*;

@Entity
@Table(name = "smsen_sms_message_batch")
public class SmsMessageBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "lot_id")
    @ManyToOne
    private SmsLot smsLot;

    @JoinColumn(name = "type_id")
    @ManyToOne
    private SmsType smsType;

    public SmsMessageBatch() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SmsLot getSmsLot() {
        return smsLot;
    }

    public void setSmsLot(SmsLot smsLot) {
        this.smsLot = smsLot;
    }

    public SmsType getSmsType() {
        return smsType;
    }

    public void setSmsType(SmsType smsType) {
        this.smsType = smsType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsMessageBatch that = (SmsMessageBatch) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
