package com.portea.commp.web.rapi.domain;

import java.util.Date;
import java.util.List;

public class DailySentSmsTypeData {
    private String day;
    private List<SmsDailyTypeData> smsTypes;
    private Date date;

    public DailySentSmsTypeData() {
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public List<SmsDailyTypeData> getSmsTypes() {
        return smsTypes;
    }

    public void setSmsTypes(List<SmsDailyTypeData> smsTypes) {
        this.smsTypes = smsTypes;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }


    public static class SmsDailyTypeData {
        private String typeName;
        private Integer count;

        public SmsDailyTypeData() {
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }
}
