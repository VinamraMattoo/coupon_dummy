package com.portea.cpnen.web.service;

import org.codehaus.jettison.json.*;
import org.junit.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import static org.junit.Assert.*;


/**
 * This class contains JUnit tests to test Proper Audit Creation for Portea Coupon System
 * through its Rest API interface.
 * To be able to execute these tests on localhost, add "127.0.0.1  coupons.localhost" (without quotes)
 * to hosts list in /etc/hosts file. Please note that the virtual host coupons.localhost must be
 * configured appropriately in the target WildFly server.
 * * <p>
 * ===========  IMPORTANT  =============<br/>
 * In order to run these tests following lines should be commented in the web.xml file
 * <br/>
 * <pre>{@code
 * <filter>
 * <filter-name>AuthFilter</filter-name>
 * <filter-class>com.portea.cpnen.web.filter.AuthenticationFilter</filter-class>
 * </filter>
 * <p>
 * <filter-mapping>
 * <filter-name>AuthFilter</filter-name>
 * <url-pattern>/web/*</url-pattern>
 * </filter-mapping>
 * <p>
 * <filter>
 * <filter-name>NoCacheFilter</filter-name>
 * <filter-class>com.portea.cpnen.web.filter.NoCacheFilter</filter-class>
 * </filter>
 * <p>
 * <filter-mapping>
 * <filter-name>NoCacheFilter</filter-name>
 * <url-pattern>/web/*</url-pattern>
 * </filter-mapping>
 * }</pre>
 * This is done to remove the need of a  session id
 */
public class CouponCreationAuditDevTest {

    private Client client;
    private String baseURI = "http://coupons.localhost:8080/web";
    private final static String CON_DRIVER = "com.mysql.jdbc.Driver";
    private final static String CON_DATABASE = "jdbc:mysql://localhost:3306/coupon_management";
    private final static String CON_USERNAME = "root";
    private final static String CON_PASSWORD = "root";
    private static Connection con = null;
    private static Statement stmt = null;

    @BeforeClass
    public static void setupCommon() {
        CpnenTestDataDb testDataDb = new CpnenTestDataDb();
        testDataDb.populateTestData();
    }

