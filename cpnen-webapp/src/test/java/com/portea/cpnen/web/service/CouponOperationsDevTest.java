package com.portea.cpnen.web.service;


import org.codehaus.jettison.json.*;
import org.junit.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * This class contains JUnit test to test various coupon operations in Porea Coupon Management System through
 * its REST API interface. To be able to execute these tests on localhost, add "127.0.0.1  coupons.localhost"
 * (without quotes) to hosts list in /etc/hosts file. Please note that the virtual host coupons.localhost must be
 * configured appropriately in the target WildFly server.
 * <p>
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
public class CouponOperationsDevTest {

    private static final String COUPON_RESPONSE_ERROR_X_HEADER = "X-Cpnen-web-Error";
    private static final String COUPON_RESPONSE_ERROR_DETAILS = "X-Cpnen-web-Error-Detail";
    private Client client;
    private String baseURL = "http://coupons.localhost:8080/web";

    @BeforeClass
    public static void commonSetup() {
        CpnenTestDataDb testDataDb = new CpnenTestDataDb();
        testDataDb.populateTestData();
    }

    @Before
    public void setup() {
        this.client = ClientBuilder.newClient();
    }

    /**
     * This test tests whether success is returned or not when user tries to Publish a draft
     * coupon.
     */
    @Test
    public void testCouponPublishFromDraftStatus() {
        try {
            String couponName = generateRandomCouponName();
            String id = createCoupon("name", couponName, Response.Status.OK.getStatusCode());
            int couponId = Integer.parseInt(id);
            String lastUpdatedOn = getCouponAttribute(couponId, Response.Status.OK.getStatusCode());
            String url = "/publish?lastUpdatedOn=" + lastUpdatedOn;
            updateCoupon("name", couponName, url, couponId, Response.Status.OK.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    /**
     * This test tests whether success is returned or not when user tries to deactivate a coupon after
     * publishing it.
     */
    @Test
    public void testCouponDeactivation() {
        try {
            String couponName = generateRandomCouponName();
            String id = createCoupon("name", couponName, Response.Status.OK.getStatusCode());
            int couponId = Integer.parseInt(id);
            String lastUpdatedOn = getCouponAttribute(couponId, Response.Status.OK.getStatusCode());
            String url = "/publish?lastUpdatedOn=" + lastUpdatedOn;
            updateCoupon("name", couponName, url, couponId, Response.Status.OK.getStatusCode());
            lastUpdatedOn = getCouponAttribute(couponId, Response.Status.OK.getStatusCode());
            url = "/deactivate?lastUpdatedOn=" + lastUpdatedOn;
            updateCoupon("name", couponName, url, couponId, Response.Status.OK.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    /**
     * This test tests whether success is returned or not when user tries to delete a coupon from
     * draft status.
     */
    @Test
    public void testDeleteDraftCoupon() {
        try {
            String couponName = generateRandomCouponName();
            String id = createCoupon("name", couponName, Response.Status.OK.getStatusCode());
            int couponId = Integer.parseInt(id);
            String lastUpdatedOn = getCouponAttribute(couponId, Response.Status.OK.getStatusCode());
            WebTarget deleteTarget = client.target(baseURL + "/rws/coupon/" + couponId + "?lastUpdatedOn=" + lastUpdatedOn);
            Response deleteResponse = deleteTarget.request().delete();
            assertEquals(Response.Status.OK.getStatusCode(), deleteResponse.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    /**
     * This test tests whether success is returned or not when user tries to extend the validity
     * of a coupon published coupon.
     */
    @Test
    public void testCouponValidityExtension() {
        try {
            String couponName = generateRandomCouponName();
            String id = createCoupon("name", couponName, Response.Status.OK.getStatusCode());
            int couponId = Integer.parseInt(id);
            String lastUpdatedOn = getCouponAttribute(couponId, Response.Status.OK.getStatusCode());
            String url = "/publish?lastUpdatedOn=" + lastUpdatedOn;
            updateCoupon("name", couponName, url, couponId, Response.Status.OK.getStatusCode());
            lastUpdatedOn = getCouponAttribute(couponId, Response.Status.OK.getStatusCode());
            WebTarget target = client.target(baseURL + "/rws/coupon/" + couponId + "/extendValidity/1485086320000" + "?lastUpdatedOn=" + lastUpdatedOn);
            Response response = target.request().method("PUT");
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    /**
     * This test tests whether success is returned or not when user deactivates a coupon and
     * coupon code linked with that coupon is automatically deactivates.
     */
    @Test
    public void testCouponCodeDeactivationWhenCouponDeactivates() {
        try {
            String couponName = generateRandomCouponName();
            String id = createCoupon("name", couponName, Response.Status.OK.getStatusCode());
            int couponId = Integer.parseInt(id);
            String lastUpdatedOn = getCouponAttribute(couponId, Response.Status.OK.getStatusCode());
            String url = "/publish?lastUpdatedOn=" + lastUpdatedOn;
            updateCoupon("name", couponName, url, couponId, Response.Status.OK.getStatusCode());
            String codeJson = "{\n" +
                    "\t\"code\": \"ABCD\",\n" +
                    "\t\"channelName\": \"FB\",\n" +
                    "\t\"reservations\": []\n" +
                    "}";
            WebTarget target = client.target(baseURL + "/rws/coupon/" + couponId + "/code");
            Response response = target.request().post(Entity.entity(codeJson, MediaType.APPLICATION_JSON_TYPE));
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            response.close();
            lastUpdatedOn = getCouponAttribute(couponId, Response.Status.OK.getStatusCode());
            url = "/deactivate?lastUpdatedOn=" + lastUpdatedOn;
            updateCoupon("name", couponName, url, couponId, Response.Status.OK.getStatusCode());
            WebTarget getTarget = client.target(baseURL + "/rws/coupon/" + couponId + "/codes");
            Response getResponse = getTarget.request().get();
            JSONObject getResponseJsonObj = new JSONObject(getResponse.readEntity(String.class));
            JSONArray jsonCodesArray = getResponseJsonObj.getJSONArray("rows");
            for (int i = 0; i < 1; i++) {
                JSONObject jsonObj = jsonCodesArray.getJSONObject(i);
                String value = jsonObj.getString("deactivatedOn");

                assertNotEquals(null, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }


    /**
     * This test case tests whether failure is returned or not when a coupon code is added for
     * a specific published coupon.
     */
    @Test
    public void testAddCouponCodeForCoupon() {
        try {
            String couponName = generateRandomCouponName();
            String id = createCoupon("name", couponName, Response.Status.OK.getStatusCode());
            int couponId = Integer.parseInt(id);
            String lastUpdatedOn = getCouponAttribute(couponId, Response.Status.OK.getStatusCode());
            String url = "/publish?lastUpdatedOn=" + lastUpdatedOn;
            updateCoupon("name", couponName, url, couponId, Response.Status.OK.getStatusCode());
            String codeJson = "{\n" +
                    "\t\"code\": \"zoozoo\",\n" +
                    "\t\"channelName\": \"FB\",\n" +
                    "\t\"reservations\": []\n" +
                    "}";
            WebTarget target = client.target(baseURL + "/rws/coupon/" + couponId + "/code");
            Response response = target.request().post(Entity.entity(codeJson, MediaType.APPLICATION_JSON_TYPE));
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether success is returned or not when user deactivates a coupon
     * code itself.
     */
    @Test
    public void testCouponCodeDeactivation() {
        try {
            String couponName = generateRandomCouponName();
            String id = createCoupon("name", couponName, Response.Status.OK.getStatusCode());
            int couponId = Integer.parseInt(id);
            String lastUpdatedOn = getCouponAttribute(couponId, Response.Status.OK.getStatusCode());
            String url = "/publish?lastUpdatedOn=" + lastUpdatedOn;
            updateCoupon("name", couponName, url, couponId, Response.Status.OK.getStatusCode());
            String codeJson = "{\n" +
                    "\t\"code\": \"PQRS\",\n" +
                    "\t\"channelName\": \"FB\",\n" +
                    "\t\"reservations\": []\n" +
                    "}";
            WebTarget target = client.target(baseURL + "/rws/coupon/" + couponId + "/code");
            Response response = target.request().post(Entity.entity(codeJson, MediaType.APPLICATION_JSON_TYPE));
            String codeId = response.readEntity(String.class);
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            response.close();
            WebTarget target1 = client.target(baseURL + "/rws/coupon/" + couponId + "/code/" + codeId + "/deactivate");
            Response response1 = target1.request().method("PUT");
            assertEquals(Response.Status.OK.getStatusCode(), response1.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    /**
     * This test tests whether failure is returned when or not. When user tries to update a coupon
     * and lastUpdatedOn is not specified in Json update payload.
     * Failure Code : 409
     * Coupon Error Header : CPNE203::Coupon update failed
     */
    @Test
    public void testFailureCouponUpdation() {
        try {
            String expectedErrorMsg = "CPNE203::Coupon update failed";
            String expectedErrorMsgDetail = "Coupon update failed: Coupon was modified by anshuman. Please reload to get the latest data.";
            String couponName = generateRandomCouponName();
            String id = createCoupon("name", couponName, Response.Status.OK.getStatusCode());
            int couponId = Integer.parseInt(id);
            String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationDraft.json");
            WebTarget putTarget = client.target(baseURL + "/rws/coupon/" + couponId);
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("name", couponName);
            Response putResponse = putTarget.request().put(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.CONFLICT.getStatusCode(), putResponse.getStatus());

            String actualErrorMsg = (String) putResponse.getHeaders().get(COUPON_RESPONSE_ERROR_X_HEADER).get(0);
            assertEquals(expectedErrorMsg, actualErrorMsg);

            String actualErrorMsgDetail = (String) putResponse.getHeaders().get(COUPON_RESPONSE_ERROR_DETAILS).get(0);
            assertEquals(actualErrorMsgDetail, expectedErrorMsgDetail);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    /**
     * This test tests whether failure is returned when user tries to extend the validity of a coupon
     * and extension time is not as per the upper limit of that coupon.
     * Failure Code : 409
     * Coupon Error Header : CPNE203::Coupon update failed
     */
    @Test
    public void testFailureCouponValidityExtension() {
        try {
            String expectedErrorMsg = "CPNE203::Coupon update failed";
            String expectedErrorMsgDetail = "Coupon update failed: Given applicability upper limit value should be greater than existing value";
            String couponName = generateRandomCouponName();
            String id = createCoupon("name", couponName, Response.Status.OK.getStatusCode());
            int couponId = Integer.parseInt(id);
            String lastUpdatedOn = getCouponAttribute(couponId, Response.Status.OK.getStatusCode());
            String url = "/publish?lastUpdatedOn=" + lastUpdatedOn;
            updateCoupon("name", couponName, url, couponId, Response.Status.OK.getStatusCode());
            lastUpdatedOn = getCouponAttribute(couponId, Response.Status.OK.getStatusCode());
            WebTarget target = client.target(baseURL + "/rws/coupon/" + couponId + "/extendValidity/1453314600000" + "?lastUpdatedOn=" + lastUpdatedOn);
            Response response = target.request().method("PUT");

            assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());

            String actualErrorMsg = (String) response.getHeaders().get(COUPON_RESPONSE_ERROR_X_HEADER).get(0);
            assertEquals(expectedErrorMsg, actualErrorMsg);

            String actualErrorMsgDetail = (String) response.getHeaders().get(COUPON_RESPONSE_ERROR_DETAILS).get(0);
            assertEquals(actualErrorMsgDetail, expectedErrorMsgDetail);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether success is returned when user tries to create a coupon with
     * same name after deactivating the earlier coupon with same name. As per the business rules
     * coupon name must be unique. But after deactivates or delete earlier coupon with same name.
     * The name can be reused.
     */
    @Test
    public void testCouponNameReuseAfterEarlierDeactivation() {
        try {
            String couponName = generateRandomCouponName();
            String id = createCoupon("name", couponName, Response.Status.OK.getStatusCode());
            int couponId = Integer.parseInt(id);
            String lastUpdatedOn = getCouponAttribute(couponId, Response.Status.OK.getStatusCode());
            String url = "/publish?lastUpdatedOn=" + lastUpdatedOn;
            updateCoupon("name", couponName, url, couponId, Response.Status.OK.getStatusCode());
            lastUpdatedOn = getCouponAttribute(couponId, Response.Status.OK.getStatusCode());
            url = "/deactivate?lastUpdatedOn=" + lastUpdatedOn;
            updateCoupon("name", couponName, url, couponId, Response.Status.OK.getStatusCode());

            createCoupon("name", couponName, Response.Status.OK.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    @After
    public void close() {
        if (client != null) {
            client.close();
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
     * Updates a specified key with specified value in the common coupon creation JSON and attempts to create a coupon.
     * The method compares the HTTP status code against the expected value and returns coupon creation Id.
     *
     * @param key    the coupon parameter to be updated
     * @param value  the value to be put for the specified key
     * @param status the expected HTTP status code
     * @return corresponding coupon creation id
     * @throws JSONException
     */
    private String createCoupon(String key, String value, int status) throws JSONException {
        String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationDraft.json");
        WebTarget postTarget = client.target(baseURL + "/rws/coupon");
        JSONObject jsonObject = new JSONObject(jsonString);
        jsonObject.put(key, value);
        Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
        String cpnId = postResponse.readEntity(String.class);
        assertEquals(status, postResponse.getStatus());
        return cpnId;

    }

    /**
     * Get the details of coupon by coupon id and returns lastUpdatedOn which is used while
     * Updating the coupon.
     *
     * @param cpnId  coupon creation id.
     * @param status the expected HTTP status code
     * @return last updated on
     * @throws JSONException
     */
    private String getCouponAttribute(int cpnId, int status) throws JSONException {
        WebTarget getTarget = client.target(baseURL + "/rws/coupon/" + cpnId);
        Response getResponse = getTarget.request().get();
        JSONObject getResponseJsonObj = new JSONObject(getResponse.readEntity(String.class));
        String lastUpdatedOn = getResponseJsonObj.get("lastUpdatedOn").toString();
        assertEquals(status, getResponse.getStatus());
        getResponse.close();
        return lastUpdatedOn;
    }

    /**
     * Updates a key with specified value with a corresponding URL and coupon id to be hit and attempts to
     * update a coupon.
     *
     * @param key    the coupon parameter to be updated
     * @param value  the value to be put for the specified key
     * @param url    corresponding URL to be hit
     * @param cpnId  coupon creation id used for update time
     * @param status the expected HTTP status code
     * @throws JSONException
     */
    private void updateCoupon(String key, String value, String url, int cpnId, int status) throws JSONException {
        String jsonString = readFile("/com/portea/cpnen/web/service/test-CouponCreationDraft.json");
        WebTarget putTarget = client.target(baseURL + "/rws/coupon/" + cpnId + url);
        JSONObject jsonObject = new JSONObject(jsonString);
        jsonObject.put(key, value);
        Response putResponse = putTarget.request().put(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
        assertEquals(status, putResponse.getStatus());
        putResponse.close();
    }
}
