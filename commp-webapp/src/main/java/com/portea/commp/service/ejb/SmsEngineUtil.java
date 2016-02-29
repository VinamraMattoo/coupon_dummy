package com.portea.commp.service.ejb;

import com.portea.common.config.dao.ConfigParamDao;
import com.portea.common.config.dao.ConfigTargetTypeDao;
import com.portea.common.config.dao.TargetConfigValueDao;
import com.portea.common.config.domain.ConfigEngineParam;
import com.portea.common.config.domain.ConfigParam;
import com.portea.common.config.domain.ConfigSmsTypeParam;
import com.portea.common.config.domain.ConfigTargetParam;
import com.portea.commp.smsen.dao.*;
import com.portea.commp.smsen.domain.*;
import com.portea.commp.smsen.engine.Brand;
import com.portea.commp.smsen.engine.ReceiverType;
import com.portea.commp.smsen.engine.SmsValidationResult;
import com.portea.commp.smsen.util.DateUtil;
import com.portea.commp.smsen.vo.SmsInAssembly;
import com.portea.dao.JpaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Stateless
public class SmsEngineUtil {

    private static final String MESSAGE_PLACEHOLDER_TOKEN = "{F";
    @Inject
    @JpaDao
    private SmsAssemblyDao smsAssemblyDao;

    @Inject
    @JpaDao
    private SmsRecordDao smsRecordDao;

    @Inject
    @JpaDao
    private SmsAuditDao smsAuditDao;

    @Inject
    @JpaDao
    private PatientDetailDao patientDetailDao;

    @Inject
    @JpaDao
    private BrandDao brandDao;

    @Inject
    @JpaDao
    private SmsSentCoolingDataDao smsSentCoolingDataDao;

    @Inject
    @JpaDao
    private ConfigTargetTypeDao configTargetTypeDao;

    @Inject
    @JpaDao
    private ConfigParamDao configParamDao;

    @Inject
    @JpaDao
    private SmsGatewayLogDao smsGatewayLogDao;

    @Inject
    @JpaDao
    private TargetConfigValueDao targetConfigValueDao;

    @Inject @JpaDao
    private SmsUserThrottlingDataDao smsUserThrottlingDataDao;

    private static final Logger LOG = LoggerFactory.getLogger(SmsEngineUtil.class);

    public String getTargetConfigValue(ConfigTargetType targetType, ConfigTargetParam configTargetParam, Integer targetId, String defaultValue){
        Integer targetTypeId = configTargetTypeDao.getIdForTargetType(targetType);

        ConfigParam configParam;
        try{
            configParam = configParamDao.getConfigParam(targetTypeId, configTargetParam);
        }
        catch (NoResultException e){
            String errorMsg = "Configuration parameter "+configTargetParam.name()+" not found for target type "+targetType;
            LOG.error(errorMsg);
            return defaultValue;
        }
        String value;
        try{
            value = targetConfigValueDao.getTargetConfigValue(configParam.getId(), targetId);
        } catch (NoResultException e) {
            String errorMsg = "No value is associated with the given config param "+configParam.getName()+" and target id "+targetId;
            LOG.error(errorMsg);
            return defaultValue;
        }

        return value;
    }


    public boolean canSendToPatient(Integer userId, String receiverType, String smsGroupName) {

        PatientDetail patientDetail;
        try {

            patientDetail = patientDetailDao.getPatientDetails(userId);
        }
        catch (NoResultException e) {
            return true;
        }

        if (receiverType != null && receiverType.equals("patient")) {

            if (Objects.equals(smsGroupName, "Patient Email Validation")) {
                return true;
            }

            return patientDetail.getSendSmsAlert();
        }
        return true;
    }

    public SmsValidationResult getValidationResult(String mobileNumber, User user, String message,
                                                   String receiverType, SmsGroup smsGroup) {

        Boolean validatePhoneNumber = Boolean.parseBoolean(
                    getTargetConfigValue(ConfigTargetType.SMS_ENGINE, ConfigEngineParam.
                            NEW_SMS_PHONE_NUMBER_VALIDATION, null, ConfigEngineParam.NEW_SMS_PHONE_NUMBER_VALIDATION.getDefaultValue()));

        Boolean validateMessage = Boolean.parseBoolean(
                    getTargetConfigValue(ConfigTargetType.SMS_ENGINE, ConfigEngineParam.
                            NEW_SMS_MESSAGE_FORMAT_VALIDATION, null, ConfigEngineParam.NEW_SMS_MESSAGE_FORMAT_VALIDATION.getDefaultValue()));

        Boolean doDndCheck = Boolean.parseBoolean(
                    getTargetConfigValue(ConfigTargetType.SMS_ENGINE, ConfigEngineParam.
                            NEW_SMS_USER_DND_VALIDATION, null, ConfigEngineParam.NEW_SMS_USER_DND_VALIDATION.getDefaultValue()));

        return getValidationResult(mobileNumber, user, message, receiverType, smsGroup,
                validatePhoneNumber, validateMessage, doDndCheck);
    }

