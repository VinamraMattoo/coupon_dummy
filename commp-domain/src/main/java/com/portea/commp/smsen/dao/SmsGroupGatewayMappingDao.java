package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsGateway;
import com.portea.commp.smsen.domain.SmsGroup;
import com.portea.commp.smsen.domain.SmsGroupGatewayMapping;
import com.portea.dao.Dao;

import javax.persistence.NoResultException;
import java.util.List;

public interface SmsGroupGatewayMappingDao extends Dao<Integer, SmsGroupGatewayMapping>{

    /**
     * Returns an active gateway (unused for the current SMS being sent) having highest priority.
     *
     * @param groupId id of the group for which gateway mappings will be searched
     * @param usedGatewayIds list of gateway ids which have already been used for sending an SMS
     */
    SmsGroupGatewayMapping getApplicableGateway(Integer groupId, List<Integer> usedGatewayIds) throws NoResultException;

    List<SmsGroupGatewayMapping> getGatewayMappings(SmsGroup smsGroup);

    List<SmsGroupGatewayMapping> getMappings();

    List<SmsGroupGatewayMapping> getMappings(SmsGroup smsGroup);

    void swapMappingPriority(SmsGroupGatewayMapping firstGGMapping, SmsGroupGatewayMapping secondGGMapping);

    void removeMapping(SmsGroupGatewayMapping smsGroupGatewayMapping);
}
