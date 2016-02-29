package com.portea.common.config.dao;

import com.portea.common.config.domain.ConfigParam;
import com.portea.common.config.domain.ConfigTargetParam;
import com.portea.dao.Dao;

import javax.persistence.NoResultException;
import java.util.List;

public interface ConfigParamDao extends Dao<Integer, ConfigParam> {

    ConfigParam getConfigParam(Integer targetTypeId, ConfigTargetParam configTargetParam)  throws NoResultException;

    List<ConfigParam> getConfigParams(Integer targetTypeId)  throws NoResultException;

    List<ConfigParam> getConfigParams(com.portea.commp.smsen.domain.ConfigTargetType targetTypeName)  throws NoResultException;

}
