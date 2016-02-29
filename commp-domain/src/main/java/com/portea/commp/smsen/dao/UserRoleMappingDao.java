package com.portea.commp.smsen.dao;


import com.portea.commp.smsen.domain.UserRoleMapping;
import com.portea.dao.Dao;

import java.util.List;

public interface UserRoleMappingDao extends Dao<Integer, UserRoleMapping> {

    List<Integer> getUserRoleIds(Integer userId);

}
