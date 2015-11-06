package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.UserDao;
import com.portea.cpnen.domain.User;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
        query.setParameter("name", username);
        String password = (String) query.getSingleResult();
        return password;
    }

    @Override
    public Integer getUserId(String username) {
        Query query = entityManager.createNamedQuery("getUserId");
        query.setParameter("name", username);
        Integer userId = (Integer) query.getSingleResult();
        return userId;
    }

}
