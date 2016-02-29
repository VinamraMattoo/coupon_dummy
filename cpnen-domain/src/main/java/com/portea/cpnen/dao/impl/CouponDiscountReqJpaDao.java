package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponDiscountRequestDao;
import com.portea.cpnen.domain.CouponDiscountRequest;
import com.portea.cpnen.domain.CouponDiscountRequestStatus;
import com.portea.cpnen.domain.CouponDiscountingRule;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@JpaDao
@Dependent
public class CouponDiscountReqJpaDao extends BaseJpaDao<Integer, CouponDiscountRequest> implements CouponDiscountRequestDao {

    public CouponDiscountReqJpaDao() {
        super(CouponDiscountRequest.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Long getCouponDiscountRequestStatusDetails(Date startDate, Date endDate, CouponDiscountRequestStatus status) {
        Query query = entityManager.createNamedQuery("getCouponDiscountRequestStatus");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("status", status);
        return (Long) query.getSingleResult();
    }

}
