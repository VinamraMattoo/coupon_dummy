package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsGroupGatewayMappingDao;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;
import com.portea.commp.smsen.domain.SmsGroupGatewayMapping;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JpaDao
@Dependent
public class SmsGroupGatewayMappingJpaDao extends BaseJpaDao<Integer, SmsGroupGatewayMapping> implements SmsGroupGatewayMappingDao {

    public SmsGroupGatewayMappingJpaDao() {
        super(SmsGroupGatewayMapping.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
