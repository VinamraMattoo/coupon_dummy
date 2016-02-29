package com.portea.cpnen.rapi.service;


import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.sql.*;

import static org.junit.Assert.*;

/**
 * This class contains JUnit tests to test Proper Audit Creation for Portea Coupon System
 * through its REST API interface.
 * To be able to execute these tests on localhost, add "127.0.0.1  coupons.localhost" (without quotes)
 * to hosts list in /etc/hosts file. Please note that the virtual host coupons.localhost must be
 * configured appropriately in the target WildFly server.
 */
public class CouponDiscountRequestAuditDevTest {

    private Client client;
    private String baseURI = "http://coupons.localhost:8080/rapi";
    private final static String CON_DRIVER = "com.mysql.jdbc.Driver";
    private final static String CON_DATABASE = "jdbc:mysql://localhost:3306/coupon_management";
    private final static String CON_USERNAME = "root";
    private final static String CON_PASSWORD = "root";
    private static Connection con = null;
    private static Statement stmt = null;

    @BeforeClass
    public static void setupCommon() {
        TestDataManagerForCDR testData = new TestDataManagerForCDR();
        testData.populateDevTestData();
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

    /**
     * This test case tests whether the proper audit is created or not when a CDR is created.
     * This test also asserts proper code and product audit creation.
     */
    @Test
    public void testSuccessfulAuditCreationForRequestCDR() {
        try {
            String cdrId = creatingCDR();
            ResultSet rs = updateCdrAndAssertsStatus(cdrId, null, null);
            rs.first();
            String auditId = rs.getString("id");
            String codeQuery = "select * from coupon_disc_req_code_audit where cdr_audit_id = " + auditId + ";";
            String productQuery = "select * from coupon_disc_req_prod_audit where cdr_audit_id = " + auditId + ";";
            ResultSet codeRS = stmt.executeQuery(codeQuery);
            codeRS.first();

            assertEquals(auditId, codeRS.getString("cdr_audit_id"));

            ResultSet productRS = stmt.executeQuery(productQuery);
            productRS.first();

            assertEquals(auditId, productRS.getString("cdr_audit_id"));
            rs.close();
            codeRS.close();
            productRS.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether proper audit is created or not when a requested CDR is applied.
     * The status in coupon_disc_req_audit table is changed from REQUESTED to APPLIED.
     */
    @Test
    public void testSuccessfulAuditCreationForApplyCDR() {
        try {
            String cdrId = creatingCDR();
            ResultSet rs = updateCdrAndAssertsStatus(cdrId, "/apply?clientContextId=1", null);
            rs.next();
            String expectedStatus = rs.getString("status");
            String expectedClientContextId = rs.getString("client_context_id");

            assertEquals("APPLIED", expectedStatus);
            assertEquals("1", expectedClientContextId);
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether proper audit is created or not when a CDR is committed with
     * clientContextID after applying.
     * The status in coupon_disc_req_audit table is changed from REQUESTED to APPLIED.
     * The clientContextId in coupon_disc_req_audit is changed from NULL to committed clientContextID.
     */
    @Test
    public void testSuccessfulAuditCreationForCommitCDR() {
        try {
            String cdrId = creatingCDR();
            ResultSet rs = updateCdrAndAssertsStatus(cdrId, "/apply", "/commit?clientContextId=1");
            rs.next();
            String expectedStatus = rs.getString("status");
            String expectedClientContextId = rs.getString("client_context_id");

            assertEquals("APPLIED", expectedStatus);
            assertEquals(null, expectedClientContextId);

            rs.next();
            expectedStatus = rs.getString("status");
            expectedClientContextId = rs.getString("client_context_id");

            assertEquals("APPLIED", expectedStatus);
            assertEquals("1", expectedClientContextId);
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether proper audit is created or not when a CDR is cancelled after Requested.
     * The status in coupon_disc_req_audit table is changed from REQUESTED to CANCELLED.
     */
    @Test
    public void testSuccessfulAuditCreationForCancelCDR() {
        try {
            String cdrId = creatingCDR();
            ResultSet rs = updateCdrAndAssertsStatus(cdrId, "/cancel", null);
            rs.next();
            String expectedStatus = rs.getString("status");
            String expectedClientContextId = rs.getString("client_context_id");

            assertEquals("CANCELED", expectedStatus);
            assertEquals(null, expectedClientContextId);
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether proper audit is created or not when a current CDR is updated and
     * some codes and products is added in a CDR.
     * It asserts same product, brand and code ID is in coupon_disc_req_code_audit, coupon_disc_req_prod_audit
     * table or not.
     */
    @Test
    public void testSuccessfulAuditCreationForAddCodesAndProducts() {
        try {
            String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");
            WebTarget postTarget = client.target(baseURI + "/cdr");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonCodesArray = jsonObject.getJSONArray("codes");
            jsonCodesArray.put(0, "C1-D2");
            jsonObject.put("requesterId", 3);
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
            JSONObject postResponseJsonObj = new JSONObject(postResponse.readEntity(String.class));
            String cdrId = postResponseJsonObj.get("cdrId").toString();
            postResponse.close();

            String code = "E1-F1";
            WebTarget putTarget = client.target(baseURI + "/cdr/" + cdrId + "/code/" + code);
            Response putResponse = putTarget.request().method("PUT");
            putResponse.close();

            WebTarget putTarget1 = client.target(baseURI + "/cdr/" + cdrId + "/product");
            String addProduct = " {\n" +
                    " \t\"totalCost\": \"100\",\n" +
                    " \t\"products\": [{\n" +
                    " \t\t\"id\": 3,\n" +
                    " \t\t\"count\": 1,\n" +
                    " \t\t\"unitCost\": 20,\n" +
                    " \t\t\"productType\": \"PACKAGE\",\n" +
                    " \t\t\"purchaseCount\": 1,\n" +
                    " \t\t\"remarks\": \"Sample Remarks\"\n" +
                    " \t}]\n" +
                    " }";
            Response putResponse1 = putTarget1.request().put(Entity.entity(addProduct, MediaType.APPLICATION_JSON_TYPE));
            putResponse1.close();

            stmt = con.createStatement();
            String query = "select * from coupon_disc_req_audit where coupon_disc_req_id = " + cdrId + ";";
            ResultSet rs = stmt.executeQuery(query);
            rs.last();
            String auditId = rs.getString("id");

            String codeQuery = "select * from coupon_disc_req_code_audit where cdr_audit_id = " + auditId + ";";
            String productQuery = "select * from coupon_disc_req_prod_audit where cdr_audit_id = " + auditId + ";";
            ResultSet codeRS = stmt.executeQuery(codeQuery);
            codeRS.last();
            assertEquals("21", codeRS.getString("code_id"));
            ResultSet productRS = stmt.executeQuery(productQuery);
            productRS.last();
            assertEquals("3", productRS.getString("product_id"));

            rs.close();
            codeRS.close();
            productRS.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests when a CDR is applied whether Coupon Discount table is updated or not
     * for that CdrId.
     */
    @Test
    public void testCouponDiscountCreationWhenCDRApplied() {
        try {
            String cdrId = creatingCDR();
            ResultSet rs = updateCdrAndAssertsStatus(cdrId, "/apply?clientContextId=1", null);
            rs.next();
            String expectedStatus = rs.getString("status");
            String expectedClientContextId = rs.getString("client_context_id");
            String exceptedAreaId = rs.getString("area_id");
            String exceptedReferrerId = rs.getString("referrer_id");
            String exceptedPatientBrandId = rs.getString("patient_brand_id");

            assertEquals("APPLIED", expectedStatus);
            assertEquals("1", expectedClientContextId);
            rs.close();
            stmt = con.createStatement();
            String query = "select * from coupon_discount where coupon_disc_req_id = " + cdrId + ";";
            ResultSet resultSet = stmt.executeQuery(query);
            resultSet.last();
            assertEquals(cdrId, resultSet.getString("coupon_disc_req_id"));
            assertEquals(expectedClientContextId, resultSet.getString("client_context_id"));
            assertEquals(exceptedAreaId, resultSet.getString("area_id"));
            assertEquals(exceptedReferrerId, resultSet.getString("referrer_id"));
            assertEquals(exceptedPatientBrandId, resultSet.getString("patient_brand_id"));
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
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

    /**
     * This method updates a current CDR and asserts its status and ClientContextId and returns
     * a set of rows in JDBC resultset.
     *
     * @param cdrId     Coupon Discount Request Id.
     * @param applyURL  URL for apply a current CDR
     * @param commitURL URL for commit a current CDR
     */
    private ResultSet updateCdrAndAssertsStatus(String cdrId, String applyURL, String commitURL) throws SQLException {
        if (applyURL != null) {
            WebTarget applyTarget = client.target(baseURI + "/cdr/" + cdrId + applyURL);
            Response applyResponse = applyTarget.request().method("PUT");
            applyResponse.close();
        }
        if (commitURL != null) {
            WebTarget commitTarget = client.target(baseURI + "/cdr/" + cdrId + commitURL);
            Response commitResponse = commitTarget.request().method("PUT");
            commitResponse.close();
        }
        stmt = con.createStatement();
        String query = "select * from coupon_disc_req_audit where coupon_disc_req_id = " + cdrId + ";";
        ResultSet rs = stmt.executeQuery(query);
        rs.first();
        String expectedStatus = rs.getString("status");
        String expectedClientContextId = rs.getString("client_context_id");

        assertEquals("REQUESTED", expectedStatus);
        assertEquals(null, expectedClientContextId);
        return rs;
    }

    /**
     * This method returns CdrId for Post response given a JSON
     *
     * @return cdrId
     * @throws JSONException
     */
    private String creatingCDR() throws JSONException {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");
        WebTarget postTarget = client.target(baseURI + "/cdr");
        JSONObject jsonObject = new JSONObject(jsonString);
        Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
        JSONObject postResponseJsonObj = new JSONObject(postResponse.readEntity(String.class));
        String cdrId = postResponseJsonObj.get("cdrId").toString();
        postResponse.close();
        return cdrId;
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
}
