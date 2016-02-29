package com.portea.cpnen.web.service;

import java.sql.*;

/**
 * This class contains  test Data that is used by JUNIT testing for test coupon creation and validation in Portea Coupon Management System
 * through its REST API interface.  Please note that the virtual host coupons.localhost must be
 * configured appropriately in the target WildFly server and the username and password for the database must be changed accordingly.
 */

public class CpnenTestDataDb {

    private final static String CON_DRIVER = "com.mysql.jdbc.Driver";
    private final static String CON_DATABASE = "jdbc:mysql://localhost:3306/coupon_management";
    private final static String CON_USERNAME = "root";
    private final static String CON_PASSWORD = "root";
    private static Connection con = null;
    private int testCpnIdCounter = -1;

    public void populateTestData() {
        Statement stmt = null;
        try {
            Class.forName(CON_DRIVER);
            con = DriverManager.getConnection(CON_DATABASE, CON_USERNAME, CON_PASSWORD);
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            con.setAutoCommit(false);

            testCpnCrDataTableTruncationBatch(stmt);
            testCpnCrDataAddUserPopulateBatch(stmt);
            testCpnCrDataAddRolesPopulationBatch(stmt);
            testCpnCrDataAddBrandsPopulationBatch(stmt);
            testCpnCrBatchAddAreasPopulationBatch(stmt);
            testCpnCrBatchAddReferrersPopulationBatch(stmt);
            testCpnCrDataAddServicesPopulationBatch(stmt);
            testCpnCrDataAddPackagesPopulationBatch(stmt);
            testCpnCrDataAddGeneralCpnPopulationBatch(stmt);

            stmt.executeBatch();
            con.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void testCpnCrDataTableTruncationBatch(Statement statement) throws SQLException {
        statement.addBatch("SET foreign_key_checks = 0");
        statement.addBatch("TRUNCATE TABLE coupon_code_reservation");
        statement.addBatch("TRUNCATE TABLE coupon_engine_config_audit");
        statement.addBatch("TRUNCATE TABLE coupon_engine_config");
        statement.addBatch("TRUNCATE TABLE coupon_disc_req_prod_audit");
        statement.addBatch("TRUNCATE TABLE coupon_disc_req_code_audit");
        statement.addBatch("TRUNCATE TABLE coupon_discount_summary");
        statement.addBatch("TRUNCATE TABLE coupon_discount_req_prod");
        statement.addBatch("TRUNCATE TABLE coupon_discount_req_code");
        statement.addBatch("TRUNCATE TABLE coupon_discount_product");
        statement.addBatch("TRUNCATE TABLE coupon_discount_code");
        statement.addBatch("TRUNCATE TABLE coupon_discount");
        statement.addBatch("TRUNCATE TABLE coupon_discounting_rule");
        statement.addBatch("TRUNCATE TABLE coupon_discount_req");
        statement.addBatch("TRUNCATE TABLE coupon_disc_req_audit");
        statement.addBatch("TRUNCATE TABLE auth_users");
        statement.addBatch("TRUNCATE TABLE auth_roles;");
        statement.addBatch("TRUNCATE TABLE auth_user_role_mapping;");
        statement.addBatch("TRUNCATE TABLE coupon_code");
        statement.addBatch("TRUNCATE TABLE coupon");
        statement.addBatch("TRUNCATE TABLE coupon_product_adapter");
        statement.addBatch("TRUNCATE TABLE packages");
        statement.addBatch("TRUNCATE TABLE services");
        statement.addBatch("TRUNCATE TABLE coupon_usage_summary");
        statement.addBatch("TRUNCATE TABLE coupon_product_adapter_mapping");
        statement.addBatch("TRUNCATE TABLE brands");
        statement.addBatch("TRUNCATE TABLE coupon_brand_mapping");
        statement.addBatch("TRUNCATE TABLE areas");
        statement.addBatch("TRUNCATE TABLE coupon_area_mapping");
        statement.addBatch("TRUNCATE TABLE referrers");
        statement.addBatch("TRUNCATE TABLE coupon_referrer_mapping");
        statement.addBatch("TRUNCATE TABLE coupon_audit");
        statement.addBatch("TRUNCATE TABLE coupon_core_audit");
        statement.addBatch("TRUNCATE TABLE coupon_brand_mapping_audit");
        statement.addBatch("TRUNCATE TABLE coupon_area_mapping_audit");
        statement.addBatch("TRUNCATE TABLE coupon_referrer_mapping_audit");
        statement.addBatch("TRUNCATE TABLE coupon_product_adapter_mapping_audit");
        statement.addBatch("TRUNCATE TABLE coupon_discounting_rule_audit");
        statement.addBatch("TRUNCATE TABLE coupon_engine_comm_audit");
        statement.addBatch("SET foreign_key_checks = 1");
    }

    private void testCpnCrDataAddUserPopulateBatch(Statement statement) throws SQLException {
        statement.addBatch("INSERT INTO auth_users" +
                "( id, login, name, password, firstName," +
                " middleName, lastName, phoneNumber, mobileNumber, date_of_birth, type)" +
                "VALUES" +
                "(1, 'tester ','anshuman','$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq'," +
                "'anshuman','','garg','7411238888','080384848834','2015-11-02','staff')");

        statement.addBatch("INSERT INTO auth_users" +
                "( id, login, name, password, firstName," +
                " middleName, lastName, phoneNumber, mobileNumber, date_of_birth, type)" +
                "VALUES" +
                "(2, 'tester1','naveen','$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq'," +
                "'naveen','','khushalappa','7411238888','080384848834','2015-11-02','staff')");

        statement.addBatch("INSERT INTO auth_users" +
                "( id, login, name, password, firstName," +
                " middleName, lastName, phoneNumber, mobileNumber, date_of_birth, type)" +
                "VALUES" +
                "(3, 'tester2','ravi','$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq'," +
                "'ravi','','sinha','7411238888','080384848834','2015-11-02','staff')");

        statement.addBatch("INSERT INTO auth_users" +
                "( id, login, name, password, firstName," +
                " middleName, lastName, phoneNumber, mobileNumber, date_of_birth, type)" +
                "VALUES" +
                "(4, 'tester3','kartik','$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq'," +
                "'kartik','','khushalappa','7411238888','080384848834','2015-11-02','staff')");

        statement.addBatch("INSERT INTO auth_users" +
                "( id, login, name, password, firstName," +
                " middleName, lastName, phoneNumber, mobileNumber, date_of_birth, type)" +
                "VALUES" +
                "(5, 'tester4','vinamra','$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq'," +
                "'vinamra','','Mattoo','7411238888','080384848834','2015-11-02','staff')");

        statement.addBatch("INSERT INTO auth_users" +
                "( id, login, name, password, firstName," +
                " middleName, lastName, phoneNumber, mobileNumber, date_of_birth, type)" +
                "VALUES" +
                "(6, 'tester5','bharath','$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq'," +
                "'bharath','','bharath','7411238888','080384848834','2015-11-02','staff')");
    }

    private void testCpnCrDataAddRolesPopulationBatch(Statement statement) throws SQLException {
        statement.addBatch("INSERT INTO auth_roles " +
                "( id, parent_id, name, description) " +
                "VALUES ('1','5', 'Coupon Admin', 'Login with Username tester and password pass')");
        statement.addBatch("INSERT INTO auth_roles " +
                "( id, parent_id, name, description) " +
                "VALUES ('2','-1', 'Coupon Manager Sales', ' ')");
        statement.addBatch("INSERT INTO auth_roles " +
                "( id, parent_id, name, description) " +
                "VALUES ('3','-1', 'Coupon Manager Marketing', ' ')");
        statement.addBatch("INSERT INTO auth_roles " +
                "( id, parent_id, name, description) " +
                "VALUES ('4','-1', 'Coupon Manager Ops', ' ')");
        statement.addBatch("INSERT INTO auth_roles " +
                "( id, parent_id, name, description) " +
                "VALUES ('5','-1', 'Coupon Manager Engagement', ' ')");

        statement.addBatch("INSERT INTO auth_user_role_mapping" +
                " ( id, user_id, role_id)" +
                " VALUES ('1', '1', '1');");
        statement.addBatch("INSERT INTO auth_user_role_mapping" +
                " ( id, user_id, role_id)" +
                " VALUES ('2', '2', '2');");

        statement.addBatch("INSERT INTO auth_user_role_mapping" +
                " ( id, user_id, role_id)" +
                " VALUES ('3', '3', '3');");
    }

    private void testCpnCrDataAddBrandsPopulationBatch(Statement statement) throws SQLException {

        statement.addBatch("INSERT INTO brands ( id, name) VALUES" +
                "(1 , 'Cloudnine')");
        statement.addBatch("INSERT INTO brands ( id, name) VALUES" +
                "(2 , 'Fortis Malar')");
        statement.addBatch("INSERT INTO brands ( id, name) VALUES" +
                "(3 , 'ICICI - Labs')");
        statement.addBatch("INSERT INTO brands ( id, name) VALUES" +
                "(4 , 'Manipal')");
        statement.addBatch("INSERT INTO brands ( id, name) VALUES" +
                "(5 , 'Max Homecar')");
        statement.addBatch("INSERT INTO brands ( id, name) VALUES" +
                "(6 , 'Medanta')");
        statement.addBatch("INSERT INTO brands ( id, name) VALUES" +
                "(7 , 'Medi Assist')");
        statement.addBatch("INSERT INTO brands ( id, name) VALUES" +
                "(8 , 'MS Ramaiah')");
        statement.addBatch("INSERT INTO brands ( id, name) VALUES" +
                "(9 , 'Narayana Hrudhyalaya')");
    }

    private void testCpnCrBatchAddAreasPopulationBatch(Statement statement) throws SQLException {
        statement.addBatch("INSERT INTO areas (id, name)" +
                " VALUES (1, 'Bangalore')");
        statement.addBatch("INSERT INTO areas (id, name) VALUES" +
                " (2, 'Mumbai')");
        statement.addBatch("INSERT INTO areas (id, name) VALUES" +
                " (3, 'NCR')");
        statement.addBatch("INSERT INTO areas (id, name) VALUES" +
                " (4, 'Kolkata')");
        statement.addBatch("INSERT INTO areas (id, name) VALUES" +
                " (5, 'Chennai')");
    }

    private void testCpnCrBatchAddReferrersPopulationBatch(Statement statement) throws SQLException {
        statement.addBatch("INSERT INTO referrers (id, name, brandId, referrerType) " +
                "VALUES (1, 'JustDial', 1, 'B2C')");
        statement.addBatch("INSERT INTO referrers (id, name, brandId, referrerType) " +
                "VALUES (2, 'ApnaCare', 1, 'B2B')");
        statement.addBatch("INSERT INTO referrers (id, name, brandId, referrerType) " +
                "VALUES (3, 'Max', 2, 'B2B')");
        statement.addBatch("INSERT INTO referrers (id, name, brandId, referrerType) " +
                "VALUES (4, 'Practo', 1, 'B2C')");
        statement.addBatch("INSERT INTO referrers (id, name, brandId, referrerType) " +
                "VALUES (5, 'Manipal', 2, 'B2B')");
        statement.addBatch("INSERT INTO referrers (id, name, brandId, referrerType) " +
                "VALUES (6, 'Medanta', 1, 'B2B')");
    }

    private void testCpnCrDataAddServicesPopulationBatch(Statement statement) throws SQLException {
        statement.addBatch("INSERT INTO services( id, sub_service, name, display_name, parentId, deleted)" +
                " VALUES" +
                "(1, 0, '12 Hr Nursing', 'Service1', 0, 0)");

        statement.addBatch("INSERT INTO services( id, sub_service, name, display_name, parentId, deleted)" +
                " VALUES" +
                "(2, 0, '12 hr nursing care', 'Service2', 0, 0)");

        statement.addBatch("INSERT INTO services( id, sub_service, name, display_name, parentId, deleted)" +
                " VALUES" +
                "(3, 0, '12Hr Nursing Attendant', 'Service3', 0, 0)");

        statement.addBatch("INSERT INTO services( id, sub_service, name, display_name, parentId, deleted)" +
                " VALUES" +
                "(4, 0, '24 Hr Nursing', 'Service4', 0, 0)");
    }

    private void testCpnCrDataAddPackagesPopulationBatch(Statement statement) throws SQLException {
        statement.addBatch("INSERT INTO packages( id, name, description, deleted) VALUES" +
                "(1, 'Elder care plan (1 Year)', 'Package1', 0)");

        statement.addBatch("INSERT INTO packages( id, name, description, deleted) VALUES" +
                "(2, '10 Physio visits', 'Package2', 0)");

        statement.addBatch("INSERT INTO packages( id, name, description, deleted) VALUES" +
                "(3, '10 Visits - Mobile', 'Package3', 0)");

        statement.addBatch("INSERT INTO packages( id, name, description, deleted) VALUES" +
                "(4, '10-Days NA Package', 'Package4', 0)");
    }

    private void testCpnCrDataAddGeneralCpnPopulationBatch(Statement statement) throws SQLException {

        int testCouponId = getNextTestCouponId();

        //adding coupon data into the database Many Times draft Coupon

        statement.addBatch("INSERT INTO coupon" +
                " (id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time,nth_time_recurring, published_by," +
                "published_on, track_use_across_codes)" +
                " VALUES" +
                "(" + testCouponId + ", NOW(), DATE_ADD(NOW(),INTERVAL 1 DAY), NULL, 'SALES'," +
                " 'MANY_TIMES', 'CUSTOMER', NOW(), NULL, 'A Many Times Draft Coupon', " +
                " b'0', 'health coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT', " +
                " NOW(), 1, NULL, b'0', NULL," +
                " NULL, b'0')");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description,disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(1, 'PERCENTAGE', NOW(), NULL, 'discount!', NULL," +
                " 10, " + testCouponId + ", 1)");

        //adding coupon data into the database Many times published coupon.

        testCouponId = getNextTestCouponId();
        statement.addBatch("INSERT INTO coupon" +
                " (id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time,nth_time_recurring, published_by," +
                "published_on, track_use_across_codes)" +
                " VALUES" +
                "(" + testCouponId + ", NOW(), DATE_ADD(NOW(),INTERVAL 1 DAY), NULL, 'SALES'," +
                " 'MANY_TIMES', 'CUSTOMER', NOW(), NULL, 'A Many Times Published Coupon', " +
                " b'0', 'more coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT', " +
                " NOW(), 1, NULL, b'0', 1," +
                " NOW(), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(1, 'PORTEA-01', NOW(), NULL, " + testCouponId + "," +
                " 1, NULL, 'facebook')");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description,disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(2, 'PERCENTAGE', NOW(), NULL, 'discount!', NULL," +
                " 10, " + testCouponId + ", 1)");

        //adding coupon data into database many times deactivated coupon.

        testCouponId = getNextTestCouponId();
        statement.addBatch("INSERT INTO coupon" +
                " (id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time,nth_time_recurring, published_by," +
                "published_on, track_use_across_codes)" +
                " VALUES" +
                "(" + testCouponId + ", NOW(), DATE_ADD(NOW(),INTERVAL 1 DAY), NULL, 'MARKETING'," +
                " 'MANY_TIMES', 'CUSTOMER', NOW(), DATE_ADD(NOW(),INTERVAL 1 DAY), 'A deactivated Many Times Draft Coupon', " +
                " b'0', 'long coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, 1, 'APPOINTMENT', " +
                " NOW(), 1, NULL, b'0', 1," +
                " NOW(), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(9, 'PORTEA-09', NOW(), DATE_ADD(NOW(),INTERVAL 1 DAY), " + testCouponId + "," +
                " 1, 1, 'facebook')");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description,disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(3, 'PERCENTAGE', NOW(), NULL, 'discount!', NULL," +
                " 10, " + testCouponId + ", 1)");

        //adding coupon data into database One Time published coupon.

        testCouponId = getNextTestCouponId();
        statement.addBatch("INSERT INTO coupon" +
                " (id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time,nth_time_recurring, published_by," +
                "published_on, track_use_across_codes)" +
                " VALUES" +
                "(" + testCouponId + ", NOW(), DATE_ADD(NOW(),INTERVAL 1 DAY), NULL, 'MARKETING'," +
                " 'ONE_TIME', 'CUSTOMER', NOW(), NULL, 'A One Time Published Coupon', " +
                " b'1', 'masti coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT', " +
                " NOW(), 1, NULL, b'0', 1," +
                " NOW(), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(2, 'PORTEA-02', NOW(), NULL, " + testCouponId + "," +
                " 1, NULL, 'facebook')");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description,disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(4, 'PERCENTAGE', NOW(), NULL, 'discount!', NULL," +
                " 10, " + testCouponId + ", 1)");

        //adding coupon data into database One Time draft coupon.

        testCouponId = getNextTestCouponId();
        statement.addBatch("INSERT INTO coupon" +
                " (id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time,nth_time_recurring, published_by," +
                "published_on, track_use_across_codes)" +
                " VALUES" +
                "(" + testCouponId + ", NOW(), DATE_ADD(NOW(),INTERVAL 2 DAY), NULL, 'MARKETING'," +
                " 'ONE_TIME', 'CUSTOMER', NOW(), NULL, 'A One Time Draft Coupon', " +
                " b'1', 'tango coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT', " +
                " NOW(), 1, NULL, b'0', NULL," +
                " NULL, b'0')");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description,disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(5, 'PERCENTAGE', NOW(), NULL, 'discount!', NULL," +
                " 10, " + testCouponId + ", 1)");

        //adding coupon data into database One Time per user published coupon.

        testCouponId = getNextTestCouponId();
        statement.addBatch("INSERT INTO coupon" +
                " (id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time,nth_time_recurring, published_by," +
                "published_on, track_use_across_codes)" +
                " VALUES" +
                "(" + testCouponId + ", NOW(), DATE_ADD(NOW(),INTERVAL 2 DAY), NULL, 'SALES'," +
                " 'ONE_TIME_PER_USER', 'STAFF', NOW(), NULL, 'A One Time Per User Published Coupon', " +
                " b'1', 'offer coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 1000, 100," +
                " 5000, 500, 1, NULL, 'SUBSCRIPTION', " +
                " NOW(), 1, NULL, b'0', 1," +
                " NOW(), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(3, 'PORTEA-03', NOW(), NULL, " + testCouponId + "," +
                " 1, NULL, 'facebook')");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description,disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(6, 'PERCENTAGE', NOW(), NULL, 'discount!', NULL," +
                " 50, " + testCouponId + ", 1)");

        //adding coupon data into database One Time per user fifo published coupon.

        testCouponId = getNextTestCouponId();
        statement.addBatch("INSERT INTO coupon" +
                " (id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time,nth_time_recurring, published_by," +
                "published_on, track_use_across_codes)" +
                " VALUES" +
                "(" + testCouponId + ", NOW(), DATE_ADD(NOW(),INTERVAL 2 DAY), 2, 'SALES'," +
                " 'ONE_TIME_PER_USER_FIFO', 'STAFF', NOW(), NULL, 'A One Time Per User Fifo Published Coupon', " +
                " b'1', 'offer1 coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 1000, 100," +
                " 5000, 500, 1, NULL, 'SUBSCRIPTION', " +
                " NOW(), 1, NULL, b'0', 1," +
                " NOW(), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(4, 'PORTEA-04', NOW(), NULL, " + testCouponId + "," +
                " 1, NULL, 'facebook')");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description,disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(7, 'PERCENTAGE', NOW(), NULL, 'discount!', NULL," +
                " 50, " + testCouponId + ", 1)");

        //adding coupon data into database Nth Time published coupon.

        testCouponId = getNextTestCouponId();
        statement.addBatch("INSERT INTO coupon" +
                " (id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time,nth_time_recurring, published_by," +
                "published_on, track_use_across_codes)" +
                " VALUES" +
                "(" + testCouponId + ", NOW(), DATE_ADD(NOW(),INTERVAL 2 DAY), NULL, 'SALES'," +
                " 'NTH_TIME', 'CUSTOMER', NOW(), NULL, 'A Nth Time Published Coupon', " +
                " b'1', 'N coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 1000, 100," +
                " 5000, 500, 1, NULL, 'APPOINTMENT', " +
                " NOW(), 1, NULL, b'0', 1," +
                " NOW(), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(5, 'PORTEA-05', NOW(), NULL, " + testCouponId + "," +
                " 1, NULL, 'facebook')");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description,disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(8, 'PERCENTAGE', NOW(), NULL, 'discount!', NULL," +
                " 50, " + testCouponId + ", 1)");

        //adding coupon data into database Nth Time draft coupon.

        testCouponId = getNextTestCouponId();
        statement.addBatch("INSERT INTO coupon" +
                " (id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time,nth_time_recurring, published_by," +
                "published_on, track_use_across_codes)" +
                " VALUES" +
                "(" + testCouponId + ", NOW(), DATE_ADD(NOW(),INTERVAL 1 DAY), NULL, 'SALES'," +
                " 'NTH_TIME', 'CUSTOMER', NOW(), NULL, 'A Nth Time Draft Coupon', " +
                " b'1', 'ND coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 1000, 100," +
                " 5000, 500, 1, NULL, 'APPOINTMENT', " +
                " NOW(), 1, NULL, b'0', NULL," +
                " NULL, b'0')");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description,disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(9, 'PERCENTAGE', NOW(), NULL, 'discount!', NULL," +
                " 50, " + testCouponId + ", 1)");

        //adding coupon data into database Nth Time per subscription published coupon.

        testCouponId = getNextTestCouponId();
        statement.addBatch("INSERT INTO coupon" +
                " (id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time,nth_time_recurring, published_by," +
                "published_on, track_use_across_codes)" +
                " VALUES" +
                "(" + testCouponId + ", NOW(), DATE_ADD(NOW(),INTERVAL 1 DAY), NULL, 'SALES'," +
                " 'NTH_TIME_PER_SUBSCRIPTION', 'STAFF', NOW(), NULL, 'A Nth Time Per Subscription Published Coupon', " +
                " b'1', 'NS coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 1000, 100," +
                " 5000, 500, 1, NULL, 'SUBSCRIPTION', " +
                " NOW(), 1, NULL, b'0', 1," +
                " NOW(), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(6, 'PORTEA-06', NOW(), NULL, " + testCouponId + "," +
                " 1, NULL, 'web')");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description,disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(10, 'PERCENTAGE', NOW(), NULL, 'discount!', NULL," +
                " 50, " + testCouponId + ", 1)");

        //adding coupon data into database Nth Time AB per subscription published coupon.

        testCouponId = getNextTestCouponId();
        statement.addBatch("INSERT INTO coupon" +
                " (id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time,nth_time_recurring, published_by," +
                "published_on, track_use_across_codes)" +
                " VALUES" +
                "(" + testCouponId + ", NOW(), DATE_ADD(NOW(),INTERVAL 1 DAY), NULL, 'SALES'," +
                " 'NTH_TIME_AB_PER_SUBSCRIPTION', 'STAFF', NOW(), NULL, 'A Nth Time AB per Subscription Published Coupon', " +
                " b'1', 'NABS coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 1000, 100," +
                " 5000, 500, 1, NULL, 'SUBSCRIPTION', " +
                " NOW(), 1, NULL, b'0', 1," +
                " NOW(), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(7, 'PORTEA-07', NOW(), NULL, " + testCouponId + "," +
                " 1, NULL, 'web')");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description,disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(11, 'PERCENTAGE', NOW(), NULL, 'discount!', NULL," +
                " 50, " + testCouponId + ", 1)");

        //adding coupon data into database Many Times non global published coupon.

        testCouponId = getNextTestCouponId();
        statement.addBatch("INSERT INTO coupon" +
                " (id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time,nth_time_recurring, published_by," +
                "published_on, track_use_across_codes)" +
                " VALUES" +
                "(" + testCouponId + ", NOW(), DATE_ADD(NOW(),INTERVAL 5 DAY), NULL, 'SALES'," +
                " 'MANY_TIMES', 'STAFF', NOW(), NULL, 'A many times non global Published Coupon', " +
                " b'1', 'This coupon', b'0', b'1', b'1'," +
                " b'1', b'1', b'1', 1000, 100," +
                " 5000, 500, 1, NULL, 'SUBSCRIPTION', " +
                " NOW(), 1, NULL, b'0', 1," +
                " NOW(), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(8, 'PORTEA-08', NOW(), NULL, " + testCouponId + "," +
                " 1, NULL, 'web')");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description,disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(12, 'PERCENTAGE', NOW(), NULL, 'discount!', NULL," +
                " 50, " + testCouponId + ", 1)");

        statement.addBatch("INSERT INTO coupon_product_adapter" +
                "( id, name, product_id, product_type, created_on)" +
                "VALUES" +
                "(1, 'Elder care plan (1 Year)', '1', 'PACKAGE', NOW())");


        statement.addBatch("INSERT INTO coupon_product_adapter_mapping" +
                "( id, coupon_id, coupon_product_adapter_id, applicable)" +
                " VALUES (1," + testCouponId + ", 1, 1)");

        statement.addBatch("INSERT INTO coupon_brand_mapping( id, coupon_id, brand_id, applicable)" +
                " VALUES (1, " + testCouponId + ", '1', b'1');");
    }

    private int getNextTestCouponId() {
        if (testCpnIdCounter <= 0) {
            testCpnIdCounter = 1;
        } else {
            ++testCpnIdCounter;
        }
        return testCpnIdCounter;
    }

    public static void main(String[] args) {
        System.out.println("Running test-data creator");
        CpnenTestDataDb cpnenTestDataDb = new CpnenTestDataDb();
        cpnenTestDataDb.populateTestData();
        System.out.println("Test data creation completed");
    }
}

