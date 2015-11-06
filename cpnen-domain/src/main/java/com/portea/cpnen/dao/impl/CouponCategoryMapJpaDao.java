package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponCategoryMapDao;
import com.portea.cpnen.domain.CouponCategoryMap;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Set;

@JpaDao
@Dependent
public class CouponCategoryMapJpaDao extends BaseJpaDao<Integer, CouponCategoryMap> implements CouponCategoryMapDao {

    public CouponCategoryMapJpaDao() {
        super(CouponCategoryMap.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean isAnyCategoryApplicable(int couponId, Set<Integer> categoryIds) {
        Query query = entityManager.createNamedQuery("getCountOfApplicableCategories").setParameter("id",couponId);
        query.setParameter("ids", categoryIds);
        query.setParameter("applicable",true);
        Long applicableCount = (Long) query.getSingleResult();
        if(applicableCount > 0){
            return true;
        }
        return false;
    }

}
