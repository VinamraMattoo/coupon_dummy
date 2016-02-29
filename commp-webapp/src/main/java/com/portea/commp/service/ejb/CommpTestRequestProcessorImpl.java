package com.portea.commp.service.ejb;

import com.portea.common.config.dao.ConfigParamDao;
import com.portea.common.config.dao.ConfigTargetTypeDao;
import com.portea.common.config.dao.TargetConfigValueDao;
import com.portea.common.config.domain.ConfigEngineParam;
import com.portea.common.config.domain.ConfigGatewayParam;
import com.portea.common.config.domain.ConfigTargetParam;
import com.portea.common.config.domain.ConfigTemplateParam;
import com.portea.commp.service.domain.SubmittedSmsVo;
import com.portea.commp.smsen.dao.*;
import com.portea.commp.smsen.domain.*;
import com.portea.commp.smsen.gw.PMockSmsGatewayStatus;
import com.portea.commp.smsen.util.DateUtil;
import com.portea.commp.smsen.util.TimeZoneEnum;
import com.portea.dao.JpaDao;
import com.portea.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@Stateless
public class CommpTestRequestProcessorImpl implements CommpTestRequestProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(CommpTestRequestProcessorImpl.class);
    private static final Integer DEFAULT_LIST_LIMIT = 10;

    @Inject @JpaDao
    private SmsQueueDao smsQueueDao;

    @Inject @JpaDao
    private BrandDao brandDao;

    @Inject @JpaDao
    private UserDao userDao;

    @Inject @JpaDao
    private SmsGroupDao smsGroupDao;

    @Inject @JpaDao
    private SmsTemplateDao smsTemplateDao;

    @Inject @JpaDao
    private ConfigTargetTypeDao configTargetTypeDao;

    @Inject @JpaDao
    private ConfigParamDao configParamDao;

    @Inject @JpaDao
    private TargetConfigValueDao targetConfigValueDao;

    @Inject @JpaDao
    private SmsAssemblyDao smsAssemblyDao;

    @Inject @JpaDao
    private SmsSentCoolingDataDao smsSentCoolingDataDao;

    @Inject @JpaDao
    private PatientDetailDao patientDetailDao;

    @Inject @JpaDao
    private SmsRecordDao smsRecordDao;

    @Inject @JpaDao
    private SmsAuditDao smsAuditDao;


    @EJB
    private SmsEngineUtil smsEngineUtil;


    @Override
    public Response createSmsBatch(final int count, String groupName, String phoneNumber,
                                   Integer userId, String templateName, String receiverType,
                                   String message, String brandName, Boolean appendTimeStampToMessage, String login) {

        LogUtil.entryTrace("createSmsBatch", LOG);

        long start = System.currentTimeMillis();

        int applicableCount = 1; // init to default
        int APPLICABLE_COUNT_MIN = 1;
        int APPLICABLE_COUNT_MAX = 50000;

        if (count >= APPLICABLE_COUNT_MIN && count <= APPLICABLE_COUNT_MAX) {
            applicableCount = count;
        }

        LOG.info("Creating " + applicableCount + " test SMS.");

        Brand testBrand;
        if (brandName == null) {

            return Response.status(Response.Status.BAD_REQUEST).header(
                    "Warning", "Brand name "+brandName +" is invalid").build();
        } else {
            try {

                testBrand = brandDao.find(brandName);
            } catch (NoResultException e) {
                return Response.status(Response.Status.BAD_REQUEST).header(
                        "Warning", "Brand name " + brandName + " is invalid").build();
            }
        }

        User testUser;
        try {

             testUser = userDao.getUser(login);
        } catch (NoResultException e) {
            return Response.status(Response.Status.BAD_REQUEST).header(
                    "Warning", "User login name " + login + " is invalid").build();
        }

        SmsGroup dummySmsGroup ;

        try {

            dummySmsGroup = smsGroupDao.findByName(groupName);
        } catch (NoResultException e) {
            return Response.status(Response.Status.BAD_REQUEST).header(
                    "Warning", "Group name " + groupName+ " is invalid").build();
        }

        TimeZone scheduledTimeZone = TimeZoneEnum.ASIA_KOLKATA.getTimeZone();
        SmsTemplate dummySmsTemplate = null;
        if (templateName != null) {

            try {

                dummySmsTemplate = smsTemplateDao.findByName(templateName);
            } catch (NoResultException e) {

                return Response.status(Response.Status.BAD_REQUEST).header(
                        "Warning", "Template name " + templateName+ " is invalid").build();
            }
        }
        else {
            dummySmsTemplate = smsTemplateDao.find(1);
        }

        LocalDateTime ldt = LocalDateTime.now();

        Date now = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        int afterSomeTimeVal = 3;

        ldt = ldt.plusSeconds((long)afterSomeTimeVal);

        Date afterSomeTime = DateUtil.convertTimeZone(Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()), scheduledTimeZone);

        //Calculating expiry time using loadWindow to make sure that the sms will be loaded by smsloaderjob;
        Integer loadWindowInMilliSec = Integer.parseInt(smsEngineUtil.getTargetConfigValue(ConfigTargetType.SMS_ENGINE, ConfigEngineParam.
                NEW_SMS_LOAD_WINDOW, null, ConfigEngineParam.NEW_SMS_LOAD_WINDOW.getDefaultValue()));

        int addExpiryTime = 12000; //200 min

        Long loadWindow = TimeUnit.MILLISECONDS.toSeconds(loadWindowInMilliSec);

        ldt = ldt.plusSeconds(loadWindow + addExpiryTime);

        Date sendBefore = DateUtil.convertTimeZone(Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()), scheduledTimeZone);

        String curatedPhoneNumber = (phoneNumber != null && phoneNumber.length() == 10) ? "91" + phoneNumber : "910000000000";

            for (int i = 0; i < applicableCount; i++) {

            SmsQueue smsQueue = new SmsQueue();

            smsQueue.setCountryCode("IN");

            String smsMessage = message;
            if (appendTimeStampToMessage) {
                if (smsMessage == null) {
                    smsMessage = "A message created at " + new Date()+".";
                }else {
                    smsMessage = message+". Created at "+new Date()+".";
                }
            }
            smsQueue.setMessage(smsMessage);

            smsQueue.setBrand(testBrand);
            smsQueue.setMobileNumber(curatedPhoneNumber);
            smsQueue.setUser(testUser);
            smsQueue.setReceiverType(receiverType);

            smsQueue.setScheduleId("SampleScheduleId");
            smsQueue.setScheduledTimeZone(scheduledTimeZone.getID());
            smsQueue.setScheduledTime(afterSomeTime);
            smsQueue.setScheduleType("SampleScheduleType");

            smsQueue.setCreatedOn(new Date());
            smsQueue.setSendBefore(sendBefore);

            smsQueue.setSmsGroup(dummySmsGroup);
            smsQueue.setSmsTemplate(dummySmsTemplate);

            smsQueueDao.create(smsQueue);
        }

        long end = System.currentTimeMillis();

        // TODO Implement AOP interceptor to measure method run-time
        String info = "Execution time for "+applicableCount+" test SMS creation : " + ((end - start) / 1000.0) + " seconds.";
        LOG.info(info);

        LogUtil.exitTrace("createSmsBatch", LOG);
        return Response.status(Response.Status.OK).header("info",info).build();
    }

    @Override
    public String getTargetConfigValue(String targetTypeName, String configParamName, Integer targetId) {

        ConfigTargetType targetType = null;
        ConfigTargetType[] configTargetTypes = ConfigTargetType.values();
        for(ConfigTargetType configTargetType : configTargetTypes){
            if(configTargetType.name().equals(targetTypeName)){
                targetType = configTargetType;
                break;
            }
        }

        ConfigTargetParam configTargetParam = getConfigTargetParam(configParamName, null);

        if(configTargetParam == null){
            return "-1";
        }
        return smsEngineUtil.getTargetConfigValue(targetType, configTargetParam, targetId,"-1");
    }

    @Override
    public String submitPMockSms(Integer count, Boolean error) {
        if (error != null && error) {

            throw new InternalServerErrorException();
        }
        int beginFrom = 1000;
        count = (count == null) ? 0 : count;

        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
        String dateFormat = sdf.format(new Date());
        return beginFrom + count + "-" + (dateFormat);
    }

    @Override
    public String getPMockSmsStatus(String status, Boolean error) {
        if (error != null && error) {

            throw new BadRequestException();
        }
        if (status == null) {
            status = PMockSmsGatewayStatus.PMOCK_DELIVERED.getName();
        }
        return status;
    }

    @Override
    public List<String> getSmsGroupForNameMatch(String name, Integer limit) {
        if (limit == null) {
            limit = DEFAULT_LIST_LIMIT;
        }
        List<SmsGroup> smsGroups = smsGroupDao.getSmsGroupForNameMatch(name, limit);

        List<String> groupNames = new ArrayList<>();
        smsGroups.forEach(smsGroup -> groupNames.add(smsGroup.getName()));
        return groupNames;
    }

    @Override
    public List<String> getUserForLoginNameMatch(String login, Integer limit) {
        if (limit == null) {
            limit = DEFAULT_LIST_LIMIT;
        }
        List<User> users = userDao.getUserForLoginNameMatch(login, limit);

        List<String> userNames = new ArrayList<>();
        users.forEach(user -> userNames.add(user.getLogin()));
        return userNames;
    }

    @Override
    public SubmittedSmsVo getSubmittedSms(Long fromDate, Long tillDate, Boolean detailed) {
        if (fromDate == null) {
            throw new BadRequestException();
        }
        SubmittedSmsVo submittedSmsVo = new SubmittedSmsVo();
        Date till = (tillDate == null) ? new Date() : new Date(tillDate);

        Date from = new Date(fromDate);

        if (detailed != null && ! detailed) {

            Long count = smsAuditDao.getSubmittedSms(from, till);
            LOG.debug("Total no of submitted sms from " + from + " till " + till + " are " + count);
            submittedSmsVo.setCount(count);

        } else {
            Object[] fromDateData = smsAuditDao.getSubmittedSmsStartingTime(from, till);
            Object[] endDateData = smsAuditDao.getSubmittedSmsEndingTime(from, till);
            if (! fromDateData[1].equals(endDateData[1])) {
                LOG.error("Count received from getSubmittedSmsStartingTime: "+fromDateData[1] +"is not " +
                        "same as getSubmittedSmsEndingTime: "+endDateData[1]);
            }
            submittedSmsVo.setCount((Long) fromDateData[1]);
            submittedSmsVo.setStartDate((Date) fromDateData[0]);
            submittedSmsVo.setEndDate((Date) endDateData[0]);
        }
        return submittedSmsVo;
    }

    /**
     * ConfigParamName is a string value and it can be representing any of the parameters from different target type
     * enums. To find a parameter which is present in any of this enum use this method and set configTargetParams as null
     * while calling this method.
     */
    private ConfigTargetParam getConfigTargetParam(String configParamName, ConfigTargetParam[] configTargetParams) {

        if(configTargetParams == null){
            configTargetParams = ConfigEngineParam.values();
        }else if(configTargetParams instanceof ConfigEngineParam[]){
            configTargetParams = ConfigTemplateParam.values();
        }else if(configTargetParams instanceof ConfigTemplateParam[]){
            configTargetParams = ConfigGatewayParam.values();
        }else{
            return null;
        }

        for(ConfigTargetParam configTargetParam : configTargetParams){
            if(configTargetParam.name().equals(configParamName)){
                return configTargetParam;
            }
        }
        return getConfigTargetParam(configParamName, configTargetParams);
    }

}
