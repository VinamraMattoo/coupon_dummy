package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.PatientDetailDao;
import com.portea.commp.smsen.domain.PatientDetail;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
