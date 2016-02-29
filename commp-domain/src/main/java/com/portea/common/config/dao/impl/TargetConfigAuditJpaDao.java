package com.portea.common.config.dao.impl;

import com.portea.common.config.dao.TargetConfigAuditDao;
import com.portea.common.config.domain.TargetConfig;
import com.portea.common.config.domain.TargetConfigAudit;
import com.portea.common.config.domain.TargetConfigValue;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@JpaDao
@Dependent
public class TargetConfigAuditJpaDao extends BaseJpaDao<Integer, TargetConfigAudit> implements TargetConfigAuditDao {

    public TargetConfigAuditJpaDao() {
        super(TargetConfigAudit.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public TargetConfigAudit create(TargetConfigValue targetConfigValue, String preValue) {
        TargetConfigAudit targetConfigAudit = new TargetConfigAudit();
        TargetConfig targetConfig = targetConfigValue.getTargetConfig();
        targetConfigAudit.setCreatedOn(new Date());
        targetConfigAudit.setCreatedBy(targetConfigValue.getLastUpdatedBy());
        targetConfigAudit.setActive(targetConfig.getActive());
        targetConfigAudit.setNewValue(targetConfigValue.getValue());
        targetConfigAudit.setOldValue(preValue);
        targetConfigAudit.setTargetConfig(targetConfig);
        return create(targetConfigAudit);
    }
}
