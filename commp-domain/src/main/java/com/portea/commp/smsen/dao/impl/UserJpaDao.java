package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.UserDao;
import com.portea.commp.smsen.domain.User;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.*;
import java.util.List;

@JpaDao
@Dependent
public class UserJpaDao extends BaseJpaDao<Integer, User> implements UserDao {

    public UserJpaDao() {
        super(User.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User getUser(String username) throws NoResultException,NonUniqueResultException {
        Query query = entityManager.createNamedQuery("getUser", User.class);
        query.setParameter("login", username);
        User user = (User) query.getSingleResult();
        return user;
    }

    @Override
    public String getPassword(String username) throws NoResultException {
        Query query = entityManager.createNamedQuery("getUserPassword");
        query.setParameter("login", username);
        String password = (String) query.getSingleResult();
        return password;
    }

    @Override
    public List<User> getUserForLoginNameMatch(String login, Integer limit) {
        Query query = entityManager.createNamedQuery("getUserForLoginNameMatch", User.class);
        query.setParameter("login", login);
        query.setMaxResults(limit);
        return query.getResultList();
    }
}
