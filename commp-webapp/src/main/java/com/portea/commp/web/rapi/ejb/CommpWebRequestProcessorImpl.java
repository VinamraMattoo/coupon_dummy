package com.portea.commp.web.rapi.ejb;

import com.portea.common.config.dao.ConfigParamDao;
import com.portea.common.config.dao.TargetConfigAuditDao;
import com.portea.common.config.dao.TargetConfigValueDao;
import com.portea.common.config.domain.ConfigParam;
import com.portea.common.config.domain.TargetConfigValue;
import com.portea.commp.smsen.dao.*;
import com.portea.commp.smsen.domain.*;
import com.portea.commp.smsen.util.DataTypeUtil;
import com.portea.commp.smsen.util.DateUtil;
import com.portea.commp.web.rapi.domain.*;
import com.portea.commp.web.rapi.exception.*;
import com.portea.dao.Dao;
import com.portea.dao.JpaDao;
import com.portea.util.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.*;
import javax.ws.rs.BadRequestException;
import java.util.*;

@Stateless
public class CommpWebRequestProcessorImpl implements CommpWebRequestProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(CommpWebRequestProcessorImpl.class);
    @PersistenceContext(name = "commpPU")
    private EntityManager entityManager;

    @Inject @JpaDao
    private SmsGroupDao smsGroupDao;

    @Inject @JpaDao
    private SmsTypeDao smsTypeDao;

    @Inject @JpaDao
    private SmsGroupGatewayMappingDao smsGroupGatewayMappingDao;

    @Inject @JpaDao
    private SmsGatewayDao smsGatewayDao;

    @Inject @JpaDao
    private SmsTemplateDao smsTemplateDao;

    @Inject @JpaDao
    private TargetConfigValueDao targetConfigValueDao;

    @Inject @JpaDao
    private ConfigParamDao configParamDao;

    @Inject @JpaDao
    private SmsSenderDao smsSenderDao;

    @Inject @JpaDao
    private SmsGroupAuditDao smsGroupAuditDao;

    @Inject @JpaDao
    private SmsTypeAuditDao smsTypeAuditDao;

    @Inject @JpaDao
    private TargetConfigAuditDao targetConfigAuditDao;

    @Inject @JpaDao
    private UserDao userDao;

    @Inject @JpaDao
    private SmsGroupGatewayMappingAuditDao smsGroupGatewayMappingAuditDao;

    @Inject @JpaDao
    private SmsSenderAuditDao smsSenderAuditDao;

    @Inject @JpaDao
    private SmsRecordDao smsRecordDao;

    public CommpWebRequestProcessorImpl() {}

    @Override
    public void evictCache(String fqClassName) {
        EntityManagerFactory entityManagerFactory = entityManager.getEntityManagerFactory();
        Cache cache = entityManagerFactory.getCache();
        try {
            Class entityClass = Class.forName(fqClassName);
            cache.evict(entityClass);
        } catch (ClassNotFoundException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    public void evictAllCache() {
        EntityManagerFactory entityManagerFactory = entityManager.getEntityManagerFactory();
        Cache cache = entityManagerFactory.getCache();
        cache.evictAll();
    }

    @Override
    public List<SmsGroupVo> getSmsGroups() {
        List<SmsGroup> smsGroups = smsGroupDao.getSmsGroups();

        List<SmsGroupVo> smsGroupVos = new ArrayList<>();
        smsGroups.forEach(smsGroup -> smsGroupVos.add(SmsGroupVo.build(smsGroup)));
        return smsGroupVos;
    }

    @Override
    public List<SmsTypeVo> getSmsTypes() {
        List<SmsType> smsTypes = smsTypeDao.getSmsTypes();

        List<SmsTypeVo> smsTypeVos = new ArrayList<>();
        smsTypes.forEach(smsType -> smsTypeVos.add(SmsTypeVo.build(smsType)));
        return smsTypeVos;
    }

    @Override
    public void updateSmsGroup(Integer userId, Integer smsGroupId, CoolingPeriodUpdateVo coolingPeriodUpdateVo) {
        SmsGroup smsGroup = getEntity(smsGroupId, smsGroupDao);
        User user = getEntity(userId, userDao);
        if (coolingPeriodUpdateVo != null) {
            coolingPeriodUpdateVo.validate();
            smsGroup.setContentMatchCoolingPeriod(coolingPeriodUpdateVo.getCMCoolingPeriod());
            smsGroup.setTypeMatchCoolingPeriod(coolingPeriodUpdateVo.getTMCoolingPeriod());
        }
        else {
            smsGroup.setContentMatchCoolingPeriod(null);
            smsGroup.setTypeMatchCoolingPeriod(null);
        }
        smsGroup.setLastUpdatedOn(new Date());
        smsGroup.setLastUpdatedBy(user);
        smsGroupDao.update(smsGroup);
        smsGroupAuditDao.create(smsGroup);
    }

    @Override
    public void updateSmsType(Integer userId, Integer smsTypeId, CoolingPeriodUpdateVo coolingPeriodUpdateVo) {
        User user = getEntity(userId, userDao);
        SmsType smsType = getEntity(smsTypeId, smsTypeDao);
        if (coolingPeriodUpdateVo != null) {
            coolingPeriodUpdateVo.validate();
            smsType.setContentMatchCoolingPeriod(coolingPeriodUpdateVo.getCMCoolingPeriod());
            smsType.setTypeMatchCoolingPeriod(coolingPeriodUpdateVo.getTMCoolingPeriod());
        }
        else {
            smsType.setContentMatchCoolingPeriod(null);
            smsType.setTypeMatchCoolingPeriod(null);
        }
        smsType.setLastUpdatedOn(new Date());
        smsType.setLastUpdatedBy(user);
        smsTypeDao.update(smsType);
        smsTypeAuditDao.create(smsType);
    }

    @Override
    public List<GroupGatewayMappingVo> getGroupGatewayMapping() {
        List<SmsGroupGatewayMapping> smsGroupGatewayMappings = smsGroupGatewayMappingDao.getMappings();
        List<SmsGroup> smsGroups = smsGroupDao.getSmsGroups();
        List<GroupGatewayMappingVo> groupGatewayMappingVos = new ArrayList<>();

        List<SmsGateway> smsGateways = smsGatewayDao.getGateways();
        Map<SmsGroup, Boolean> groupAddedTrack = new HashMap<>();
        smsGroupGatewayMappings.forEach(
                smsGroupGatewayMapping -> {

                    groupAddedTrack.put(smsGroupGatewayMapping.getSmsGroup(), true);
                    groupGatewayMappingVos.add(GroupGatewayMappingVo.build(smsGroupGatewayMapping));
                });
        //Creating dummy Gateway mapping so that client can explicitly show that no mapping is done between group and gateway.
        smsGateways.forEach(smsGateway -> smsGroups.forEach(smsGroup -> {
            if (groupAddedTrack.get(smsGroup) == null) {
                GroupGatewayMappingVo groupGatewayMappingVo = new GroupGatewayMappingVo();
                groupGatewayMappingVo.setSmsGroupVo(SmsGroupVo.build(smsGroup));
                groupGatewayMappingVo.setSmsGatewayVo(SmsGatewayVo.build(smsGateway));
                groupGatewayMappingVo.setId(null);
                groupGatewayMappingVo.setPriority(null);

                groupGatewayMappingVos.add(groupGatewayMappingVo);
            }
        }));

        return groupGatewayMappingVos;
    }

    @Override
    public void crudGroupGatewayMapping(Integer userId, CRUDGroupGatewayMappingVo mappingVo) {
        User user = getEntity(userId, userDao);
        mappingVo.validate();
        Integer groupId = mappingVo.getGroupId();
        SmsGroup smsGroup = getEntity(groupId, smsGroupDao);
        List<SmsGroupGatewayMapping> smsGroupGatewayMappings = smsGroupGatewayMappingDao.getMappings(smsGroup);

        mappingVo.getGatewayPriorityList().forEach(gatewayPriorityMapping -> {
            Integer newPriority = gatewayPriorityMapping.getPriority();
            Integer gatewayId = gatewayPriorityMapping.getGatewayId();
            SmsGateway smsGateway = getEntity(gatewayId, smsGatewayDao);

            SmsGroupGatewayMapping mapping;
            try {
                mapping = smsGroupGatewayMappings.stream()
                        .filter(smsGroupGatewayMapping ->
                                Objects.equals(smsGroupGatewayMapping.getSmsGateway().getId(), gatewayId))
                        .findFirst()
                        .get();
                Integer existingPriority = mapping.getPriority();

                if (newPriority == null) {
                    LOG.debug("Deleting existing group-gateway mapping priority: "+mapping.toString());
                    Integer prePriority = mapping.getPriority();
                    mapping.setPriority(null);
                    smsGroupGatewayMappingAuditDao.create(mapping, prePriority);
                    smsGroupGatewayMappingDao.delete(mapping);
                    smsGroupGatewayMappings.remove(mapping);
                }
                else if (! existingPriority.equals(newPriority)) {
                    String fromMapping = mapping.toString();
                    Integer prePriority = mapping.getPriority();
                    mapping.setPriority(newPriority);
                    mapping.setLastUpdatedOn(new Date());
                    mapping.setLastUpdatedBy(user);
                    smsGroupGatewayMappingDao.update(mapping);
                    smsGroupGatewayMappingAuditDao.create(mapping, prePriority);
                    LOG.debug("Updating existing group-gateway mapping from: " + fromMapping + " to: " + mapping.toString());
                }
                else {
                    LOG.debug("No priority change in requested group-gateway mapping: " + mapping.toString());
                }
            } catch (NoSuchElementException e) {
                //IF no existing mapping is found create a new mapping.
                if (newPriority != null) {
                    SmsGroupGatewayMapping newMapping = new SmsGroupGatewayMapping();
                    newMapping.setPriority(newPriority);
                    newMapping.setSmsGroup(smsGroup);
                    newMapping.setSmsGateway(smsGateway);
                    newMapping.setLastUpdatedOn(new Date());
                    newMapping.setLastUpdatedBy(user);
                    smsGroupGatewayMappingDao.create(newMapping);
                    smsGroupGatewayMappingAuditDao.create(newMapping, null);
                    smsGroupGatewayMappings.add(newMapping);
                    LOG.debug("Creating new group-gateway mapping priority: " + newMapping.toString());
                }
                else {
                    LOG.debug("Ignoring group-gateway mapping request: group: "+smsGroup.getName()+" gw: "
                            +gatewayPriorityMapping.toString()+" as priority is null");
                }
            }
        });


        Comparator<SmsGroupGatewayMapping> comparator = (o1, o2) -> {
            if (o1.getPriority() < o2.getPriority()) {
                return -1;
            }
            else if (o1.getPriority() > o2.getPriority()) {
                return 1;
            }
            else {
                return 0;
            }
        };
        smsGroupGatewayMappings.sort(comparator);

        List<Integer> newPriorities = new ArrayList<>();
        smsGroupGatewayMappings.forEach(gatewayPriorityMapping -> {
            Integer currentPriority = gatewayPriorityMapping.getPriority();

            if (newPriorities.isEmpty()) {
                if (currentPriority != 1) {
                    LOG.debug("First group-gateway mapping priority should always start from 1");
                    throw new MissingGwMappingForPriorityException(1);
                } else {
                    newPriorities.add(currentPriority);
                }
            }
            else {
                Integer lastCheckedPriority = newPriorities.get(newPriorities.size() - 1);
                Integer difference = currentPriority - lastCheckedPriority;
                if (difference != 1) {
                    LOG.debug("Group-gateway mapping priority difference " + difference +
                            " is not acceptable between consecutive gateways, as this difference should only differ by one");
                    throw new MissingGwMappingForPriorityException(lastCheckedPriority + 1);
                }
                newPriorities.add(currentPriority);
            }
        });
    }

    @Override
    public List<SmsSenderVo> getSmsSenders() {
        List<SmsSenderVo> smsSenderVos = new ArrayList<>();

        List<SmsSender> smsSenders = smsSenderDao.getSmsSenders();
        smsSenders.forEach(smsSender -> smsSenderVos.add(SmsSenderVo.build(smsSender)));
        return smsSenderVos;
    }

    @Override
    public Integer createSmsSender(Integer userId, SmsSenderCreateReq smsSenderCreateReq) {
        User user = getEntity(userId, userDao);
        smsSenderCreateReq.validate();
        try {

            SmsSender regSender = smsSenderDao.find(smsSenderCreateReq.getName());
            if (regSender != null) {
                throw new UserNameAlreadyExistsException(smsSenderCreateReq.getName());
            }
        } catch (NoResultException ignored) {

        }

        String salt = BCrypt.gensalt();
        String key = BCrypt.hashpw(smsSenderCreateReq.getPassword(), salt);

        SmsSender smsSender = new SmsSender();
        smsSender.setPassword(key);
        smsSender.setName(smsSenderCreateReq.getName());

        smsSender.setRegisteredOn(new Date());
        smsSender.setEmail(smsSenderCreateReq.getEmail());
        smsSender.setDescription(smsSenderCreateReq.getDescription());
        smsSender.setActive(true);
        
        smsSender.setCreatedBy(user);
        smsSender.setLastUpdatedBy(user);
        smsSender.setLastUpdatedOn(new Date());
        smsSender = smsSenderDao.create(smsSender);
        smsSenderAuditDao.create(smsSender);
        return smsSender.getId();
    }

    @Override
    public void deactivateSmsSender(Integer userId, Integer smsSenderId) {
        User user = getEntity(userId, userDao);
        SmsSender smsSender = getEntity(smsSenderId, smsSenderDao);

        if (smsSender.getActive() != null && ! smsSender.getActive()) {
            throw new UserAlreadyInStateException(smsSender.getName(), UserAlreadyInStateException.DEACTIVATED);
        }
        smsSender.setActive(false);
        smsSender.setLastUpdatedOn(new Date());
        smsSender.setLastUpdatedBy(user);
        smsSenderDao.update(smsSender);
        smsSenderAuditDao.create(smsSender);
    }

    @Override
    public void resetPassword(Integer userId, Integer smsSenderId, String password) {
        User user = getEntity(userId, userDao);
        SmsSender smsSender = getEntity(smsSenderId, smsSenderDao);
        String salt = BCrypt.gensalt();
        String key = BCrypt.hashpw(password, salt);
        smsSender.setPassword(key);
        smsSender.setLastUpdatedOn(new Date());
        smsSender.setLastUpdatedBy(user);
        smsSenderDao.update(smsSender);
        smsSenderAuditDao.create(smsSender);
    }

    @Override
    public List<TargetConfigValueVo> getTargetConfigValues() {
        List<TargetConfigValueVo> targetConfigValueVos = new ArrayList<>();

        List<TargetConfigValue> configValues = targetConfigValueDao.getTargetConfigValues();

        configValues.forEach(targetConfigValue -> {
            com.portea.common.config.domain.ConfigTargetType configTargetType =
                    targetConfigValue.getTargetConfig().getConfigParam().getConfigTargetType();
            Integer targetId = targetConfigValue.getTargetConfig().getTargetId();
            String targetName = null;
            switch (configTargetType.getTargetType()) {
                case SMS_GATEWAY:
                    SmsGateway smsGateway = smsGatewayDao.find(targetId);
                    targetName = smsGateway.getName();
                    break;
                case SMS_ENGINE:
                    break;
                case SMS_TEMPLATE:
                    SmsTemplate smsTemplate = smsTemplateDao.find(targetId);
                    targetName = smsTemplate.getName();
                    break;
                case SMS_TYPE:
                    SmsType smsType = smsTypeDao.find(targetId);
                    targetName = smsType.getName();
                    break;
            }
            targetConfigValueVos.add(TargetConfigValueVo.build(targetConfigValue, targetName));
        });
        return targetConfigValueVos;
    }

    @Override
    public void updateTargetConfigValue(Integer userId, TargetConfigUpdateValueReq targetConfigUpdateValueReq) {
        User user = getEntity(userId, userDao);
        Integer configParamId = targetConfigUpdateValueReq.getConfigParamId();
        String value = targetConfigUpdateValueReq.getValue();
        ConfigParam configParam = getEntity(configParamId, configParamDao);
        if (!DataTypeUtil.isValueOfDataType(configParam.getValueDataType(), value)) {
            throw new InvalidRequestException("value", value);
        }

        try {
            TargetConfigValue targetConfigValue = targetConfigValueDao.
                    getTargetConfig(configParamId, targetConfigUpdateValueReq.getTargetId());
            String preValue = targetConfigValue.getValue();
            targetConfigValue.setValue(value);
            targetConfigValue.setLastUpdatedOn(new Date());
            targetConfigValue.setLastUpdatedBy(user);
            targetConfigValueDao.update(targetConfigValue);
            targetConfigAuditDao.create(targetConfigValue, preValue);
        } catch (NoResultException e) {

            throw new InvalidRequestException("targetId", String.valueOf(targetConfigUpdateValueReq.getTargetId()));
        }
    }

    @Override
    public List<SmsGatewayVo> getGateways() {
        List<SmsGateway> smsGateways = smsGatewayDao.getGateways();
        List<SmsGatewayVo> smsGatewayVos = new ArrayList<>();

        smsGateways.forEach(smsGateway -> smsGatewayVos.add(SmsGatewayVo.build(smsGateway)));
        return smsGatewayVos;
    }

    @Override
    public void reactivateSmsSender(Integer userId, Integer smsSenderId) {
        User user = getEntity(userId, userDao);
        SmsSender smsSender = getEntity(smsSenderId, smsSenderDao);

        if (smsSender.getActive() != null && smsSender.getActive()) {
            throw new UserAlreadyInStateException(smsSender.getName(), UserAlreadyInStateException.ACTIVE);
        }
        smsSender.setActive(true);
        smsSender.setLastUpdatedOn(new Date());
        smsSender.setLastUpdatedBy(user);
        smsSenderDao.update(smsSender);
        smsSenderAuditDao.create(smsSender);
    }

    @Override
    public List<SmsSourceUsageData> getSmsSourceUsage() {
        List<Object[]> sourceDetails = smsRecordDao.getSmsSourceUsage();
        List<SmsSourceUsageData> sourceUsageData = new ArrayList<>();
        sourceDetails.forEach(sourceData -> {
            SmsSourceUsageData smsSourceUsageData = new SmsSourceUsageData();
            smsSourceUsageData.setCount((Long) sourceData[0]);
            smsSourceUsageData.setSource((SmsSource) sourceData[1]);
            sourceUsageData.add(smsSourceUsageData);
        });
        return sourceUsageData;
    }

    @Override
    public List<SmsGatewayUsageData> getSmsGatewayUsage() {
        List<Object[]> gatewayUsage = smsRecordDao.getSmsGatewayUsage();
        List<SmsGatewayUsageData> usageDataList = new ArrayList<>();
        gatewayUsage.forEach(smsGatewayUsage -> {
            SmsGatewayUsageData smsGatewayUsageData = new SmsGatewayUsageData();
            smsGatewayUsageData.setCount((Long) smsGatewayUsage[0]);
            smsGatewayUsageData.setGatewayName((String) smsGatewayUsage[1]);
            usageDataList.add(smsGatewayUsageData);
        });
        return usageDataList;
    }

    @Override
    public List<DailySentSmsData> getDailySmsStatus(Integer days) {
        List<DailySentSmsData> smsUsage = new ArrayList<>();
        if (days == null) {
            days = 7;
        }
        Map<String, List<SmsRecord>> dateRecordMap = getDateRecordMap(days);

        dateRecordMap.forEach((day, records) -> {
            Integer delivered = 0;
            Integer failed = 0;
            Integer pending = 0;
            DailySentSmsData dailySentSmsData = new DailySentSmsData();
            dailySentSmsData.setDay(day);
            dailySentSmsData.setDate(records.get(0).getLastUpdatedOn());

            for (SmsRecord record : records) {
                SmsSecondaryProcessingState state = record.getSmsSecondaryProcessingState();

                if (state == null) {
                    pending++;
                    continue;
                }
                switch (state.getNormalizedStatus()) {
                    case DELIVERED:
                        delivered++;
                        break;
                    case FAILURE:
                        failed++;
                        break;
                    case PENDING:
                        pending++;
                        break;
                }
            }
            dailySentSmsData.setDelivered(delivered);
            dailySentSmsData.setFailed(failed);
            dailySentSmsData.setPending(pending);
            smsUsage.add(dailySentSmsData);
        });
        return smsUsage;
    }

    //Todo:: For days more than one week, this method will club same days data. So use this  method for days less than or equal to 7.
    private Map<String, List<SmsRecord>> getDateRecordMap(Integer days) {
        Date currDate = new Date();
        Date pastDate = DateUtil.priorDate(currDate, days);

        List<SmsRecord> smsRecords = smsRecordDao.getRecords(pastDate, currDate);

        Map<String, List<SmsRecord>> dateRecordMap = new HashMap<>();

        smsRecords.forEach(record -> {
            Date lastUpdatedOn = record.getLastUpdatedOn();
            String day = DateUtil.getDayForDate(lastUpdatedOn);
            List<SmsRecord> dailyRecords = dateRecordMap.get(day);
            if (dailyRecords == null) {
                dailyRecords = new ArrayList<>();
                dateRecordMap.put(day, dailyRecords);
            }
            dailyRecords.add(record);
        });
        return dateRecordMap;
    }

    private Map<String, List<SmsRecord>> getTypeRecordMap(List<SmsRecord> records) {
        Map<String, List<SmsRecord>> typeRecordMap = new HashMap<>();

        records.forEach(record -> {
            SmsGroup smsGroup = record.getSmsGroup(); //todo:: this check should not be necessary. As group should always be non null value in the record.
            if (smsGroup != null) {
                String smsType = smsGroup.getSmsType().getName();
                List<SmsRecord> typeRecords = typeRecordMap.get(smsType);
                if (typeRecords == null) {
                    typeRecords = new ArrayList<>();
                    typeRecordMap.put(smsType, typeRecords);
                }
                typeRecords.add(record);
            }
        });
        return typeRecordMap;
    }

    @Override
    public List<DailySentSmsTypeData> getDailySmsType(Integer days) {
        List<DailySentSmsTypeData> smsFailures = new ArrayList<>();
        if (days == null) {
            days = 7;
        }
        Map<String, List<SmsRecord>> dateRecordMap = getDateRecordMap(days);

        dateRecordMap.forEach((day, records) -> {
            DailySentSmsTypeData dailySentSmsTypeData = new DailySentSmsTypeData();
            dailySentSmsTypeData.setDay(day);
            dailySentSmsTypeData.setDate(records.get(0).getLastUpdatedOn());

            Map<String, List<SmsRecord>> typeRecordMap = getTypeRecordMap(records);

            List<DailySentSmsTypeData.SmsDailyTypeData> smsDailyTypeDatas = new ArrayList<>();
            typeRecordMap.forEach((type, typeRecord) -> {
                DailySentSmsTypeData.SmsDailyTypeData smsDailyTypeData = new DailySentSmsTypeData.SmsDailyTypeData();
                smsDailyTypeData.setTypeName(type);
                smsDailyTypeData.setCount(typeRecord.size());
                smsDailyTypeDatas.add(smsDailyTypeData);
            });
            dailySentSmsTypeData.setSmsTypes(smsDailyTypeDatas);
            smsFailures.add(dailySentSmsTypeData);
        });
        return smsFailures;
    }


    private <E> E getEntity(Integer id, Dao<Integer, E> dao) {
        E entity;
        if (id == null) {
            throw new IncompleteRequestException(dao.getEntityClass().getSimpleName() + ".id");
        }
        if (id < 0 || (entity = dao.find(id)) == null) {
            throw new InvalidRequestException(dao.getEntityClass().getSimpleName() + ".id", String.valueOf(id));
        }
        return entity;
    }
}
