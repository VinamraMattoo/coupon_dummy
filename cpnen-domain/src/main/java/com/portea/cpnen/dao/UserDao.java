package com.portea.cpnen.dao;

import com.portea.cpnen.domain.User;
import com.portea.dao.Dao;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;

public interface UserDao extends Dao<Integer, User> {

    String getPassword(String username) throws NoResultException;

    Integer getUserId(String username) throws NoResultException,NonUniqueResultException;

    User getUser(String username) throws NoResultException,NonUniqueResultException;

    List<String> getUserNames(String name, Integer limit);
}
