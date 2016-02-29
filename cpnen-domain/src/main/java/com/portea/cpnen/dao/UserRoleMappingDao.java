package com.portea.cpnen.dao;

import com.portea.cpnen.domain.UserRoleMapping;
import com.portea.dao.Dao;

import java.util.List;

public interface UserRoleMappingDao extends Dao<Integer, UserRoleMapping> {

    List<Integer> getUserRoleIds(Integer userId);

}
