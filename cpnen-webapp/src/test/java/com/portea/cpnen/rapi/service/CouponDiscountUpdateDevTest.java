package com.portea.cpnen.rapi.service;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.*;

import static org.junit.Assert.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;

/**
 * This class contains JUnit tests to test various functionality of Portea Coupon System
 * through its REST API interface. For CDR Update Only.
 * To be able to execute these tests on localhost, add "127.0.0.1  coupons.localhost" (without quotes)
 * to hosts list in /etc/hosts file. Please note that the virtual host coupons.localhost must be
 * configured appropriately in the target WildFly server.
 */
public class CouponDiscountUpdateDevTest {

    private static final String COUPON_APP_ERROR_X_HEADER = "X-Cpnen-Error";
    private static final String COUPON_APP_DETAIL_X_HEADER = "X-Cpnen-Error-Detail";
    private static final String ERROR_MSG_PART_DELIMITER = "::";
    private Client client;
    private String baseURI = "http://coupons.localhost:8080/rapi";

    @BeforeClass
    public static void setupCommon() {
        TestDataManagerForCDR testData = new TestDataManagerForCDR();
        testData.populateDevTestData();
    }

    @Before
    public void setup() {
        this.client = ClientBuilder.newClient();
    }

    /**
     * This test case tests whether failure is returned when user updates a existing Cdr and
     * codes specified in JSON payload is invalid.
     * Failure Code : 409
     * Coupon Error Header : CPNE08::Coupon code is not recognized
     */
    @Test
    public void testUpdateInvalidCodes(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE08{0}Coupon code is not recognized";
            String expectedErrorMessageDetail = "Coupon code {0} is invalid{1}{0}";
            String cdrId = getPostResponse(jsonString);
            String key = "codes";
            String changedValue = "BLA-BLA";
            Response putResponse = getPutResponseAndAssert(jsonString, cdrId, key, changedValue, expectedStatusCode);
            assertErrorMessage(putResponse, expectedErrorMessage);

            assertErrorMessageDetail(putResponse, expectedErrorMessageDetail, changedValue);

    } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when user updates a existing Cdr and
     * codes specified in JSON payload is already expired.
     * Failure Code : 409
     * Coupon Error Header : CPNE02::Coupon validity has expired
     */
    @Test
    public void testUpdateExpiredCode(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE02{0}Coupon validity has expired";
            String expectedErrorMessageDetail = "Coupon code C3-D3 was valid from [15-12-15:00:00:00] to " +
                    "[18-12-15:00:00:00]{1}{0}|15-12-15:00:00:00|18-12-15:00:00:00";
            String cdrId = getPostResponse(jsonString);
            String key = "codes";
            String changedValue = "C3-D3";
            Response putResponse = getPutResponseAndAssert(jsonString, cdrId, key, changedValue, expectedStatusCode);

            assertErrorMessage(putResponse, expectedErrorMessage);

            assertErrorMessageDetail(putResponse, expectedErrorMessageDetail, changedValue);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }

    }

    /**
     * This test case tests whether failure is returned when user updates a existing Cdr and
     * codes specified in JSON payload is deactivated already.
     * Failure Code : 409
     * Coupon Error Header : CPNE02::Coupon validity has expired
     */
    @Test
    public void testUpdateInactiveCouponCode(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE06{0}Coupon is deactivated";
            String expectedErrorMessageDetail = "Coupon code {0} is deactivated{1}{0}";
            String cdrId = getPostResponse(jsonString);

            String key = "codes";
            String changedValue = "MMMM";
            Response putResponse = getPutResponseAndAssert(jsonString, cdrId, key, changedValue, expectedStatusCode);

            assertErrorMessage(putResponse, expectedErrorMessage);

            assertErrorMessageDetail(putResponse, expectedErrorMessageDetail, changedValue);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when user updates a existing Cdr and
     * codes specified in JSON payload related with coupon is deactivated already.
     * Failure Code : 409
     * Coupon Error Header : CPNE02::Coupon validity has expired
     */
    @Test
    public void testUpdateInactiveCoupon(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE06{0}Coupon is deactivated";
            String expectedErrorMessageDetail = "Coupon code {0} is deactivated{1}{0}";
            String cdrId = getPostResponse(jsonString);

            String key = "codes";
            String changedValue = "NNNN";
            Response putResponse = getPutResponseAndAssert(jsonString, cdrId, key, changedValue, expectedStatusCode);

            assertErrorMessage(putResponse, expectedErrorMessage);

            assertErrorMessageDetail(putResponse, expectedErrorMessageDetail, changedValue);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when user updates a existing Cdr and
     * code specified in Json Payload is Multiple Codes for same coupon which is not allowed.
     * Failure Code : 409
     * Error Header Code : CPNE22::Multiple codes of the same coupon are not allowed.
     */
    @Test
    public void testUpdateMultipleCodesForSameCoupon(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE22{0}Multiple codes of the same coupon are not allowed";
            String cdrId = getPostResponse(jsonString);
            WebTarget updateTarget = client.target(baseURI + "/cdr/" + cdrId);
            JSONObject jsonPutObject = new JSONObject(jsonString);
            jsonPutObject.remove("requesterId");
            jsonPutObject.remove("beneficiaryId");
            jsonPutObject.remove("patientBrandId");
            jsonPutObject.remove("areaId");
            jsonPutObject.remove("referrerId");
            jsonPutObject.remove("contextType");
            jsonPutObject.remove("sourceName");
            jsonPutObject.remove("withinSubscription");
            JSONArray jsonCodesPutArray = jsonPutObject.getJSONArray("codes");
            jsonCodesPutArray.put(1, "C4-D4");
            Object firstCode = jsonCodesPutArray.get(0);
            Object secondCode = jsonCodesPutArray.get(1);
            Response putResponse = updateTarget.request().put(Entity.entity(jsonPutObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.CONFLICT.getStatusCode(), putResponse.getStatus());

            assertErrorMessage(putResponse,expectedErrorMessage);

            String actualErrorDetail = (String) putResponse.getHeaders().get(COUPON_APP_DETAIL_X_HEADER).get(0);
            String expectedErrorDetail = MessageFormat.format("Coupon codes {0} and {1} belong to same coupon{2}{0}|{1}",
                    firstCode, secondCode, ERROR_MSG_PART_DELIMITER);

            assertEquals(expectedErrorDetail, actualErrorDetail);

        } catch (Exception e) {
            e.printStackTrace();
           fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when user updates a existing Cdr and
     * code specified in Json Payload is Multiple Exclusive Codes which is not allowed.
     * Failure Code : 409
     * Error Header Code : CPNE03::Multiple exclusive coupons cannot be applied
     */
    @Test
    public void testUpdateAddMultipleExclusiveCodes(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");
        try {
            String expectedErrorMessage = "CPNE03{0}Multiple exclusive coupons cannot be applied";
            String cdrId = getPostResponse(jsonString);
            WebTarget updateTarget = client.target(baseURI + "/cdr/" + cdrId);
            JSONObject jsonPutObject = new JSONObject(jsonString);
            jsonPutObject.remove("requesterId");
            jsonPutObject.remove("beneficiaryId");
            jsonPutObject.remove("patientBrandId");
            jsonPutObject.remove("areaId");
            jsonPutObject.remove("referrerId");
            jsonPutObject.remove("contextType");
            jsonPutObject.remove("sourceName");
            jsonPutObject.remove("withinSubscription");
            JSONArray jsonCodesPutArray = jsonPutObject.getJSONArray("codes");
            jsonCodesPutArray.put(1, "A1-B1");
            Object firstCode = jsonCodesPutArray.get(0);
            Object secondCode = jsonCodesPutArray.get(1);

            Response putResponse = updateTarget.request().put(Entity.entity(jsonPutObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.CONFLICT.getStatusCode(), putResponse.getStatus());

            assertErrorMessage(putResponse, expectedErrorMessage);

            String actualErrorDetail = (String) putResponse.getHeaders().get(COUPON_APP_DETAIL_X_HEADER).get(0);
            String expectedErrorDetail = MessageFormat.format("Coupon code {1} is exclusive and in conflict with {0}{2}{1}|{0}",
                    firstCode, secondCode, ERROR_MSG_PART_DELIMITER);

            assertEquals(expectedErrorDetail, actualErrorDetail);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * Thus test case tests whether failure is returned when user updates an existing Cdr and
     * and ProductId specified in JSON payload is invalid.
     * Failure Code : 409
     * Coupon Error Header : CPNE10::Product code is not recognized
     */
    @Test
    public void testUpdateInvalidProductId(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");
        try {
            String expectedErrorMessage = "CPNE10{0}Product code is not recognized";
            String expectedErrorMessgaeDetail = "Product code {0} of type SERVICE is invalid{1}{0}|SERVICE";
            String cdrId = getPostResponse(jsonString);
            WebTarget updateTarget = client.target(baseURI + "/cdr/" + cdrId);
            JSONObject jsonPutObject = new JSONObject(jsonString);
            jsonPutObject.remove("requesterId");
            jsonPutObject.remove("beneficiaryId");
            jsonPutObject.remove("patientBrandId");
            jsonPutObject.remove("areaId");
            jsonPutObject.remove("referrerId");
            jsonPutObject.remove("contextType");
            jsonPutObject.remove("sourceName");
            jsonPutObject.remove("withinSubscription");
            JSONArray jsonProductsArray = jsonPutObject.getJSONArray("products");
            String productId = null;

            for (int i = 0; i < jsonProductsArray.length(); i++) {
                JSONObject productJsonObj = jsonProductsArray.getJSONObject(i);
                productJsonObj.put("id", 11);
                productId = productJsonObj.getString("id");
            }
            Response putResponse = updateTarget.request().put(Entity.entity(jsonPutObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.CONFLICT.getStatusCode(), putResponse.getStatus());

           assertErrorMessage(putResponse, expectedErrorMessage);

            assertErrorMessageDetail(putResponse, expectedErrorMessgaeDetail, productId);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * Thus test case tests whether failure is returned when user updates an existing Cdr and
     * and ProductType specified in JSON payload is Mismatched.
     * Failure Code : 409
     * Coupon Error Header : CPNE12::Coupon is not applicable on a specified product
     */
    @Test
    public void testUpdateMismatchedProductType(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE12{0}Coupon is not applicable on a specified product";
            String cdrId = getPostResponse(jsonString);
            WebTarget updateTarget = client.target(baseURI + "/cdr/" + cdrId);
            JSONObject jsonPutObject = new JSONObject(jsonString);
            jsonPutObject.remove("requesterId");
            jsonPutObject.remove("beneficiaryId");
            jsonPutObject.remove("patientBrandId");
            jsonPutObject.remove("areaId");
            jsonPutObject.remove("referrerId");
            jsonPutObject.remove("contextType");
            jsonPutObject.remove("sourceName");
            jsonPutObject.remove("withinSubscription");
            JSONArray jsonProductsArray = jsonPutObject.getJSONArray("products");
            Object productId = null;

            for (int i = 0; i < jsonProductsArray.length(); i++) {
                JSONObject productJsonObj = jsonProductsArray.getJSONObject(i);
                productJsonObj.put("productType", "PACKAGE");
                productId = productJsonObj.getString("id");
            }
            Response putResponse = updateTarget.request().put(Entity.entity(jsonPutObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.CONFLICT.getStatusCode(), putResponse.getStatus());

            assertErrorMessage(putResponse, expectedErrorMessage);

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray codesArray = jsonObject.getJSONArray("codes");
            Object couponCode = codesArray.get(0);
            String actualErrorDetail = (String) putResponse.getHeaders().get(COUPON_APP_DETAIL_X_HEADER).get(0);
            String expectedErrorDetail = MessageFormat.format("Coupon code {0} is not applicable on the product {1} of type PACKAGE{2}{0}|{1}|PACKAGE",
                    couponCode, productId, ERROR_MSG_PART_DELIMITER);

            assertEquals(expectedErrorDetail, actualErrorDetail);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when user updates a existing cdr and
     * code specified in Json Payload in invalid mapped with that existing product.
     * Failure Code : 409
     * Error Header Code : CPNE12::Coupon is not applicable on a specified product.
     */
    @Test
    public void testUpdateNonGlobalCouponWithInvalidProductMapping(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");
        try {
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE12{0}Coupon is not applicable on a specified product";
            String  expectedErrorMessageDetail = "Coupon code {0} is not applicable on the product 1 of type SERVICE{1}{0}|1|SERVICE";
            String cdrId = getPostResponse(jsonString);

            String key = "codes";
            String changedValue = "C5-D5";
            Response putResponse = getPutResponseAndAssert(jsonString, cdrId,key,changedValue, expectedStatusCode);

            assertErrorMessage(putResponse, expectedErrorMessage);

            assertErrorMessageDetail(putResponse, expectedErrorMessageDetail, changedValue);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when user updates a existing cdr and
     * code specified in Json payload is of staff type. And requesterId of customer type Coupon.
     * Failure Code : 409
     * Error Header Code : CPNE19::Coupon actor type is invalid.
     */
    @Test
    public void testUpdateMismatchedCouponActorType(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE19{0}Coupon actor type is invalid";
            String expectedErrorMessageDetail = "Requester id {0} of actor type CUSTOMER cannot apply coupon code of type STAFF{1}{0}";
            String cdrId = getPostResponse(jsonString);
            String key = "codes";
            String changedValue = "C1-D2";
            Response putResponse = getPutResponseAndAssert(jsonString, cdrId, key, changedValue, expectedStatusCode);

            assertErrorMessage(putResponse, expectedErrorMessage);

            JSONObject jsonObject = new JSONObject(jsonString);
            String requesterId = jsonObject.getString("requesterId");

            assertErrorMessageDetail(putResponse, expectedErrorMessageDetail, requesterId);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when user tries to update an existing CDR
     * total cost specified in JSON payload is out of range value for that coupon.
     * Failure Code : 409
     * Coupon Header Error : CPNE04::Transaction value is out of valid range.
     */
    @Test
    public void testUpdateTransactionValueOutOfRange(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE04{0}Transaction value is out of valid range";
            String expectedErrorMessageDetail = "Transaction value is out of range for coupon code {0}. " +
                    "The range is from 100 to 1,000{1}{0}|100|1,000";
            String cdrId = getPostResponse(jsonString);
            WebTarget updateTarget = client.target(baseURI + "/cdr/" + cdrId);
            JSONObject jsonPutObject = new JSONObject(jsonString);
            jsonPutObject.remove("requesterId");
            jsonPutObject.remove("beneficiaryId");
            jsonPutObject.remove("patientBrandId");
            jsonPutObject.remove("areaId");
            jsonPutObject.remove("referrerId");
            jsonPutObject.remove("contextType");
            jsonPutObject.remove("sourceName");
            jsonPutObject.remove("withinSubscription");
            jsonPutObject.put("totalCost", 1500);
            Response putResponse = updateTarget.request().put(Entity.entity(jsonPutObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.CONFLICT.getStatusCode(), putResponse.getStatus());

            assertErrorMessage(putResponse, expectedErrorMessage);

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray codesArray = jsonObject.getJSONArray("codes");
            String code = codesArray.get(0).toString();
            assertErrorMessageDetail(putResponse, expectedErrorMessageDetail, code);

    } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when user tries to updates an existing CDR and
     * Coupon specified in JSON update payload is of type One Time and it is already used earlier so
     * it can't be user again.
     * Failure Code : 409
     * Coupon Header Error  : CPNE21::Coupon applicability count exceeded
     */
    @Test
    public void testUpdateCdrForOneTypeCoupon(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");
        try {
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE21{0}Coupon applicability count exceeded";
            String expectedErrorMessageDetail = "The maximum applicable count 1 has exhausted for this coupon{1}1|{0}";
            String key = "codes";
            String changedValue = "A6-B6";
            String cdrId = updateJsonKeyAndPost(jsonString, key, changedValue);

            WebTarget putTarget = client.target(baseURI + "/cdr/" + cdrId + "/apply?clientContextId=1");
            Response putResponse = putTarget.request().method("PUT");

            assertEquals(Response.Status.OK.getStatusCode(), putResponse.getStatus());

            putResponse.close();

            changedValue = "C1-D1";
            cdrId = updateJsonKeyAndPost(jsonString, key, changedValue);

            changedValue = "A6-B6";
            Response rePutResponse = getPutResponseAndAssert(jsonString, cdrId, key, changedValue, expectedStatusCode);

            assertErrorMessage(rePutResponse, expectedErrorMessage);

            assertErrorMessageDetail(rePutResponse, expectedErrorMessageDetail, changedValue);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when user tries to updates an existing CDR and
     * Coupon specified in JSON update payload is of type one Time Per User and it is already used earlier
     * for that user so it can't be user again.
     * Failure Code : 409
     * Coupon Header Error  : CPNE21::Coupon applicability count exceeded
     */
    @Test
    public void testUpdateForOneTimePerUserCoupon(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");
        try {
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE21{0}Coupon applicability count exceeded";
            String expectedErrorMessageDetail = "The maximum applicable count 1 has exhausted for this coupon{1}1|{0}";
            String key = "codes";
            String changedValue = "A7-B7";
            String cdrId = updateJsonKeyAndPost(jsonString, key, changedValue);

            WebTarget putTarget = client.target(baseURI + "/cdr/" + cdrId + "/apply?clientContextId=1");
            Response putResponse = putTarget.request().method("PUT");

            assertEquals(Response.Status.OK.getStatusCode(), putResponse.getStatus());
            putResponse.close();

            changedValue = "C1-D1";
            cdrId = updateJsonKeyAndPost(jsonString, key, changedValue);

            changedValue = "A7-B7";
            Response rePutResponse = getPutResponseAndAssert(jsonString, cdrId, key, changedValue, expectedStatusCode);

            assertErrorMessage(rePutResponse, expectedErrorMessage);

            assertErrorMessageDetail(rePutResponse, expectedErrorMessageDetail, changedValue);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when user tries to update an existing CDR and
     * coupon code specified in JSON payload is of type One Time Per User Fifo and this coupon already
     * discount for its upper limit so this time it won't be requested.
     * Failure Code : 409
     * Coupon Header Error : CPNE21::Coupon applicability count exceeded
      */
    @Test
    public void testUpdateCdrOneTimePerUserFifoCoupon(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE21{0}Coupon applicability count exceeded";
            String expectedErrorMessageDetail = "The maximum applicable count 2 has exhausted for this coupon{1}2|{0}";
            String key = "codes";
            String changedValue = "A8-B8";
            String cdrId = updateJsonKeyAndPost(jsonString, key, changedValue);

            WebTarget firstPutTarget = client.target(baseURI + "/cdr/" + cdrId + "/apply?clientContextId=1");
            Response firstPutResponse = firstPutTarget.request().method("PUT");

            assertEquals(Response.Status.OK.getStatusCode(), firstPutResponse.getStatus());

            firstPutResponse.close();

            JSONObject postJsonObj = new JSONObject(jsonString);
            JSONArray jsonCodesArray = postJsonObj.getJSONArray("codes");
            jsonCodesArray.put(0, "A8-B8");
            JSONObject secondPostJsonObj = new JSONObject(postJsonObj.toString()); // reusing JSON String from before
            secondPostJsonObj.put("requesterId", 2);
            secondPostJsonObj.put("beneficiaryId", 2);
            WebTarget secondPostTarget = client.target(baseURI + "/cdr");
            Response secondPostResponse = secondPostTarget.request().post(Entity.entity(secondPostJsonObj.toString(), MediaType.APPLICATION_JSON_TYPE));
            JSONObject secondPostResponseJsonObj = new JSONObject(secondPostResponse.readEntity(String.class));
            String secondCdrId = secondPostResponseJsonObj.get("cdrId").toString();

            assertEquals(Response.Status.OK.getStatusCode(), secondPostResponse.getStatus());

            secondPostResponse.close();

            WebTarget secondPutTarget = client.target(baseURI + "/cdr/" + secondCdrId + "/apply?clientContextId=2");
            Response secondPutResponse = secondPutTarget.request().method("PUT");

            assertEquals(Response.Status.OK.getStatusCode(), secondPutResponse.getStatus());

            secondPutResponse.close();

            changedValue = "C1-D1";
            cdrId = updateJsonKeyAndPost(jsonString, key, changedValue);

            changedValue = "A8-B8";
            Response rePutResponse = getPutResponseAndAssert(jsonString, cdrId, key, changedValue, expectedStatusCode);

            assertErrorMessage(rePutResponse, expectedErrorMessage);

            assertErrorMessageDetail(rePutResponse, expectedErrorMessageDetail, changedValue);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned or not when user tries to update a existing
     * CDR and coupon code specified in JSON payload is Nth Time with false Nth Time Recurring.
     * The update request is not within Nth Time Limit
     * Failure Code : 409
     * Coupon Error Header : CPNE23::Discount cannot be applied as product is not being purchased nth time
     */
    @Test
    public void testUpdateCdrNthTimeWithFalseRecurring(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE23{0}Discount cannot be applied as product is not being purchased nth time";
            String expectedErrorMessageDetail = "This coupon having nth-time as 3 and recurring as false is not " +
                    "applicable for purchase count span: [4, 5]{0}3|false|[4, 5]";
            String key = "codes";
            String changedValue = "C1-D1";
            String cdrId = updateJsonKeyAndPost(jsonString, key, changedValue);

            changedValue = "X1-Y2";
            Response putResponse = getPutResponseAndAssert(jsonString, cdrId, key, changedValue, expectedStatusCode);

            assertErrorMessage(putResponse, expectedErrorMessage);

            assertErrorMessageDetail(putResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned or not when user tries to update a existing
     * CDR and coupon code specified in JSON payload is Nth Time with true Nth Time Recurring.
     * The update request is not within Nth Time Limit
     * Failure Code : 409
     * Coupon Error Header : CPNE23::Discount cannot be applied as product is not being purchased nth time
     */
    @Test
    public void testUpdateCdrNthTimeWithTrueRecurring(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE23{0}Discount cannot be applied as product is not being purchased nth time";
            String expectedErrorMessageDetail = "This coupon having nth-time as 3 and recurring as true is not " +
                    "applicable for purchase count span: [4, 5]{0}3|true|[4, 5]";

            String key = "codes";
            String changedValue = "C1-D1";
            String cdrId = updateJsonKeyAndPost(jsonString, key, changedValue);

            changedValue = "X1-Y3";
            Response putResponse = getPutResponseAndAssert(jsonString, cdrId, key, changedValue, expectedStatusCode);

            assertErrorMessage(putResponse, expectedErrorMessage);

            assertErrorMessageDetail(putResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned or not when user tries to update a existing
     * CDR and coupon code specified in JSON payload is NTh Time Per Subscription which is outside
     * Subscription. So update request won't be applicable.
     * Failure Code : 409
     * Coupon Error Header : CPNE25::Coupon discount request is not within subscription
     */
    @Test
    public void testUpdateCdrNpsOutsideSubscription(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE25::Coupon discount request is not within subscription";
            String expectedErrorMessageDetail = "Not applicable as given cdr context {0} is not within subscription{1}{0}";
            WebTarget postTarget = client.target(baseURI + "/cdr");
            JSONObject jsonObject = new JSONObject(jsonString);
            String value = jsonObject.getString("contextType");
            jsonObject.put("withinSubscription", "false");
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
            JSONObject postResponseJsonObj = new JSONObject(postResponse.readEntity(String.class));
            String cdrId = postResponseJsonObj.get("cdrId").toString();

            assertEquals(Response.Status.OK.getStatusCode(), postResponse.getStatus());

            String key = "codes";
            String changedValue = "A1-B1";
            Response putResponse = getPutResponseAndAssert(jsonString, cdrId, key, changedValue, expectedStatusCode);

            assertErrorMessage(putResponse, expectedErrorMessage);

            assertErrorMessageDetail(putResponse, expectedErrorMessageDetail, value);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned or not when user tries to update a existing
     * CDR and coupon code specified in JSON payload is NTh Time Per Subscription within Subscription
     * with false Nth Time Recurring. The update request is not within Nth Time Limit.
     * Failure Code : 409
     * Coupon Error Header : CPNE23{0}Discount cannot be applied as product is not being purchased nth time
     */
    @Test
    public void testUpdateCdrNpsFalseRecurring(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE23{0}Discount cannot be applied as product is not being purchased nth time";
            String expectedErrorMessageDetail = "This coupon having nth-time as 3 and recurring as false is not " +
                    "applicable for purchase count span: [4, 5]{0}3|false|[4, 5]";
            String key = "codes";
            String changedValue = "C1-D1";
            String cdrId = updateJsonKeyAndPost(jsonString, key, changedValue);

            changedValue = "A2-B2";
            Response putResponse = getPutResponseAndAssert(jsonString, cdrId, key, changedValue, expectedStatusCode);

            assertErrorMessage(putResponse, expectedErrorMessage);

            assertErrorMessageDetail(putResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned or not when user tries to update a existing
     * CDR and coupon code specified in JSON payload is NTh Time Per Subscription within Subscription
     * with true Nth Time Recurring. The update request is not within Nth Time Limit.
     * Failure Code : 409
     * Coupon Error Header : CPNE23{0}Discount cannot be applied as product is not being purchased nth time
     */
    @Test
    public void testUpdateCdrNpsTrueRecurring(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE23{0}Discount cannot be applied as product is not being purchased nth time";
            String expectedErrorMessageDetail = "This coupon having nth-time as 3 and recurring as true is not " +
                    "applicable for purchase count span: [4, 5]{0}3|true|[4, 5]";

            String key = "codes";
            String changedValue = "C1-D1";
            String cdrId = updateJsonKeyAndPost(jsonString, key, changedValue);

            changedValue = "A3-B3";
            Response putResponse = getPutResponseAndAssert(jsonString, cdrId, key, changedValue, expectedStatusCode);

            assertErrorMessage(putResponse, expectedErrorMessage);

            assertErrorMessageDetail(putResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned or not when user tries to update a existing
     * CDR and coupon code specified in JSON payload is NTh Time Per AB Subscription which is outside
     * Subscription. So update request won't be applicable.
     * Failure Code : 409
     * Coupon Error Header : CPNE25::Coupon discount request is not within subscription
     */
    @Test
    public void testUpdateCdrNabpsOutsideSubscription(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE25::Coupon discount request is not within subscription";
            String expectedErrorMessageDetail = "Not applicable as given cdr context {0} is not within subscription{1}{0}";
            String key = "codes";
            String changedValue = "A5-B5";

            WebTarget postTarget = client.target(baseURI + "/cdr");
            JSONObject jsonObject = new JSONObject(jsonString);
            String value = jsonObject.getString("contextType");
            jsonObject.put("withinSubscription", "false");
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
            JSONObject postResponseJsonObj = new JSONObject(postResponse.readEntity(String.class));
            String cdrId = postResponseJsonObj.get("cdrId").toString();

            assertEquals(Response.Status.OK.getStatusCode(), postResponse.getStatus());

            Response putResponse = getPutResponseAndAssert(jsonString, cdrId, key, changedValue, expectedStatusCode);

            assertErrorMessage(putResponse, expectedErrorMessage);

            assertErrorMessageDetail(putResponse, expectedErrorMessageDetail, value);
    } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned or not when user tries to update a existing
     * CDR and coupon code specified in JSON payload is NTh Time Per AB Subscription within Subscription.
     * Update request is not within Limit of Nth Time
     * Failure Code : 409
     * Coupon Error Header : CPNE23{0}Discount cannot be applied as product is not being purchased nth time
     */
    @Test
    public void testUpdateCdrNabpsWithinSubscription(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE23{0}Discount cannot be applied as product is not being purchased nth time";
            String expectedErrorMessageDetail = "This coupon having nth-time as 6 and recurring as false is not " +
                    "applicable for purchase count span: [4, 5]{0}6|false|[4, 5]";
            String key = "codes";
            String changedValue = "C1-D1";
            String cdrId = updateJsonKeyAndPost(jsonString, key, changedValue);

            changedValue = "A5-B5";
            Response putResponse = getPutResponseAndAssert(jsonString, cdrId, key, changedValue, expectedStatusCode);

            assertErrorMessage(putResponse, expectedErrorMessage);

            assertErrorMessageDetail(putResponse, expectedErrorMessageDetail, null);

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

    /**
     *
     * @param jsonString input JSON updating for a CDR
     * @param cdrId returned cdrId after post
     * @param key key to matched with json array
     * @param changedValue changed value associated with key
     * @param statusToBeAsserted expected status code
     * @return HTTP response for PUT request
     * @throws JSONException
     */
    private Response getPutResponseAndAssert(String jsonString, String cdrId, String key, String changedValue, int statusToBeAsserted) throws JSONException{
        WebTarget updateTarget = client.target(baseURI + "/cdr/" + cdrId);
        JSONObject jsonPutObject = new JSONObject(jsonString);
        jsonPutObject.remove("requesterId");
        jsonPutObject.remove("beneficiaryId");
        jsonPutObject.remove("patientBrandId");
        jsonPutObject.remove("areaId");
        jsonPutObject.remove("referrerId");
        jsonPutObject.remove("contextType");
        jsonPutObject.remove("sourceName");
        jsonPutObject.remove("withinSubscription");

        JSONArray jsonCodesArray = jsonPutObject.getJSONArray(key);
        jsonCodesArray.put(0, changedValue);
        Response putResponse = updateTarget.request().put(Entity.entity(jsonPutObject.toString(), MediaType.APPLICATION_JSON_TYPE));

        assertEquals(statusToBeAsserted, putResponse.getStatus());
        return  putResponse;
    }

    /**
     * This method returns CdrId for Post response given a JSON
     * @param jsonString input JSON requesting for a CDR
     * @return cdrId for post response
     * @throws JSONException
     */
    private String getPostResponse(String jsonString) throws JSONException{
        WebTarget postTarget = client.target(baseURI + "/cdr");
        JSONObject jsonObject = new JSONObject(jsonString);
        Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
        JSONObject postResponseJsonObj = new JSONObject(postResponse.readEntity(String.class));
        String cdrId = postResponseJsonObj.get("cdrId").toString();
        assertEquals(Response.Status.OK.getStatusCode(), postResponse.getStatus());
        postResponse.close();
        return cdrId;
    }

    /**
     * This method returns CdrId for a Post response given a JSON and key/value.
     * @param jsonString input JSON requesting for a CDR
     * @param key input parameter key for the changed JSON value
     * @param value changed parameter value
     * @return cdrId for postResponse
     * @throws JSONException
     */
    private String updateJsonKeyAndPost(String jsonString, String key, String value) throws JSONException{
        WebTarget postTarget = client.target(baseURI + "/cdr");
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonCodesArray = jsonObject.getJSONArray(key);
        jsonCodesArray.put(0, value);
        Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
        JSONObject postResponseJsonObj = new JSONObject(postResponse.readEntity(String.class));
        String cdrId = postResponseJsonObj.get("cdrId").toString();

        assertEquals(Response.Status.OK.getStatusCode(), postResponse.getStatus());

        postResponse.close();
        return cdrId;
    }

    /**
     * This method asserts Header ErrorMessage
     * @param response object
     * @param expectedErrorMessage expected error message
     */
    private void assertErrorMessage(Response response, String expectedErrorMessage) {
        String actualErrorMsg = (String) response.getHeaders().get(COUPON_APP_ERROR_X_HEADER).get(0);
        String expectedErrorMsg = MessageFormat.format(expectedErrorMessage, ERROR_MSG_PART_DELIMITER);

        assertEquals(expectedErrorMsg, actualErrorMsg);
    }

    /**
     * This method asserts Header ErrorMessageDetail
     * @param postResponse post response object
     * @param expectedErrorMessageDetail expected error message detail
     * @param changedValue changed parameter value
     */
    private void assertErrorMessageDetail(Response postResponse, String expectedErrorMessageDetail, String changedValue) {
        String actualErrorDetail = (String) postResponse.getHeaders().get(COUPON_APP_DETAIL_X_HEADER).get(0);
        String expectedErrorDetail = null;
        if (changedValue == null) {
            expectedErrorDetail = MessageFormat.format(expectedErrorMessageDetail, ERROR_MSG_PART_DELIMITER);
        } else {
            expectedErrorDetail = MessageFormat.format(expectedErrorMessageDetail,
                    changedValue, ERROR_MSG_PART_DELIMITER);
        }
        assertEquals(expectedErrorDetail, actualErrorDetail);
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
