package com.portea.commp.web.rapi.domain;

import com.portea.commp.smsen.domain.SmsSource;

public class SmsSourceUsageData {

    private SmsSource source;
    private Long count;

    public SmsSourceUsageData() {
    }

    public SmsSource getSource() {
        return source;
    }

    public void setSource(SmsSource source) {
        this.source = source;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
