package com.portea.commp.smsen.gw;

import com.portea.common.config.dao.ConfigParamDao;
import com.portea.common.config.dao.ConfigTargetTypeDao;
import com.portea.common.config.dao.TargetConfigValueDao;
import com.portea.common.config.domain.ConfigEngineParam;
import com.portea.common.config.domain.ConfigParam;
import com.portea.commp.service.ejb.SmsEngineUtil;
import com.portea.commp.smsen.dao.SmsGatewayDao;
import com.portea.commp.smsen.dao.SmsGroupGatewayMappingDao;
import com.portea.commp.smsen.domain.ConfigTargetType;
import com.portea.commp.smsen.domain.SmsGateway;
import com.portea.commp.smsen.domain.SmsGroupGatewayMapping;
import com.portea.commp.smsen.vo.SmsInAssembly;
import com.portea.dao.JpaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.InternalServerErrorException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A manager to manage the collection of available gateways. This manager can be used to retrieve
 * an available gateway
 */
@Startup
@Singleton
public class SmsGatewayManager {

    @Inject @JpaDao
    private SmsGroupGatewayMappingDao smsGroupGatewayMappingDao;

    @Inject @JpaDao
    private ConfigTargetTypeDao configTargetTypeDao;

    @Inject @JpaDao
    private ConfigParamDao configParamDao;

    @Inject @JpaDao
    private TargetConfigValueDao targetConfigValueDao;

    @Inject @JpaDao
    private SmsGatewayDao smsGatewayDao;

    @EJB
    private SmsEngineUtil smsEngineUtil;

    private Map<String, Class<? extends  SmsGatewayHandler>> gatewayNameClassMapping = new HashMap<>();

    private static final Logger LOG = LoggerFactory.getLogger(SmsGatewayManager.class);

    public SmsGatewayManager() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

    }

    @PostConstruct
    protected void setupGatewayMap() {

        gatewayNameClassMapping.put("PINNACLE",PinnacleSmsGatewayHandler.class);
        gatewayNameClassMapping.put("MGAGE", MgageSmsGatewayHandler.class);
        gatewayNameClassMapping.put("MGAGE_PRIORITY", MgagePrioritySmsGatewayHandler.class);
        gatewayNameClassMapping.put("DUMMY_GATEWAY_1", MockSmsGatewayHandler.class);
        gatewayNameClassMapping.put("DUMMY_GATEWAY_2", PMockSmsGatewayHandler.class);

    }

    /**
     * Returns an SmsGatewayHandler that can be used by an SmsWorker. This method filters the gateways
     * currently reported as unavailable.
     *
     * @param smsGroupGatewayMapping the mapping based on which handler needs to be created
     * @return a SmsGatewayHandler
     */
    public SmsGatewayHandler getGatewayHandler(SmsGroupGatewayMapping smsGroupGatewayMapping) {

        Integer givenGatewayId = smsGroupGatewayMapping.getSmsGateway().getId();

        SmsGatewayHandler smsGatewayHandler = getGatewayHandler(givenGatewayId);

        return smsGatewayHandler;
    }

    public SmsGatewayHandler getGatewayHandler(Integer givenGatewayId) {
        SmsGateway smsGateway = smsGatewayDao.find(givenGatewayId);

        SmsGatewayHandler smsGatewayHandler = null;

        if(gatewayNameClassMapping.get(smsGateway.getName()) != null) {

            Class<? extends SmsGatewayHandler> handler = gatewayNameClassMapping.get(smsGateway.getName());
            Integer gatewayId = smsGateway.getId();
            Map<String, String> configData = new HashMap<>();

            List<ConfigParam> configGatewayParams = configParamDao.getConfigParams(ConfigTargetType.SMS_GATEWAY);

            configGatewayParams.forEach((configParam) -> {
                try{
                    String value = targetConfigValueDao.getTargetConfigValue(configParam.getId(), gatewayId);
                    configData.put(configParam.getName(), value);
                } catch (NoResultException e) {
                    /*LOG.info("No value found for config param " + configParam.getName() +
                            " and target type " + configParam.getConfigTargetType().getTargetType());*/
                }
            });

            try {
                smsGatewayHandler = handler.getConstructor(Map.class).newInstance(configData);
            }
            catch (InstantiationException | IllegalAccessException |  InvocationTargetException e) {
                throw new InternalServerErrorException("Could not create SMS handler instance", e);
            }
            catch (NoSuchMethodException e) {
                throw new InternalServerErrorException("Could not find appropriate handler class", e);
            }
        }else{
            throw new InternalServerErrorException("Given SmsGateway name "+smsGateway.getName()
                    + " is not registered in gateway class mapping");
        }

        return smsGatewayHandler;
    }

    /**
     * Returns a group gateway mapping based on gateway usage policy except first time
     * when it gets the group gateway data from database and returns highest priority
     * mapping.
     */
    public SmsGroupGatewayMapping getSmsGroupGatewayMapping(SmsInAssembly smsInAssembly) {

        if (smsInAssembly.getGatewayMappings().isEmpty()) {

            List<SmsGroupGatewayMapping> smsGroupGatewayMappings =
                    smsGroupGatewayMappingDao.getGatewayMappings(smsInAssembly.getSmsAssembly().getSmsGroup());

            smsGroupGatewayMappings.forEach(smsInAssembly::addGatewayMapping);

            return smsInAssembly.getGatewayMapping();
        }

        String policyName = smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE,
                ConfigEngineParam.GATEWAY_USAGE_POLICY, null, ConfigEngineParam.GATEWAY_USAGE_POLICY.getDefaultValue());

        GatewayUsagePolicy gatewayUsagePolicy = GatewayUsagePolicy.find(policyName);

        switch (gatewayUsagePolicy) {

            case RETRY_UNTIL_UNAVAILABLE:
                return smsInAssembly.getGatewayMapping();

            case CYCLE_AVAILABLE:
                return smsInAssembly.getNextGatewayMapping();

            default:
                return smsInAssembly.getNextGatewayMapping();
        }
    }
    
    public void restSmsSubmissionCount(Integer gatewayId) {
        SmsGatewayHandler smsGatewayHandler = getGatewayHandler(gatewayId);
        smsGatewayHandler.resetSmsSubmissionCount();
    }

    public Integer getSubmissionCount(Integer gatewayId) {
        SmsGatewayHandler smsGatewayHandler = getGatewayHandler(gatewayId);
        return smsGatewayHandler.getSmsSubmissionCount();
    }
}
