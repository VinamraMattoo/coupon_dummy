package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.CouponCodeReservationDao;
import com.portea.cpnen.domain.CouponCodeReservation;
import com.portea.dao.impl.BaseJpaDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class CouponCodeReservationJpaDao extends BaseJpaDao<Integer, CouponCodeReservation> implements CouponCodeReservationDao {

    public CouponCodeReservationJpaDao() {
        super(CouponCodeReservation.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        //System.out.println("Successfully set the entity manager = " + entityManager);
        this.entityManager = entityManager;
    }

}
