package com.portea.commp.web.rapi.domain;

import com.portea.commp.smsen.domain.CoolingPeriod;
import com.portea.commp.smsen.domain.CoolingPeriodUnit;

public class CoolingPeriodVo {

    private CoolingPeriodUnit unit;
    private Integer value;

    public CoolingPeriodVo() {
    }

    public CoolingPeriodUnit getUnit() {
        return unit;
    }

    public void setUnit(CoolingPeriodUnit unit) {
        this.unit = unit;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public static CoolingPeriodVo build(CoolingPeriod coolingPeriod) {

        if (coolingPeriod == null) {
            return null;
        }
        CoolingPeriodVo coolingPeriodVo = new CoolingPeriodVo();
        coolingPeriodVo.setUnit(coolingPeriod.getUnit());
        coolingPeriodVo.setValue(coolingPeriod.getValue());
        return coolingPeriodVo;
    }

    public CoolingPeriod createCoolingPeriod() {
        CoolingPeriod coolingPeriod = new CoolingPeriod();
        coolingPeriod.setUnit(this.getUnit());
        coolingPeriod.setValue(this.getValue());
        return coolingPeriod;
    }
}
