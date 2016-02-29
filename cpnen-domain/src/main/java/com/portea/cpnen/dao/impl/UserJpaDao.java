package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.UserDao;
import com.portea.cpnen.domain.User;
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
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public String getPassword(String username) throws NoResultException {
        Query query = entityManager.createNamedQuery("getUserPassword");
        query.setParameter("login", username);
        String password = (String) query.getSingleResult();
        return password;
    }

    @Override
    public Integer getUserId(String username) throws NoResultException,NonUniqueResultException {
        Query query = entityManager.createNamedQuery("getUserId");
        query.setParameter("name", username);
        Integer userId = (Integer) query.getSingleResult();
        return userId;
    }

    @Override
    public User getUser(String username) throws NoResultException,NonUniqueResultException {
        Query query = entityManager.createNamedQuery("getUser", User.class);
        query.setParameter("login", username);
        User user = (User) query.getSingleResult();
        return user;
    }

    @Override
    public List<String> getUserNames(String name, Integer limit) {
        Query query = entityManager.createNamedQuery("getUserNames");
        query.setParameter("name", name);
        query.setMaxResults(limit);
        return query.getResultList();
    }

}
