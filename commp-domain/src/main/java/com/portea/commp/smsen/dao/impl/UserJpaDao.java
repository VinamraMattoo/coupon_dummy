package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.UserDao;
import com.portea.commp.smsen.domain.User;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
