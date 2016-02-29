package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponDiscountRequestCodeDao;
import com.portea.cpnen.domain.CouponDiscountRequest;
import com.portea.cpnen.domain.CouponDiscountRequestCode;
import com.portea.cpnen.domain.CouponDiscountRequestStatus;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@JpaDao
@Dependent
public class CouponDiscountReqCodeJpaDao extends BaseJpaDao<Integer, CouponDiscountRequestCode> implements CouponDiscountRequestCodeDao {

    public CouponDiscountReqCodeJpaDao() {
        super(CouponDiscountRequestCode.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<String> getCouponCodes(Integer cdrId) {
        Query query = entityManager.createNamedQuery("getCodesForReqId").setParameter("id", cdrId);
        return query.getResultList();
    }

    @Override
    public List<CouponDiscountRequestCode> getCouponCodes(CouponDiscountRequest couponDiscountRequest) {
        Query query = entityManager.createNamedQuery("getCodesForDiscountRequest", CouponDiscountRequestCode.class);
        query.setParameter("cdr", couponDiscountRequest);
        return query.getResultList();
    }

    @Override
    public CouponDiscountRequestCode getRequestCodeId(Integer cdrId, Integer codeId){
        Query query = entityManager.createNamedQuery("getRequestCodeForReqId", CouponDiscountRequestCode.class).setParameter("cdrId",cdrId).setParameter("codeId", codeId);
        List<CouponDiscountRequestCode> list = query.getResultList();
        return list.get(0);
    }


}
