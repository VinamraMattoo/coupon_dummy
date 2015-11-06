package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CategoryDao;
import com.portea.cpnen.domain.Category;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@JpaDao
@Dependent
public class CategoryJpaDao extends BaseJpaDao<Integer, Category> implements CategoryDao {

    public CategoryJpaDao() {
        super(Category.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public int getCountOfCategories() {
        Query totalSizeQuery = entityManager.createNamedQuery("totalCategoryCount");
        Integer totalSize = ((Long) totalSizeQuery.getSingleResult()).intValue();
        return totalSize;
    }

    @Override
    public Integer getParentCategoryId(int categoryId) {
        Query query = entityManager.createNamedQuery("getParentCategoryID").setParameter("id",categoryId);
        List list = query.getResultList();
        Integer obj = (Integer) list.get(0);
        return obj;
    }

}
