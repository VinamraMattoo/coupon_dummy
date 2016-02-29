package com.portea.commp.smsen.domain;

import javax.persistence.*;

@Entity
@Table(name = "smsen_sms_message_batch_record_mapping")
public class SmsMessageBatchRecordMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "record_id")
    @ManyToOne
    private SmsRecord smsRecord;

    @JoinColumn(name = "message_batch_id")
    @ManyToOne
    private SmsMessageBatch smsMessageBatch;

    public SmsMessageBatchRecordMapping() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SmsMessageBatch getSmsMessageBatch() {
        return smsMessageBatch;
    }

    public void setSmsMessageBatch(SmsMessageBatch smsMessageBatch) {
        this.smsMessageBatch = smsMessageBatch;
    }

    public SmsRecord getSmsRecord() {
        return smsRecord;
    }

    public void setSmsRecord(SmsRecord smsRecord) {
        this.smsRecord = smsRecord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsMessageBatchRecordMapping that = (SmsMessageBatchRecordMapping) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
