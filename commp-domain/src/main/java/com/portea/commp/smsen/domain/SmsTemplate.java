package com.portea.commp.smsen.domain;

import javax.persistence.*;

@Entity
@Table(name = "sms_template")
public class SmsTemplate {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "templateId", columnDefinition = "varchar(255)")
    private String templateId;

    @Column(name = "templateName", columnDefinition = "varchar(256)")
    private String name;

    @Column(name = "is_active")
    private boolean isActive;

    /**
     * This column keeps the id of an SmsGroup. As this is an existing table, relationship is not
     * being defined.
     */
    @Column(name = "sms_group_id")
    private Integer smsGroupId;

    public SmsTemplate() {
    }

    public Integer getId() {
        return id;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

    public Integer getSmsGroupId() {
        return smsGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsTemplate that = (SmsTemplate) o;

        return !(getId() != null ? !getId().equals(that.getId()) : that.getId() != null);

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}
