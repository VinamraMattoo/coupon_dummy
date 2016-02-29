package com.portea.common.config.dao;

import com.portea.common.config.domain.ConfigTargetType;
import com.portea.dao.Dao;

import javax.persistence.NoResultException;

public interface ConfigTargetTypeDao extends Dao<Integer, ConfigTargetType> {

    Integer getIdForTargetType(com.portea.commp.smsen.domain.ConfigTargetType targetType) throws NoResultException;

}
