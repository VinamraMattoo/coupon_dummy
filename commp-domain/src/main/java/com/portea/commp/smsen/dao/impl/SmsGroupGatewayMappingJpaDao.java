package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsGroupGatewayMappingDao;
import com.portea.commp.smsen.domain.GatewayStatus;
import com.portea.commp.smsen.domain.SmsGroup;
import com.portea.commp.smsen.domain.SmsGroupGatewayMapping;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

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

    @Override
    public SmsGroupGatewayMapping getApplicableGateway(Integer groupId,
                                                       List<Integer> usedGatewayIds) throws NoResultException {

        if(usedGatewayIds.size() == 0) {
            usedGatewayIds.add(-1); // Adding '-1' so that the underlying query has something to work upon
        }

        Query query = entityManager.createNamedQuery("getApplicableGateway", SmsGroupGatewayMapping.class);
        query.setParameter("groupId", groupId);
        query.setParameter("status", GatewayStatus.ACTIVE);
        query.setParameter("usedGatewayIds", usedGatewayIds);
        query.setMaxResults(1);
        SmsGroupGatewayMapping smsGroupGatewayMapping = (SmsGroupGatewayMapping) query.getSingleResult();
        return smsGroupGatewayMapping;
    }

    @Override
    public List<SmsGroupGatewayMapping> getGatewayMappings(SmsGroup smsGroup) {
        Query query = entityManager.createNamedQuery("getGatewayMappingsForGroup", SmsGroupGatewayMapping.class);
        query.setParameter("group", smsGroup);
        query.setParameter("status", GatewayStatus.ACTIVE);
        return query.getResultList();
    }

    @Override
    public List<SmsGroupGatewayMapping> getMappings() {
        Query query = entityManager.createNamedQuery("getGroupGatewayMappings", SmsGroupGatewayMapping.class);
        return query.getResultList();
    }

    @Override
    public List<SmsGroupGatewayMapping> getMappings(SmsGroup smsGroup) {
        Query query = entityManager.createNamedQuery("getGroupGatewayMappingsForGroup", SmsGroupGatewayMapping.class);
        query.setParameter("group", smsGroup);
        return query.getResultList();
    }

    @Override
    public void swapMappingPriority(SmsGroupGatewayMapping firstGGMapping, SmsGroupGatewayMapping secondGGMapping) {

        String sql = "UPDATE smsen_sms_group_gateway_mapping SET priority = CASE id " +
                " WHEN "+firstGGMapping.getId()+" THEN "+secondGGMapping.getPriority() +
                " WHEN "+secondGGMapping.getId()+" THEN "+firstGGMapping.getPriority() +
                " END WHERE id IN ("+firstGGMapping.getId()+","+secondGGMapping.getId()+")";

        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }

    @Override
    public void removeMapping(SmsGroupGatewayMapping smsGroupGatewayMapping) {
        SmsGroup smsGroup = smsGroupGatewayMapping.getSmsGroup();
        Integer priority = smsGroupGatewayMapping.getPriority();
        delete(smsGroupGatewayMapping);
        Query query = entityManager.createNamedQuery("updateGroupGwPriority");
        query.setParameter("group", smsGroup);
        query.setParameter("priority", priority);
        query.executeUpdate();
    }
}
