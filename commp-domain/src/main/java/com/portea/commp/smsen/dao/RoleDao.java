package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.Role;
import com.portea.dao.Dao;

import java.util.List;

public interface RoleDao extends Dao<Integer, Role> {

    List<Role> getRoles(List<Integer> roleIds);

    Integer getParentRoleId(Integer roleId);

    Integer getRoleId(String name);
}
