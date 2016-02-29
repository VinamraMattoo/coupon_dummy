package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.RoleDao;
import com.portea.cpnen.domain.Role;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@JpaDao
@Dependent
public class RoleJpaDao extends BaseJpaDao<Integer, Role> implements RoleDao {

    public RoleJpaDao() {
        super(Role.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<String> getRoles(List<Integer> roleIds) {
        Query query = entityManager.createNamedQuery("getRoles");
        query.setParameter("roleIds", roleIds);
        return query.getResultList();
    }

    @Override
    public Integer getParentRoleId(Integer roleId) {
        Query query = entityManager.createNamedQuery("getParentRoleId");
        query.setParameter("id", roleId);
        return (Integer) query.getSingleResult();
    }

    @Override
    public Integer getRoleId(String name) {
        Query query = entityManager.createNamedQuery("getRoleId");
        query.setParameter("name", name);
        return (Integer) query.getSingleResult();
    }

    @Override
    public List<String> getRolesByUserId(Integer userId) {
        Query query = entityManager.createNamedQuery("getRolesForUserId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
