package com.portea.commp.smsen.domain;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class CoolingPeriod {

    @Enumerated(EnumType.STRING)
    private CoolingPeriodUnit unit;

    private int value;

    public CoolingPeriod() {
    }

    public CoolingPeriodUnit getUnit() {
        return unit;
    }

    public void setUnit(CoolingPeriodUnit unit) {
        this.unit = unit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoolingPeriod that = (CoolingPeriod) o;

        if (getValue() != that.getValue()) return false;
        return getUnit() == that.getUnit();

    }

    @Override
    public int hashCode() {
        int result = getUnit() != null ? getUnit().hashCode() : 0;
        result = 31 * result + getValue();
        return result;
    }

}
