package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsSenderAuditDao;
import com.portea.commp.smsen.domain.SmsSender;
import com.portea.commp.smsen.domain.SmsSenderAudit;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Dependent
@JpaDao
public class SmsSenderAuditJpaDao extends BaseJpaDao<Integer, SmsSenderAudit> implements SmsSenderAuditDao {

    public SmsSenderAuditJpaDao() {
        super(SmsSenderAudit.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public SmsSenderAudit create(SmsSender smsSender) {
        SmsSenderAudit smsSenderAudit = new SmsSenderAudit();
        smsSenderAudit.setCreatedOn(smsSender.getLastUpdatedOn());
        smsSenderAudit.setCreatedBy(smsSender.getLastUpdatedBy());
        smsSenderAudit.setActive(smsSender.getActive());
        smsSenderAudit.setPassword(smsSender.getPassword());
        smsSenderAudit.setSmsSender(smsSender);
        return create(smsSenderAudit);
    }
}
