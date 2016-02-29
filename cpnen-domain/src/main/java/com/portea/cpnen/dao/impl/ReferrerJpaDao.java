package com.portea.cpnen.dao.impl;


import com.portea.cpnen.dao.ReferrerDao;
import com.portea.cpnen.domain.Referrer;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@JpaDao
@Dependent
public class ReferrerJpaDao extends BaseJpaDao<Integer, Referrer> implements ReferrerDao {

    public ReferrerJpaDao() {
        super(Referrer.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Referrer> getReferrers(Integer offset, Integer limit) {
        Query query = entityManager.createNamedQuery("getReferrers", Referrer.class);
        if(limit != null){
            query.setFirstResult(offset);
            query.setMaxResults(limit);
        }
        return query.getResultList();
    }

    @Override
    public List<Referrer> getReferrersByBrandAndType(Integer brandId, String referrerType, Integer offset, Integer limit) {
        Query query = entityManager.createNamedQuery("getReferrersByBrandAndType", Referrer.class);
        query.setParameter("brandId", brandId);
        query.setParameter("referrerType", referrerType);

        if(limit != null){
            query.setFirstResult(offset);
            query.setMaxResults(limit);
        }

        return query.getResultList();
    }
}