    @Before
    public void setup() {
        try {
            this.client = ClientBuilder.newClient();
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

    @Test
    public void testCouponAuditCreationForAddTransactionMinMax() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "transactionValMin", "500", "transactionValMax", "5000");
            couponAuditAssertAndCoreAssert(cpnId, couponName, "500", "trans_val_min", "5000", "trans_val_max");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForAddDiscountMinMax() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "discountAmountMin", "100", "discountAmountMax", "1000");
            couponAuditAssertAndCoreAssert(cpnId, couponName, "100", "discount_amt_min", "1000", "discount_amt_max");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForAddApplicableFromTill() {
        try {
            String couponName = generateRandomCouponName();

            Date date = new Date();
            Long epoch = date.getTime();
            String startDate = Long.toString(epoch);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, 5);
            Long epoch1 = date.getTime();
            String endDate = Long.toString(epoch1);
            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "applicableFrom", startDate, "applicableTill", endDate);
            stmt = con.createStatement();
            String query = "select * from coupon_audit where coupon_id =" + cpnId + ";";
            ResultSet rs = stmt.executeQuery(query);
            rs.first();
            rs.next();
            assertEquals("1", rs.getString("core_update"));
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForAddContextType() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "contextType", "SUBSCRIPTION", "description", "");
            couponAuditAssertAndCoreAssert(cpnId, couponName, "SUBSCRIPTION", "context_type", "", "description");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForAddCategory() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "category", "SALES", "description", "");
            couponAuditAssertAndCoreAssert(cpnId, couponName, "SALES", "category", "", "description");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForAddInclusiveFalse() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "inclusive", "false", "description", "");
            couponAuditAssertAndCoreAssert(cpnId, couponName, "0", "inclusive", "", "description");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForAddActorType() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "actorType", "STAFF", "description", "");
            couponAuditAssertAndCoreAssert(cpnId, couponName, "STAFF", "actor_type", "", "description");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForAddAppTypeNth() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "applicationType", "NTH_TIME", "nthTime", "3");
            couponAuditAssertAndCoreAssert(cpnId, couponName, "NTH_TIME", "application_type", "3", "nth_time");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForAddAppTypeFIFO() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "applicationType", "ONE_TIME_PER_USER_FIFO", "applicableUseCount", "3");
            couponAuditAssertAndCoreAssert(cpnId, couponName, "ONE_TIME_PER_USER_FIFO", "application_type", "3", "applicable_use_count");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForAddAppTypeOneTime() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "applicationType", "ONE_TIME_PER_USER", "description", "");
            couponAuditAssertAndCoreAssert(cpnId, couponName, "ONE_TIME_PER_USER", "application_type", "", "description");

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForAddDescription() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "description", "bla bla bla", "description", "bla bla bla");
            couponAuditAssertAndCoreAssert(cpnId, couponName, "bla bla bla", "description", "bla bla bla", "description");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForAddGlobalTrue() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "global", "true", "global", "true");
            couponAuditAssertAndCoreAssert(cpnId, couponName, "1", "global", "1", "global");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForAddAllProductsTrue() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "isAllProducts", "true", "isAllProducts", "true");
            couponAuditAssertAndCoreAssert(cpnId, couponName, "1", "is_for_all_products", "1", "is_for_all_products");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForAllBrandsTrue() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "isAllBrands", "true", "isAllBrands", "true");
            couponAuditAssertAndCoreAssert(cpnId, couponName, "1", "is_for_all_brands", "1", "is_for_all_brands");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForAllAreasTrue() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "isAllAreas", "true", "isAllAreas", "true");
            couponAuditAssertAndCoreAssert(cpnId, couponName, "1", "is_for_all_areas", "1", "is_for_all_areas");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForIsB2BTrue() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "isB2B", "true", "isB2B", "true");
            couponAuditAssertAndCoreAssert(cpnId, couponName, "1", "is_for_all_b2b", "1", "is_for_all_b2b");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForIsB2CTrue() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "isB2C", "true", "isB2C", "true");
            couponAuditAssertAndCoreAssert(cpnId, couponName, "1", "is_for_all_b2c", "1", "is_for_all_b2c");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForAddDiscountingRuleFlat() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "ruleType", "FLAT", "discountFlatAmount", "100");
            couponAuditAssertAndCpnDiscRuleAssert(cpnId, "FLAT", "type", "100", "disc_flat_amt");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForAddDiscountingRulePercentage() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "ruleType", "PERCENTAGE", "discountPercentage", "10");
            couponAuditAssertAndCpnDiscRuleAssert(cpnId, "PERCENTAGE", "type", "10", "disc_percentage");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForAddBrands() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "brandId", "1", null, null);
            couponAuditAssertAndCpnBrandMappingAssert(cpnId, "1", "brand_id");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForAddProducts() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "productId", "2", "type", "PACKAGE");
            couponAuditAssertAndCpnProductAdapterMappingAssert(cpnId, "2", "product_id", "PACKAGE", "product_type");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForAddAreas() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "areaId", "1", null, null);
            couponAuditAssertAndCpnAreaMappingAssert(cpnId, "1", "area_id");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @Test
    public void testCouponAuditCreationForAddReferrers() {
        try {
            String couponName = generateRandomCouponName();

            int cpnId = createCouponAndUpdateKeyValue("name", couponName, "referrerId", "1", "type", "B2C");
            couponAuditAssertAndCpnReferrerMappingAssert(cpnId, "1", "referrer_id");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @After
    public void close() {
        try {
            if (client != null) {
                client.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readFile(String filepath) {
        StringBuilder jsonInputBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(this.getClass().getResourceAsStream(filepath)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonInputBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonInputBuilder.toString();
    }

    private String generateRandomCouponName() {
        return "Coupon" + (new Random()).nextInt();
    }

    /**
     * This method creates a Draft Coupon and updates with changed key/value pairs
     *
     * @param keyName           Coupon Name
     * @param keyValue          Coupon name value
     * @param keyAddField1      first updated key
     * @param keyAddFieldValue1 first updated value
     * @param keyAddField2      second updated key
     * @param keyAddFieldValue2 second updated value
     * @return id of the created/updated coupon
     * @throws JSONException
     */
    private int createCouponAndUpdateKeyValue(String keyName, String keyValue, String keyAddField1, String keyAddFieldValue1, String keyAddField2, String keyAddFieldValue2) throws JSONException {
        String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationForAudit.json");
        WebTarget postTarget = client.target(baseURI + "/rws/coupon");
        JSONObject jsonObject = new JSONObject(jsonString);
        jsonObject.put(keyName, keyValue);
        Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
        String id = postResponse.readEntity(String.class);
        int cpnId = Integer.parseInt(id);
        postResponse.close();

        WebTarget getTarget = client.target(baseURI + "/rws/coupon/" + cpnId);
        Response getResponse = getTarget.request().get();
        JSONObject getResponseJsonObj = new JSONObject(getResponse.readEntity(String.class));
        String lastUpdatedOn = getResponseJsonObj.get("lastUpdatedOn").toString();
        getResponse.close();

        WebTarget putTarget = client.target(baseURI + "/rws/coupon/" + cpnId);
        jsonObject = new JSONObject(jsonString);
        jsonObject.put(keyName, keyValue);
        jsonObject.put("lastUpdatedOn", lastUpdatedOn);
        if (keyAddField1.equals("ruleType")) {
            JSONObject jsonObject1 = jsonObject.getJSONObject("discountRule");
            jsonObject1.put(keyAddField1, keyAddFieldValue1);
            jsonObject1.put(keyAddField2, keyAddFieldValue2);
        } else if (keyAddField1.equals("brandId")) {
            JSONArray jsonArray = jsonObject.getJSONArray("brandMapping");
            JSONObject jsonObject1 = new JSONObject().put(keyAddField1, keyAddFieldValue1);
            jsonArray.put(jsonObject1);
        } else if (keyAddField1.equals("areaId")) {
            JSONArray jsonArray = jsonObject.getJSONArray("areaMapping");
            JSONObject jsonObject1 = new JSONObject().put(keyAddField1, keyAddFieldValue1);
            jsonArray.put(jsonObject1);
        } else if (keyAddField1.equals("productId")) {
            JSONArray jsonArray = jsonObject.getJSONArray("productMapping");
            JSONObject jsonObject1 = new JSONObject().accumulate(keyAddField1, keyAddFieldValue1).accumulate("name", "10 Physio visits").accumulate(keyAddField2, keyAddFieldValue2);
            jsonArray.put(jsonObject1);
        } else if (keyAddField1.equals("referrerId")) {
            JSONArray jsonArray = jsonObject.getJSONArray("referrerMapping");
            JSONObject jsonObject1 = new JSONObject().accumulate(keyAddField1, keyAddFieldValue1).accumulate("name", "JustDIal").accumulate(keyAddField2, keyAddFieldValue2);
            jsonArray.put(jsonObject1);
        } else {
            jsonObject.put(keyAddField1, keyAddFieldValue1);
            jsonObject.put(keyAddField2, keyAddFieldValue2);
        }
        Response putResponse = putTarget.request().put(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
        putResponse.close();
        return cpnId;
    }

    /**
     * This method asserts coupon audit and coupon core audit
     *
     * @param cpnId          created/updated coupon id
     * @param couponName     The coupon name
     * @param exceptedValue1 excepted asserted value
     * @param actualValue1   actual asserted value
     * @param exceptedValue2 excepted asserted value
     * @param actualValue2   actual asserted value
     * @throws SQLException
     */
    private void couponAuditAssertAndCoreAssert(int cpnId, String couponName, String exceptedValue1, String actualValue1,
                                                String exceptedValue2, String actualValue2) throws SQLException {
        stmt = con.createStatement();
        String query = "select * from coupon_audit where coupon_id =" + cpnId + ";";
        ResultSet rs = stmt.executeQuery(query);
        rs.first();
        rs.next();
        assertEquals("1", rs.getString("core_update"));
        rs.close();
        String coreAuditQuery = "select * from coupon_core_audit where name = " + "'" + couponName + "'";
        ResultSet resultSet = stmt.executeQuery(coreAuditQuery);
        resultSet.first();
        assertEquals(exceptedValue1, resultSet.getString(actualValue1));
        assertEquals(exceptedValue2, resultSet.getString(actualValue2));
        resultSet.close();
    }

    /**
     * This method asserts coupon audit and coupon Discounting Rule audit.
     *
     * @param cpnId          created/updated coupon id
     * @param exceptedValue1 excepted asserted value
     * @param actualValue1   actual asserted value
     * @param exceptedValue2 excepted asserted value
     * @param actualValue2   actual asserted value
     * @throws SQLException
     */
    private void couponAuditAssertAndCpnDiscRuleAssert(int cpnId, String exceptedValue1, String actualValue1,
                                                       String exceptedValue2, String actualValue2) throws SQLException {
        stmt = con.createStatement();
        String query = "select * from coupon_audit where coupon_id =" + cpnId + ";";
        ResultSet rs = stmt.executeQuery(query);
        rs.first();
        rs.next();
        assertEquals("1", rs.getString("discounting_rule_update"));
        rs.close();
        String coreAuditQuery = "select * from coupon_discounting_rule_audit where coupon_id =" + cpnId + ";";
        ResultSet resultSet = stmt.executeQuery(coreAuditQuery);
        resultSet.first();
        assertEquals(exceptedValue1, resultSet.getString(actualValue1));
        assertEquals(exceptedValue2, resultSet.getString(actualValue2));
        resultSet.close();
    }

    /**
     * This method asserts coupon audit and coupon product adapter mapping audit
     *
     * @param cpnId          created/updated coupon id
     * @param exceptedValue1 excepted asserted value
     * @param actualValue1   actual asserted value
     * @param exceptedValue2 excepted asserted value
     * @param actualValue2   actual asserted value
     * @throws SQLException
     */
    private void couponAuditAssertAndCpnProductAdapterMappingAssert(int cpnId, String exceptedValue1, String actualValue1, String exceptedValue2, String actualValue2) throws SQLException {
        stmt = con.createStatement();
        String query = "select * from coupon_audit where coupon_id =" + cpnId + ";";
        ResultSet rs = stmt.executeQuery(query);
        rs.first();
        rs.next();
        assertEquals("1", rs.getString("products_update"));
        int id = rs.getInt("id");
        rs.close();
        String coreAuditQuery = "select * from coupon_product_adapter_mapping_audit where coupon_audit_id = " + "'" + id + "'";
        ResultSet resultSet = stmt.executeQuery(coreAuditQuery);
        String adapterQuery = "select * from coupon_product_adapter where product_id = " + "'" + exceptedValue1 + "'";
        ResultSet resultSet1 = stmt.executeQuery(adapterQuery);
        resultSet1.first();
        assertEquals(exceptedValue1, resultSet1.getString(actualValue1));
        assertEquals(exceptedValue2, resultSet1.getString(actualValue2));
        resultSet.close();
    }

    /**
     * This method asserts coupon audit and coupon Referrer adapter mapping audit
     *
     * @param cpnId          created/updated coupon id
     * @param exceptedValue1 excepted asserted value
     * @param actualValue1   actual asserted value
     * @throws SQLException
     */
    private void couponAuditAssertAndCpnReferrerMappingAssert(int cpnId, String exceptedValue1, String actualValue1) throws SQLException {
        stmt = con.createStatement();
        String query = "select * from coupon_audit where coupon_id =" + cpnId + ";";
        ResultSet rs = stmt.executeQuery(query);
        rs.first();
        rs.next();
        assertEquals("1", rs.getString("referrers_update"));
        int id = rs.getInt("id");
        rs.close();
        String coreAuditQuery = "select * from coupon_referrer_mapping_audit where coupon_audit_id = " + "'" + id + "'";
        ResultSet resultSet = stmt.executeQuery(coreAuditQuery);
        resultSet.first();
        assertEquals(exceptedValue1, resultSet.getString(actualValue1));
        resultSet.close();

    }

    /**
     * This method asserts coupon audit and coupon brand mapping audit
     *
     * @param cpnId          created/updated coupon id
     * @param exceptedValue1 excepted asserted value
     * @param actualValue1   actual asserted value
     * @throws SQLException
     */
    private void couponAuditAssertAndCpnBrandMappingAssert(int cpnId, String exceptedValue1, String actualValue1) throws SQLException {
        stmt = con.createStatement();
        String query = "select * from coupon_audit where coupon_id =" + cpnId + ";";
        ResultSet rs = stmt.executeQuery(query);
        rs.first();
        rs.next();
        assertEquals("1", rs.getString("brands_update"));
        int id = rs.getInt("id");
        rs.close();
        String coreAuditQuery = "select * from coupon_brand_mapping_audit where coupon_audit_id = " + "'" + id + "'";
        ResultSet resultSet = stmt.executeQuery(coreAuditQuery);
        resultSet.first();
        assertEquals(exceptedValue1, resultSet.getString(actualValue1));
        resultSet.close();
    }

    /**
     * This method asserts coupon audit and coupon area mapping audit
     * @param cpnId          created/updated coupon id
     * @param exceptedValue1 excepted asserted value
     * @param actualValue1   actual asserted value
     * @throws SQLException
     */
    private void couponAuditAssertAndCpnAreaMappingAssert(int cpnId, String exceptedValue1, String actualValue1) throws SQLException {
        stmt = con.createStatement();
        String query = "select * from coupon_audit where coupon_id =" + cpnId + ";";
        ResultSet rs = stmt.executeQuery(query);
        rs.first();
        rs.next();
        assertEquals("1", rs.getString("areas_update"));
        int id = rs.getInt("id");
        rs.close();
        String coreAuditQuery = "select * from coupon_area_mapping_audit where coupon_audit_id = " + "'" + id + "'";
        ResultSet resultSet = stmt.executeQuery(coreAuditQuery);
        resultSet.first();
        assertEquals(exceptedValue1, resultSet.getString(actualValue1));
        resultSet.close();
    }
}
