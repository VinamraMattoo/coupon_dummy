package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.UserRoleMappingDao;
import com.portea.cpnen.domain.Role;
import com.portea.cpnen.domain.UserRoleMapping;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@JpaDao
@Dependent
public class UserRoleMappingJpaDao extends BaseJpaDao<Integer, UserRoleMapping> implements UserRoleMappingDao {

    public UserRoleMappingJpaDao() {
        super(UserRoleMapping.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Integer> getUserRoleIds(Integer userId) {
        Query query = entityManager.createNamedQuery("getUserRoles");
        query.setParameter("userId", userId);
        return (List<Integer>) query.getResultList();
    }
}
