package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.PatientDetailDao;
import com.portea.commp.smsen.domain.PatientDetail;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@JpaDao
@Dependent
public class PatientDetailJpaDao extends BaseJpaDao<Integer, PatientDetail> implements PatientDetailDao {

    public PatientDetailJpaDao() {
        super(PatientDetail.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public PatientDetail getPatientDetails(Integer loginId) throws NoResultException{
        Query query = entityManager.createNamedQuery("getPatientDetail");
        query.setParameter("loginId", loginId);
        return (PatientDetail) query.getSingleResult();
    }
}
