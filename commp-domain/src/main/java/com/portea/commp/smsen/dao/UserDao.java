package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.User;
import com.portea.dao.Dao;

import java.util.List;

public interface UserDao extends Dao<Integer, User> {

    User getUser(String username);

    String getPassword(String username);

    List<User> getUserForLoginNameMatch(String login, Integer limit);
}
