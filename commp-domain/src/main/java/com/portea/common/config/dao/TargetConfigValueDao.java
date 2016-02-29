package com.portea.common.config.dao;

import com.portea.common.config.domain.ConfigParam;
import com.portea.common.config.domain.TargetConfigValue;
import com.portea.dao.Dao;

import javax.persistence.NoResultException;
import java.util.List;

public interface TargetConfigValueDao extends Dao<Integer,TargetConfigValue> {

    String getTargetConfigValue(Integer configParamId, Integer targetId) throws NoResultException;

    TargetConfigValue getTargetConfig(Integer configParamId, Integer targetId) throws NoResultException;

    List<TargetConfigValue> getTargetConfigValues();
}