    public SmsValidationResult getValidationResult(String mobileNumber, User user, String message,
                                                    String receiverType, SmsGroup smsGroup,
                                                    Boolean validatePhoneNumber, Boolean validateMessage,
                                                    Boolean doDndCheck) {

        if(validatePhoneNumber && ! isValidPhoneNumber(mobileNumber)) {

            return new SmsValidationResult(false, SmsProcessingStatusReason.INVALID_PHONE_NUMBER.status());
        }

        if(validateMessage){
            SmsValidationResult messageValidationResult = validateMessage(user.getId(), message, receiverType, smsGroup.getName());

            if (! messageValidationResult.isValid()) {

                return messageValidationResult;
            }
        }

        if(doDndCheck && isDNDUser(user.getId(), receiverType, smsGroup.getName())){

            return new SmsValidationResult(false, SmsProcessingStatusReason.DND_USER.status());
        }

        return new SmsValidationResult(true, null);
    }

    /**
     * In order to get the validation result for sms sent cooling period first check if cooling period exits
     * If no cooling period already exits and for this sms group/type and if we can create cooling period then we try to create a cooling period.
     * Due to concurrency if other thread creates similar cooling period {@link PersistenceException} is thrown
     * and we try go get the cooling period result again.
     */
    public SmsValidationResult checkCoolingPeriod(User user, SmsGroup smsGroup, SmsType smsType, String mobileNumber,
                                                  Integer messageHash, String message, String scheduledTimeZone) {

        SmsValidationResult coolingPeriodResult = validateCoolingPeriodData(user, smsType, mobileNumber, message, messageHash);

        if (! coolingPeriodResult.isValid()) {
            return coolingPeriodResult;
        }

        if (canCreateCoolingPeriodData(smsGroup, smsType)) {
            LOG.debug("Creating cooling period data for user "+user.getId()+" smsType "+smsType.getName());
            Integer smsSentCoolingDataId =
                    createCoolingPeriodData(user, smsGroup, smsType, mobileNumber, messageHash, message, scheduledTimeZone);
            //If the id is zero that implies no record is created. This could be due to another record created at the same time.
            if (smsSentCoolingDataId == 0) {
                LOG.debug("Failed to create cooling period data, possibly another another thread has created similar cooling period");
                throw new PersistenceException();
            } else {
                LOG.debug("Created sms sent cooling period data id: "+smsSentCoolingDataId);
            }
        }

        if (! coolingPeriodResult.isValid()) {
            return coolingPeriodResult;
        }
        return coolingPeriodResult;
    }

    /**
     * In order to get the validation result for sms sent within duration first we check if cooling period exists.
     * If no cooling period already exits and for this sms type/group if we can create cooling period. We try to create
     * a cooling period. Due to concurrency if another thread already creates a similar record then {@link PersistenceException}
     * is thrown. If a cooling period already exists then we try to increase the usage count, at this time if the read version
     * is different from update version of {@link SmsUserThrottlingData} then {@link OptimisticLockException} is thrown.
     */
    public SmsValidationResult checkSmsThrottling(User user, String mobileNumber, SmsType smsType) {
        SmsValidationResult throttlingResult = new SmsValidationResult(true, null);
        Integer maxThrottlingCount = Integer.parseInt(getTargetConfigValue(ConfigTargetType.SMS_TYPE,
                ConfigSmsTypeParam.SMS_MAX_THROTTLING_COUNT, smsType.getId(),
                ConfigSmsTypeParam.SMS_MAX_THROTTLING_COUNT.getDefaultValue()));
        if (maxThrottlingCount == -1) {
            return throttlingResult;
        } else if (maxThrottlingCount == 0) {
            throttlingResult.setValid(false);
            throttlingResult.setReason(SmsProcessingStatusReason.SMS_THROTTLING_APPLIED.getReason());
            return throttlingResult;
        }

        Date currDate = new Date();
        try {

            SmsUserThrottlingData smsUserThrottlingData = smsUserThrottlingDataDao
                    .findMatchingRecord(mobileNumber, smsType.getName(), new Date());
            Integer sentCount = smsUserThrottlingData.getSentCount();
            if (sentCount < maxThrottlingCount) {
                smsUserThrottlingData.setSentCount(smsUserThrottlingData.getSentCount() + 1);

                smsUserThrottlingDataDao.update(smsUserThrottlingData);
            } else {
                throttlingResult.setValid(false);
                throttlingResult.setReason(SmsProcessingStatusReason.SMS_THROTTLING_APPLIED.getReason());
            }
        } catch (NoResultException e) {

            smsUserThrottlingDataDao.create(user, mobileNumber, smsType.getName(), currDate,
                    DateUtil.getStartOfDay(currDate), DateUtil.getEndOfDay(currDate), 1);
        }

        if (! throttlingResult.isValid()) {
            return throttlingResult;
        }

        return throttlingResult;
    }

