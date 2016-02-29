package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.PatientDetail;
import com.portea.dao.Dao;

import javax.persistence.NoResultException;

public interface PatientDetailDao extends Dao<Integer,PatientDetail> {

    PatientDetail getPatientDetails(Integer userId) throws NoResultException;
}
