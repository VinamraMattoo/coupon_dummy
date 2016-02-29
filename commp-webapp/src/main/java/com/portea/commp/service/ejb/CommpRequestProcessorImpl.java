package com.portea.commp.service.ejb;

import com.portea.common.config.domain.ConfigSmsTypeParam;
import com.portea.common.config.domain.ConfigEngineParam;
import com.portea.commp.service.domain.SendSmsRequest;
import com.portea.commp.service.domain.SendSmsResponse;
import com.portea.commp.service.domain.SmsAssemblyVo;
import com.portea.commp.service.domain.*;
import com.portea.commp.service.exception.*;
import com.portea.commp.smsen.dao.*;
import com.portea.commp.smsen.domain.*;
import com.portea.commp.smsen.engine.ReceiverType;
import com.portea.commp.smsen.engine.SmsValidationResult;
import com.portea.commp.smsen.engine.batch.SmsBatchedQueueManager;
import com.portea.commp.smsen.gw.SmsGatewayManager;
import com.portea.commp.smsen.util.DateUtil;
import com.portea.commp.smsen.util.StringUtil;
import com.portea.commp.smsen.util.TimeZoneEnum;
import com.portea.commp.web.rapi.exception.IncompleteRequestException;
import com.portea.dao.Dao;
import com.portea.dao.JpaDao;
import com.portea.util.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@Stateless
public class CommpRequestProcessorImpl implements CommpRequestProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(CommpRequestProcessorImpl.class);

    @Inject
    SmsBatchedQueueManager queueManager;

    @Inject @JpaDao
    private UserDao userDao;

    @Inject @JpaDao
    private SmsTemplateDao smsTemplateDao;

    @Inject @JpaDao
    private SmsGroupDao smsGroupDao;

    @Inject @JpaDao
    private BrandDao brandDao;

    @Inject @JpaDao
    private SmsAssemblyDao smsAssemblyDao;

    @Inject @JpaDao
    private SmsRecordDao smsRecordDao;

    @EJB
    private SmsEngineUtil smsEngineUtil;

    @Inject
    private SmsGatewayManager smsGatewayManager;

    @Inject @JpaDao
    private SmsGatewayDao smsGatewayDao;

    @Inject @JpaDao
    private SmsAuditDao smsAuditDao;

    @Inject @JpaDao
    private SmsSenderDao smsSenderDao;

    @Inject @JpaDao
    private SmsTypeDao smsTypeDao;

    @Inject @JpaDao
    private PatientDetailDao patientDetailDao;

    @Inject @JpaDao
    private SmsMessageBatchDao smsMessageBatchDao;

    @Inject @JpaDao
    private SmsLotDao smsLotDao;

    @Inject @JpaDao
    private SmsMessageBatchRecordMappingDao smsMessageBatchRecordMappingDao;

    @Override
    public void queueDirectSms(SmsAssemblyVo smsAssemblyVo) {

        LOG.debug("Queuing direct sms");

        validateSms(smsAssemblyVo);

        SmsGroup smsGroup = smsGroupDao.find(smsAssemblyVo.getSmsGroup());

        Boolean validatePhoneNumber = Boolean.parseBoolean(
                smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE, ConfigEngineParam.
                        NEW_SMS_PHONE_NUMBER_VALIDATION, null, ConfigEngineParam.NEW_SMS_PHONE_NUMBER_VALIDATION.getDefaultValue()));

        Boolean validateMessage = Boolean.parseBoolean(
                smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE, ConfigEngineParam.
                        NEW_SMS_MESSAGE_FORMAT_VALIDATION, null, ConfigEngineParam.NEW_SMS_MESSAGE_FORMAT_VALIDATION.getDefaultValue()));

        Boolean doDndCheck = Boolean.parseBoolean(
                smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE, ConfigEngineParam.
                        NEW_SMS_USER_DND_VALIDATION, null, ConfigEngineParam.NEW_SMS_USER_DND_VALIDATION.getDefaultValue()));

        User user = userDao.find(smsAssemblyVo.getUserId());
        SmsValidationResult smsValidationResult = smsEngineUtil.getValidationResult(smsAssemblyVo.getMobileNumber(),
                user, smsAssemblyVo.getMessage(), smsAssemblyVo.getReceiverType(),
                smsGroup, validatePhoneNumber, validateMessage, doDndCheck);

        if (smsValidationResult.isValid()) {
            SmsRecord smsRecord = getSmsRecord(smsAssemblyVo);
            smsRecordDao.create(smsRecord);
            SmsAssembly smsAssembly = smsEngineUtil.createNewSmsAssembly(smsRecord);
            smsAuditDao.create(smsRecord);
            queueManager.queueDirectSms(smsAssembly);
        } else {
            SmsRecord smsRecord = getSmsRecord(smsAssemblyVo);
            smsRecord.setSmsPrimaryProcessingState(SmsPrimaryProcessingState.LOADED_FOR_CREATION);
            smsRecord.setSmsSecondaryProcessingState(SmsSecondaryProcessingState.NEVER_SENT_FOR_SUBMISSION);
            smsRecord.setStatusReason(smsValidationResult.getReason());
            smsRecord = smsRecordDao.create(smsRecord);
            smsAuditDao.create(smsRecord);
            LOG.debug("Validation Failed " + smsValidationResult.getReason() + ". Sms record id " + smsRecord.getId());
        }
    }


    public void resetSmsSubmissionCount(String gatewayName) {
        smsGatewayManager.restSmsSubmissionCount(getGatewayId(gatewayName));
    }

    public Integer getSmsSubmissionCount(String gatewayName) {
        return smsGatewayManager.getSubmissionCount(getGatewayId(gatewayName));
    }

    @Override
    public SendSmsResponse queueSms(SendSmsRequest sendSmsRequest) {

        Integer upperLimit = Integer.parseInt(smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE,
                ConfigEngineParam.SMS_RECEIVED_UPPER_LIMIT_PER_LOT, null, ConfigEngineParam.SMS_RECEIVED_UPPER_LIMIT_PER_LOT.getDefaultValue()));

        Integer givenSize = sendSmsRequest.getSizeSmsToBeSent();
        Integer maxMessageLength = getMaxMessageLength();
        LOG.debug("Sms received size: "+givenSize);
        if (givenSize > upperLimit) {
            throw new SmsReceivedUpperLimitExceededException(givenSize, upperLimit);
        }

        sendSmsRequest.validate();
        SmsSender smsSender;
        try {

            smsSender = smsSenderDao.find(sendSmsRequest.getSenderName());
            LOG.debug("Received send sms request from: " + smsSender.getName());
        } catch (NoResultException e) {
            throw new InvalidUserNameException(sendSmsRequest.getSenderName());
        }

        if (!isSenderRegistered(smsSender, sendSmsRequest.getKey())) {
            throw new InvalidCredentialException(smsSender.getName());
        }

        Integer wrapLength = getWrapLength();
        SendSmsResponse smsResponse = new SendSmsResponse();
        LOG.debug("Creating sms lot");

        SmsLot smsLot = smsLotDao.create(smsSender);
        smsResponse.setLotId(wrapTrackingId(Long.valueOf(smsLot.getId()), wrapLength));

        List<SendSmsResponse.MessageBatch> batchIds = createBatchIds(sendSmsRequest.getLot(), smsLot, maxMessageLength);
        smsResponse.setBatches(batchIds);
        LOG.debug("Successfully created sms lot");
        return smsResponse;
    }

    private Integer getWrapLength() {
        return 10;
    }

    private List<SendSmsResponse.MessageBatch> createBatchIds(List<MessageBatchVo> lot, SmsLot smsLot, Integer maxMessageLength) {
        List<SendSmsResponse.MessageBatch> batchIds = new ArrayList<>();
        final int[] index = {0};
        lot.forEach(messageBatchVo -> {
            String lotPrefix = "lot[" + index[0] + "].";

            final SmsType smsType;
            try {

                smsType = smsTypeDao.find(messageBatchVo.getSmsType());
            } catch (NoResultException e) {
                throw new InvalidRequestException(lotPrefix+"smsType", messageBatchVo.getSmsType());
            }

            final SmsGroup smsGroup = getDefaultGroup(smsType);
            final String timeZone = getScheduledTimeZone(messageBatchVo.getScheduledTimeZone());
            final Date scheduledTime = getScheduledTime(messageBatchVo.getScheduledTime(), timeZone);
            final Date sendBefore = getSendBefore(messageBatchVo.getSendBefore(), timeZone);

            SendSmsResponse.MessageBatch responseBatch = new SendSmsResponse.MessageBatch();
            SmsMessageBatch messageBatch = smsMessageBatchDao.create(smsLot, smsType);
            responseBatch.setBatchId(wrapTrackingId(Long.valueOf(messageBatch.getId()), getWrapLength()));

            String receiverType = messageBatchVo.getReceiverType();
            if (! ReceiverType.PATIENT.name().toLowerCase().equals(receiverType)) {
                throw new InvalidRequestException(lotPrefix+"receiverType", receiverType);
            }

            List<String> trackingIds = getTrackingIds(messageBatchVo.getMessages(), messageBatch,
                    receiverType, smsGroup, sendBefore, scheduledTime, timeZone, lotPrefix, maxMessageLength);
            responseBatch.setTrackingIds(trackingIds);
            batchIds.add(responseBatch);
            index[0]++;
        });
        return batchIds;
    }

    private List<String> getTrackingIds(List<MessageVo> messages,
                                        SmsMessageBatch messageBatch, String receiverType,
                                        SmsGroup smsGroup, Date sendBefore, Date scheduledTime,
                                        String timeZone, String lotPrefix, Integer maxMessageLength) {
        List<String> trackingIds = new ArrayList<>();

        final int[] messagesIndex = {0};
        messages.forEach(messageVo -> {
            String messagesPrefix = lotPrefix + "messages[" + messagesIndex[0] + "].";

            String message = messageVo.getText();

            if (message.length() == 0) {
                throw new MessageEmptyException(messagesPrefix + "text");
            }

            if (message.length() > maxMessageLength) {
                throw new MessageLengthExceedsMaxLimitException(message.length(), maxMessageLength);
            }
            final int[] receiversIndex = {0};
            messageVo.getReceivers().forEach(receiver -> {
                String receiverPrefix = messagesPrefix + "receivers[" + receiversIndex[0] + "]";
                Integer patientId;
                try {
                    patientId = Integer.parseInt(receiver);
                } catch (NumberFormatException e) {
                    throw new InvalidRequestException(receiverPrefix, receiver);
                }
                PatientDetail patientDetail = patientDetailDao.find(patientId);

                if (patientDetail == null) {
                    throw new InvalidRequestException(receiverPrefix, receiver);
                }
                User user = getEntity(patientDetail.getLoginId(), userDao);

                trackingIds.add(createSms(messageBatch, user, message, smsGroup, sendBefore,
                        scheduledTime, receiverType, timeZone,
                        getSmsSource(), getWrapLength()));
                receiversIndex[0]++;
            });

            messagesIndex[0]++;
        });

        return trackingIds;
    }

    private int getMaxMessageLength() {
        return Integer.parseInt(smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE,
                ConfigEngineParam.MESSAGE_MAX_LENGTH, null, ConfigEngineParam.MESSAGE_MAX_LENGTH.getDefaultValue()));
    }

    @Override
    public SmsStatusResponse getSmsStatus(String id) {
        if (id == null) {
            throw new IncompleteRequestException("id");
        }
        if (! StringUtil.isNumber(id)) {
            throw new InvalidRequestException("id", id);
        }
        Long trackingId = Long.parseLong(id);
        SmsRecord smsRecord = smsRecordDao.find(trackingId);
        if (smsRecord == null) {
            throw new InvalidRequestException("id", id);
        }
        return createSmsStatusResponse(smsRecord.getSmsSecondaryProcessingState(), smsRecord.getStatusReason());
    }

    @Override
    public BatchStatusResponse getBatchStatus(String id) {
        BatchStatusResponse response = new BatchStatusResponse();
        List<Integer> messageBatchIds = new ArrayList<>();
        if (! StringUtil.isNumber(id)) {
            throw new InvalidRequestException("id", id);
        }
        messageBatchIds.add(Integer.parseInt(id));
        List<SmsMessageBatchRecordMapping> mappings = smsMessageBatchRecordMappingDao.find(messageBatchIds);

        if (mappings.size() == 0) {
            LOG.debug("No messages found for given id " + id);
            throw new InvalidRequestException("id", id);
        }

        updateStatusResponse(mappings, response);
        return response;
    }

    @Override
    public LotStatusResponse getLotStatus(String id) {
        LotStatusResponse response = new LotStatusResponse();
        if (!StringUtil.isNumber(id)) {
            throw new InvalidRequestException("id", id);
        }

        Integer lotId = Integer.parseInt(id);
        List<SmsMessageBatchRecordMapping> mappings = smsMessageBatchRecordMappingDao.getMappings(lotId);

        if (mappings.size() == 0) {
            LOG.debug("No messages found for given id " + id);
            throw new InvalidRequestException("id", id);
        }
        updateStatusResponse(mappings, response);
        return response;
    }

    private void updateStatusResponse(List<SmsMessageBatchRecordMapping> mappings, IStatusResponse response) {
        Integer delivered = 0;
        Integer failure = 0;
        Integer pending = 0;
        for (SmsMessageBatchRecordMapping mapping : mappings) {
            SmsRecord smsRecord = mapping.getSmsRecord();
            SmsSecondaryProcessingState state = smsRecord.getSmsSecondaryProcessingState();

            if (state == null) {
                pending++;
                continue;
            }
            switch (state.getNormalizedStatus()) {
                case DELIVERED:
                    delivered++;
                    break;
                case FAILURE:
                    failure++;
                    break;
                case PENDING:
                    pending++;
                    break;
            }
        }
        response.setDelivered(delivered);
        response.setFailed(failure);
        response.setPending(pending);
        response.setTotal(mappings.size());
    }

    @Override
    public BatchFailureResponse getBatchFailureStatus(String id) {
        BatchFailureResponse batchFailureResponse = new BatchFailureResponse();

        List<Integer> messageBatchIds = new ArrayList<>();
        if (! StringUtil.isNumber(id)) {
            throw new InvalidRequestException("id", id);
        }
        messageBatchIds.add(Integer.parseInt(id));
        List<SmsMessageBatchRecordMapping> mappings = smsMessageBatchRecordMappingDao.find(messageBatchIds);

        if (mappings.size() == 0) {
            LOG.debug("No messages found for given id " + id);
            throw new InvalidRequestException("id", id);
        }

        final int[] count = {0};
        List<BatchFailureResponse.SmsFailureReason> smsFailureReasonList = new ArrayList<>();
        batchFailureResponse.setSmsFailures(smsFailureReasonList);
        mappings.forEach(mapping -> {
            SmsRecord smsRecord = mapping.getSmsRecord();
            SmsSecondaryProcessingState state = smsRecord.getSmsSecondaryProcessingState();
            if (state != null && state.getNormalizedStatus().equals(NormalizedSmsStatus.FAILURE)) {
                count[0]++;
                BatchFailureResponse.SmsFailureReason smsFailureReason= new BatchFailureResponse.SmsFailureReason();
                smsFailureReason.setId(wrapTrackingId(smsRecord.getId(), getWrapLength()));
                smsFailureReason.setReason(smsRecord.getStatusReason());
                smsFailureReasonList.add(smsFailureReason);
            }
        });
        batchFailureResponse.setTotal(count[0]);
        return batchFailureResponse;
    }

    /**
     * Returns the appropriate normalized sms status. Secondary processing status is not created
     * during the time when the sms is created for submission and before it is picked up by
     * sms processing queue. During this period Normalized status is given Pending state.
     */
    private SmsStatusResponse createSmsStatusResponse(SmsSecondaryProcessingState smsSecondaryProcessingState,
                                                      String statusReason) {
        SmsStatusResponse smsStatusResponse = new SmsStatusResponse();
        statusReason = (statusReason == null) ? "N/A" : statusReason;
        smsSecondaryProcessingState =
                (smsSecondaryProcessingState == null) ? SmsSecondaryProcessingState.QUEUED_FOR_SUBMISSION : smsSecondaryProcessingState;
        smsStatusResponse.setReason(statusReason);
        smsStatusResponse.setStatus(smsSecondaryProcessingState.getNormalizedStatus());
        return smsStatusResponse;

    }

    private String createSms(SmsMessageBatch messageBatch,User user, String message, SmsGroup smsGroup, Date sendBefore,
                             Date scheduledTime, String receiverType, String timeZone, SmsSource source, Integer wrapLength) {


        SmsValidationResult smsValidationResult = smsEngineUtil.getValidationResult(user.getMobileNumber()
        , user, message, receiverType, smsGroup);

        Brand brand = brandDao.find(com.portea.commp.smsen.engine.Brand.PORTEA.getName());

        SmsRecord smsRecord = getSmsRecord(message, user, receiverType, user.getMobileNumber(),
                scheduledTime, sendBefore, timeZone, smsGroup, source, SmsPrimaryProcessingState.CREATED_FOR_SUBMISSION,
                null, brand);

        if (smsValidationResult.isValid()) {

            smsRecordDao.create(smsRecord);
            smsEngineUtil.createNewSmsAssembly(smsRecord);

        } else {
            smsRecord.setSmsPrimaryProcessingState(SmsPrimaryProcessingState.LOADED_FOR_CREATION);
            smsRecord.setSmsSecondaryProcessingState(SmsSecondaryProcessingState.NEVER_SENT_FOR_SUBMISSION);
            smsRecord.setStatusReason(smsValidationResult.getReason());
            smsRecord = smsRecordDao.create(smsRecord);
            LOG.debug("Validation Failed " + smsValidationResult.getReason() + ". Sms record id " + smsRecord.getId());
        }
        smsAuditDao.create(smsRecord);

        smsMessageBatchRecordMappingDao.create(messageBatch, smsRecord);

        return wrapTrackingId(smsRecord.getId(), wrapLength);
    }

    private String wrapTrackingId(Long id, Integer totalLength) {

        StringBuilder trackingId = new StringBuilder();
        trackingId.append(String.valueOf(id));
        while (trackingId.length() < totalLength) {
            trackingId.insert(0, "0");
        }
        return trackingId.toString();
    }


    private SmsSource getSmsSource() {
        return SmsSource.BATCH_WEB_SERVICE;
    }


    private Date getScheduledTime(Date scheduledTime, String timeZone) {

        scheduledTime = (scheduledTime == null) ? new Date() : scheduledTime;
        return DateUtil.convertTimeZone(scheduledTime, TimeZone.getTimeZone(timeZone));
    }

    private String getScheduledTimeZone(String timeZone) {
        if (timeZone == null) {

            return TimeZoneEnum.ASIA_KOLKATA.getTimeZone().getID();
        }
        else {
            return timeZone;
        }
    }

    private Date getSendBefore(Date sendBefore, String timeZone) {
        if (sendBefore == null) {
            long oneHour = TimeUnit.HOURS.toMillis(1);
            return DateUtil.convertTimeZone(new Date(System.currentTimeMillis() + oneHour), TimeZone.getTimeZone(timeZone)); //todo:: get default value from config param.
        }
        return sendBefore;
    }

    private SmsGroup getDefaultGroup(SmsType type) {
        String groupName = smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_TYPE, ConfigSmsTypeParam.DEFAULT_GROUP_NAME,
                type.getId(), ConfigSmsTypeParam.DEFAULT_GROUP_NAME.getDefaultValue());
        return smsGroupDao.findByName(groupName); //todo:: Based on final agreement of implementation change this.
    }

    private boolean isSenderRegistered(SmsSender smsSender, String password) {
        boolean isValidUser;
        try{

            isValidUser = BCrypt.checkpw(password, smsSender.getPassword());
        }catch (NoResultException e){
            isValidUser = false;
        }
        return isValidUser;
    }

    public Integer getGatewayId(String gatewayName) {
        if(gatewayName == null) {
            throw new BadRequestException("gateway name not provided");
        }

        try {
            SmsGateway smsGateway = smsGatewayDao.find(gatewayName);
            return smsGateway.getId();
        }
        catch (NoResultException e) {
            throw new BadRequestException("Invalid gateway name "+gatewayName);
        }
    }

    private SmsRecord getSmsRecord(SmsAssemblyVo smsAssemblyVo) {

        Brand brand;
        if (smsAssemblyVo.getBrand() == null || brandDao.find(smsAssemblyVo.getBrand()) == null) {
            brand = brandDao.find(com.portea.commp.smsen.engine.Brand.PORTEA.getName());
        }
        else {
            brand = brandDao.find(smsAssemblyVo.getBrand());
        }

        SmsTemplate smsTemplate;
        if (smsAssemblyVo.getSmsTemplate() == null) {
            smsTemplate = null;
        }
        else {

            smsTemplate = smsTemplateDao.find(smsAssemblyVo.getSmsTemplate());
        }

        return getSmsRecord(smsAssemblyVo.getMessage(), getEntity(smsAssemblyVo.getUserId(), userDao),
                smsAssemblyVo.getReceiverType(), smsAssemblyVo.getMobileNumber(),
                smsAssemblyVo.getScheduledTime(), smsAssemblyVo.getSendBefore(),
                smsAssemblyVo.getScheduledTimeZone(), getEntity(smsAssemblyVo.getSmsGroup(), smsGroupDao),
                SmsSource.DIRECT_WEB_SERVICE, SmsPrimaryProcessingState.SUBMISSION_UNDER_PROCESS,
                SmsSecondaryProcessingState.QUEUED_FOR_SUBMISSION, brand,
                smsTemplate, smsAssemblyVo.getCountryCode(),
                smsAssemblyVo.getScheduledId(), smsAssemblyVo.getScheduledType());
    }

    private SmsRecord getSmsRecord(String message, User user, String receiverType,
                                   String mobileNumber, Date scheduledTime, Date sendBefore,
                                   String scheduledTimeZone, SmsGroup smsGroup, SmsSource source,
                                   SmsPrimaryProcessingState primaryProcessingState,
                                   SmsSecondaryProcessingState secondaryProcessingState, Brand brand) {
        return getSmsRecord(message, user, receiverType, mobileNumber, scheduledTime, sendBefore, scheduledTimeZone,
                smsGroup, source, primaryProcessingState, secondaryProcessingState, brand, null, null, null, null);
    }

    private SmsRecord getSmsRecord(String message, User user, String receiverType,
                                   String mobileNumber, Date scheduledTime, Date sendBefore,
                                   String scheduledTimeZone, SmsGroup smsGroup, SmsSource source,
                                   SmsPrimaryProcessingState primaryProcessingState,
                                   SmsSecondaryProcessingState secondaryProcessingState, Brand brand,
                                   SmsTemplate smsTemplate, String countryCode, String scheduledId, String scheduledType) {

        SmsRecord smsRecord = new SmsRecord();
        smsRecord.setBrand(brand);
        smsRecord.setLastUpdatedOn(new Date());
        smsRecord.setMessage(message);

        smsRecord.setUser(user);
        smsRecord.setReceiverType(receiverType);

        String curatedPhoneNumber =
                (mobileNumber.length() == 10) ? "91" + mobileNumber : mobileNumber;
        smsRecord.setMobileNumber(curatedPhoneNumber);

        smsRecord.setCountryCode(countryCode);
        smsRecord.setScheduledTime(scheduledTime);
        smsRecord.setSendBefore(sendBefore);

        smsRecord.setScheduledId(scheduledId);
        smsRecord.setScheduledTimeZone(scheduledTimeZone);
        smsRecord.setScheduledType(scheduledType);

        smsRecord.setSmsGroup(smsGroup);
        smsRecord.setSmsTemplate(smsTemplate);
        smsRecord.setSourceName(source);

        smsRecord.setSmsPrimaryProcessingState(primaryProcessingState);
        smsRecord.setSmsSecondaryProcessingState(secondaryProcessingState);
        smsRecord.setMessageHash(message.hashCode());

        return smsRecord;
    }

    private void validateSms(SmsAssemblyVo smsAssemblyVo) {



        if (smsAssemblyVo.getMobileNumber() == null) {
            throw new BadRequestException("Mobile number is null");
        }

        if (smsAssemblyVo.getMessage() == null) {
            throw new BadRequestException("Message is null");
        }

        if (smsAssemblyVo.getSendBefore() == null) {
            throw new BadRequestException("Send before is null");
        }



        validate(smsAssemblyVo.getUserId(), userDao, "user");
        validate(smsAssemblyVo.getSmsGroup(), smsGroupDao, "sms group");

    }

    private void validate(Integer id, Dao<Integer, ?> dao, String entityName) {
        if(id == null){
            throw new BadRequestException(entityName+" not specified");
        }

        Object entity = dao.find(id);

        if(entity == null){
            throw new BadRequestException(entityName+" not found for given id: "+id);
        }
    }

    private <E> E getEntity(Integer id, Dao<Integer, E> dao) {
        E entity;
        if (id == null) {
            throw new BadRequestException();
        }
        if (id < 0 || (entity = dao.find(id)) == null) {
            throw new BadRequestException();
        }
        return entity;
    }
}
