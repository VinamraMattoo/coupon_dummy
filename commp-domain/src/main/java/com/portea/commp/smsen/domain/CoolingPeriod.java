package com.portea.commp.smsen.domain;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Embeddable
public class CoolingPeriod {

    @Enumerated(EnumType.STRING)
    private CoolingPeriodUnit unit;

    private Integer value;

    public CoolingPeriod() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoolingPeriod that = (CoolingPeriod) o;

        return (Objects.equals(getValue(), that.getValue())) && getUnit() == that.getUnit();

    }

    @Override
    public int hashCode() {
        int result = getUnit() != null ? getUnit().hashCode() : 0;
        result = 31 * result + getValue();
        return result;
    }

}
