package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponCodeDao;
import com.portea.cpnen.domain.CouponCode;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

@JpaDao
@Dependent
public class CouponCodeJpaDao extends BaseJpaDao<Integer, CouponCode> implements CouponCodeDao {

    public CouponCodeJpaDao() {
        super(CouponCode.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<CouponCode> getCouponCodes(String[] codes) {
        List<String> codeList = Arrays.asList(codes);
        Query query = entityManager.createNamedQuery("getCouponCodes", CouponCode.class).setParameter("codes", codeList);
        List<CouponCode> list = query.getResultList();
        return list;
    }

    @Override
    public CouponCode getCouponCode(String code) {
        Query query = entityManager.createNamedQuery("getCouponCode", CouponCode.class).setParameter("code",code);
        List<CouponCode> list = query.getResultList();
        return list.get(0);
    }

}
