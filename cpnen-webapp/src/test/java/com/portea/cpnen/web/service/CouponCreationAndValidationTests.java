package com.portea.cpnen.web.service;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.junit.*;

import java.util.Random;

import javax.ws.rs.client.Client;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.ws.rs.client.ClientBuilder;


/**
 * This class contains JUnit tests to test coupon creation and validation in Portea Coupon Management System
 * through its REST API interface. To be able to execute these tests on localhost, add "127.0.0.1  coupons.localhost"
 * (without quotes) to hosts list in /etc/hosts file. Please note that the virtual host coupons.localhost must be
 * configured appropriately in the target WildFly server.
 * ===========  IMPORTANT  =============
 * In order to run these tests following lines should be commented in the web.xml file
 * <p>
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
 * this is done to remove the need of a  session id
 */
public class CouponCreationAndValidationTests {

    private static final String COUPON_RESPONSE_ERROR_DETAILS = "X-Cpnen-web-Error-Detail";
    public String expectedMsg;
    private Client client;
    private String baseURL = "http://coupons.localhost:8080/web";

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
     * This test case tests whether failure is returned when an invalid Coupon name is sent
     * in the JSON payload for Coupon creation.
     */
    @Test
    public void testCpnCrFlrWhenInvalidCharInName() {
        String string = generateRandomCouponName();
        expectedMsg = "Coupon creation failed: Coupon name can have only alphanumeric and - or _ or space' characters";
        tryCreatingCpnAndAssertResponse("name", "(*&^%$#@#$%^" + string, Response.Status.CONFLICT.getStatusCode(), expectedMsg);
    }

    /**
     * This test case tests the failure scenario when coupon name is specified as NULL
     */
    @Test
    public void testCpnCrFlrWhenEmptyName1() {
        expectedMsg = "Coupon creation failed: Coupon name cannot have null or empty values";
        tryCreatingCpnAndAssertResponse("name", null, Response.Status.CONFLICT.getStatusCode(), expectedMsg);
    }

    /**
     * This test case tests the failure scenario when coupon name is specified as empty String
     */
    @Test
    public void testCpnCrFlrWhenEmptyName2() {
        expectedMsg = "Coupon creation failed: Coupon name cannot have null or empty values";
        tryCreatingCpnAndAssertResponse("name", "", Response.Status.CONFLICT.getStatusCode(), expectedMsg);
    }

