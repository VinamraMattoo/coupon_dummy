package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponProductMapDao;
import com.portea.cpnen.domain.CouponProductMap;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Set;

@JpaDao
@Dependent
public class CouponProductMapJpaDao extends BaseJpaDao<Integer, CouponProductMap> implements CouponProductMapDao {

    public CouponProductMapJpaDao() {
        super(CouponProductMap.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean isAnyProductApplicable(int couponId, Set<Integer> productIds) {
        Query query = entityManager.createNamedQuery("getCountOfApplicableProducts").setParameter("id",couponId);
        query.setParameter("ids",productIds);
        query.setParameter("applicable",true);
        Long applicableCount = (Long) query.getSingleResult();
        if(applicableCount > 0){
            return true;
        }
        return false;
    }

}
