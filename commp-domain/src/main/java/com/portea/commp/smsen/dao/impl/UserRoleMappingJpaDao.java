package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.UserRoleMappingDao;
import com.portea.commp.smsen.domain.UserRoleMapping;
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
    @PersistenceContext(name = "commpPU")
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