    public void persistSmsSubmittedAt(SmsInAssembly smsInAssembly, SmsGateway smsGateway) {
        SmsAssembly smsAssembly = smsAssemblyDao.find(smsInAssembly.getSmsAssembly().getId());

        smsAssembly.setSmsRequestedAt(new Date());
        smsAssembly.setSmsSecondaryProcessingState(SmsSecondaryProcessingState.SUBMITTED_TO_GATEWAY);
        smsAssemblyDao.update(smsAssembly);
        smsInAssembly.updateSmsAssembly(smsAssembly);

        SmsRecord smsRecord = smsAssembly.getSmsRecord();
        smsRecord.setLastUpdatedOn(new Date());
        smsRecord.setSmsRequestedAt(new Date());
        smsRecord.setSmsGateway(smsGateway);

        smsRecord.setSmsSecondaryProcessingState(smsAssembly.getSmsSecondaryProcessingState());
        smsRecord.setStatusReason(SmsProcessingStatusReason.SUBMITTED_TO_GATEWAY.status());

        smsRecordDao.update(smsRecord);

        smsAuditDao.create(smsRecord);
    }

    public boolean isDNDUser(SmsInAssembly smsInAssembly) {

        SmsAssembly smsAssembly = smsInAssembly.getSmsAssembly();
        return isDNDUser( smsAssembly.getUser().getId(), smsAssembly.getReceiverType(), smsAssembly.getSmsGroup().getName());

    }

    public boolean isDNDUser(Integer userId, String receiverType, String smsGroupName) {

        if( ! canSendToPatient(userId, receiverType, smsGroupName)){
            SmsProcessingStatusReason smsProcessingStatusReason = SmsProcessingStatusReason.DND_USER;
            LOG.debug(smsProcessingStatusReason.name());
            return true;
        }
        return false;
    }

    public SmsValidationResult validateMessage(SmsInAssembly smsInAssembly) {

        SmsAssembly smsAssembly = smsInAssembly.getSmsAssembly();
        return validateMessage(smsAssembly.getUser().getId(), smsAssembly.getMessage(),
                smsAssembly.getReceiverType(), smsAssembly.getSmsGroup().getName());
    }

    public SmsValidationResult validateMessage(Integer userId, String message, String receiverType, String smsGroupName) {

        if (message.contains(MESSAGE_PLACEHOLDER_TOKEN)) {

            return new SmsValidationResult(false, SmsProcessingStatusReason.MESSAGE_HAS_PLACEHOLDER_TOKEN.status());
        }

        if (msgHasIncorrectBrandInfo(userId, receiverType, message)) {

            return new SmsValidationResult(false, SmsProcessingStatusReason.MESSAGE_HAS_UNACCEPTED_BRAND_NAME.status());
        }

        if( ! canSendSmsToPatientWithBrand(userId, receiverType, smsGroupName)) {

            return new SmsValidationResult(false, SmsProcessingStatusReason.CANNOT_SEND_SMS_TO_BRAND.status());
        }

        return new SmsValidationResult(true, null);
    }


    public boolean isValidPhoneNumber(SmsInAssembly smsInAssembly) {

        return isValidPhoneNumber(smsInAssembly.getSmsAssembly().getMobileNumber());
    }

    public boolean isValidPhoneNumber(String mobileNumber) {
        char firstChar = mobileNumber.charAt(mobileNumber.length() - (10));
        Integer numToBeVerified = Character.getNumericValue(firstChar);
        return IntStream.rangeClosed(7, 9).anyMatch(num -> num == numToBeVerified);
    }

    public boolean canSendSmsToPatientWithBrand(Integer userId, String receiverType, String smsGroupName) {

        PatientDetail patientDetail;
        try {

            patientDetail = patientDetailDao.getPatientDetails(userId);
        }
        catch (NoResultException e) {
            return true;
        }
        com.portea.commp.smsen.domain.Brand brand = brandDao.find(patientDetail.getBrandId());

        if (brand == null) {
            return true;
        }
        if (receiverType != null && receiverType.equals(ReceiverType.PATIENT.name())) {

            if (Objects.equals(smsGroupName, "Patient Email Validation")) {
                return true;
            }

            return brand.getCanSendSms();
        }
        return true;
    }

