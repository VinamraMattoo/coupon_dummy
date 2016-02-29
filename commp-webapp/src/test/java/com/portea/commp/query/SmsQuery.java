package com.portea.commp.query;

public enum SmsQuery {

    GET_PHONE_NUMBER_VALIDATION_STATUS(" select value from `cmn_target_config_value` " +
            "           where target_config_id = (SELECT ctc.id " +
            "           FROM cmn_target_config ctc " +
            "           WHERE ctc.target_id IS NULL " +
            "           AND ctc.config_param_id = (SELECT id " +
            "           FROM cmn_config_param " +
            "           WHERE name = 'NEW_SMS_PHONE_NUMBER_VALIDATION'))"),

    UPDATE_PHONE_NUMBER_VALIDATION_STATUS_TO_TRUE("update `cmn_target_config_value` set value = 'true' " +
            "            where target_config_id = (SELECT ctc.id\n" +
            "            FROM cmn_target_config ctc\n" +
            "            WHERE ctc.target_id IS NULL\n" +
            "            AND ctc.config_param_id = (SELECT id\n" +
            "            FROM cmn_config_param\n" +
            "            WHERE name = 'NEW_SMS_PHONE_NUMBER_VALIDATION'))"),

    GET_PROCESSING_STATUS("select primary_processing_status,secondary_processing_status,status_reason from smsen_sms_record"),

    UPDATE_PHONE_NUMBER_VALIDATION_STATUS_TO_FALSE("update `cmn_target_config_value` set value = 'false'" +
            "            where target_config_id = (SELECT ctc.id\n" +
            "            FROM cmn_target_config ctc\n" +
            "            WHERE ctc.target_id IS NULL\n" +
            "            AND ctc.config_param_id = (SELECT id\n" +
            "            FROM cmn_config_param\n" +
            "            WHERE name = 'NEW_SMS_PHONE_NUMBER_VALIDATION'))"),

    GET_MESSAGE_PROCESSING_STATUS("select message from smsen_sms_record"),



    GET_SMS_MESSAGE_FORMAT_VALIDATION_STATUS("select value from `cmn_target_config_value` " +
            "       where target_config_id = (SELECT ctc.id " +
            "           FROM cmn_target_config ctc " +
            "           WHERE ctc.target_id IS NULL " +
            "           AND ctc.config_param_id = (SELECT id " +
            "           FROM cmn_config_param " +
            "           WHERE name = 'NEW_SMS_MESSAGE_FORMAT_VALIDATION'))"),

    UPDATE_SMS_MESSAGE_FORMAT_VALIDATION_STATUS_TO_TRUE("update `cmn_target_config_value` set value = 'true'" +
            "       where target_config_id = (SELECT ctc.id " +
            "           FROM cmn_target_config ctc " +
            "           WHERE ctc.target_id IS NULL " +
            "           AND ctc.config_param_id = (SELECT id " +
            "           FROM cmn_config_param " +
            "           WHERE name = 'NEW_SMS_MESSAGE_FORMAT_VALIDATION'))"),

    UPDATE_SMS_MESSAGE_FORMAT_VALIDATION_STATUS_TO_FALSE("update `cmn_target_config_value` set value = 'false'" +
            "       where target_config_id = (SELECT ctc.id " +
            "           FROM cmn_target_config ctc " +
            "           WHERE ctc.target_id IS NULL " +
            "           AND ctc.config_param_id = (SELECT id " +
            "           FROM cmn_config_param " +
            "           WHERE name = 'NEW_SMS_MESSAGE_FORMAT_VALIDATION'))"),;

    private String query;

    SmsQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