    /**
     * This test case tests the failure scenario when coupon name is specified as text containing whitespaces
     */
    @Test
    public void testCpnCrFlrWhenEmptyName3() {
        expectedMsg = "Coupon creation failed: Coupon name cannot start with '_' or '-' or ' '";
        tryCreatingCpnAndAssertResponse("name", "   ", Response.Status.CONFLICT.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenDuplicateName() {
        String string = generateRandomCouponName();
        expectedMsg = "None";
        tryCreatingCpnAndAssertResponse("name", string, Response.Status.OK.getStatusCode(), expectedMsg);
        expectedMsg = "Coupon creation failed: An active coupon with this name already exists";
        tryCreatingCpnAndAssertResponse("name", string, Response.Status.CONFLICT.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenNameIsTooLong() {
        expectedMsg = "Coupon creation failed: Coupon name cannot exceed 128 characters";
        tryCreatingCpnAndAssertResponse("name",
                "bvbekvbevbesvsevblesvbfdbvsvblvbsbvisbvebviebvbekvbevbesvsevblesvbfdbvsvb" +
                        "lvbsbvisbvebviebvbekvbevbesvsevblesvbfdbvsvblvbsbvisbvebvie",
                Response.Status.CONFLICT.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenApplicableValueAreNegativeEpoch() {
        expectedMsg = "Coupon creation failed: Negative epoch values at the time of coupon creation are not allowed";
        String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationPublished.json");

        try {
            WebTarget postTarget = client.target(baseURL + "/rws/coupon");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject changedJson = jsonObject.put("applicableTill", "-9");
            jsonObject.put("applicableFrom", "-10");
            Response postResponse = postTarget.request().post(Entity.entity(changedJson.toString(), MediaType.APPLICATION_JSON_TYPE));
            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());
            String actualErrorMsg = (String) postResponse.getHeaders().get(COUPON_RESPONSE_ERROR_DETAILS).get(0);
            System.out.println(actualErrorMsg);
            assertEquals(actualErrorMsg, expectedMsg);
            postResponse.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    @Test
    public void testCpnCrFlrWhenApplicabilityTillLessThanFrom() {
        String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationPublished.json");
        String expectedErrorMsg = "Coupon creation failed: Coupon applicable from is greater than applicable till";
        try {
            WebTarget postTarget = client.target(baseURL + "/rws/coupon");
            JSONObject jsonObject = new JSONObject(jsonString);
            String string = generateRandomCouponName();
            jsonObject.put("applicableFrom", 1453314600000L);
            jsonObject.put("applicableTill", 1453314000000L);
            jsonObject.put("name", string);
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());
            String actualErrorMsg = (String) postResponse.getHeaders().get(COUPON_RESPONSE_ERROR_DETAILS).get(0);
            System.out.println(actualErrorMsg);
            assertEquals(expectedErrorMsg, actualErrorMsg);
            postResponse.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    @Test
    public void testCpnCrFlrApplicableFromIncomplete(){
        expectedMsg = "Coupon creation failed: Parameter applicableFrom is incomplete";
        tryCreatingCpnAndAssertResponse("applicableFrom", null, Response.Status.CONFLICT.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrApplicableTillIncomplete(){
        expectedMsg = "Coupon creation failed: Parameter applicableTill is incomplete";
        tryCreatingCpnAndAssertResponse("applicableTill", null, Response.Status.CONFLICT.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrForTransactionMinMaxInvalid(){
        String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationPublished.json");
        String expectedErrorMsg = "Coupon creation failed: coupon transaction minimum value is greater than transaction maximum value";
        try {
            WebTarget postTarget = client.target(baseURL + "/rws/coupon");
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("transactionValMin", 5000);
            jsonObject.put("transactionValMax", 100);
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());
            String actualErrorMsg = (String) postResponse.getHeaders().get(COUPON_RESPONSE_ERROR_DETAILS).get(0);
            System.out.println(actualErrorMsg);
            assertEquals(expectedErrorMsg, actualErrorMsg);
            postResponse.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    @Test
    public void testCpnCrFlrForDiscountMinMaxInvalid(){
        String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationPublished.json");
        String expectedErrorMsg = "Coupon creation failed: Coupon discount minimum amount is greater than discount maximum amount";
        try {
            WebTarget postTarget = client.target(baseURL + "/rws/coupon");
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("discountAmountMin", 500);
            jsonObject.put("discountAmountMax", 10);
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());
            String actualErrorMsg = (String) postResponse.getHeaders().get(COUPON_RESPONSE_ERROR_DETAILS).get(0);
            System.out.println(actualErrorMsg);
            assertEquals(expectedErrorMsg, actualErrorMsg);
            postResponse.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    @Test
    public void testCpnCrFlrWhenTransMaxValueIsInvalid() {
        expectedMsg = "None";
        tryCreatingCpnAndAssertResponse("transactionValMax", "11234123412341242141242134142134124124123412341",
                Response.Status.BAD_REQUEST.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenTransMinValueIsInvalid() {
        expectedMsg = "None";
        tryCreatingCpnAndAssertResponse("transactionValMax", "11234123412341242141242134142134124124123412341",
                Response.Status.BAD_REQUEST.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenTransMaxValueIsNegative() {
        expectedMsg = "Coupon creation failed: Coupon transaction maximum value has to be a positive non-zero value";
        tryCreatingCpnAndAssertResponse("transactionValMax", "-5000", Response.Status.CONFLICT.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenTransactionMinValueIsNegative() {
        expectedMsg = "Coupon creation failed: Coupon transaction minimum value has to be a positive non-zero value";
        tryCreatingCpnAndAssertResponse("transactionValMin", "-500", Response.Status.CONFLICT.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenDiscountMaxValueIsInvalid() {
        expectedMsg = "None";
        tryCreatingCpnAndAssertResponse("discountAmountMax", "11234123412341242141242134142134124124123412341",
                Response.Status.BAD_REQUEST.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenDiscountMinValueIsInvalid() {
        expectedMsg = "None";
        tryCreatingCpnAndAssertResponse("discountAmountMin", "11234123412341242141242134142134124124123412341",
                Response.Status.BAD_REQUEST.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenDiscountMaxValueIsNegative() {
        expectedMsg = "Coupon creation failed: Coupon discount maximum value has to be a positive non-zero value";
        tryCreatingCpnAndAssertResponse("discountAmountMax", "-500", Response.Status.CONFLICT.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenDiscountMinValueIsNegative() {
        expectedMsg = "Coupon creation failed: Coupon discount minimum value has to be a positive non-zero value";
        tryCreatingCpnAndAssertResponse("discountAmountMin", "-500", Response.Status.CONFLICT.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenContextTypeIsInvalid() {
        expectedMsg = "None";
        tryCreatingCpnAndAssertResponse("contextType", "hh", Response.Status.BAD_REQUEST.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenContextTypeIncomplete() {
        expectedMsg = "Coupon creation failed: Parameter contextType is incomplete";
        tryCreatingCpnAndAssertResponse("contextType", null, Response.Status.CONFLICT.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenDescriptionIsTooLong() {
        expectedMsg = "Coupon creation failed: Coupon Description cannot exceed 512 characters";
        tryCreatingCpnAndAssertResponse("description",
                "bvbekvbevbesvsevblesvbfdbvsvblvbsbvisbvebviebvbekvbevbesvsevblesvbfdbvsvblvbsbvisbvebviebvbekv" +
                        "bevbesvsevblesvbfdbvsvblvbsbvisbvebviebvbekvbevbesvsevblesvbfdbvsvblvbsbvisbvebviebvbekbevbe" +
                        "svsevblesvbfdbvsvblvbsbvisbvebviebvbekvbevbesvsevblesvbfdbvsvblvbsbvisbvebviebvbekbevbesvsevblesvb" +
                        "bevbesvsevblesvbfdbvsvblvbsbvisbvebviebvbekvbevbesvsevvbekvbevbesvsevblesvbfdbvsvblvbsbvisbvebviebvbek" +
                        "vbevbesvsevblesvbevbesvsevblesvbfdbvsvblvbsbvisbvebviebvbekvbevbesvsevblesvbfdbvsvblvbsbvisbvebviebvbekbfdb" +
                        "vsvblvbsbvisbvebviebvbekvbevbesvsevblesvbfdbvsvblvbsbvisbvebvie",
                Response.Status.CONFLICT.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenCouponCategoryIsInvalid() {
        expectedMsg = "None";
        tryCreatingCpnAndAssertResponse("category", "hh", Response.Status.BAD_REQUEST.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenCouponCategoryIncomplete() {
        expectedMsg = "Coupon creation failed: Parameter category is incomplete";
        tryCreatingCpnAndAssertResponse("category", null, Response.Status.CONFLICT.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenInclusiveValueIsInvalid() {
        expectedMsg = "None";
        tryCreatingCpnAndAssertResponse("inclusive", "hh", Response.Status.BAD_REQUEST.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenActorTypeIsInvalid() {
        expectedMsg = "None";
        tryCreatingCpnAndAssertResponse("actorType", "hh", Response.Status.BAD_REQUEST.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenActorTypeInComplete() {
        expectedMsg = "Coupon creation failed: Parameter actorType is incomplete";
        tryCreatingCpnAndAssertResponse("actorType", null, Response.Status.CONFLICT.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenApplicationTypeIsInvalid() {
        expectedMsg = "None";
        tryCreatingCpnAndAssertResponse("applicationType", "hh", Response.Status.BAD_REQUEST.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrAppTypeNthUndefinedNthTime(){
        expectedMsg = "Coupon creation failed: For this Coupon type, the value of 'n' must be specified";
        tryCreatingCpnAndAssertResponse("applicationType", "NTH_TIME", Response.Status.CONFLICT.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrAppTypeOneTimeFiFoUndefinedUseCount(){
        expectedMsg = "Coupon creation failed: For this coupon type, applicable count must be specified";
        tryCreatingCpnAndAssertResponse("applicationType", "ONE_TIME_PER_USER_FIFO", Response.Status.CONFLICT.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrAppTypeIncomplete(){
        expectedMsg = "Coupon creation failed: Parameter applicationType is incomplete";
        tryCreatingCpnAndAssertResponse("applicationType", null, Response.Status.CONFLICT.getStatusCode(), expectedMsg);
    }
    @Test
    public void testCpnCrFlrWhenNTimeRecurringValueIsInvalid() {
        expectedMsg = "None";
        tryCreatingCpnAndAssertResponse("nthTimeRecurring", "hh", Response.Status.BAD_REQUEST.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenNthValueIsInvalid() {
        expectedMsg = "None";
        tryCreatingCpnAndAssertResponse("nthTime", "hh", Response.Status.BAD_REQUEST.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenNthValueIsTooLong() {
        expectedMsg = "None";
        tryCreatingCpnAndAssertResponse("nthTime", "787845565656565", Response.Status.BAD_REQUEST.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenApplicationCountIsInvalid() {
        expectedMsg = "None";
        tryCreatingCpnAndAssertResponse("applicableUseCount", "hh", Response.Status.BAD_REQUEST.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenApplicationCountIsTooLong() {
        expectedMsg = "None";
        tryCreatingCpnAndAssertResponse("applicableUseCount", "4545656585646554654", Response.Status.BAD_REQUEST.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenRuleTypeIsInvalid() {
        expectedMsg = "None";
        tryCreatingCpnAndAssertResponse("ruleType", "hh", Response.Status.BAD_REQUEST.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenRulePercentValueIsInvalid() {
        expectedMsg = "None";
        tryCreatingCpnAndAssertResponse("discountPercentage", "hh", Response.Status.BAD_REQUEST.getStatusCode(), expectedMsg);
    }

    @Test
    public void testCpnCrFlrWhenPercentageRuleValueIsTooLong() {
        String expectedErrorMsg = "Coupon creation failed: The discount rule percentage must be within [1,100]";
        String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationPublished.json");
        String name = generateRandomCouponName();
        try {
            WebTarget postTarget = client.target(baseURL + "/rws/coupon");
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("name", name);
            JSONObject jsonObject1 = jsonObject.getJSONObject("discountRule");
            jsonObject1.put("ruleType", "PERCENTAGE");
            jsonObject1.put("discountPercentage", "555");
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());
            String actualErrorMsg = (String) postResponse.getHeaders().get(COUPON_RESPONSE_ERROR_DETAILS).get(0);
            assertEquals(expectedErrorMsg, actualErrorMsg);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    @Test
    public void testCpnCrFlrWhenFlatRuleValueIsNegative() {
        String expectedErrorMsg = "Coupon creation failed: The discount rule flat value must be positive and non-zero";
        String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationPublished.json");
        String name = generateRandomCouponName();
        try {
            WebTarget postTarget = client.target(baseURL + "/rws/coupon");
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("name", name);
            JSONObject jsonObject1 = jsonObject.getJSONObject("discountRule");
            jsonObject1.put("ruleType", "FLAT");
            jsonObject1.put("discountFlatAmount", "-100");
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());
            String actualErrorMsg = (String) postResponse.getHeaders().get(COUPON_RESPONSE_ERROR_DETAILS).get(0);
            assertEquals(expectedErrorMsg, actualErrorMsg);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    @Test
    public void testCpnCrFlrWhenPackageNameForMappingIsInvalid() {
        String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationPublished.json");
        String expectedErrorMsg = "Coupon creation failed: Package id and Package name do not match";
        String name = generateRandomCouponName();
        try {
            WebTarget postTarget = client.target(baseURL + "/rws/coupon");
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("name",name);
            JSONArray jsonArray = jsonObject.getJSONArray("productMapping");
            for (int i = 0; i < 1; i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                jsonObj.put("productId", "1");
                jsonObj.put("name", "10 Physio visits");
                jsonObj.put("type", "PACKAGE");
            }
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());
            String actualErrorMsg = (String) postResponse.getHeaders().get(COUPON_RESPONSE_ERROR_DETAILS).get(0);
            System.out.println(actualErrorMsg);
            assertEquals(expectedErrorMsg, actualErrorMsg);
            postResponse.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    @Test
    public void testCpnCrFlrWhenServiceNameForMappingIsInvalid() {
        String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationPublished.json");
        String expectedErrorMsg = "Coupon creation failed: Service id and Service name do not match";
        String name = generateRandomCouponName();
        try {
            WebTarget postTarget = client.target(baseURL + "/rws/coupon");
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("name", name);
            JSONArray jsonArray = jsonObject.getJSONArray("productMapping");
            for (int i = 0; i < 1; i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                jsonObj.put("productId", "1");
                jsonObj.put("name", "12 hr nursing care");
                jsonObj.put("type", "SERVICE");
            }
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());
            String actualErrorMsg = (String) postResponse.getHeaders().get(COUPON_RESPONSE_ERROR_DETAILS).get(0);
            System.out.println(actualErrorMsg);
            assertEquals(expectedErrorMsg, actualErrorMsg);
            postResponse.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    @Test
    public void testCpnCrFlrWhenReferrerNameForMappingInvalid1() {
        String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationPublished.json");
        String expectedErrorMsg = "Coupon creation failed: Referrer id and Referrer name do not match";
        String name = generateRandomCouponName();
        try {
            WebTarget postTarget = client.target(baseURL + "/rws/coupon");
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("name", name);
            JSONArray jsonArray = jsonObject.getJSONArray("referrerMapping");
            for (int i = 0; i < 1; i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                jsonObj.put("referrerId", "1");
                jsonObj.put("name", "Practo");
                jsonObj.put("type", "B2C");
            }
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());
            String actualErrorMsg = (String) postResponse.getHeaders().get(COUPON_RESPONSE_ERROR_DETAILS).get(0);
            System.out.println(actualErrorMsg);
            assertEquals(expectedErrorMsg, actualErrorMsg);
            postResponse.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    @Test
    public void testCpnCrFlrWhenReferrerNameForMappingInvalid2() {
        String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationPublished.json");
        String expectedErrorMsg = "Coupon creation failed: No referrer source found for the specified id 111";
        String name = generateRandomCouponName();
        try {
            WebTarget postTarget = client.target(baseURL + "/rws/coupon");
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("name", name);
            JSONArray jsonArray = jsonObject.getJSONArray("referrerMapping");
            for (int i = 0; i < 1; i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                jsonObj.put("referrerId", "111");
                jsonObj.put("name", "JustDial");
                jsonObj.put("type", "B2C");
            }
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());
            String actualErrorMsg = (String) postResponse.getHeaders().get(COUPON_RESPONSE_ERROR_DETAILS).get(0);
            System.out.println(actualErrorMsg);
            assertEquals(expectedErrorMsg, actualErrorMsg);
            postResponse.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    @Test
    public void testCpnFlrWhenAreaMappingInvalid(){
        String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationPublished.json");
        String expectedErrorMsg = "Coupon creation failed: No area found for the specified id 9999";
        String name = generateRandomCouponName();
        try {
            WebTarget postTarget = client.target(baseURL + "/rws/coupon");
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("name", name);
            JSONArray jsonArray = jsonObject.getJSONArray("areaMapping");
            for (int i = 0; i < 1; i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                jsonObj.put("areaId", "9999");
            }
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());

            String actualErrorMsg = (String) postResponse.getHeaders().get(COUPON_RESPONSE_ERROR_DETAILS).get(0);
            System.out.println(actualErrorMsg);
            assertEquals(expectedErrorMsg, actualErrorMsg);

            postResponse.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    @Test
    public void testCpnCrFlrWhenAreaIdMappingIsNull() {
        String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationPublished.json");
        String expectedErrorMsg = "Coupon creation failed: Area id must be specified";
        String name = generateRandomCouponName();
        try {
            WebTarget postTarget = client.target(baseURL + "/rws/coupon");
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("name", name);
            JSONArray jsonArray = jsonObject.getJSONArray("areaMapping");
            for (int i = 0; i < 1; i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                jsonObj.put("areaId", "");
            }
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());

            String actualErrorMsg = (String) postResponse.getHeaders().get(COUPON_RESPONSE_ERROR_DETAILS).get(0);
            System.out.println(actualErrorMsg);
            assertEquals(expectedErrorMsg, actualErrorMsg);

            postResponse.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    @Test
    public void testCpnCrFlrWhenBrandIdMappingIsInvalid() {
        String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationPublished.json");
        String expectedErrorMsg = "Coupon creation failed: No brand found for the specified id 9999";
        String name = generateRandomCouponName();
        try {
            WebTarget postTarget = client.target(baseURL + "/rws/coupon");
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("name", name);
            JSONArray jsonArray = jsonObject.getJSONArray("brandMapping");
            for (int i = 0; i < 1; i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                jsonObj.put("brandId", "9999");
            }
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());

            String actualErrorMsg = (String) postResponse.getHeaders().get(COUPON_RESPONSE_ERROR_DETAILS).get(0);
            System.out.println(actualErrorMsg);
            assertEquals(expectedErrorMsg, actualErrorMsg);

            postResponse.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    @Test
    public void testCpnCrFlrWhenBrandIdMappingIsNull() {
        String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationPublished.json");
        String expectedErrorMsg = "Coupon creation failed: Parameter BrandMapping.brandId is incomplete";
        String name = generateRandomCouponName();
        try {
            WebTarget postTarget = client.target(baseURL + "/rws/coupon");
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("name", name);
            JSONArray jsonArray = jsonObject.getJSONArray("brandMapping");
            for (int i = 0; i < 1; i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                jsonObj.put("brandId", "");
            }
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());

            String actualErrorMsg = (String) postResponse.getHeaders().get(COUPON_RESPONSE_ERROR_DETAILS).get(0);
            System.out.println(actualErrorMsg);
            assertEquals(expectedErrorMsg, actualErrorMsg);

            postResponse.close();
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
     * Updates a specified key with specified value in the common coupon creation JSON and attempts to create a coupon.
     * The method compares the HTTP status code against the expected value.
     *
     * @param key            the coupon parameter to be updated
     * @param value          the value to be put for the specified key
     * @param expectedStatus the expected HTTP status code
     */
    private void tryCreatingCpnAndAssertResponse(String key, String value, int expectedStatus, String expectedErrorMsg) {
        String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationPublished.json");

        try {
            WebTarget postTarget = client.target(baseURL + "/rws/coupon");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject changedJson = jsonObject.put(key, value);
            Response postResponse = postTarget.request().post(Entity.entity(changedJson.toString(), MediaType.APPLICATION_JSON_TYPE));
            assertEquals(expectedStatus, postResponse.getStatus());
            if (!expectedErrorMsg.equals("None")) {
                String actualErrorMsg = (String) postResponse.getHeaders().get(COUPON_RESPONSE_ERROR_DETAILS).get(0);
                System.out.println(actualErrorMsg);
                assertEquals(expectedErrorMsg, actualErrorMsg);
            }
            postResponse.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    @After
    public void close() {
        if (client != null) {
            client.close();

        }
    }
}