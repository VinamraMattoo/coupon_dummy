package com.portea.commp.query;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.*;
import java.sql.*;
import java.util.Date;


public class SmsQueueServiceTest {

    private Client client;
    private Connection con = null;
    private Date date = null;
    private Long epoch = null;
    private final String CON_DRIVER = "com.mysql.jdbc.Driver";
    private final String CON_DATABASE = "jdbc:mysql://localhost:3306/sms_management";
    private final String CON_USERNAME = "root";
    private final String CON_PASSWORD = "root";
    private final Long WAIT_TIME_FOR_SMS_CREATION = 25000L;
    private String baseURI = "http://commp.localhost:8080/rapi";
    private String smsMessage = null;

    @Before
    public void init() {
        try {
            this.client = ClientBuilder.newClient();
            date = new Date();
            epoch = date.getTime();
            Class.forName(CON_DRIVER);
            con = DriverManager.getConnection(CON_DATABASE, CON_USERNAME, CON_PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Error Driver class Not found " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error instantiating Database " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This test case tests an SMS when submitted to sms queue. And after a pre-defined interval of time when
     * checked in records. If records contains that SMS, then SMS was successfully picked by SMS Engine.
     */
    @Test
    public void testSuccessfulSmsQueue() {
        try {
            smsMessage = "Test Messsage create at " + date.toString();
            Statement stmt = con.createStatement();
            String sql = "INSERT into smsen_sms_queue " +
                    "values(" +
                    "null, 'IN', NOW(), " +
                    "'" + smsMessage + "', '919034454545', 'SampleReceiverType', " +
                    "'SampleScheduleId', DATE_ADD(NOW(),INTERVAL 16 SECOND), " +
                    "'+5.5', 'SampleScheduleType', DATE_ADD(NOW()," +
                    "INTERVAL 20 SECOND), 1, 4, " +
                    "1, 1)";
            int count = stmt.executeUpdate(sql);
            Assert.assertEquals("got back " + count +
                    " rows instead of 1", 1, count);
            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            ResultSet rs = stmt.executeQuery(SmsQuery.GET_MESSAGE_PROCESSING_STATUS.getQuery());
            rs.last();
            String message = rs.getString("message");
            Assert.assertEquals(smsMessage, message);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This test case tests a SMS which is in JSON format when hits the target URI and submitted. After a
     * Pre-defined interval of time when checked in records. if records table contains that SMS then SMS is successfully
     * picked by SMS Engine.
     */
    @Test
    public void testSuccessfulSmsQueueService() {
        try {
            smsMessage = "Test Message create at " + date.toString();
            WebTarget creationTarget = client.target(baseURI + "/sendSms");
            String jsonSms = "{\n" +
                    "\"receiverType\": \"SampleReceiverType\",\n" +
                    "\"userId\": 1,\n" +
                    "\"smsTemplate\": 1,\n" +
                    "\"smsGroup\": 4,\n" +
                    "\"mobileNumber\": \"917034189267\",\n" +
                    "\"countryCode\": \"IN\",\n" +
                    "\"message\":\"" + smsMessage + "\",\n" +
                    "\"sendBefore\":\" " + (epoch + 20000) + "\",\n" +
                    "\"brand\": 1,\n" +
                    "\"scheduledId\": \"SampleScheduleId\",\n" +
                    "\"scheduledType\": \"SampleScheduleType\",\n" +
                    "\"scheduledTime\":\" " + (epoch + 16000) + "\",\n" +
                    "\"scheduledTimeZone\": \"+5.5\"\n" +
                    "}";
            Response response = creationTarget.request().post(Entity.entity(jsonSms, "application/json"));
            Assert.assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SmsQuery.GET_MESSAGE_PROCESSING_STATUS.getQuery());
            rs.last();
            String message = rs.getString("message");
            Assert.assertEquals(smsMessage, message);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This test case tests a SMS which is in JSON format when hits the target URI and submitted, If starting
     * with 91 third number of mobile number is other then 9, 8 or 7, then when checked in records the
     * secondary_processing_status should be NEVER_SENT_FOR_SUBMISSION.
     */
    @Test
    public void testValidatePhoneNumber() {
        try {
            smsMessage = "Test Message create at " + date.toString();
            WebTarget creationTarget = client.target(baseURI + "/sendSms");
            String jsonSms = "{\n" +
                    "\"receiverType\": \"SampleReceiverType\",\n" +
                    "\"userId\": 1,\n" +
                    "\"smsTemplate\": 1,\n" +
                    "\"smsGroup\": 4,\n" +
                    "\"mobileNumber\": \"911034189267\",\n" +
                    "\"countryCode\": \"IN\",\n" +
                    "\"message\":\"" + smsMessage + "\",\n" +
                    "\"sendBefore\":\" " + (epoch + 20000) + "\",\n" +
                    "\"brand\": 1,\n" +
                    "\"scheduledId\": \"SampleScheduleId\",\n" +
                    "\"scheduledType\": \"SampleScheduleType\",\n" +
                    "\"scheduledTime\":\" " + (epoch + 16000) + "\",\n" +
                    "\"scheduledTimeZone\": \"+5.5\"\n" +
                    "}";
            Response response = creationTarget.request().post(Entity.entity(jsonSms, "application/json"));
            Assert.assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SmsQuery.GET_PROCESSING_STATUS.getQuery());
            rs.last();
            String status = rs.getString("secondary_processing_status");
            Assert.assertEquals("NEVER_SENT_FOR_SUBMISSION", status);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This test case tests a SMS which is in JSON format when hits the target URI and submitted, If the submitted
     * message contains {F character then when checked in records the secondary_processing_status should be
     * NEVER_SENT_FOR_SUBMISSION.
     */
    @Test
    public void testInvalidMessage() {
        try {
            smsMessage = "Test Message {F create at " + date.toString();
            WebTarget creationTarget = client.target(baseURI + "/sendSms");
            String jsonSms = "{\n" +
                    "\"receiverType\": \"SampleReceiverType\",\n" +
                    "\"userId\": 1,\n" +
                    "\"smsTemplate\": 1,\n" +
                    "\"smsGroup\": 4,\n" +
                    "\"mobileNumber\": \"919034189267\",\n" +
                    "\"countryCode\": \"IN\",\n" +
                    "\"message\":\"" + smsMessage + "\",\n" +
                    "\"sendBefore\":\" " + (epoch + 20000) + "\",\n" +
                    "\"brand\": 1,\n" +
                    "\"scheduledId\": \"SampleScheduleId\",\n" +
                    "\"scheduledType\": \"SampleScheduleType\",\n" +
                    "\"scheduledTime\":\" " + (epoch + 16000) + "\",\n" +
                    "\"scheduledTimeZone\": \"+5.5\"\n" +
                    "}";
            Response response = creationTarget.request().post(Entity.entity(jsonSms, "application/json"));
            Assert.assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SmsQuery.GET_PROCESSING_STATUS.getQuery());
            rs.last();
            String status = rs.getString("secondary_processing_status");
            Assert.assertEquals("NEVER_SENT_FOR_SUBMISSION", status);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This test case tests a SMS which is in JSON format when hits the target URI and submitted, If submitted
     * Brand_id is 2 which maps to Manipal and submitted Message contains word "Portea" then when checked in records the
     * secondary_processing_status for that SMS should be NEVER_SENT_FOR_SUBMISSION.
     */
    @Test
    public void testInvalidMessageWithBrandId() {
        try {
            smsMessage = "Hello portea  create at " + date.toString();
            WebTarget creationTarget = client.target(baseURI + "/sendSms");
            String jsonSms = "{\n" +
                    "\"receiverType\": \"SampleReceiverType\",\n" +
                    "\"userId\": 1,\n" +
                    "\"smsTemplate\": 1,\n" +
                    "\"smsGroup\": 4,\n" +
                    "\"mobileNumber\": \"919034189267\",\n" +
                    "\"countryCode\": \"IN\",\n" +
                    "\"message\":\"" + smsMessage + "\",\n" +
                    "\"sendBefore\":\" " + (epoch + 20000) + "\",\n" +
                    "\"brand\": 2,\n" +
                    "\"scheduledId\": \"SampleScheduleId\",\n" +
                    "\"scheduledType\": \"SampleScheduleType\",\n" +
                    "\"scheduledTime\":\" " + (epoch + 16000) + "\",\n" +
                    "\"scheduledTimeZone\": \"+5.5\"\n" +
                    "}";
            Response response = creationTarget.request().post(Entity.entity(jsonSms, "application/json"));
            Assert.assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT secondary_processing_status from smsen_sms_record");
            rs.last();
            String status = rs.getString("secondary_processing_status");
            Assert.assertEquals("NEVER_SENT_FOR_SUBMISSION", status);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * This test case tests a SMS with invalid phone number(starting number other than 7 or 8 or 9) when added into the
     * queue by directly inserting into the database.
     * The config value NEW_SMS_PHONE_NUMBER_VALIDATION is set to true to test this case.
     * If submitted successfully the primary_processing_status and secondary_processing_status should be
     * "LOADED_FOR_CREATION" and "NEVER_SENT_FOR_SUBMISSION" respectively from record table.
     * The config value is reset to initial value in the finally block.
     */
    @Test
    public void testInvalidPhoneNumberWithConfigTrue() {

        boolean configCurrentStatus = false;
        try {
            smsMessage = "Test message with invalid mobile number at" + date.toString();
            Statement stmt = con.createStatement();

            String sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + smsMessage + "'," +
                    "                   '914140793520','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    1,4,1,1\n" +
                    "                    )";

            String getConfigSQL = SmsQuery.GET_PHONE_NUMBER_VALIDATION_STATUS.getQuery();


            ResultSet resultSet = stmt.executeQuery(getConfigSQL);

            resultSet.last();

            configCurrentStatus = resultSet.getBoolean("value");


            String changeConfigToTrueSQL = SmsQuery.UPDATE_PHONE_NUMBER_VALIDATION_STATUS_TO_TRUE.getQuery();

            stmt.executeUpdate(changeConfigToTrueSQL);


            int resultCount = stmt.executeUpdate(sql);
            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);
            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            ResultSet processingStatusResultSet = stmt.executeQuery(SmsQuery.GET_PROCESSING_STATUS.getQuery());

            processingStatusResultSet.last();
            String actualPrimaryStatus = processingStatusResultSet.getString("primary_processing_status");
            String expectedPrimaryStatus = "LOADED_FOR_CREATION";

            Assert.assertEquals(expectedPrimaryStatus, actualPrimaryStatus);

            String actualSecondaryStatus = processingStatusResultSet.getString("secondary_processing_status");
            String expectedSecondaryStatus = "NEVER_SENT_FOR_SUBMISSION";

            Assert.assertEquals(expectedSecondaryStatus, actualSecondaryStatus);

            String actualStatusMessage = processingStatusResultSet.getString("status_reason");
            String expectedStatusMessage = "Phone number not valid";

            Assert.assertEquals(expectedStatusMessage, actualStatusMessage);

            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                Statement stmt = con.createStatement();

                String revertConfigBackSQL = "update `cmn_target_config_value` " +
                        "            set value = '" + configCurrentStatus + "'" +
                        "            where target_config_id = (SELECT ctc.id\n" +
                        "            FROM cmn_target_config ctc\n" +
                        "            WHERE ctc.target_id IS NULL\n" +
                        "            AND ctc.config_param_id = (SELECT id\n" +
                        "            FROM cmn_config_param\n" +
                        "            WHERE name = 'NEW_SMS_PHONE_NUMBER_VALIDATION'))";
                stmt.executeUpdate(revertConfigBackSQL);

                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * This test case tests a SMS with invalid phone number(starting number other than 7 or 8 or 9) when added into the
     * queue by directly inserting into the database. The config value NEW_SMS_PHONE_NUMBER_VALIDATION
     * is set to false to test this case.
     * If submitted successfully the primary_processing_status and secondary_processing_status should be
     * "SUBMISSION_COMPLETED" and "NEVER_SENT_DURING_SUBMISSION"
     * respectively from record table. The config value is reset to initial value in the finally block.
     */
    @Test
    public void testInvalidPhoneNumberWithConfigFalse() {

        boolean configCurrentStatus = false;

        try {
            WebTarget creationTarget = client.target("http://commp.localhost:8080/web/rws/entities/evictCache");

            Response response = creationTarget.request().get();


            smsMessage = "Test message with invalid mobile number at" + date.toString();

            Statement stmt = con.createStatement();

            String sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + smsMessage + "'," +
                    "                   '914140793520','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    1,4,1,1\n" +
                    "                    )";

            String getConfigSQL = SmsQuery.GET_PHONE_NUMBER_VALIDATION_STATUS.getQuery();

            ResultSet resultSet = stmt.executeQuery(getConfigSQL);

            resultSet.last();

            configCurrentStatus = resultSet.getBoolean("value");

            String changeConfigToFalseSQL = SmsQuery.UPDATE_PHONE_NUMBER_VALIDATION_STATUS_TO_FALSE.getQuery();


            stmt.executeUpdate(changeConfigToFalseSQL);

            int resultCount = stmt.executeUpdate(sql);
            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);
            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            ResultSet processingStatusResultSet = stmt.executeQuery(SmsQuery.GET_PROCESSING_STATUS.getQuery());
            processingStatusResultSet.last();

            String actualPrimaryStatus = processingStatusResultSet.getString("primary_processing_status");
            String expectedPrimaryStatus = "SUBMISSION_COMPLETED";

            Assert.assertEquals(expectedPrimaryStatus, actualPrimaryStatus);

            String actualSecondaryStatus = processingStatusResultSet.getString("secondary_processing_status");
            String expectedSecondaryStatus = "NEVER_SENT_DURING_SUBMISSION";

            Assert.assertEquals(expectedSecondaryStatus, actualSecondaryStatus);

            String actualStatusMessage = processingStatusResultSet.getString("status_reason");
            String expectedStatusMessage = "Phone number not valid";

            Assert.assertEquals(expectedStatusMessage, actualStatusMessage);

            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                Statement stmt = con.createStatement();

                String revertConfigBackSQL = "update `cmn_target_config_value` " +
                        "            set value = '" + configCurrentStatus + "'" +
                        "            where target_config_id = (SELECT ctc.id\n" +
                        "            FROM cmn_target_config ctc\n" +
                        "            WHERE ctc.target_id IS NULL\n" +
                        "            AND ctc.config_param_id = (SELECT id\n" +
                        "            FROM cmn_config_param\n" +
                        "            WHERE name = 'NEW_SMS_PHONE_NUMBER_VALIDATION'))";
                stmt.executeUpdate(revertConfigBackSQL);

                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * This test case tests a SMS with invalid character added in the queue directly by inserting into the database.
     * The config value NEW_SMS_PHONE_NUMBER_VALIDATION is set to true to test this case. If submitted successfully
     * the primary_processing status and the secondary_processing status should be LOADED_FOR_CREATION" and
     * "NEVER_SENT_FOR_SUBMISSION" respectively from record table.
     * The config value is reset to initial value in the finally block.
     */
    @Test
    public void testInvalidCharacterWithConfigTrue() {
        boolean configCurrentStatus = false;

        try {
            WebTarget creationTarget = client.target("http://commp.localhost:8080/web/rws/entities/evictCache");

            Response response = creationTarget.request().put(null);

            smsMessage = "Test message with invalid character {F at" + date.toString();

            Statement stmt = con.createStatement();


            String sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + smsMessage + " '," +
                    "                   '919140793520','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    1,4,1,1\n" +
                    "                    )";

            String getConfigSQL = SmsQuery.GET_SMS_MESSAGE_FORMAT_VALIDATION_STATUS.getQuery();

            ResultSet resultSet = stmt.executeQuery(getConfigSQL);

            resultSet.last();

            configCurrentStatus = resultSet.getBoolean("value");

            String changeConfigToTrueSQL = SmsQuery.UPDATE_SMS_MESSAGE_FORMAT_VALIDATION_STATUS_TO_TRUE.getQuery();

            stmt.executeUpdate(changeConfigToTrueSQL);

            int resultCount = stmt.executeUpdate(sql);

            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);

            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            String recordSQL = "SELECT primary_processing_status,secondary_processing_status" +
                    " from smsen_sms_record WHERE message = '" + smsMessage + "'";

            ResultSet processingStatusResultSet = stmt.executeQuery(recordSQL);

            processingStatusResultSet.next();

            String actualPrimaryStatus = processingStatusResultSet.getString("primary_processing_status");
            String expectedPrimaryStatus = "LOADED_FOR_CREATION";

            Assert.assertEquals(expectedPrimaryStatus, actualPrimaryStatus);

            String actualSecondaryStatus = processingStatusResultSet.getString("secondary_processing_status");
            String expectedSecondaryStatus = "NEVER_SENT_FOR_SUBMISSION";

            Assert.assertEquals(expectedSecondaryStatus, actualSecondaryStatus);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                Statement stmt = con.createStatement();

                String revertConfigBackSQL = "update `cmn_target_config_value` " +
                        "            set value = '" + configCurrentStatus + "'" +
                        "            where target_config_id = (SELECT ctc.id\n" +
                        "            FROM cmn_target_config ctc\n" +
                        "            WHERE ctc.target_id IS NULL\n" +
                        "            AND ctc.config_param_id = (SELECT id\n" +
                        "            FROM cmn_config_param\n" +
                        "            WHERE name = 'NEW_SMS_MESSAGE_FORMAT_VALIDATION '))";
                stmt.executeUpdate(revertConfigBackSQL);

                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This test case tests a SMS with invalid character added in the queue directly by inserting into the database.
     * The config value NEW_SMS_PHONE_NUMBER_VALIDATION is set to false to test this case.
     * If submitted successfully the primary_processing status and the secondary_processing status
     * should be "SUBMISSION_COMPLETED" and "NEVER_SENT_DURING_SUBMISSION" respectively from record table.
     * The config value is reset to initial value in the finally block.
     */
    @Test
    public void testInvalidCharacterWithConfigFalse() {
        boolean configCurrentStatus = false;

        try {
            WebTarget creationTarget = client.target("http://commp.localhost:8080/web/rws/entities/evictCache");

            Response response = creationTarget.request().put(null);

            smsMessage = "Test message with invalid character {F at " + date.toString();

            Statement stmt = con.createStatement();


            String sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + smsMessage + "'," +
                    "                   '919140793520','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    1,4,1,1\n" +
                    "                    )";

            String getConfigSQL = SmsQuery.GET_SMS_MESSAGE_FORMAT_VALIDATION_STATUS.getQuery();

            ResultSet resultSet = stmt.executeQuery(getConfigSQL);

            resultSet.last();

            configCurrentStatus = resultSet.getBoolean("value");

            String changeConfigToTrueSQL = SmsQuery.UPDATE_SMS_MESSAGE_FORMAT_VALIDATION_STATUS_TO_FALSE.getQuery();

            stmt.executeUpdate(changeConfigToTrueSQL);

            int resultCount = stmt.executeUpdate(sql);

            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);

            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            String recordSQL = "SELECT primary_processing_status,secondary_processing_status,status_reason" +
                    " from smsen_sms_record WHERE message = '" + smsMessage + "'";

            ResultSet processingStatusResultSet = stmt.executeQuery(recordSQL);

            processingStatusResultSet.next();
            String actualPrimaryStatus = processingStatusResultSet.getString("primary_processing_status");
            String expectedPrimaryStatus = "SUBMISSION_COMPLETED";

            Assert.assertEquals(expectedPrimaryStatus, actualPrimaryStatus);

            String actualSecondaryStatus = processingStatusResultSet.getString("secondary_processing_status");
            String expectedSecondaryStatus = "NEVER_SENT_DURING_SUBMISSION";

            Assert.assertEquals(expectedSecondaryStatus, actualSecondaryStatus);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                Statement stmt = con.createStatement();

                String revertConfigBackSQL = "update `cmn_target_config_value` " +
                        "            set value = '" + configCurrentStatus + "'" +
                        "            where target_config_id = (SELECT ctc.id\n" +
                        "            FROM cmn_target_config ctc\n" +
                        "            WHERE ctc.target_id IS NULL\n" +
                        "            AND ctc.config_param_id = (SELECT id\n" +
                        "            FROM cmn_config_param\n" +
                        "            WHERE name = 'NEW_SMS_MESSAGE_FORMAT_VALIDATION '))";
                stmt.executeUpdate(revertConfigBackSQL);

                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This test case tests a SMS with invalid word (like "PORTEA" for a sms for brand other than portea )
     * added in the queue directly by inserting into the database.
     * The config value NEW_SMS_PHONE_NUMBER_VALIDATION is set to true to test this case.
     * If submitted successfully the primary_processing status and the secondary_processing status
     * should be LOADED_FOR_CREATION" and "NEVER_SENT_FOR_SUBMISSION" respectively from record table.
     * The config value is reset to initial value in the finally block.
     */
    @Test
    public void testInvalidWordWithConfigTrue() {


        boolean configCurrentStatus = false;

        try {
            WebTarget creationTarget = client.target("http://commp.localhost:8080/web/rws/entities/evictCache");

            Response response = creationTarget.request().put(null);

            smsMessage = "Test message with invalid word PORTEA at" + date.toString();

            Statement stmt = con.createStatement();


            String sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + smsMessage + " '," +
                    "                   '919036767036','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    2,4,1,1\n" +
                    "                    )";

            String getConfigSQL = SmsQuery.GET_SMS_MESSAGE_FORMAT_VALIDATION_STATUS.getQuery();

            ResultSet resultSet = stmt.executeQuery(getConfigSQL);

            resultSet.last();

            configCurrentStatus = resultSet.getBoolean("value");

            String changeConfigToTrueSQL = SmsQuery.UPDATE_SMS_MESSAGE_FORMAT_VALIDATION_STATUS_TO_TRUE.getQuery();

            stmt.executeUpdate(changeConfigToTrueSQL);

            int resultCount = stmt.executeUpdate(sql);

            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);

            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            String recordSQL = "SELECT primary_processing_status,secondary_processing_status" +
                    " from smsen_sms_record WHERE message = '" + smsMessage + "'";

            ResultSet processingStatusResultSet = stmt.executeQuery(recordSQL);

            processingStatusResultSet.next();

            String actualPrimaryStatus = processingStatusResultSet.getString("primary_processing_status");
            String expectedPrimaryStatus = "LOADED_FOR_CREATION";

            Assert.assertEquals(expectedPrimaryStatus, actualPrimaryStatus);

            String actualSecondaryStatus = processingStatusResultSet.getString("secondary_processing_status");
            String expectedSecondaryStatus = "NEVER_SENT_FOR_SUBMISSION";

            Assert.assertEquals(expectedSecondaryStatus, actualSecondaryStatus);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                Statement stmt = con.createStatement();

                String revertConfigBackSQL = "update `cmn_target_config_value` " +
                        "            set value = '" + configCurrentStatus + "'" +
                        "            where target_config_id = (SELECT ctc.id\n" +
                        "            FROM cmn_target_config ctc\n" +
                        "            WHERE ctc.target_id IS NULL\n" +
                        "            AND ctc.config_param_id = (SELECT id\n" +
                        "            FROM cmn_config_param\n" +
                        "            WHERE name = 'NEW_SMS_MESSAGE_FORMAT_VALIDATION '))";
                stmt.executeUpdate(revertConfigBackSQL);

                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * This test case tests a SMS with invalid word (like "PORTEA" for a sms for brand other than portea )
     * added in the queue directly by inserting into the database.The config value NEW_SMS_PHONE_NUMBER_VALIDATION is
     * set to false to test this case.
     * If submitted successfully the primary_processing status and the secondary_processing status
     * should be "SUBMISSION_COMPLETED" and "NEVER_SENT_DURING_SUBMISSION" respectively from record table.
     * The config value is reset to initial value in the finally block.
     */
    @Test
    public void testInvalidWordWithConfigFalse() {


        boolean configCurrentStatus = false;

        try {
            WebTarget creationTarget = client.target("http://commp.localhost:8080/web/rws/entities/evictCache");

            Response response = creationTarget.request().put(null);

            smsMessage = "Test message with invalid word PORTEA at" + date.toString();

            Statement stmt = con.createStatement();


            String sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + smsMessage + " '," +
                    "                   '919036767036','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    2,4,1,1\n" +
                    "                    )";

            String getConfigSQL = SmsQuery.GET_SMS_MESSAGE_FORMAT_VALIDATION_STATUS.getQuery();

            ResultSet resultSet = stmt.executeQuery(getConfigSQL);

            resultSet.last();

            configCurrentStatus = resultSet.getBoolean("value");

            String changeConfigToTrueSQL = SmsQuery.UPDATE_SMS_MESSAGE_FORMAT_VALIDATION_STATUS_TO_FALSE.getQuery();

            stmt.executeUpdate(changeConfigToTrueSQL);

            int resultCount = stmt.executeUpdate(sql);

            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);

            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            String recordSQL = "SELECT primary_processing_status,secondary_processing_status" +
                    " from smsen_sms_record WHERE message = '" + smsMessage + "'";

            ResultSet processingStatusResultSet = stmt.executeQuery(recordSQL);

            processingStatusResultSet.next();

            String actualPrimaryStatus = processingStatusResultSet.getString("primary_processing_status");
            String expectedPrimaryStatus = "SUBMISSION_COMPLETED";

            Assert.assertEquals(expectedPrimaryStatus, actualPrimaryStatus);

            String actualSecondaryStatus = processingStatusResultSet.getString("secondary_processing_status");
            String expectedSecondaryStatus = "NEVER_SENT_DURING_SUBMISSION";

            Assert.assertEquals(expectedSecondaryStatus, actualSecondaryStatus);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                Statement stmt = con.createStatement();

                String revertConfigBackSQL = "update `cmn_target_config_value` " +
                        "            set value = '" + configCurrentStatus + "'" +
                        "            where target_config_id = (SELECT ctc.id\n" +
                        "            FROM cmn_target_config ctc\n" +
                        "            WHERE ctc.target_id IS NULL\n" +
                        "            AND ctc.config_param_id = (SELECT id\n" +
                        "            FROM cmn_config_param\n" +
                        "            WHERE name = 'NEW_SMS_MESSAGE_FORMAT_VALIDATION '))";
                stmt.executeUpdate(revertConfigBackSQL);

                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * This test case tests a SMS for a brand that doesn't accept SMS added in the queue directly by inserting
     * into the database. The config value NEW_SMS_PHONE_NUMBER_VALIDATION is set to true to test this case.
     * If submitted successfully the primary_processing status and the secondary_processing status should be
     * LOADED_FOR_CREATION" and "NEVER_SENT_FOR_SUBMISSION" respectively from record table.
     * The config value is reset to initial value in the finally block.
     */
    @Test
    public void testInappropriateBrandWithTrueConfig() {
        boolean configCurrentStatus = false;

        try {
            WebTarget creationTarget = client.target("http://commp.localhost:8080/web/rws/entities/evictCache");

            Response response = creationTarget.request().put(null);

            smsMessage = "Test message for inappropriate brand " + date.toString();

            Statement stmt = con.createStatement();


            String sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + smsMessage + "'," +
                    "                   '919036767036','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    3,4,1,1\n" +
                    "                    )";

            String getConfigSQL = SmsQuery.GET_SMS_MESSAGE_FORMAT_VALIDATION_STATUS.getQuery();

            ResultSet resultSet = stmt.executeQuery(getConfigSQL);

            resultSet.last();

            configCurrentStatus = resultSet.getBoolean("value");

            String changeConfigToTrueSQL = SmsQuery.UPDATE_SMS_MESSAGE_FORMAT_VALIDATION_STATUS_TO_TRUE.getQuery();

            stmt.executeUpdate(changeConfigToTrueSQL);

            int resultCount = stmt.executeUpdate(sql);

            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);

            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            String recordSQL = "SELECT primary_processing_status,secondary_processing_status,status_reason" +
                    " from smsen_sms_record WHERE message = '" + smsMessage + "'";

            ResultSet processingStatusResultSet = stmt.executeQuery(recordSQL);

            processingStatusResultSet.next();
            String actualPrimaryStatus = processingStatusResultSet.getString("primary_processing_status");
            String expectedPrimaryStatus = "LOADED_FOR_CREATION";

            Assert.assertEquals(expectedPrimaryStatus, actualPrimaryStatus);

            String actualSecondaryStatus = processingStatusResultSet.getString("secondary_processing_status");
            String expectedSecondaryStatus = "NEVER_SENT_FOR_SUBMISSION";

            Assert.assertEquals(expectedSecondaryStatus, actualSecondaryStatus);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                Statement stmt = con.createStatement();

                String revertConfigBackSQL = "update `cmn_target_config_value` " +
                        "            set value = '" + configCurrentStatus + "'" +
                        "            where target_config_id = (SELECT ctc.id\n" +
                        "            FROM cmn_target_config ctc\n" +
                        "            WHERE ctc.target_id IS NULL\n" +
                        "            AND ctc.config_param_id = (SELECT id\n" +
                        "            FROM cmn_config_param\n" +
                        "            WHERE name = 'NEW_SMS_MESSAGE_FORMAT_VALIDATION '))";
                stmt.executeUpdate(revertConfigBackSQL);

                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This test case tests a SMS for a brand that doesn't accept SMS added in the queue directly by inserting
     * into the database.The config value NEW_SMS_PHONE_NUMBER_VALIDATION is set to false to test this case.
     * If submitted successfully the primary_processing status and the secondary_processing status should be
     * "SUBMISSION_COMPLETED" and "NEVER_SENT_DURING_SUBMISSION" respectively from record table.
     * The config value is reset to initial value in the finally block.
     */
    @Test
    public void testInappropriateBrandWithFalseConfig() {

        boolean configCurrentStatus = false;

        try {
            WebTarget creationTarget = client.target("http://commp.localhost:8080/web/rws/entities/evictCache");

            Response response = creationTarget.request().put(null);

            smsMessage = "Test message with invalid word PORTEA at" + date.toString();

            Statement stmt = con.createStatement();


            String sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + smsMessage + " '," +
                    "                   '919036767036','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    2,4,1,1\n" +
                    "                    )";

            String getConfigSQL = SmsQuery.GET_SMS_MESSAGE_FORMAT_VALIDATION_STATUS.getQuery();

            ResultSet resultSet = stmt.executeQuery(getConfigSQL);

            resultSet.last();

            configCurrentStatus = resultSet.getBoolean("value");

            String changeConfigToTrueSQL = SmsQuery.UPDATE_SMS_MESSAGE_FORMAT_VALIDATION_STATUS_TO_FALSE.getQuery();

            stmt.executeUpdate(changeConfigToTrueSQL);

            int resultCount = stmt.executeUpdate(sql);

            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);

            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            String recordSQL = "SELECT primary_processing_status,secondary_processing_status" +
                    " from smsen_sms_record WHERE message = '" + smsMessage + "'";

            ResultSet processingStatusResultSet = stmt.executeQuery(recordSQL);

            processingStatusResultSet.next();

            String actualPrimaryStatus = processingStatusResultSet.getString("primary_processing_status");
            String expectedPrimaryStatus = "SUBMISSION_COMPLETED";

            Assert.assertEquals(expectedPrimaryStatus, actualPrimaryStatus);

            String actualSecondaryStatus = processingStatusResultSet.getString("secondary_processing_status");
            String expectedSecondaryStatus = "NEVER_SENT_DURING_SUBMISSION";

            Assert.assertEquals(expectedSecondaryStatus, actualSecondaryStatus);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                Statement stmt = con.createStatement();

                String revertConfigBackSQL = "update `cmn_target_config_value` " +
                        "            set value = '" + configCurrentStatus + "'" +
                        "            where target_config_id = (SELECT ctc.id\n" +
                        "            FROM cmn_target_config ctc\n" +
                        "            WHERE ctc.target_id IS NULL\n" +
                        "            AND ctc.config_param_id = (SELECT id\n" +
                        "            FROM cmn_config_param\n" +
                        "            WHERE name = 'NEW_SMS_MESSAGE_FORMAT_VALIDATION '))";
                stmt.executeUpdate(revertConfigBackSQL);

                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    /*Method contains the filepaths which has the SQL queries required for refreshing the database */
    public void refreshDatabase() {
        String filePathForRemoveTables = "/com/portea/commp/query/SMS-Remove-Tables.TEST.sql";

        String pathForConfigRemove = "/com/portea/commp/query/Common-Config-Remove-Tables.TEST.sql";
        String pathForCreateTables = "/com/portea/commp/query/SMS-Create-Tables.TEST.sql";
        String pathForCreateConfig = "/com/portea/commp/query/Common-Config-Create-Tables.TEST.sql";

        String pathForPopulateTables = "/com/portea/commp/query/SMS-Populate-Test-Data.TEST.sql";
        String pathForPopulateConfig = "/com/portea/commp/query/Common-Config-Populate-Default-Data.TEST.sql";

        updateDataBase(filePathForRemoveTables);
        updateDataBase(pathForConfigRemove);

        updateDataBase(pathForCreateTables);
        updateDataBase(pathForCreateConfig);
        updateDataBase(pathForPopulateTables);
        updateDataBase(pathForPopulateConfig);
    }

    /*Method extracts the SQL queries from the files coming as the parameter and updates the database*/
    public void updateDataBase(String filePath) {
        String s;
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            Statement statement = con.createStatement();
            br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(filePath)));
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }


            String[] inst = sb.toString().split(";");


            for (int i = 0; i < inst.length; i++) {
                if (!inst[i].trim().equals("")) {
                    statement.executeUpdate(inst[i]);
                    System.out.println(inst[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This test case tests two SMS  having same message added in the queue directly by inserting into the database.
     * The database is refreshed by calling the refreshDatabase method at the start of the test.
     * A new sms group is created with content match set as CALENDAR_DAY and type match as ABSOLUTE_PERIOD.
     * If first message is submitted successfully the primary_processing status and the secondary_processing status should be
     * "STATUS_CHECK_COMPLETED" and "SUCCESSFUL_DELIVERY" respectively from record table.
     * If second message is submitted successfully the primary_processing status and the secondary_processing status should be
     * "LOADED_FOR_CREATION" and "NEVER_SENT_FOR_SUBMISSION" respectively from record table.
     * */
    @Test
    public void testCoolingPeriod() {
        try {

            refreshDatabase();

            smsMessage = "Test message for cooling period at" + date.toString();

            Statement stmt = con.createStatement();

            String insertNewGroupQuery = "INSERT INTO smsen_sms_group VALUES ('5','CALENDAR_DAY','1',\n" +
                    "'temporary group',TRUE ,'DummyGroup5','3','ABSOLUTE_PERIOD','1800','1')";

            String insertMappings = "INSERT INTO smsen_sms_group_gateway_mapping VALUES (null,2,4,5)";

            stmt.executeUpdate(insertNewGroupQuery);

            stmt.executeUpdate(insertMappings);

            String sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + smsMessage + " '," +
                    "                   '9199483250540','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    2,5,1,1\n" +
                    "                    )";


            int resultCount = stmt.executeUpdate(sql);

            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);

            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            String recordSQL = "SELECT primary_processing_status, secondary_processing_status\n" +
                    "FROM smsen_sms_record WHERE message = '" + smsMessage + "'";


            ResultSet processingStatusResultSet = stmt.executeQuery(recordSQL);
            processingStatusResultSet.next();

            String actualPrimaryStatus = processingStatusResultSet.getString("primary_processing_status");
            String expectedPrimaryStatus = "STATUS_CHECK_COMPLETED";

            Assert.assertEquals(expectedPrimaryStatus, actualPrimaryStatus);

            String actualSecondaryStatus = processingStatusResultSet.getString("secondary_processing_status");
            String expectedSecondaryStatus = "SUCCESSFUL_DELIVERY";

            Assert.assertEquals(expectedSecondaryStatus, actualSecondaryStatus);


            String newSmsMessage = "Test message for cooling period after first SMS at" + date.toString();


            sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + newSmsMessage + " '," +
                    "                   '9199483250540','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    2,5,1,1\n" +
                    "                    )";

            String secondRecordSQL = "SELECT primary_processing_status, secondary_processing_status\n" +
                    "FROM smsen_sms_record WHERE message = '" + newSmsMessage + "'";

            resultCount = stmt.executeUpdate(sql);

            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);

            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            ResultSet secondaryProcessingStatusResultSet = stmt.executeQuery(secondRecordSQL);

            secondaryProcessingStatusResultSet.next();

            String secondaryActualPrimaryStatus = secondaryProcessingStatusResultSet.getString("primary_processing_status");
            String secondaryExpectedPrimaryStatus = "LOADED_FOR_CREATION";

            Assert.assertEquals(secondaryExpectedPrimaryStatus, secondaryActualPrimaryStatus);

            String secondaryActualSecondaryStatus = secondaryProcessingStatusResultSet.getString("secondary_processing_status");
            String secondaryExpectedSecondaryStatus = "NEVER_SENT_FOR_SUBMISSION";

            Assert.assertEquals(secondaryExpectedSecondaryStatus, secondaryActualSecondaryStatus);
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * This test case tests two SMS  having same message added in the queue directly by inserting into the database.
     * The database is refreshed by calling the refreshDatabase method at the start of the test.
     * A new sms group is created with content match set as ABSOLUTE_PERIOD and type match as CALENDAR_DAY.
     * If first message is submitted successfully the primary_processing status and the secondary_processing status should be
     * "STATUS_CHECK_COMPLETED" and "SUCCESSFUL_DELIVERY" respectively from record table.
     * If second message is submitted successfully the primary_processing status and the secondary_processing status should be
     * "LOADED_FOR_CREATION" and "NEVER_SENT_FOR_SUBMISSION" respectively from record table.
     * */
    @Test
    public void testCoolingPeriodSwappedTypeMatch() {
        try {
            refreshDatabase();
            smsMessage = "Test message for cooling period at" + date.toString();

            Statement stmt = con.createStatement();

            String insertNewGroupQuery = "INSERT INTO smsen_sms_group VALUES ('5','ABSOLUTE_PERIOD','1',\n" +
                    "'temporary group',TRUE ,'DummyGroup5','3','CALENDAR_DAY','1800','1')";

            String insertMappings = "INSERT INTO smsen_sms_group_gateway_mapping VALUES (null,2,4,5)";

            stmt.executeUpdate(insertNewGroupQuery);

            stmt.executeUpdate(insertMappings);


            String sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + smsMessage + " '," +
                    "                   '9199483250586','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    2,5,1,1\n" +
                    "                    )";


            int resultCount = stmt.executeUpdate(sql);

            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);

            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            String recordSQL = "SELECT primary_processing_status, secondary_processing_status\n" +
                    "FROM smsen_sms_record WHERE message = '" + smsMessage + "'";


            ResultSet processingStatusResultSet = stmt.executeQuery(recordSQL);
            processingStatusResultSet.next();

            String actualPrimaryStatus = processingStatusResultSet.getString("primary_processing_status");
            String expectedPrimaryStatus = "STATUS_CHECK_COMPLETED";

            Assert.assertEquals(expectedPrimaryStatus, actualPrimaryStatus);

            String actualSecondaryStatus = processingStatusResultSet.getString("secondary_processing_status");
            String expectedSecondaryStatus = "SUCCESSFUL_DELIVERY";

            Assert.assertEquals(expectedSecondaryStatus, actualSecondaryStatus);


            String newSmsMessage = "Test message for cooling period after first SMS at" + date.toString();


            sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + newSmsMessage + " '," +
                    "                   '9199483250586','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    2,5,1,1\n" +
                    "                    )";


            String secondRecordSQL = "SELECT primary_processing_status, secondary_processing_status\n" +
                    "FROM smsen_sms_record WHERE message = '" + newSmsMessage + "'";

            resultCount = stmt.executeUpdate(sql);

            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);

            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            ResultSet secondaryProcessingStatusResultSet = stmt.executeQuery(secondRecordSQL);

            secondaryProcessingStatusResultSet.next();

            String secondaryActualPrimaryStatus = secondaryProcessingStatusResultSet.getString("primary_processing_status");
            String secondaryExpectedPrimaryStatus = "LOADED_FOR_CREATION";

            Assert.assertEquals(secondaryExpectedPrimaryStatus, secondaryActualPrimaryStatus);

            String secondaryActualSecondaryStatus = secondaryProcessingStatusResultSet.getString("secondary_processing_status");
            String secondaryExpectedSecondaryStatus = "NEVER_SENT_FOR_SUBMISSION";

            Assert.assertEquals(secondaryExpectedSecondaryStatus, secondaryActualSecondaryStatus);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This test case tests two SMS  having same message added in the queue directly by inserting into the database.
     * The database is refreshed by calling the refreshDatabase method at the start of the test.
     * A new sms group is created with content match set as Null and type match as CALENDAR_DAY.
     * If first message is submitted successfully the primary_processing status and the secondary_processing status should be
     * "STATUS_CHECK_COMPLETED" and "SUCCESSFUL_DELIVERY" respectively from record table.
     * If second message is submitted successfully the primary_processing status and the secondary_processing status should be
     * "LOADED_FOR_CREATION" and "NEVER_SENT_FOR_SUBMISSION" respectively from record table.
     * */
    @Test
    public void testCoolingPeriodForTypeMatch() {
        try {
            refreshDatabase();
            smsMessage = "Test message for cooling period at" + date.toString();

            Statement stmt = con.createStatement();

            String insertNewGroupQuery = "INSERT INTO smsen_sms_group VALUES ('5',NULL ,NULL,\n" +
                    "'temporary group',TRUE ,'DummyGroup5','3','CALENDAR_DAY','1','1')";

            String insertMappings = "INSERT INTO smsen_sms_group_gateway_mapping VALUES (null,2,4,5)";

            stmt.executeUpdate(insertNewGroupQuery);

            stmt.executeUpdate(insertMappings);


            String sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + smsMessage + " '," +
                    "                   '9199483250786','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    2,5,1,1\n" +
                    "                    )";


            int resultCount = stmt.executeUpdate(sql);

            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);

            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            String recordSQL = "SELECT primary_processing_status, secondary_processing_status\n" +
                    "FROM smsen_sms_record WHERE message = '" + smsMessage + "'";


            ResultSet processingStatusResultSet = stmt.executeQuery(recordSQL);
            processingStatusResultSet.next();

            String actualPrimaryStatus = processingStatusResultSet.getString("primary_processing_status");
            String expectedPrimaryStatus = "STATUS_CHECK_COMPLETED";

            Assert.assertEquals(expectedPrimaryStatus, actualPrimaryStatus);

            String actualSecondaryStatus = processingStatusResultSet.getString("secondary_processing_status");
            String expectedSecondaryStatus = "SUCCESSFUL_DELIVERY";

            Assert.assertEquals(expectedSecondaryStatus, actualSecondaryStatus);


            String newSmsMessage = "Test message for cooling period after first SMS at" + date.toString();


            sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + newSmsMessage + " '," +
                    "                   '9199483250786','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    2,5,1,1\n" +
                    "                    )";


            String secondRecordSQL = "SELECT primary_processing_status, secondary_processing_status\n" +
                    "FROM smsen_sms_record WHERE message = '" + newSmsMessage + "'";

            resultCount = stmt.executeUpdate(sql);

            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);

            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            ResultSet secondaryProcessingStatusResultSet = stmt.executeQuery(secondRecordSQL);

            secondaryProcessingStatusResultSet.next();

            String secondaryActualPrimaryStatus = secondaryProcessingStatusResultSet.getString("primary_processing_status");
            String secondaryExpectedPrimaryStatus = "LOADED_FOR_CREATION";

            Assert.assertEquals(secondaryExpectedPrimaryStatus, secondaryActualPrimaryStatus);

            String secondaryActualSecondaryStatus = secondaryProcessingStatusResultSet.getString("secondary_processing_status");
            String secondaryExpectedSecondaryStatus = "NEVER_SENT_FOR_SUBMISSION";

            Assert.assertEquals(secondaryExpectedSecondaryStatus, secondaryActualSecondaryStatus);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This test case tests two SMS  having same message added in the queue directly by inserting into the database.
     * The database is refreshed by calling the refreshDatabase method at the start of the test.
     * A new sms group is created with content match set as Null and type match as ABSOLUTE_PERIOD.
     * If first message is submitted successfully the primary_processing status and the secondary_processing status should be
     * "STATUS_CHECK_COMPLETED" and "SUCCESSFUL_DELIVERY" respectively from record table.
     * If second message is submitted successfully the primary_processing status and the secondary_processing status should be
     * "LOADED_FOR_CREATION" and "NEVER_SENT_FOR_SUBMISSION" respectively from record table.
     */
    @Test
    public void testCoolingPeriodForAbsolutePeriodTypeMatch() {
        try {
            refreshDatabase();
            smsMessage = "Test message for cooling period at" + date.toString();

            Statement stmt = con.createStatement();

            String insertNewGroupQuery = "INSERT INTO smsen_sms_group VALUES ('5',NULL ,NULL,\n" +
                    "'temporary group',TRUE ,'DummyGroup5','3','ABSOLUTE_PERIOD','1800','1')";

            String insertMappings = "INSERT INTO smsen_sms_group_gateway_mapping VALUES (null,2,4,5)";

            stmt.executeUpdate(insertNewGroupQuery);

            stmt.executeUpdate(insertMappings);


            String sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + smsMessage + " '," +
                    "                   '9199483250786','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    2,5,1,1\n" +
                    "                    )";


            int resultCount = stmt.executeUpdate(sql);

            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);

            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            String recordSQL = "SELECT primary_processing_status, secondary_processing_status\n" +
                    "FROM smsen_sms_record WHERE message = '" + smsMessage + "'";


            ResultSet processingStatusResultSet = stmt.executeQuery(recordSQL);
            processingStatusResultSet.next();

            String actualPrimaryStatus = processingStatusResultSet.getString("primary_processing_status");
            String expectedPrimaryStatus = "STATUS_CHECK_COMPLETED";

            Assert.assertEquals(expectedPrimaryStatus, actualPrimaryStatus);

            String actualSecondaryStatus = processingStatusResultSet.getString("secondary_processing_status");
            String expectedSecondaryStatus = "SUCCESSFUL_DELIVERY";

            Assert.assertEquals(expectedSecondaryStatus, actualSecondaryStatus);


            String newSmsMessage = "Test message for cooling period after first SMS at" + date.toString();


            sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + newSmsMessage + " '," +
                    "                   '9199483250786','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    2,5,1,1\n" +
                    "                    )";


            String secondRecordSQL = "SELECT primary_processing_status, secondary_processing_status\n" +
                    "FROM smsen_sms_record WHERE message = '" + newSmsMessage + "'";

            resultCount = stmt.executeUpdate(sql);

            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);

            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            ResultSet secondaryProcessingStatusResultSet = stmt.executeQuery(secondRecordSQL);

            secondaryProcessingStatusResultSet.next();

            String secondaryActualPrimaryStatus = secondaryProcessingStatusResultSet.getString("primary_processing_status");
            String secondaryExpectedPrimaryStatus = "LOADED_FOR_CREATION";

            Assert.assertEquals(secondaryExpectedPrimaryStatus, secondaryActualPrimaryStatus);

            String secondaryActualSecondaryStatus = secondaryProcessingStatusResultSet.getString("secondary_processing_status");
            String secondaryExpectedSecondaryStatus = "NEVER_SENT_FOR_SUBMISSION";

            Assert.assertEquals(secondaryExpectedSecondaryStatus, secondaryActualSecondaryStatus);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This test case tests two SMS  having same message added in the queue directly by inserting into the database.
     * The database is refreshed by calling the refreshDatabase method at the start of the test.
     * A new sms group is created with content match set as CALENDAR_DAY and type match as null.
     * If first message is submitted successfully the primary_processing status and the secondary_processing status should be
     * "STATUS_CHECK_COMPLETED" and "SUCCESSFUL_DELIVERY" respectively from record table.
     * If second message is submitted successfully the primary_processing status and the secondary_processing status should be
     * "LOADED_FOR_CREATION" and "NEVER_SENT_FOR_SUBMISSION" respectively from record table.
     */
    @Test
    public void testCoolingPeriodForContentMatch() {
        try {
            refreshDatabase();
            smsMessage = "Test message for cooling period at" + date.toString();

            Statement stmt = con.createStatement();

            String insertNewGroupQuery = "INSERT INTO smsen_sms_group VALUES ('5','CALENDAR_DAY','1',\n" +
                    "'temporary group',TRUE ,'DummyGroup5','3',NULL ,NULL,'1')";

            String insertMappings = "INSERT INTO smsen_sms_group_gateway_mapping VALUES (null,2,4,5)";

            stmt.executeUpdate(insertNewGroupQuery);

            stmt.executeUpdate(insertMappings);


            String sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + smsMessage + " '," +
                    "                   '9199483250786','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    2,5,1,1\n" +
                    "                    )";


            int resultCount = stmt.executeUpdate(sql);

            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);

            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            String recordSQL = "SELECT primary_processing_status, secondary_processing_status\n" +
                    "FROM smsen_sms_record WHERE message = '" + smsMessage + "'";


            ResultSet processingStatusResultSet = stmt.executeQuery(recordSQL);
            processingStatusResultSet.next();

            String actualPrimaryStatus = processingStatusResultSet.getString("primary_processing_status");
            String expectedPrimaryStatus = "STATUS_CHECK_COMPLETED";

            Assert.assertEquals(expectedPrimaryStatus, actualPrimaryStatus);

            String actualSecondaryStatus = processingStatusResultSet.getString("secondary_processing_status");
            String expectedSecondaryStatus = "SUCCESSFUL_DELIVERY";

            Assert.assertEquals(expectedSecondaryStatus, actualSecondaryStatus);


            String newSmsMessage = "Test message for cooling period after first SMS at" + date.toString();


            sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + newSmsMessage + " '," +
                    "                   '9199483250786','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    2,5,1,1\n" +
                    "                    )";


            String secondRecordSQL = "SELECT primary_processing_status, secondary_processing_status\n" +
                    "FROM smsen_sms_record WHERE message = '" + newSmsMessage + "'";

            resultCount = stmt.executeUpdate(sql);

            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);

            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            ResultSet secondaryProcessingStatusResultSet = stmt.executeQuery(secondRecordSQL);

            secondaryProcessingStatusResultSet.next();

            String secondaryActualPrimaryStatus = secondaryProcessingStatusResultSet.getString("primary_processing_status");
            String secondaryExpectedPrimaryStatus = "LOADED_FOR_CREATION";

            Assert.assertEquals(secondaryExpectedPrimaryStatus, secondaryActualPrimaryStatus);

            String secondaryActualSecondaryStatus = secondaryProcessingStatusResultSet.getString("secondary_processing_status");
            String secondaryExpectedSecondaryStatus = "NEVER_SENT_FOR_SUBMISSION";

            Assert.assertEquals(secondaryExpectedSecondaryStatus, secondaryActualSecondaryStatus);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This test case tests two SMS  having same message added in the queue directly by inserting into the database.
     * The database is refreshed by calling the refreshDatabase method at the start of the test.
     * A new sms group is created with content match set as ABSOLUTE_PERIOD and type match as null.
     * If first message is submitted successfully the primary_processing status and the secondary_processing status should be
     * "STATUS_CHECK_COMPLETED" and "SUCCESSFUL_DELIVERY" respectively from record table.
     * If second message is submitted successfully the primary_processing status and the secondary_processing status should be
     * "LOADED_FOR_CREATION" and "NEVER_SENT_FOR_SUBMISSION" respectively from record table.
     */
    @Test
    public void testCoolingPeriodForAbsolutePeriodContentMatch() {
        try {
            refreshDatabase();
            smsMessage = "Test message for cooling period at" + date.toString();

            Statement stmt = con.createStatement();

            String insertNewGroupQuery = "INSERT INTO smsen_sms_group VALUES ('5','ABSOLUTE_PERIOD','1800',\n" +
                    "'temporary group',TRUE ,'DummyGroup5','3',NULL ,NULL,'1')";

            String insertMappings = "INSERT INTO smsen_sms_group_gateway_mapping VALUES (null,2,4,5)";

            stmt.executeUpdate(insertNewGroupQuery);

            stmt.executeUpdate(insertMappings);


            String sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + smsMessage + " '," +
                    "                   '9199483250786','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    2,5,1,1\n" +
                    "                    )";


            int resultCount = stmt.executeUpdate(sql);

            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);

            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            String recordSQL = "SELECT primary_processing_status, secondary_processing_status\n" +
                    "FROM smsen_sms_record WHERE message = '" + smsMessage + "'";


            ResultSet processingStatusResultSet = stmt.executeQuery(recordSQL);
            processingStatusResultSet.next();

            String actualPrimaryStatus = processingStatusResultSet.getString("primary_processing_status");
            String expectedPrimaryStatus = "STATUS_CHECK_COMPLETED";

            Assert.assertEquals(expectedPrimaryStatus, actualPrimaryStatus);

            String actualSecondaryStatus = processingStatusResultSet.getString("secondary_processing_status");
            String expectedSecondaryStatus = "SUCCESSFUL_DELIVERY";

            Assert.assertEquals(expectedSecondaryStatus, actualSecondaryStatus);


            String newSmsMessage = "Test message for cooling period after first SMS at" + date.toString();


            sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + newSmsMessage + " '," +
                    "                   '9199483250786','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    2,5,1,1\n" +
                    "                    )";


            String secondRecordSQL = "SELECT primary_processing_status, secondary_processing_status\n" +
                    "FROM smsen_sms_record WHERE message = '" + newSmsMessage + "'";

            resultCount = stmt.executeUpdate(sql);

            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);

            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            ResultSet secondaryProcessingStatusResultSet = stmt.executeQuery(secondRecordSQL);

            secondaryProcessingStatusResultSet.next();

            String secondaryActualPrimaryStatus = secondaryProcessingStatusResultSet.getString("primary_processing_status");
            String secondaryExpectedPrimaryStatus = "LOADED_FOR_CREATION";

            Assert.assertEquals(secondaryExpectedPrimaryStatus, secondaryActualPrimaryStatus);

            String secondaryActualSecondaryStatus = secondaryProcessingStatusResultSet.getString("secondary_processing_status");
            String secondaryExpectedSecondaryStatus = "NEVER_SENT_FOR_SUBMISSION";

            Assert.assertEquals(secondaryExpectedSecondaryStatus, secondaryActualSecondaryStatus);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This test case tests two SMS having same message added in the queue directly by inserting into the database.
     * The database is refreshed by calling the refreshDatabase method at the start of the test.
     * A new sms group is created with both content match and type match as null.
     * If second message is submitted successfully the primary_processing status and the secondary_processing status should be
     * "STATUS_CHECK_COMPLETED" and "SUCCESSFUL_DELIVERY" respectively from record table for both SMS's.
     */
    @Test
    public void testNoCoolingPeriod() {
        try {
            refreshDatabase();
            smsMessage = "Test message for cooling period at" + date.toString();

            Statement stmt = con.createStatement();

            String insertNewGroupQuery = "INSERT INTO smsen_sms_group VALUES ('5',NULL ,NULL,\n" +
                    "'temporary group',TRUE ,'DummyGroup5','3',NULL ,NULL,'1')";

            String insertMappings = "INSERT INTO smsen_sms_group_gateway_mapping VALUES (null,2,4,5)";

            stmt.executeUpdate(insertNewGroupQuery);

            stmt.executeUpdate(insertMappings);


            String sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + smsMessage + " '," +
                    "                   '9199483250786','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    2,5,1,1\n" +
                    "                    )";


            int resultCount = stmt.executeUpdate(sql);

            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);

            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            String recordSQL = "SELECT primary_processing_status, secondary_processing_status\n" +
                    "FROM smsen_sms_record WHERE message = '" + smsMessage + "'";


            ResultSet processingStatusResultSet = stmt.executeQuery(recordSQL);
            processingStatusResultSet.next();

            String actualPrimaryStatus = processingStatusResultSet.getString("primary_processing_status");
            String expectedPrimaryStatus = "STATUS_CHECK_COMPLETED";

            Assert.assertEquals(expectedPrimaryStatus, actualPrimaryStatus);

            String actualSecondaryStatus = processingStatusResultSet.getString("secondary_processing_status");
            String expectedSecondaryStatus = "SUCCESSFUL_DELIVERY";

            Assert.assertEquals(expectedSecondaryStatus, actualSecondaryStatus);


            String newSmsMessage = "Test message for cooling period after first SMS at" + date.toString();


            sql = "INSERT into smsen_sms_queue values(\n" +
                    "                    null,'IN',NOW(),'" + newSmsMessage + " '," +
                    "                   '9199483250786','SampleReceiverType','SampleScheduleId',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 16 SECOND),\n" +
                    "                    '+5.5','SampleScheduleType',\n" +
                    "                    DATE_ADD(NOW(),INTERVAL 20 SECOND),\n" +
                    "                    2,5,1,1\n" +
                    "                    )";


            String secondRecordSQL = "SELECT primary_processing_status, secondary_processing_status\n" +
                    "FROM smsen_sms_record WHERE message = '" + newSmsMessage + "'";

            resultCount = stmt.executeUpdate(sql);

            Assert.assertEquals("Returned " + resultCount + " rows instead of 1", 1, resultCount);

            Thread.sleep(WAIT_TIME_FOR_SMS_CREATION);

            ResultSet secondaryProcessingStatusResultSet = stmt.executeQuery(secondRecordSQL);

            secondaryProcessingStatusResultSet.next();

            String secondaryActualPrimaryStatus = secondaryProcessingStatusResultSet.getString("primary_processing_status");
            String secondaryExpectedPrimaryStatus = "STATUS_CHECK_COMPLETED";

            Assert.assertEquals(secondaryExpectedPrimaryStatus, secondaryActualPrimaryStatus);

            String secondaryActualSecondaryStatus = secondaryProcessingStatusResultSet.getString("secondary_processing_status");
            String secondaryExpectedSecondaryStatus = "SUCCESSFUL_DELIVERY";

            Assert.assertEquals(secondaryExpectedSecondaryStatus, secondaryActualSecondaryStatus);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @After
    public void close() {
        try {
            if (client != null) {
                client.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
