package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsSentCoolingDataDao;
import com.portea.commp.smsen.domain.SmsSentCoolingData;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.MessageFormat;
import java.util.List;

@JpaDao
@Dependent
public class SmsSentCoolingDataJpaDao extends BaseJpaDao<Integer, SmsSentCoolingData> implements SmsSentCoolingDataDao {

    public SmsSentCoolingDataJpaDao() {
        super(SmsSentCoolingData.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<SmsSentCoolingData> getCoolingPeriodData(Integer userId, String mobileNumber, Integer messageHash, String smsTypeName) {
        Query query = entityManager.createNativeQuery("SELECT * From smsen_sms_sent_cooling_data sscd " +
                "WHERE (sscd.user_id = "+userId+" AND sscd.mobile_number = "+mobileNumber+") AND" +
                " ((sscd.message_hash = "+messageHash+" AND  sscd.msg_content_expires > " +
                "CONVERT_TZ(now(), (select @@time_zone), sscd.time_zone)) OR" +
                "(sscd.sms_type_name = '"+smsTypeName+"' AND sscd.sms_type_expires > " +
                "CONVERT_TZ(now(), (select @@time_zone), sscd.time_zone)))",SmsSentCoolingData.class);

        List<SmsSentCoolingData> list = query.getResultList();
        return list;
    }

    @Override
    public Integer createWhenNotExists(SmsSentCoolingData smsSentCoolingData, String sentAt,
                                    String smsTypeExpires, String msgContentExpires) {
        String userId = String.valueOf(smsSentCoolingData.getUser().getId());
        String mobileNumber = smsSentCoolingData.getMobileNumber();
        String messageHash = String.valueOf(smsSentCoolingData.getMessageHash());

        String smsTypeName = "'"+smsSentCoolingData.getSmsTypeName()+"'";
        String message = "'"+smsSentCoolingData.getMessage()+"'";
        String timeZone = "'"+smsSentCoolingData.getTimeZone()+"'";
        sentAt = (sentAt == null) ? null : "'" + sentAt + "'";
        smsTypeExpires = (smsTypeExpires == null) ? null : "'" + smsTypeExpires + "'";
        msgContentExpires = (msgContentExpires == null) ? null : "'" + msgContentExpires + "'";

        String dynamicSql = "(SELECT {0}  as user_id, {1} as mobile_number, {2} as message_hash, {3} message, " +
                                    "{4} as sms_type_name, {5} as time_zone, {6} as sent_at, {7} as sms_type_expires, "+
                                    "{8} as msg_content_expires) AS dyn_smsen_sms_sent_cooling_data ";

        String checkerSql = "SELECT * From smsen_sms_sent_cooling_data sscd" +
                                " WHERE (sscd.user_id = {0}  " +
                                    "AND sscd.mobile_number = {1} " +
                                    "AND (" +
                                            "(sscd.message_hash = {2} " +
                                            " AND  sscd.msg_content_expires > CONVERT_TZ(now(), (select @@time_zone), sscd.time_zone)) " +
                                        "OR " +
                                            "(sscd.sms_type_name = {4} " +
                                            "AND sscd.sms_type_expires > CONVERT_TZ(now(), (select @@time_zone), sscd.time_zone))" +
                                         ")" +
                                     ")";

        String insertSql = "INSERT INTO smsen_sms_sent_cooling_data " +
                                        "(user_id, mobile_number, message_hash," +
                                        " message, sms_type_name, time_zone, sent_at," +
                                        " sms_type_expires, msg_content_expires) " +
                                "SELECT * FROM "+dynamicSql+ "WHERE NOT EXISTS("+checkerSql+") limit 1";

        String formattedMsg = MessageFormat
                .format(insertSql, userId, mobileNumber, messageHash, message, smsTypeName,
                        timeZone, sentAt, smsTypeExpires, msgContentExpires);

        Query query = entityManager.createNativeQuery(formattedMsg);
        return query.executeUpdate();
    }

}
