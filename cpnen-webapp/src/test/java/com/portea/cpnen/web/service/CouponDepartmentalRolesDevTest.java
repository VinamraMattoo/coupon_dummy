package com.portea.cpnen.web.service;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.io.*;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * This class contains JUnit tests to test various coupon operations of Portea Coupon Management System
 * through its REST api interface. To be able to execute these tests on localhost, add "127.0.0.1  coupons.localhost"
 * (without quotes) to hosts list in /etc/hosts file. Please note that the virtual host coupons.localhost must be
 * configured appropriately in the target WildFly server.
 * <p>
 * ===========  IMPORTANT  =============<br/>
 * Each of these test methods have to be run singly, rather not as a whole suite. <br/>
 * In order to run the following tests, login to the Coupon Management System with the specified username/password
 * mentioned with each tests and fetch the JSESSIONID from the cookie and update the global "JSESSIONID_VALUE" variable
 * with this new fetched value.<br/>
 * As an Example :- <br/>
 * private static final String JSESSIONID_VALUE = "8c7qIwtGE_tmmvAnWPshaoIqy0B2trwZrtb482ga"; <br/>
 * JSESSIONID is look like this. <br/>
 * =====================================
 */
public class CouponDepartmentalRolesDevTest {

    private static final String COUPON_RESPONSE_ERROR_X_HEADER = "X-Cpnen-web-Error";
    private static final String COUPON_RESPONSE_ERROR_DETAILS = "X-Cpnen-web-Error-Detail";
    private Client client;
    private String baseURL = "http://coupons.localhost:8080/web";

    private static final String JSESSIONID_VALUE = "Your JSESSIONID Value";

    @BeforeClass
    public static void commonSetup() {
        CpnenTestDataDb testDatadb = new CpnenTestDataDb();
        testDatadb.populateTestData();
    }

    @Before
    public void setup() {
        this.client = ClientBuilder.newClient();
    }

