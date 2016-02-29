package com.portea.cpnen.dao;

import com.portea.cpnen.domain.Role;
import com.portea.dao.Dao;

import java.util.List;

public interface RoleDao extends Dao<Integer, Role> {

    List<String> getRoles(List<Integer> roleIds);

    Integer getParentRoleId(Integer roleId);

    Integer getRoleId(String name);

    List<String> getRolesByUserId(Integer userId);
}
