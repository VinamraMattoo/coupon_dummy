package com.portea.cpnen.rapi.service;

import java.sql.*;

/**
 * This Class contains test Data that is used by JUNIT testing for functionality of Portea Coupon System
 * through its REST API interface
 */
public class TestDataManagerForCDR {

    private static Connection con = null;

    private final static String CON_DRIVER = "com.mysql.jdbc.Driver";
    private final static String CON_DATABASE = "jdbc:mysql://localhost:3306/coupon_management";
    private final static String CON_USERNAME = "root";
    private final static String CON_PASSWORD = "root";
    private int testCpnIdCounter = -1;

    public void populateDevTestData() {
        Statement stmt = null;
        try {
            Class.forName(CON_DRIVER);
            con = DriverManager.getConnection(CON_DATABASE, CON_USERNAME, CON_PASSWORD);
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            con.setAutoCommit(false);

            addTableTruncationBatch(stmt);
            addUserPopulationBatch(stmt);
            addRolesPopulationBatch(stmt);
            addBrandsPopulationBatch(stmt);
            addAreasPopulationBatch(stmt);
            addReferrersPopulationBatch(stmt);
            addServicesPopulationBatch(stmt);
            addPackagesPopulationBatch(stmt);
            addGeneralCpnPopulationBatch(stmt);
            addOneTimeAppTypeCpnPopulationBatch(stmt);
            addOtpuApplicationTypePopulationBatch(stmt);
            addOtpuFifoApplicationTypePopulationBatch(stmt);
            addNthTimeAppTypeCpnPopulationBatch(stmt);
            addNpsAppTypeCpnPopulationBatch(stmt);
            addNabpsAppTypeCpnPopulationBatch(stmt);

            stmt.executeBatch();
            con.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void addUserPopulationBatch(Statement statement) throws SQLException {
        statement.addBatch("INSERT INTO auth_users" +
                "( id, login, name, password, firstName," +
                " middleName, lastName, phoneNumber, mobileNumber, date_of_birth, type)" +
                "VALUES" +
                "(1, 'tester','tester','$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq'," +
                "'firstName','middleName','lastName','phoneNumber','mobileNumber','2015-11-02','patient')");

        statement.addBatch("INSERT INTO auth_users" +
                "(id, login, name, password, firstName, middleName," +
                " lastName, phoneNumber, mobileNumber, date_of_birth, type)" +
                "VALUES" +
                "(2, 'DummyLogin2','tester1','$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq'," +
                "'firstName','middleName','lastName','phoneNumber','mobileNumber','2015-12-16','patient')");

        statement.addBatch("INSERT INTO auth_users" +
                "( id, login, name, password, firstName," +
                " middleName, lastName, phoneNumber, mobileNumber, date_of_birth, type)" +
                "VALUES" +
                "(3, 'DummyLogin3','tester','$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq'," +
                "'firstName','middleName','lastName','phoneNumber','mobileNumber','2015-11-02','staff')");

        statement.addBatch("INSERT INTO auth_users" +
                "( id, login, name, password, firstName," +
                " middleName, lastName, phoneNumber, mobileNumber, date_of_birth, type)" +
                "VALUES" +
                "(4, 'DummyLogin4','tester','$2a$11$WsLBOdqI1v//VOyWo/vGmuDpKnn2diUHWQaOz.8/R8HrGafix2anq'," +
                "'firstName','middleName','lastName','phoneNumber','mobileNumber','2015-11-02','patient')");
    }

    private void addPackagesPopulationBatch(Statement statement) throws SQLException {
        statement.addBatch("INSERT INTO packages( id, name, description, deleted) VALUES" +
                "(1, 'DummyPackage1', 'Package1', 0)");

        statement.addBatch("INSERT INTO packages( id, name, description, deleted) VALUES" +
                "(2, 'DummyPackage2', 'Package2', 0)");

        statement.addBatch("INSERT INTO packages( id, name, description, deleted) VALUES" +
                "(3, 'DummyPackage3', 'Package3', 0)");

        statement.addBatch("INSERT INTO packages( id, name, description, deleted) VALUES" +
                "(4, 'DummyPackage4', 'Package4', 0)");
    }

    private void addServicesPopulationBatch(Statement statement) throws SQLException {
        statement.addBatch("INSERT INTO services( id, sub_service, name, display_name, parentId, deleted)" +
                " VALUES" +
                "(1, 0, 'DummyService1', 'Service1', 0, 0)");

        statement.addBatch("INSERT INTO services( id, sub_service, name, display_name, parentId, deleted)" +
                " VALUES" +
                "(2, 0, 'DummyService2', 'Service2', 0, 0)");

        statement.addBatch("INSERT INTO services( id, sub_service, name, display_name, parentId, deleted)" +
                " VALUES" +
                "(3, 0, 'DummyService3', 'Service3', 0, 0)");

        statement.addBatch("INSERT INTO services( id, sub_service, name, display_name, parentId, deleted)" +
                " VALUES" +
                "(4, 0, 'DummyService4', 'Service4', 0, 0)");
    }

    private void addBrandsPopulationBatch(Statement statement) throws SQLException {
        statement.addBatch("INSERT INTO brands ( id, name) VALUES" +
                "(1 , 'DummyBrand1')");

        statement.addBatch("INSERT INTO brands ( id, name) VALUES" +
                "(2 , 'DummyBrand2')");
    }

    private void addAreasPopulationBatch(Statement statement) throws SQLException{
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

    private void addReferrersPopulationBatch(Statement statement) throws SQLException{
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

    private void addTableTruncationBatch(Statement statement) throws SQLException {

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

    private void addRolesPopulationBatch(Statement statement) throws SQLException {
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

    /**
     * Creates various test coupons for testing invocation of business rules during coupon discount request creation.
     */
    private void addGeneralCpnPopulationBatch(Statement statement) throws  SQLException{

        int testCouponId = getNextTestCouponId();

        // A test Non Global Many Times Coupon.
        statement.addBatch("INSERT INTO coupon" +
                " (id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time,nth_time_recurring, published_by," +
                "published_on, track_use_across_codes)" +
                " VALUES" +
                "(" + testCouponId + ", DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 180 MINUTE), 2, 'SALES'," +
                " 'MANY_TIMES', 'CUSTOMER', DATE_ADD(NOW(),INTERVAL -4 DAY), NULL, 'A test Non Global Many Times Coupon', " +
                " b'0', 'health coupon', b'0', b'0', b'0'," +
                " b'0', b'0', b'0', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT', " +
                " NULL, NULL, 3, b'0',1," +
                " DATE_ADD(NOW(),INTERVAL -3 DAY),b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(1, 'C1-D1', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, " + testCouponId + "," +
                " 1, NULL, NULL)");

        // A test Coupon Code that is also linked with this coupon only.
        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(16, 'C4-D4', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, " + testCouponId + "," +
                " 1, NULL, NULL)");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description,disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(1, 'PERCENTAGE', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, 'discount!', NULL," +
                " 10, " + testCouponId + ", 1)");

        statement.addBatch("INSERT INTO coupon_product_adapter" +
                "( id, name, product_id, product_type, created_on)" +
                "VALUES" +
                "(1, 'DummyProduct1', '1', 'SERVICE', DATE_ADD(NOW(),INTERVAL -4 DAY))");

        statement.addBatch("INSERT INTO coupon_product_adapter" +
                "( id, name, product_id, product_type, created_on)" +
                "VALUES" +
                "(3, 'DummyProduct3', '2', 'SERVICE', DATE_ADD(NOW(),INTERVAL -4 DAY))");

        statement.addBatch("INSERT INTO coupon_product_adapter_mapping" +
                "( id, coupon_id, coupon_product_adapter_id, applicable)" +
                " VALUES (1," + testCouponId + ",1,1)");

        statement.addBatch("INSERT INTO coupon_product_adapter_mapping" +
                "( id, coupon_id, coupon_product_adapter_id, applicable)" +
                " VALUES (2, " + testCouponId + ",3,1)");

        statement.addBatch("INSERT INTO coupon_brand_mapping( id, coupon_id, brand_id, applicable)" +
                " VALUES (1, " + testCouponId + ", '1', b'1');");

        statement.addBatch("INSERT INTO coupon_area_mapping ( id, coupon_id, area_id, applicable)" +
                " VALUES (1, " + testCouponId + ", '1', b'1')" );

        statement.addBatch("INSERT INTO coupon_referrer_mapping( id, coupon_id, referrer_id, applicable)" +
                " VALUES (1, " + testCouponId + ", '1', b'1')");

        // A test Deactivated Coupon Code.
        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(2, 'MMMM', DATE_ADD(NOW(),INTERVAL -3 DAY), DATE_ADD(NOW(),INTERVAL -1 DAY), " + testCouponId + "," +
                " 1, 1, NULL)");

        testCouponId = getNextTestCouponId();
        // A test Deactivated Coupon.
        statement.addBatch("INSERT INTO coupon" +
                " ( id, applicable_from, applicable_till, applicable_use_count,category, " +
                "application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time, nth_time_recurring, published_by," +
                " published_on, track_use_across_codes)" +
                " VALUES" +
                "(" + testCouponId + ", DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 180 MINUTE), 2, 'SALES'," +
                " 'MANY_TIMES', 'CUSTOMER', DATE_ADD(NOW(),INTERVAL -4 DAY), DATE_ADD(NOW(),INTERVAL -3 DAY), 'A test Deactivated Coupon'," +
                " b'0', 'health coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, 1, 'APPOINTMENT'," +
                " NULL,NULL, 3, b'0',1," +
                " DATE_ADD(NOW(),INTERVAL -3 DAY), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(3, 'NNNN', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, " + testCouponId + "," +
                " 1, NULL, NULL)");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description,disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(3, 'PERCENTAGE', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, 'discount!', NULL," +
                " 10, " + testCouponId + ", 1)");

        testCouponId = getNextTestCouponId();
        // A test Coupon to test Actor Type of coupon.
        statement.addBatch("INSERT INTO coupon" +
                " (id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time, nth_time_recurring, published_by," +
                " published_on, track_use_across_codes)" +
                " VALUES" +
                "(" + testCouponId + ", DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 180 MINUTE), 5, 'SALES'," +
                " 'ONE_TIME', 'STAFF', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, 'A test Coupon to test Actor Type of Coupon'," +
                " b'1', 'more coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'SUBSCRIPTION'," +
                " NULL, NULL, 3, b'0', 1," +
                " DATE_ADD(NOW(),INTERVAL -3 DAY), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(4, 'C1-D2', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, " + testCouponId + "," +
                " 1, NULL, NULL)");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description,disc_flat_amt," +
                " disc_percentage, coupon_id, created_by) " +
                "VALUES" +
                "(2, 'FLAT', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, 'flat rule', 50," +
                " NULL, " + testCouponId + ", 1)");

        testCouponId = getNextTestCouponId();
        // A test Non Global Coupon without Coupon_Brand mapping.
        statement.addBatch("INSERT INTO coupon" +
                " ( id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time, nth_time_recurring, published_by," +
                " published_on,track_use_across_codes)" +
                " VALUES" +
                "( " + testCouponId + ", DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 180 MINUTE), 2, 'SALES'," +
                " 'MANY_TIMES', 'CUSTOMER', DATE_ADD(NOW(),INTERVAL -4 DAY), NULL, 'A test Non Global Coupon without Coupon_Brand mapping'," +
                " b'0', 'health coupon', b'0', b'0', b'0'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT'," +
                " NULL, NULL, 3, b'0', 1," +
                " DATE_ADD(NOW(),INTERVAL -3 DAY), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(5, 'C1-D3', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, " + testCouponId + "," +
                " 1, NULL, NULL)");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description,disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(4, 'PERCENTAGE', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, 'discount!', NULL," +
                " 10, " + testCouponId + ", 1)");

        statement.addBatch("INSERT INTO coupon_product_adapter_mapping" +
                "( id, coupon_id, coupon_product_adapter_id, applicable)" +
                " VALUES (3," + testCouponId + ", 1, 1)");

       testCouponId = getNextTestCouponId();
        // A test Coupon with expired validity.
        statement.addBatch("INSERT INTO coupon" +
                " ( id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time, nth_time_recurring, published_by," +
                " published_on,track_use_across_codes)" +
                " VALUES" +
                "( " + testCouponId + ", '2015-12-15 00:00:00', '2015-12-18 00:00:00', 2, 'SALES'," +
                "'MANY_TIMES', 'CUSTOMER', '2015-12-15 00:00:00', NULL, 'A test Coupon with expired validity'," +
                " b'0', 'more coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT'," +
                " NULL, NULL, NULL , b'0', 1," +
                " '2015-12-16 00:00:00', b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(15, 'C3-D3', '2015-12-16 00:00:00', NULL, " + testCouponId + "," +
                " 1, NULL, NULL)");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description, disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(14, 'PERCENTAGE', '2015-12-16 00:00:00', NULL, 'discount!', NULL," +
                " 10, " + testCouponId + ", 1)");

        testCouponId = getNextTestCouponId();
        // A test Non Global Coupon with invalid Coupon_Product Mapping.
        statement.addBatch("INSERT INTO coupon" +
                " ( id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time, nth_time_recurring, published_by," +
                " published_on,track_use_across_codes)" +
                " VALUES" +
                "( " + testCouponId + ", DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 180 MINUTE), 2, 'SALES'," +
                " 'MANY_TIMES', 'CUSTOMER', DATE_ADD(NOW(),INTERVAL -4 DAY), NULL, 'A test Non Global Coupon with invalid Coupon_Product Mapping'," +
                " b'0', 'more coupon', b'0', b'0', b'0'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT'," +
                " NULL, NULL, NULL , b'0', 1," +
                " DATE_ADD(NOW(),INTERVAL -3 DAY), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(17, 'C5-D5', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, " + testCouponId + "," +
                " 1, NULL, NULL)");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description, disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(15, 'PERCENTAGE', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, 'discount!', NULL," +
                " 10, " + testCouponId + ", 1)");

        statement.addBatch("INSERT INTO coupon_product_adapter" +
                "( id, name, product_id, product_type, created_on)" +
                "VALUES" +
                "(2, 'DummyProduct2', '1', 'PACKAGE', DATE_ADD(NOW(),INTERVAL -4 DAY))");


        statement.addBatch("INSERT INTO coupon_product_adapter_mapping" +
                "( id, coupon_id, coupon_product_adapter_id, applicable)" +
                " VALUES (4," + testCouponId + ",2,1)");

        statement.addBatch("INSERT INTO coupon_brand_mapping( id, coupon_id, brand_id, applicable)" +
                " VALUES (2, " + testCouponId + ", '1', b'1');");

        testCouponId = getNextTestCouponId();

        statement.addBatch("INSERT INTO coupon" +
                " ( id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time, nth_time_recurring, published_by," +
                " published_on,track_use_across_codes)" +
                " VALUES" +
                "( " + testCouponId + ", DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 180 MINUTE), 2, 'SALES'," +
                " 'MANY_TIMES', 'CUSTOMER', DATE_ADD(NOW(),INTERVAL -4 DAY), NULL, 'A test Coupon'," +
                " b'1', 'more coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT'," +
                " NULL, NULL, NULL , b'0', 1," +
                " DATE_ADD(NOW(),INTERVAL -3 DAY), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(21, 'E1-F1', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, " + testCouponId + "," +
                " 1, NULL, NULL)");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description, disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(19, 'PERCENTAGE', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, 'discount!', NULL," +
                " 10, " + testCouponId + ", 1)");

        statement.addBatch("INSERT INTO coupon_product_adapter" +
                "( id, name, product_id, product_type, created_on)" +
                "VALUES" +
                "(4, 'DummyProduct4', '3', 'PACKAGE', DATE_ADD(NOW(),INTERVAL -4 DAY))");

    }

    /**
     * Test data population to add Nth Time Per Subscription(NPS) Type Coupons.
     */
    private void addNpsAppTypeCpnPopulationBatch(Statement statement) throws  SQLException {

        int testCouponId = getNextTestCouponId();

        // A test NPS coupon to test scenario where request is outside subscription
        statement.addBatch("INSERT INTO coupon" +
                " ( id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time, nth_time_recurring, published_by," +
                " published_on, track_use_across_codes)" +
                " VALUES" +
                "( " + testCouponId + ", DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 180 MINUTE), 2, 'SALES'," +
                " 'NTH_TIME_PER_SUBSCRIPTION', 'CUSTOMER', DATE_ADD(NOW(),INTERVAL -4 DAY), NULL, 'A test Nth Time per subs coupon to test scenario where request is outside subscription'," +
                " b'0', 'more coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT'," +
                " NULL, NULL, NULL, b'0', 1," +
                " DATE_ADD(NOW(),INTERVAL -3 DAY), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(10, 'A1-B1', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, " + testCouponId + ", " +
                "1, NULL, NULL)");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description,disc_flat_amt," +
                " disc_percentage, coupon_id, created_by) VALUES" +
                "(9, 'PERCENTAGE', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, 'discount!', NULL, " +
                "10, " + testCouponId + ", 1)");

        // A test NPS coupons that is with Nth_Time_Recurring as 'false'.

        testCouponId = getNextTestCouponId();
        statement.addBatch("INSERT INTO coupon" +
                " ( id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time, nth_time_recurring, published_by," +
                " published_on,track_use_across_codes)" +
                " VALUES" +
                "( " + testCouponId + ", DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 180 MINUTE), 2, 'SALES'," +
                " 'NTH_TIME_PER_SUBSCRIPTION', 'CUSTOMER', DATE_ADD(NOW(),INTERVAL -4 DAY), NULL, 'A test Nth Time per subs coupons that is with Nth_Time_Recurring as false'," +
                " b'0', 'more coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT'," +
                " NULL, NULL, 3, b'0', 1," +
                " DATE_ADD(NOW(),INTERVAL -3 DAY), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(11, 'A2-B2', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, " + testCouponId + ", " +
                "1, NULL, NULL)");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description, disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(10, 'PERCENTAGE', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, 'discount!', NULL, " +
                "10, " + testCouponId + ", 1)");

        // A test NPS coupons that is with Nth_Time_Recurring as 'true'.
        testCouponId = getNextTestCouponId();
        statement.addBatch("INSERT INTO coupon" +
                " ( id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type,created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time, nth_time_recurring, published_by," +
                " published_on, track_use_across_codes)" +
                " VALUES" +
                "( " + testCouponId + ", DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 180 MINUTE), 2, 'SALES'," +
                "'NTH_TIME_PER_SUBSCRIPTION', 'CUSTOMER', DATE_ADD(NOW(),INTERVAL -4 DAY), NULL, 'A test Nth Time per subs coupons that is with Nth_Time_Recurring as true'," +
                " b'0', 'more coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT'," +
                " NULL,NULL, 3, b'1', 1," +
                " DATE_ADD(NOW(),INTERVAL -3 DAY), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id,code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(12, 'A3-B3', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, " + testCouponId + "," +
                " 1, NULL, NULL)");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description, disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(11, 'PERCENTAGE', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, 'discount!', NULL," +
                " 10, " + testCouponId + ", 1)");

    }

    /**
     * Test data population to add Nth Time AB Per Subscription(NABPS) Type Coupons.
     */
    private void addNabpsAppTypeCpnPopulationBatch(Statement statement) throws SQLException {

        int testCouponId = getNextTestCouponId();

        // A test NABPS with out of scope Nth Time.
        statement.addBatch("INSERT INTO coupon" +
                " ( id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time, nth_time_recurring, published_by," +
                " published_on, track_use_across_codes)" +
                " VALUES" +
                "( " + testCouponId + ", DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 180 MINUTE), 2, 'SALES'," +
                " 'NTH_TIME_AB_PER_SUBSCRIPTION', 'CUSTOMER', DATE_ADD(NOW(),INTERVAL -4 DAY), NULL, 'A test Nth Time AB per subs with out of scope Nth Time'," +
                " b'0', 'more coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT'," +
                " NULL, NULL, NULL, b'0', 1," +
                " DATE_ADD(NOW(),INTERVAL -3 DAY), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(13, 'A4-B4', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, " + testCouponId + "," +
                " 1, NULL, NULL)");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description, disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(12, 'PERCENTAGE', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, 'discount!', NULL, " +
                "10, " + testCouponId + ", 1)");

        // A test NABPS Coupon.
        testCouponId = getNextTestCouponId();
        statement.addBatch("INSERT INTO coupon" +
                " ( id, applicable_from, applicable_till, applicable_use_count,category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time, nth_time_recurring, published_by," +
                " published_on,track_use_across_codes)" +
                " VALUES" +
                "( " + testCouponId + ", DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 180 MINUTE), 2, 'SALES'," +
                " 'NTH_TIME_AB_PER_SUBSCRIPTION', 'CUSTOMER', DATE_ADD(NOW(),INTERVAL -4 DAY), NULL, 'A test Nth Time AB Per Subs Coupon'," +
                " b'0', 'more coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT'," +
                " NULL,NULL, 6, b'0', 1," +
                " DATE_ADD(NOW(),INTERVAL -3 DAY), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(14, 'A5-B5', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, " + testCouponId + ", " +
                "1, NULL, NULL)");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description, disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(13, 'PERCENTAGE', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, 'discount!', NULL, " +
                "10, " + testCouponId + ", 1)");
    }

    /**
     * Test data population to add Nth Time Per Subscription Type Coupons.
     */
    private void addNthTimeAppTypeCpnPopulationBatch(Statement statement) throws  SQLException {

        int testCouponId = getNextTestCouponId();
        // A test Nth Time Coupon that is out of scope Nth Time
        statement.addBatch("INSERT INTO coupon" +
                " ( id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time, nth_time_recurring, published_by," +
                " published_on, track_use_across_codes)" +
                " VALUES" +
                "( " + testCouponId + ", DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 180 MINUTE), 2, 'SALES'," +
                " 'NTH_TIME', 'CUSTOMER', DATE_ADD(NOW(),INTERVAL -4 DAY), NULL, 'A test Nth Time Coupon that is out of scope Nth Time'," +
                " b'0', 'more coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT'," +
                " NULL,NULL, NULL, b'0', 1," +
                " DATE_ADD(NOW(),INTERVAL -3 DAY), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(6, 'X1-Y1', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, "+ testCouponId + "," +
                " 1, NULL, NULL)");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description, disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(5, 'PERCENTAGE', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, 'discount!', NULL," +
                " 10, " + testCouponId + ", 1)");

        testCouponId = getNextTestCouponId();
        // A test Nth Time coupons that is with Nth_Time_Recurring as 'false'.
        statement.addBatch("INSERT INTO coupon" +
                " ( id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time, nth_time_recurring, published_by," +
                " published_on, track_use_across_codes)" +
                " VALUES" +
                "( " + testCouponId + ", DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 180 MINUTE), 2, 'SALES'," +
                " 'NTH_TIME', 'CUSTOMER', DATE_ADD(NOW(),INTERVAL -4 DAY), NULL, 'A test Nth Time coupons that is with Nth_Time_Recurring as false'," +
                " b'0', 'more coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT'," +
                " NULL, NULL, 3, b'0',1," +
                " DATE_ADD(NOW(),INTERVAL -3 DAY),b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(7, 'X1-Y2', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, " + testCouponId + "," +
                " 1, NULL, NULL)");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description,disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(6, 'PERCENTAGE', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, 'discount!', NULL," +
                " 10, " + testCouponId + ", 1)");

        testCouponId = getNextTestCouponId();
        // A test Nth Time coupons that is with Nth_Time_Recurring as 'true'.
        statement.addBatch("INSERT INTO coupon" +
                " ( id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time, nth_time_recurring, published_by," +
                " published_on, track_use_across_codes)" +
                " VALUES" +
                "( " + testCouponId + ", DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 180 MINUTE), 2, 'SALES'," +
                "'NTH_TIME', 'CUSTOMER', DATE_ADD(NOW(),INTERVAL -4 DAY), NULL, 'A test Nth Time coupons that is with Nth_Time_Recurring as true'," +
                " b'0', 'more coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT'," +
                " NULL,NULL, 3, b'1', 1," +
                " DATE_ADD(NOW(),INTERVAL -3 DAY), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(8, 'X1-Y3', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, " + testCouponId + "," +
                " 1, NULL, NULL)");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description,disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(7, 'PERCENTAGE', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, 'discount!', NULL, " +
                "10, " + testCouponId + ", 1)");
    }


    /**
     * Test data population to add One Time Coupon.
     */
    private void addOneTimeAppTypeCpnPopulationBatch(Statement statement) throws SQLException{

        int testCouponId = getNextTestCouponId();
        statement.addBatch("INSERT INTO coupon" +
                " ( id, applicable_from, applicable_till, applicable_use_count, category," +
                "application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time, nth_time_recurring, published_by," +
                " published_on, track_use_across_codes)" +
                " VALUES" +
                "( "+ testCouponId + ", DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 180 MINUTE), 2, 'SALES'," +
                " 'ONE_TIME', 'STAFF', DATE_ADD(NOW(),INTERVAL -4 DAY), NULL, 'A test One Time coupon'," +
                " b'0', 'more coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT'," +
                " NULL, NULL, NULL, b'0', 1," +
                " DATE_ADD(NOW(),INTERVAL -3 DAY), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(9, 'X2-Y1', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, "+ testCouponId + "," +
                " 1, NULL, NULL)");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description,disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(8, 'PERCENTAGE', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, 'discount!', NULL," +
                " 10, "+ testCouponId + ", 1)");

        testCouponId = getNextTestCouponId();

        statement.addBatch("INSERT INTO coupon" +
                " ( id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time, nth_time_recurring, published_by," +
                " published_on,track_use_across_codes)" +
                " VALUES" +
                "( " + testCouponId + ", DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 180 MINUTE), 2, 'SALES'," +
                "'ONE_TIME', 'CUSTOMER', DATE_ADD(NOW(),INTERVAL -4 DAY), NULL, 'A test One Time Used Coupon'," +
                " b'0', 'more coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT'," +
                " NULL, NULL, NULL , b'0', 1," +
                " DATE_ADD(NOW(),INTERVAL -3 DAY), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(18, 'A6-B6', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, " + testCouponId + "," +
                " 1, NULL, NULL)");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description, disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(16, 'PERCENTAGE', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, 'discount!', NULL," +
                " 10, " + testCouponId + ", 1)");
    }

    /**
     * Test data population to add One Time Per User (Otpu) Coupon.
     */
    private void addOtpuApplicationTypePopulationBatch(Statement statement) throws SQLException{

        int testCouponId = getNextTestCouponId();
        statement.addBatch("INSERT INTO coupon" +
                " ( id, applicable_from, applicable_till, applicable_use_count, category," +
                "application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time, nth_time_recurring, published_by," +
                " published_on, track_use_across_codes)" +
                " VALUES" +
                "( " + testCouponId + ", DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 180 MINUTE), 2, 'SALES'," +
                " 'ONE_TIME_PER_USER', 'CUSTOMER', DATE_ADD(NOW(),INTERVAL -4 DAY), NULL, 'A test One Time Per User Coupon'," +
                " b'0', 'more coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT'," +
                " NULL, NULL, NULL , b'0', 1," +
                " DATE_ADD(NOW(),INTERVAL -3 DAY), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(19, 'A7-B7', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, " + testCouponId + "," +
                " 1, NULL, NULL)");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description, disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(17, 'PERCENTAGE', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, 'discount!', NULL," +
                " 10, " + testCouponId + ", 1)");

    }

    /**
     * Test data population to add One Time Per User FIFO (Otpu) coupon.
     */
    private void addOtpuFifoApplicationTypePopulationBatch(Statement statement) throws SQLException{

        int testCouponId = getNextTestCouponId();
        statement.addBatch("INSERT INTO coupon" +
                " ( id, applicable_from, applicable_till, applicable_use_count, category," +
                " application_type, actor_type, created_on, deactivated_on, description," +
                " inclusive, name, global, is_for_all_brands, is_for_all_products," +
                " is_for_all_areas, is_for_all_b2b, is_for_all_b2c, discount_amt_max, discount_amt_min," +
                " trans_val_max, trans_val_min, created_by, deactivated_by, context_type," +
                " last_updated_on, last_updated_by, nth_time, nth_time_recurring, published_by," +
                " published_on, track_use_across_codes)" +
                " VALUES" +
                "( " + testCouponId + ", DATE_ADD(NOW(),INTERVAL -15 MINUTE), DATE_ADD(NOW(),INTERVAL 180 MINUTE), 2, 'SALES'," +
                " 'ONE_TIME_PER_USER_FIFO', 'CUSTOMER', DATE_ADD(NOW(),INTERVAL -4 DAY), NULL, 'A test One Time Per User Fifo Coupon'," +
                " b'0', 'more coupon', b'1', b'1', b'1'," +
                " b'1', b'1', b'1', 10, 1," +
                " 1000, 100, 1, NULL, 'APPOINTMENT'," +
                " NULL, NULL, NULL , b'0', 1," +
                " DATE_ADD(NOW(),INTERVAL -3 DAY), b'0')");

        statement.addBatch("INSERT INTO coupon_code( id, code, created_on, deactivated_on, coupon_id," +
                " created_by, deactivated_by, channel_name)" +
                "VALUES" +
                "(20, 'A8-B8', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, " + testCouponId + "," +
                " 1, NULL, NULL)");

        statement.addBatch("INSERT INTO coupon_discounting_rule" +
                "( id, type, created_on, deactivated_on, description, disc_flat_amt," +
                " disc_percentage, coupon_id, created_by)" +
                "VALUES" +
                "(18, 'PERCENTAGE', DATE_ADD(NOW(),INTERVAL -3 DAY), NULL, 'discount!', NULL," +
                " 10, " + testCouponId + ", 1)");

    }



    private int getNextTestCouponId() {
        if (testCpnIdCounter <= 0) {
            testCpnIdCounter = 1;
        }
        else {
            ++testCpnIdCounter;
        }
        return testCpnIdCounter;
    }

    public static void main(String[] args) {
        System.out.println("Running test-data creator");
        TestDataManagerForCDR testDataManagerForCDR = new TestDataManagerForCDR();
        testDataManagerForCDR.populateDevTestData();
        System.out.println("Test data creation completed");
    }
}