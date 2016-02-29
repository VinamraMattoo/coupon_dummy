package com.portea.commp.web.rapi.domain;

import com.portea.commp.smsen.domain.CoolingPeriod;
import com.portea.commp.web.rapi.exception.InvalidRequestException;

public class CoolingPeriodUpdateVo {

    private CoolingPeriodVo contentMatchCoolingPeriod;
    private CoolingPeriodVo typeMatchCoolingPeriod;

    public CoolingPeriodVo getContentMatchCoolingPeriod() {
        return contentMatchCoolingPeriod;
    }

    public void setContentMatchCoolingPeriod(CoolingPeriodVo contentMatchCoolingPeriod) {
        this.contentMatchCoolingPeriod = contentMatchCoolingPeriod;
    }

    public CoolingPeriodVo getTypeMatchCoolingPeriod() {
        return typeMatchCoolingPeriod;
    }

    public void setTypeMatchCoolingPeriod(CoolingPeriodVo typeMatchCoolingPeriod) {
        this.typeMatchCoolingPeriod = typeMatchCoolingPeriod;
    }

    public CoolingPeriod getCMCoolingPeriod() {
        if (contentMatchCoolingPeriod != null) {
            return contentMatchCoolingPeriod.createCoolingPeriod();
        }
        return null;
    }

    public CoolingPeriod getTMCoolingPeriod() {
        if (typeMatchCoolingPeriod != null) {
            return typeMatchCoolingPeriod.createCoolingPeriod();
        }
        return null;
    }

    public void validate() {
        if (contentMatchCoolingPeriod != null) {
            if (contentMatchCoolingPeriod.getValue() != null && contentMatchCoolingPeriod.getValue() < 0 ) {
                throw new InvalidRequestException("contentMatchCoolingPeriod.value",
                        String.valueOf(contentMatchCoolingPeriod.getValue()));
            }
        }
        if (typeMatchCoolingPeriod != null) {
            if (typeMatchCoolingPeriod.getValue() != null && typeMatchCoolingPeriod.getValue() < 0 ) {
                throw new InvalidRequestException("typeMatchCoolingPeriod.value",
                        String.valueOf(typeMatchCoolingPeriod.getValue()));
            }
        }
    }
}