    /**
     * This test case tests Successful creation of a Coupon when the user logs in with role Coupon Admin,
     * this testcase creates a Sales coupon.<br/>
     * To Run this test case u should login as :-<br/>
     * Username : tester <br/>
     * Password : pass
     */
    @Test
    public void testSalesCpnCreationForAdminRole() {
        String name = generateRandomCouponName();
        try {
            createCouponAndAssertStatus(name, "SALES", Response.Status.OK.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case testes Successful Coupon Creation when the user logs in with role Coupon Admin ,
     * this testcase create marketing coupon.<br/>
     * To Run this test case u should login as:-<br/>
     * Username : tester <br/>
     * Password : pass
     */
    @Test
    public void testMarketingCpnCreationForAdminRole() {
        String name = generateRandomCouponName();
        try {
            createCouponAndAssertStatus(name, "MARKETING", Response.Status.OK.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case testes Successful Coupon Creation when the user logs in with role Coupon Manager Sales,
     * this testcase create Sales coupon.<br/>
     * To Run this test case u should login as:-<br/>
     * Username : tester1 <br/>
     * Password : pass
     */
    @Test
    public void testCpnCpnCreationForCategoryWithValidRole() {
        String name = generateRandomCouponName();
        try {
            createCouponAndAssertStatus(name, "SALES", Response.Status.OK.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This testcase tests whether the coupon is created or not when the user logs in with role Coupon Manager Sales
     * and trying to create a Marketing category coupon.<br/>
     * Failure Code : 409 <br/>
     * Coupon Error Header Detail : Coupon creation failed: User cannot create a department specific coupon to which he isn't assigned to <br/>
     * <p>
     * To Run this testcase u should login as:-<br/>
     * Username : tester1 <br/>
     * Password : pass
     */
    @Test
    public void testCpnCreationForCategoryAndRoleMismatch() {
        String name = generateRandomCouponName();
        try {
            String expectedErrorMsg = "CPNE201::Coupon creation failed";
            String expectedErrorMsgDetail = "Coupon creation failed: User cannot create a department specific coupon to which he isn't assigned to";
            Response response = createCouponAndAssertStatus(name, "MARKETING", Response.Status.CONFLICT.getStatusCode());

            String actualErrorMsg = (String) response.getHeaders().get(COUPON_RESPONSE_ERROR_X_HEADER).get(0);
            assertEquals(expectedErrorMsg, actualErrorMsg);

            String actualErrorMsgDetail = (String) response.getHeaders().get(COUPON_RESPONSE_ERROR_DETAILS).get(0);
            assertEquals(expectedErrorMsgDetail, actualErrorMsgDetail);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests Successful default coupon listing when the user logs in with role Coupon Admin.<br/>
     * To run this testcase u should login as:-<br/>
     * Username : tester <br/>
     * Password : pass
     */
    @Test
    public void testCpnListingForAdminRole() {
        try {
            totalRecordsAndAssertStatus("/rws/coupon/list", Response.Status.OK.getStatusCode(), "12");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests Successful coupon listing when the user logs in with role Coupon Manager Sales.<br/>
     * To run this test case u should login as:-<br/>
     * Username : tester1 <br/>
     * Password : pass
     */
    @Test
    public void testCpnListingForValidRole() {
        try {
            totalRecordsAndAssertStatus("/rws/coupon/list", Response.Status.OK.getStatusCode(), "9");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests Successful Coupon listing By Coupon name when the user logs in with role Coupon<br/>
     * Manager Sales. In this testcase 'health coupon' is a sales coupon. <br/>
     * To run this testcase u should login as:- <br/>
     * Username : tester1 <br/>
     * Password : pass
     */
    @Test
    public void testCpnListingByNameForValidRole() {
        try {
            totalRecordsAndAssertStatus("/rws/coupon/list?name=health coupon", Response.Status.OK.getStatusCode(), "1");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests Successful Coupon listing By Coupon name when the user logs in with role Coupon
     * Manager Sales. In this testcase 'long coupon' is a Marketing coupon. This testcase asserts
     * zero records.<br/>
     * To run this testcase u should login as:-<br/>
     * Username : tester1 <br/>
     * Password : pass
     */
    @Test
    public void testCpnListingByNameForInvalidRole() {
        try {
            totalRecordsAndAssertStatus("/rws/coupon/list?name=long coupon", Response.Status.OK.getStatusCode(), "0");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests Successful Coupon listing when the user logs in with role Coupon
     * Manager Marketing.<br/>
     * To run this testcase u should login as:-<br/>
     * Username : tester2 <br/>
     * Password : pass
     */
    @Test
    public void testCpnListingForRoleMarketing() {
        try {
            totalRecordsAndAssertStatus("/rws/coupon/list", Response.Status.OK.getStatusCode(), "3");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This testcase tests whether failure is returned or not when the user logs in with role Coupon Manager Sales
     * and user tries to update a Marketing category coupon.<br/>
     * Status Error Code : 409 <br/>
     * Coupon Error Header Detail : Coupon update failed: User cannot create a department specific coupon to which he isn't assigned to <br/>
     * To Run this testcase u should login as:-<br/>
     * Username : tester1 <br/>
     * password : pass
     */
    @Test
    public void testCpnUpdateForCategoryAndRoleMismatch() {
        String name = generateRandomCouponName();
        try {
            Response response = createCouponAndAssertStatus(name, "SALES", Response.Status.OK.getStatusCode());
            String cpnId = response.readEntity(String.class);
            response.close();

            String lastUpdatedOn = getCouponAttribute(cpnId, Response.Status.OK.getStatusCode());
            Response putResponse = updateCouponAndAssertStatus(name, cpnId, lastUpdatedOn, "MARKETING", Response.Status.CONFLICT.getStatusCode());

            String expectedErrorMsg = "CPNE203::Coupon update failed";
            String expectedErrorMsgDetail = "Coupon update failed: User cannot create a department specific coupon to which he isn't assigned to";
            String actualErrorMsg = (String) putResponse.getHeaders().get(COUPON_RESPONSE_ERROR_X_HEADER).get(0);
            assertEquals(expectedErrorMsg, actualErrorMsg);

            String actualErrorMsgDetail = (String) putResponse.getHeaders().get(COUPON_RESPONSE_ERROR_DETAILS).get(0);
            assertEquals(expectedErrorMsgDetail, actualErrorMsgDetail);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    private String generateRandomCouponName() {
        return "Coupon" + (new Random()).nextInt();
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

    /**
     * This method Create a coupon and asserts Status code and returns http post response.
     *
     * @param nameValue      : Random Generated Coupon Name.
     * @param categoryValue  : Category of coupon 'SALES, MARKETING, ENGAGEMENT, Ops'.
     * @param exceptedStatus : Excepted Http status code to chaeck
     * @return Post Coupon response
     * @throws JSONException
     */
    private Response createCouponAndAssertStatus(String nameValue, String categoryValue, int exceptedStatus) throws JSONException {
        String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationDraft.json");
        WebTarget postTarget = client.target(baseURL + "/rws/coupon");
        JSONObject jsonObject = new JSONObject(jsonString);
        jsonObject.put("name", nameValue);
        jsonObject.put("category", categoryValue);
        Response postResponse = postTarget.request().cookie("JSESSIONID", JSESSIONID_VALUE).post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
        assertEquals(exceptedStatus, postResponse.getStatus());
        return postResponse;
    }

    /**
     * This method Asserts Http status Code and total number of records returned in response
     *
     * @param url             : URL to hit to get response
     * @param exceptedStatus  : Excepted HTTP status code
     * @param exceptedRecords : Excepted total number of records to assert
     * @throws JSONException
     */
    private void totalRecordsAndAssertStatus(String url, int exceptedStatus, String exceptedRecords) throws JSONException {
        WebTarget getTarget = client.target(baseURL + url);
        Response getResponse = getTarget.request().cookie("JSESSIONID", JSESSIONID_VALUE).get();
        assertEquals(exceptedStatus, getResponse.getStatus());
        JSONObject getResponseJsonObj = new JSONObject(getResponse.readEntity(String.class));
        String totalRecords = getResponseJsonObj.get("total").toString();

        assertEquals(exceptedRecords, totalRecords);
    }

    /**
     * This method returns a coupon attribute which is used at a time of coupon update
     *
     * @param cpnId  : Id of the created coupon
     * @param status : Excepted HTTP status code
     * @return lastUpdatedOn coupon attribute which is used at time of update.
     * @throws JSONException
     */
    private String getCouponAttribute(String cpnId, int status) throws JSONException {
        WebTarget getTarget = client.target(baseURL + "/rws/coupon/" + cpnId);
        Response getResponse = getTarget.request().get();
        JSONObject getResponseJsonObj = new JSONObject(getResponse.readEntity(String.class));
        String lastUpdatedOn = getResponseJsonObj.get("lastUpdatedOn").toString();
        assertEquals(Response.Status.OK.getStatusCode(), getResponse.getStatus());
        getResponse.close();
        return lastUpdatedOn;
    }

    /**
     * This method updates a coupon and asserts Http status and returns response.
     *
     * @param nameValue     : Random Generated Coupon name
     * @param cpnId         : Id of the created
     * @param lastUpdatedOn : Coupon attribute needed at the time of coupon update
     * @param categoryValue : Changed category value
     * @param status        : Excepted HTTP status code.
     * @return
     * @throws JSONException
     */
    private Response updateCouponAndAssertStatus(String nameValue, String cpnId, String lastUpdatedOn, String categoryValue, int status) throws JSONException {
        String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationDraft.json");
        WebTarget putTarget = client.target(baseURL + "/rws/coupon/" + cpnId);
        JSONObject jsonObject = new JSONObject(jsonString);
        jsonObject.put("name", nameValue);
        jsonObject.put("lastUpdatedOn", lastUpdatedOn);
        jsonObject.put("category", categoryValue);
        Response putResponse = putTarget.request().put(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
        assertEquals(status, putResponse.getStatus());
        return putResponse;
    }

    @After
    public void close() {
        if (client != null) {
            client.close();
        }
    }
}
