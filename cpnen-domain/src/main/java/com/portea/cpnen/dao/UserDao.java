package com.portea.cpnen.dao;

import com.portea.cpnen.domain.User;
import com.portea.dao.Dao;

import javax.persistence.NoResultException;

public interface UserDao extends Dao<Integer, User> {

    String getPassword(String username) throws NoResultException;

    Integer getUserId(String username);
}