    public boolean msgHasIncorrectBrandInfo(Integer userId, String receiverType, String message) {

        if (receiverType != null &&
                (receiverType.equals(ReceiverType.PATIENT.name().toLowerCase()) ||
                        receiverType.equals(ReceiverType.LEAD.name().toLowerCase()))) {

            PatientDetail patientDetail;
            try {

                patientDetail = patientDetailDao.getPatientDetails(userId);
            }
            catch (NoResultException e) {
                return false;
            }

            com.portea.commp.smsen.domain.Brand brand = brandDao.find(patientDetail.getBrandId());

            if (brand == null) {
                return false;
            }

            if (brand.getName().toLowerCase().equals(Brand.MANIPAL.getName().toLowerCase())) {
                message = message.toLowerCase();

                if (message.contains(Brand.PORTEA.getName().toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void persistSmsStatus(SmsInAssembly smsInAssembly, SmsPrimaryProcessingState primaryProcessingState,
                                 SmsSecondaryProcessingState secondaryProcessingState, String smsProcessingStatusReason){

        SmsAssembly currSms = smsAssemblyDao.find(smsInAssembly.getSmsAssembly().getId());
        currSms.setSmsPrimaryProcessingState(primaryProcessingState);
        currSms.setSmsSecondaryProcessingState(secondaryProcessingState);

        smsAssemblyDao.update(currSms);

        smsInAssembly.updateSmsAssembly(currSms);
        SmsRecord smsRecord = currSms.getSmsRecord();
        smsRecord.setLastUpdatedOn(new Date());

        smsRecord.setSmsPrimaryProcessingState(primaryProcessingState);
        smsRecord.setSmsSecondaryProcessingState(secondaryProcessingState);
        smsRecord.setStatusReason(smsProcessingStatusReason);

        smsRecordDao.update(smsRecord);
        smsAuditDao.create(smsRecord);
    }

    public void deleteSmsAssembly(SmsInAssembly smsInAssembly) {
        SmsAssembly smsAssembly = smsAssemblyDao.find(smsInAssembly.getSmsAssembly().getId());
        smsAssemblyDao.delete(smsAssembly);
    }

    public void persistSmsCorrelationId(SmsInAssembly smsInAssembly, String correlationId, String responseCode, String responseMessage) {
        SmsAssembly smsAssembly = smsAssemblyDao.find(smsInAssembly.getSmsAssembly().getId());

        Integer gatewayFindingFailureCount = (smsAssembly.getGatewayFindingFailureCount() == null) ? 0 : smsAssembly.getGatewayFindingFailureCount();
        Integer submissionTrialCount = (smsAssembly.getCorrelationIdCheckTrialCount() == null) ? 0 : smsAssembly.getCorrelationIdCheckTrialCount();

        smsAssembly.setGatewayFindingFailureCount(gatewayFindingFailureCount + smsInAssembly.getGatewayFindingFailureCount());
        smsAssembly.setCorrelationIdCheckTrialCount(submissionTrialCount + smsInAssembly.getCorrelationIdCheckTrialCount());


        smsAssembly.setCorrelationId(correlationId);
        smsAssembly.setReceivedCorrelationIdAt(new Date());

        smsAssembly.setSmsPrimaryProcessingState(SmsPrimaryProcessingState.SUBMISSION_COMPLETED);
        smsAssembly.setSmsSecondaryProcessingState(SmsSecondaryProcessingState.SUCCESSFUL_SUBMISSION);

        smsAssemblyDao.update(smsAssembly);
        smsInAssembly.updateSmsAssembly(smsAssembly);

        SmsRecord smsRecord = smsAssembly.getSmsRecord();
        smsRecord.setLastUpdatedOn(new Date());

        smsRecord.setCorrelationId(correlationId);
        smsRecord.setReceivedCorrelationIdAt(new Date());

        smsRecord.setGatewayFindingFailureCount(smsAssembly.getGatewayFindingFailureCount());
        smsRecord.setCorrelationIdCheckTrialCount(smsAssembly.getCorrelationIdCheckTrialCount());

        smsRecord.setSmsPrimaryProcessingState(SmsPrimaryProcessingState.SUBMISSION_COMPLETED);
        smsRecord.setSmsSecondaryProcessingState(SmsSecondaryProcessingState.SUCCESSFUL_SUBMISSION);
        smsRecord.setStatusReason(SmsProcessingStatusReason.RECEIVED_CORRELATION_ID.status());

        smsRecordDao.update(smsRecord);

        smsAuditDao.create(smsRecord, responseCode, responseMessage);

    }

    public void persistSmsGatewayLogData(SmsInAssembly smsInAssembly, String correlationId) {

        SmsGatewayLog smsGatewayLog = new SmsGatewayLog();
        smsGatewayLog.setCorrelationId(correlationId);

        smsGatewayLog.setMessage(smsInAssembly.getSmsAssembly().getMessage());
        smsGatewayLog.setMobileNumber(smsInAssembly.getSmsAssembly().getMobileNumber());

        smsGatewayLog.setSmsGateway(smsInAssembly.getGatewayMapping().getSmsGateway());
        smsGatewayLog.setCreatedOn(new Date());

        smsGatewayLogDao.create(smsGatewayLog);
    }

    public SmsAssembly createNewSmsAssembly(final SmsRecord smsRecord) {

        SmsAssembly smsAssembly = new SmsAssembly();
        smsAssembly.setCreatedOn(new Date());
        smsAssembly.setSmsRecord(smsRecord);

        smsAssembly.setBrand(smsRecord.getBrand());
        smsAssembly.setMessage(smsRecord.getMessage());

        smsAssembly.setUser(smsRecord.getUser());
        smsAssembly.setReceiverType(smsRecord.getReceiverType());
        smsAssembly.setMobileNumber(smsRecord.getMobileNumber());

        smsAssembly.setCountryCode(smsRecord.getCountryCode());
        smsAssembly.setScheduledTime(smsRecord.getScheduledTime());
        smsAssembly.setSendBefore(smsRecord.getSendBefore());

        smsAssembly.setScheduledId(smsRecord.getScheduledId());
        smsAssembly.setScheduledTimeZone(smsRecord.getScheduledTimeZone());
        smsAssembly.setScheduledType(smsRecord.getScheduledType());

        smsAssembly.setSmsGroup(smsRecord.getSmsGroup());
        smsAssembly.setSmsTemplate(smsRecord.getSmsTemplate());
        smsAssembly.setMessageHash(smsRecord.getMessage().hashCode());

        smsAssembly.setCorrelationId(smsRecord.getCorrelationId());
        smsAssembly.setSourceName(smsRecord.getSourceName());

        smsAssembly.setSmsPrimaryProcessingState(smsRecord.getSmsPrimaryProcessingState());
        smsAssembly.setSmsSecondaryProcessingState(smsRecord.getSmsSecondaryProcessingState());
        smsAssembly = smsAssemblyDao.create(smsAssembly);
        return smsAssembly;
    }

    private SmsRecord copyRecordForRetry(SmsRecord prevRecord,
                                        SmsPrimaryProcessingState smsPrimaryProcessingState,
                                        SmsSecondaryProcessingState smsSecondaryProcessingState) {
        SmsRecord newRecord = new SmsRecord();
        newRecord.setCorrelationId(prevRecord.getCorrelationId());

        newRecord.setBrand(prevRecord.getBrand());
        newRecord.setLastUpdatedOn(new Date());
        newRecord.setMessage(prevRecord.getMessage());

        newRecord.setUser(prevRecord.getUser());
        newRecord.setReceiverType(prevRecord.getReceiverType());
        newRecord.setMobileNumber(prevRecord.getMobileNumber());

        newRecord.setCountryCode(prevRecord.getCountryCode());
        newRecord.setScheduledTime(prevRecord.getScheduledTime());
        newRecord.setSendBefore(prevRecord.getSendBefore());

        newRecord.setScheduledId(prevRecord.getScheduledId());
        newRecord.setScheduledTimeZone(prevRecord.getScheduledTimeZone());
        newRecord.setScheduledType(prevRecord.getScheduledType());

        newRecord.setSmsGroup(prevRecord.getSmsGroup());
        newRecord.setSmsTemplate(prevRecord.getSmsTemplate());
        newRecord.setMessageHash(prevRecord.getMessage().hashCode());

        newRecord.setSmsGateway(prevRecord.getSmsGateway());
        newRecord.setSourceName(prevRecord.getSourceName());

        newRecord.setCopyOf(prevRecord);
        newRecord.setSmsPrimaryProcessingState(smsPrimaryProcessingState);
        newRecord.setSmsSecondaryProcessingState(smsSecondaryProcessingState);
        smsRecordDao.create(newRecord);
        return newRecord;
    }

    public void updateStatusCheckTrialCount(SmsInAssembly smsInAssembly) {
        SmsAssembly smsAssembly = smsAssemblyDao.find(smsInAssembly.getSmsAssembly().getId());

        Integer statusCheckTrialCount = (smsAssembly.getStatusCheckTrialCount() == null) ? 0 : smsAssembly.getStatusCheckTrialCount();

        smsAssembly.setStatusCheckTrialCount(statusCheckTrialCount + smsInAssembly.getStatusCheckTrialCount());
        smsAssemblyDao.update(smsAssembly);
        smsInAssembly.updateSmsAssembly(smsAssembly);

        SmsRecord smsRecord = smsAssembly.getSmsRecord();
        smsRecord.setLastUpdatedOn(new Date());
        smsRecord.setStatusCheckTrialCount(smsAssembly.getStatusCheckTrialCount());

        smsRecordDao.update(smsRecord);
    }

    public SmsValidationResult validateCoolingPeriodData(User user, SmsType smsType, String mobileNumber,
                                                         String message, Integer messageHash) {
        SmsValidationResult smsValidationResult = new SmsValidationResult(true, null);

        Integer userId = user.getId();
        String smsTypeName = smsType.getName();

        List<SmsSentCoolingData> smsSentCoolingDataList =
                smsSentCoolingDataDao.getCoolingPeriodData(userId, mobileNumber, messageHash, smsTypeName);


        for (SmsSentCoolingData smsSentCoolingData : smsSentCoolingDataList) {
            Date currentDate = DateUtil.convertTimeZone(new Date(), TimeZone.getTimeZone(smsSentCoolingData.getTimeZone()));

            if (smsSentCoolingData.getSmsTypeExpires() != null
                    && currentDate.compareTo(smsSentCoolingData.getSmsTypeExpires()) < 0
                    && smsTypeName.equals(smsSentCoolingData.getSmsTypeName())) {

                SmsProcessingStatusReason smsProcessingStatusReason = SmsProcessingStatusReason.SMS_TYPE_IN_COOLING_PERIOD;
                LOG.debug(smsProcessingStatusReason.name() + " remaining time " +
                        TimeUnit.MILLISECONDS.toSeconds(smsSentCoolingData.getSmsTypeExpires().getTime() - currentDate.getTime()) + " sec");
                return new SmsValidationResult(false, SmsProcessingStatusReason.SMS_TYPE_IN_COOLING_PERIOD.status());
            }

            if (smsSentCoolingData.getMsgContentExpires() != null
                    && currentDate.compareTo(smsSentCoolingData.getMsgContentExpires()) < 0
                    && message.equals(smsSentCoolingData.getMessage())) {

                SmsProcessingStatusReason smsProcessingStatusReason = SmsProcessingStatusReason.SMS_MESSAGE_CONTENT_IN_COOLING_PERIOD;
                LOG.debug(smsProcessingStatusReason.name() + " remaining time " +
                        TimeUnit.MILLISECONDS.toSeconds(smsSentCoolingData.getMsgContentExpires().getTime() - currentDate.getTime()));
                return new SmsValidationResult(false, SmsProcessingStatusReason.SMS_MESSAGE_CONTENT_IN_COOLING_PERIOD.status());
            }
        }
        return smsValidationResult;
    }

    public Integer createCoolingPeriodData(User user, SmsGroup smsGroup, SmsType smsType, String mobileNumber,
                                           Integer messageHash, String message, String scheduledTimeZone) {

        SmsSentCoolingData smsSentCoolingData = new SmsSentCoolingData();
        smsSentCoolingData.setUser(user);
        smsSentCoolingData.setMobileNumber(mobileNumber);

        smsSentCoolingData.setMessageHash(messageHash);
        smsSentCoolingData.setMessage(message);
        smsSentCoolingData.setSmsTypeName(smsGroup.getSmsType().getName());

        smsSentCoolingData.setTimeZone(scheduledTimeZone);

        smsSentCoolingData.setSentAt(new Date());

        TimeZone timeZone = TimeZone.getTimeZone(scheduledTimeZone);
        if(smsGroup.getTypeMatchCoolingPeriod() != null && smsGroup.getTypeMatchCoolingPeriod().getValue() != null) {
            smsSentCoolingData.setSmsTypeExpires(getCoolingPeriodExpiry(smsGroup.getTypeMatchCoolingPeriod(), timeZone));
        }
        else if (smsType.getTypeMatchCoolingPeriod() != null && smsType.getTypeMatchCoolingPeriod().getValue() != null) {
            smsSentCoolingData.setSmsTypeExpires(getCoolingPeriodExpiry(smsType.getTypeMatchCoolingPeriod(), timeZone));
        }

        if(smsGroup.getContentMatchCoolingPeriod() != null && smsGroup.getContentMatchCoolingPeriod().getValue() != null) {
            smsSentCoolingData.setMsgContentExpires(getCoolingPeriodExpiry(smsGroup.getContentMatchCoolingPeriod(), timeZone));
        }
        else if (smsType.getContentMatchCoolingPeriod() != null && smsType.getContentMatchCoolingPeriod().getValue() != null) {
            smsSentCoolingData.setMsgContentExpires(getCoolingPeriodExpiry(smsType.getContentMatchCoolingPeriod(), timeZone));
        }

        Integer rowId = smsSentCoolingDataDao.createWhenNotExists(
                smsSentCoolingData, DateUtil.getDateTime(new Date()),
                DateUtil.getDateTime(smsSentCoolingData.getSmsTypeExpires()),
                DateUtil.getDateTime(smsSentCoolingData.getMsgContentExpires()));

        return rowId;
    }

    private boolean canCreateCoolingPeriodData(SmsGroup smsGroup, SmsType smsType) {

        return !(
                (smsGroup.getTypeMatchCoolingPeriod() == null || smsGroup.getTypeMatchCoolingPeriod().getValue() == null) &&
                (smsGroup.getContentMatchCoolingPeriod() == null || smsGroup.getContentMatchCoolingPeriod().getValue() == null) &&
                (smsType.getTypeMatchCoolingPeriod() == null || smsType.getTypeMatchCoolingPeriod().getValue() == null) &&
                (smsType.getContentMatchCoolingPeriod() == null || smsType.getContentMatchCoolingPeriod().getValue() == null)
        );
    }

    private Date getCoolingPeriodExpiry(CoolingPeriod coolingPeriod, TimeZone timeZone) {
        long coolingPeriodVal = 0;

        if (coolingPeriod.getUnit().equals(CoolingPeriodUnit.ABSOLUTE_PERIOD)) {
            coolingPeriodVal = coolingPeriod.getValue();
        }else if(coolingPeriod.getUnit().equals(CoolingPeriodUnit.CALENDAR_DAY)){
            coolingPeriodVal = TimeUnit.DAYS.toSeconds(coolingPeriod.getValue());
        }

        LocalDateTime ldt = LocalDateTime.now();
        ldt = ldt.plusSeconds(coolingPeriodVal);
        Date expiryDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        expiryDate = DateUtil.convertTimeZone(expiryDate, timeZone);
        return expiryDate;
    }

    public SmsInAssembly createCopySmsInAssembly(SmsInAssembly smsInAssembly,
                                                 SmsPrimaryProcessingState smsPrimaryProcessingState,
                                                 SmsSecondaryProcessingState smsSecondaryProcessingState) {
        SmsInAssembly newSmsInAssembly = new SmsInAssembly();
        SmsAssembly currSms = smsAssemblyDao.find(smsInAssembly.getSmsAssembly().getId());
        SmsRecord prevRecord = currSms.getSmsRecord();

        SmsRecord newRecord = copyRecordForRetry(prevRecord, smsPrimaryProcessingState, smsSecondaryProcessingState);
        SmsAssembly newSmsAssembly = createNewSmsAssembly(newRecord);
        smsAuditDao.create(newRecord);

        newSmsInAssembly.updateSmsAssembly(newSmsAssembly);
        return newSmsInAssembly;
    }

    public boolean hasSmsExpired(SmsInAssembly smsInAssembly) {

        Date sendBefore = smsInAssembly.getSmsAssembly().getSendBefore();

        String timeZone = smsInAssembly.getSmsAssembly().getScheduledTimeZone();
        Date currentDate;

        currentDate = DateUtil.convertTimeZone(new Date(), TimeZone.getTimeZone(timeZone));

        LOG.debug("Time left for expiry: "+(sendBefore.getTime() - currentDate.getTime())/1000.0+" seconds");
        return currentDate.compareTo(sendBefore) > 0;
    }

    public void removeSmsFromAssembly(SmsInAssembly smsInAssembly, SmsPrimaryProcessingState primaryProcessingState,
                                      SmsSecondaryProcessingState secondaryProcessingState, String smsProcessingStatusReason) {
        removeSmsFromAssembly(smsInAssembly, primaryProcessingState, secondaryProcessingState, smsProcessingStatusReason, null, null, null);
    }

    public void removeSmsFromAssembly(SmsInAssembly smsInAssembly, SmsPrimaryProcessingState primaryProcessingState,
                                      SmsSecondaryProcessingState secondaryProcessingState, String smsProcessingStatusReason,
                                      String responseCode, String responseMessage) {
        removeSmsFromAssembly(smsInAssembly, primaryProcessingState,
                secondaryProcessingState, smsProcessingStatusReason, responseCode, responseMessage, null);
    }
    public void removeSmsFromAssembly(SmsInAssembly smsInAssembly, SmsPrimaryProcessingState primaryProcessingState,
                                      SmsSecondaryProcessingState secondaryProcessingState, String smsProcessingStatusReason,
                                      String responseCode, String responseMessage, String gatewayStatus) {

        SmsAssembly currSms = smsAssemblyDao.find(smsInAssembly.getSmsAssembly().getId());

        SmsRecord smsRecord = currSms.getSmsRecord();
        smsRecord.setLastUpdatedOn(new Date());
        smsRecord.setGatewayStatus(gatewayStatus);

        smsRecord.setSmsPrimaryProcessingState(primaryProcessingState);
        smsRecord.setSmsSecondaryProcessingState(secondaryProcessingState);
        smsRecord.setStatusReason(smsProcessingStatusReason);

        Integer statusCheckTrialCount = (currSms.getStatusCheckTrialCount() == null) ? 0 : currSms.getStatusCheckTrialCount();
        Integer gatewayFindingFailureCount = (currSms.getGatewayFindingFailureCount() == null) ? 0 : currSms.getGatewayFindingFailureCount();
        Integer correlationIdTrialCount = (currSms.getCorrelationIdCheckTrialCount() == null) ? 0 : currSms.getCorrelationIdCheckTrialCount();

        smsRecord.setStatusCheckTrialCount(smsInAssembly.getStatusCheckTrialCount() + statusCheckTrialCount);
        smsRecord.setGatewayFindingFailureCount(smsInAssembly.getGatewayFindingFailureCount() + gatewayFindingFailureCount);
        smsRecord.setCorrelationIdCheckTrialCount(smsInAssembly.getCorrelationIdCheckTrialCount() + correlationIdTrialCount);

        smsRecordDao.update(smsRecord);

        smsAuditDao.create(smsRecord, responseCode, responseMessage);
        deleteSmsAssembly(smsInAssembly);
    }

    public void intermediateStatusUpdate(SmsInAssembly smsInAssembly, SmsPrimaryProcessingState primaryProcessingState,
                                         SmsSecondaryProcessingState secondaryProcessingState, String statusReason,
                                         String gatewayStatus) {

        intermediateStatusUpdate(
                smsInAssembly, primaryProcessingState, secondaryProcessingState, statusReason, gatewayStatus, null, null);
    }

    public void intermediateStatusUpdate(SmsInAssembly smsInAssembly, SmsPrimaryProcessingState primaryProcessingState,
                                         SmsSecondaryProcessingState secondaryProcessingState, String statusReason,
                                         String gatewayStatus, String responseCode, String responseMessage) {

        String prevGatewayStatus = smsInAssembly.getGatewayStatus();

        if (gatewayStatus.equals(prevGatewayStatus)) {
            return;
        } else {
            smsInAssembly.setGatewayStatus(gatewayStatus);
        }
        SmsAssembly currSms = smsAssemblyDao.find(smsInAssembly.getSmsAssembly().getId());
        currSms.setSmsPrimaryProcessingState(primaryProcessingState);
        currSms.setSmsSecondaryProcessingState(secondaryProcessingState);

        smsAssemblyDao.update(currSms);

        smsInAssembly.updateSmsAssembly(currSms);
        SmsRecord smsRecord = currSms.getSmsRecord();
        smsRecord.setLastUpdatedOn(new Date());

        smsRecord.setSmsPrimaryProcessingState(primaryProcessingState);
        smsRecord.setSmsSecondaryProcessingState(secondaryProcessingState);

        smsRecord.setStatusReason(statusReason);
        smsRecord.setGatewayStatus(gatewayStatus);

        smsRecordDao.update(smsRecord);
        smsAuditDao.create(smsRecord, responseCode, responseMessage);
    }

    public SmsInAssembly createNewSmsForRetrial(SmsInAssembly smsInAssembly) {

        LOG.debug("Sending to retry queue");

        SmsInAssembly newSmsInAssembly = createCopySmsInAssembly(smsInAssembly,
                SmsPrimaryProcessingState.SUBMISSION_UNDER_PROCESS, SmsSecondaryProcessingState.QUEUED_IN_RETRY_FOR_SUBMISSION);
        smsInAssembly.getGatewayMappings().forEach(newSmsInAssembly::addGatewayMapping);
        newSmsInAssembly.setCurrentGatewayMappingIndex(smsInAssembly.getCurrentGatewayMappingIndex());
        increaseRetrialCount(smsInAssembly, newSmsInAssembly);
        return newSmsInAssembly;
    }

    private void increaseRetrialCount(SmsInAssembly prevSms, SmsInAssembly newSmsInAssembly) {
        SmsAssembly currSms = smsAssemblyDao.find(newSmsInAssembly.getSmsAssembly().getId());

        Integer retryCount = prevSms.getSmsAssembly().getRetryCount();
        retryCount = (retryCount == null) ? 1 : retryCount + 1;
        SmsRecord smsRecord = currSms.getSmsRecord();

        currSms.setRetryCount(retryCount);
        smsAssemblyDao.update(currSms);
        newSmsInAssembly.updateSmsAssembly(currSms);

        smsRecord.setRetryCount(retryCount);
        smsRecordDao.update(smsRecord);
    }
}
