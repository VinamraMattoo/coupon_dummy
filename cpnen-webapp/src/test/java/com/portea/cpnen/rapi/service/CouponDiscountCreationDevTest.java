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
 * through its REST API interface. For CDR Creation Only.
 * To be able to execute these tests on localhost, add "127.0.0.1  coupons.localhost" (without quotes)
 * to hosts list in /etc/hosts file. Please note that the virtual host coupons.localhost must be
 * configured appropriately in the target WildFly server.
 */
public class CouponDiscountCreationDevTest {

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
     * This test case tests whether failure is returned when an invalid requester id is specified
     * in the JSON payload for CDR creation.
     * Failure Code : 409
     * Error Header Code : CPNE11::Consumer not recognized.
     */
    @Test
    public void testInvalidRequesterId() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "requesterId";
            String changedValue = "10";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE11{0}Consumer not recognized";
            String expectedErrorMessageDetail = "Consumer id {0} is invalid{1}{0}";

            Response postResponse = updateJsonKeyAndPostAndAssertStatus(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, changedValue);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an invalid beneficiary id is specified
     * in the JSON payload for CDR creation.
     * Failure Code : 409
     * Error Header Code : CPNE11::Consumer not recognized.
     */
    @Test
    public void testInvalidBeneficiaryId() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "beneficiaryId";
            String changedValue = "10";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE11{0}Consumer not recognized";
            String expectedErrorMessageDetail = "Consumer id {0} is invalid{1}{0}";

            Response postResponse = updateJsonKeyAndPostAndAssertStatus(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, changedValue);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an invalid coupon code is specified
     * in the JSON payload for CDR creation.
     * Here 'PPPP' is a invalid Coupon Code.
     * Failure Code : 409
     * Error Header Code : CPNE08::Coupon code is not recognized.
     */
    @Test
    public void testInvalidCouponCode() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "codes";
            String changedValue = "PPPP";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE08{0}Coupon code is not recognized";
            String expectedErrorMessageDetail = "Coupon code {0} is invalid{1}{0}";

            Response postResponse = updateJsonArrayAndPostAndAssertStatus(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, changedValue);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when a valid coupon code with expired
     * coupon validity is specified in the JSON payload for CDR creation.
     * Failure Code : 409
     * Error Header Code : CPNE06::Coupon is deactivated.
     */
    @Test
    public void testCouponCodeDeactivated() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "codes";
            String changedValue = "MMMM";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE06{0}Coupon is deactivated";
            String expectedErrorMessageDetail = "Coupon code {0} is deactivated{1}{0}";

            Response postResponse = updateJsonArrayAndPostAndAssertStatus(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, changedValue);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when a activated valid coupon code is
     * specified in the JSON payload for CDR creation but coupon related with the coupon code is
     * deactivated.
     * Failure Code : 409
     * Error Header Code : CPNE06::Coupon is deactivated.
     */
    @Test
    public void testCouponDeactivated() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "codes";
            String changedValue = "NNNN";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE06{0}Coupon is deactivated";
            String expectedErrorMessageDetail = "Coupon code {0} is deactivated{1}{0}";

            Response postResponse = updateJsonArrayAndPostAndAssertStatus(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, changedValue);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an invalid context type is specified
     * in the JSON payload for CDR creation.
     * Failure Code : 409
     * Error Header Code : CPNE16::Coupon Discount Request is not.
     */
    @Test
    public void testMismatchedContextType() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "contextType";
            String changedValue = "SUBSCRIPTION";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE16{0}Coupon Discount Request is not applicable for the context type";
            String expectedErrorMessageDetail = "Coupon Discount Request of type {0} is not applicable for coupon context Type " +
                    "APPOINTMENT{1}{0}|APPOINTMENT";

            Response postResponse = updateJsonKeyAndPostAndAssertStatus(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, changedValue);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an invalid product id is specified
     * in the JSON payload for CDR creation.
     * Failure Code : 409
     * Error Header Code : CPNE10::Product code is not recognized.
     */
    @Test
    public void testInvalidProductId() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String errorMessage = "CPNE10{0}Product code is not recognized";
            String errorMessageDetail = "Product code {0} of type SERVICE is invalid{1}{0}|SERVICE";
            WebTarget postTarget = client.target(baseURI + "/cdr");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonProductsArray = jsonObject.getJSONArray("products");
            Object productId = 0;

            for (int i = 0; i < jsonProductsArray.length(); i++) {
                JSONObject productJsonObj = jsonProductsArray.getJSONObject(i);
                productJsonObj.put("id", 11);
                productId = productJsonObj.getString("id");
            }
            String value = productId.toString();
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());

            assertErrorMessage(postResponse, errorMessage);

            assertErrorMessageDetail(postResponse, errorMessageDetail, value);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an invalid product type is specified
     * in the JSON payload for CDR creation.
     * Failure Code : 409
     * Error Header Code : CPNE12::Coupon is not applicable on a specified product.
     */
    @Test
    public void testMismatchedProductType() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String errorMessage = "CPNE12{0}Coupon is not applicable on a specified product";
            WebTarget postTarget = client.target(baseURI + "/cdr");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonProductsArray = jsonObject.getJSONArray("products");
            Object productId = null;

            for (int i = 0; i < jsonProductsArray.length(); i++) {
                JSONObject productJsonObj = jsonProductsArray.getJSONObject(i);
                productJsonObj.put("productType", "PACKAGE");
                productId = productJsonObj.getString("id");
            }

            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());

            assertErrorMessage(postResponse, errorMessage);

            JSONArray codesArray = jsonObject.getJSONArray("codes");
            Object couponCode = codesArray.get(0);
            String actualErrorDetail = (String) postResponse.getHeaders().get(COUPON_APP_DETAIL_X_HEADER).get(0);
            String expectedErrorDetail = MessageFormat.format("Coupon code {0} is not applicable on the product {1} of type PACKAGE{2}{0}|{1}|PACKAGE",
                    couponCode, productId, ERROR_MSG_PART_DELIMITER);

            assertEquals(expectedErrorDetail, actualErrorDetail);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an invalid brand id is specified in the
     * JSON payload for CDR creation.
     * Failure Code : 409
     * Error Header Code : CPNE14::Coupon is not applicable on a specified brand.
     */
    @Test
    public void testInvalidBrandId() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "patientBrandId";
            String changedValue = "72";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE14::Coupon is not applicable on a specified brand";
            String expectedErrorMessageDetail = "Coupon code C1-D1 is not applicable on the brand {0}{1}C1-D1|{0}";

            Response postResponse = updateJsonKeyAndPostAndAssertStatus(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, changedValue);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when a valid brand id is specified in the
     * JSON payload but for that coupon no brand is mapped. i.e no coupon_brand mapping valid for
     * Non Global Coupon only.
     * Failure Code : 409
     * Error Header Code : CPNE14::Coupon is not applicable on a specified brand.
     */
    @Test
    public void testNonGlobalCouponWithoutBrandMapping() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String errorMessage = "CPNE14{0}Coupon is not applicable on a specified brand";
            WebTarget postTarget = client.target(baseURI + "/cdr");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonCodesArray = jsonObject.getJSONArray("codes");
            jsonCodesArray.put(0, "C1-D3");
            Object codesValue = jsonCodesArray.get(0);
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());

            assertErrorMessage(postResponse, errorMessage);

            Object productBrand = jsonObject.getString("patientBrandId");
            String actualErrorDetail = (String) postResponse.getHeaders().get(COUPON_APP_DETAIL_X_HEADER).get(0);
            String expectedErrorDetail = MessageFormat.format("Coupon code {0} is not applicable on the brand {1}{2}{0}|{1}",
                    codesValue, productBrand, ERROR_MSG_PART_DELIMITER);

            assertEquals(expectedErrorDetail, actualErrorDetail);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an invalid area mapping id is specified in the
     * JSON payload for CDR creation.
     * Failure Code : 409
     * Error Header Code : CPNE31::Coupon is not applicable on a specified area
     */
    @Test
    public void testInvalidCouponAreaMapping(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "areaId";
            String changedValue = "55";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE31::Coupon is not applicable on a specified area";
            String expectedErrorMessageDetail = "Coupon code C1-D1 is not applicable on the area {0}{1}C1-D1|{0}";

            Response postResponse = updateJsonKeyAndPostAndAssertStatus(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, changedValue);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an invalid referrer mapping id is specified in the
     * JSON payload for CDR creation.
     * Failure Code : 409
     * Error Header Code : CPNE31::Coupon is not applicable on a specified area
     */
    @Test
    public void testInvalidCouponReferrerMapping(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "referrerId";
            String changedValue = "55";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE32::Coupon is not applicable on a specified referrer source";
            String expectedErrorMessageDetail = "Coupon code C1-D1 is not applicable on the referrer source {0}{1}C1-D1|{0}";

            Response postResponse = updateJsonKeyAndPostAndAssertStatus(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, changedValue);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when Product Mapping for a Non Global
     * Coupon is Invalid for that requested ProductId which is requested in JSON payload.
     * Failure Code : 409
     * Error Header Code : CPNE12::Coupon is not applicable on a specified product.
     */
    @Test
    public void testNonGlobalCouponWithInvalidCouponProductMapping() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "codes";
            String changedValue = "C5-D5";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE12{0}Coupon is not applicable on a specified product";
            String expectedErrorMessageDetail = "Coupon code {0} is not applicable on the product 1 of type SERVICE{1}{0}|1|SERVICE";

            Response postResponse = updateJsonArrayAndPostAndAssertStatus(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, changedValue);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when coupon type is of staff type. And
     * a Type customer is requested for himself for a discount i.e RequesterId is of customer type
     * and BeneficiaryId is of also customer type. But requested is for a Staff type Coupon.
     * Failure Code : 409
     * Error Header Code : CPNE19::Coupon actor type is invalid.
     */
    @Test
    public void testMismatchedCouponActorType() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String errorMessage = "CPNE19{0}Coupon actor type is invalid";
            String errorMessageDetail = "Requester id {0} of actor type CUSTOMER cannot apply coupon code of type STAFF{1}{0}";
            WebTarget postTarget = client.target(baseURI + "/cdr");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonCodesArray = jsonObject.getJSONArray("codes");
            jsonCodesArray.put(0, "C1-D2");
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());

            assertErrorMessage(postResponse, errorMessage);
            String value = jsonObject.getString("requesterId");
            assertErrorMessageDetail(postResponse, errorMessageDetail, value);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when coupon code requested in JSON
     * payload is already expired i.e the coupon validity is expired. In this case C3-D3 is
     * a Expired Coupon.
     * Failure code : 409
     * Error Header Code : CPNE02::Coupon validity has expired.
     */
    @Test
    public void testCouponValidityExpired() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "codes";
            String changedValue = "C3-D3";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE02{0}Coupon validity has expired";
            String expectedErrorMessageDetail = "Coupon code C3-D3 was valid from [15-12-15:00:00:00] to " +
                    "[18-12-15:00:00:00]{1}{0}|15-12-15:00:00:00|18-12-15:00:00:00";

            Response postResponse = updateJsonArrayAndPostAndAssertStatus(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, changedValue);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when Multiple Codes for same coupon
     * is requested in JSON payload which is not allowed.
     * Failure Code : 409
     * Error Header Code : CPNE22::Multiple codes of the same coupon are not allowed.
     */
    @Test
    public void testMultipleCodeForSameCoupon() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String errorMessage = "CPNE22{0}Multiple codes of the same coupon are not allowed";
            WebTarget postTarget = client.target(baseURI + "/cdr");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonCodesArray = jsonObject.getJSONArray("codes");
            jsonCodesArray.put(1, "C4-D4");
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());

            assertErrorMessage(postResponse, errorMessage);

            Object firstCode = jsonCodesArray.get(0);
            Object secondCode = jsonCodesArray.get(1);
            String actualErrorDetail = (String) postResponse.getHeaders().get(COUPON_APP_DETAIL_X_HEADER).get(0);
            String expectedErrorDetail = MessageFormat.format("Coupon codes {0} and {1} belong to same coupon{2}{0}|{1}",
                    firstCode, secondCode, ERROR_MSG_PART_DELIMITER);

            assertEquals(expectedErrorDetail, actualErrorDetail);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when Two Exclusive coupon is requested
     * in CDR JSON payload. In this case C1-D1 and A1-B1 are two exclusive coupon codes.
     * Failure Code : 409
     * Error Header Code : CPNE03::Multiple exclusive coupons cannot be applied.
     */
    @Test
    public void testMultipleExclusiveCoupon() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String errorMessage = "CPNE03{0}Multiple exclusive coupons cannot be applied";
            WebTarget postTarget = client.target(baseURI + "/cdr");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonCodesArray = jsonObject.getJSONArray("codes");
            jsonCodesArray.put(1, "A1-B1");
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());

            assertErrorMessage(postResponse, errorMessage);

            Object firstCode = jsonCodesArray.get(0);
            Object secondCode = jsonCodesArray.get(1);
            String actualErrorDetail = (String) postResponse.getHeaders().get(COUPON_APP_DETAIL_X_HEADER).get(0);
            String expectedErrorDetail = MessageFormat.format("Coupon code {1} is exclusive and in conflict with {0}{2}{1}|{0}",
                    firstCode, secondCode, ERROR_MSG_PART_DELIMITER);

            assertEquals(expectedErrorDetail, actualErrorDetail);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when the TotalCost given in requested
     * JSON payload crosses the Max Transaction Amount for that Coupon.
     * Failure Code : 409
     * Error Header Code : CPNE04::Transaction value is out of valid range.
     */
    @Test
    public void testTransactionValueOutOfRange() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String errorMessage = "CPNE04{0}Transaction value is out of valid range";
            String errorMessageDetail = "Transaction value is out of range for coupon code {0}. " +
                    "The range is from 100 to 1,000{1}{0}|100|1,000";
            WebTarget postTarget = client.target(baseURI + "/cdr");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject changedJson = jsonObject.put("totalCost", 1100);
            Response postResponse = postTarget.request().post(Entity.entity(changedJson.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());

            assertErrorMessage(postResponse, errorMessage);

            JSONArray codesArray = jsonObject.getJSONArray("codes");
            Object code = codesArray.get(0);
            String value = code.toString();

            assertErrorMessageDetail(postResponse, errorMessageDetail, value);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when application_type for coupon is set to
     * NTH_TIME_PER_SUBSCRIPTION with declared nth_time but in JSON payload within subscription is
     * false.
     * <p>
     * Failure Code : 409
     * Error Header Code : CPNE23::Discount cannot be applied as product is not being purchased nth time
     */
    @Test
    public void testApplicationTypeNpsOutsideSubscription() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String errorMessage = "CPNE25{0}Coupon discount request is not within subscription";
            String errorMessageDetail = "Not applicable as given cdr context {0} is not within subscription{1}{0}";
            WebTarget postTarget = client.target(baseURI + "/cdr");
            JSONObject jsonObject = new JSONObject(jsonString);
            String value = jsonObject.getString("contextType");
            jsonObject.put("withinSubscription", "false");
            JSONArray jsonCodesArray = jsonObject.getJSONArray("codes");
            jsonCodesArray.put(0, "A1-B1");
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());

            assertErrorMessage(postResponse, errorMessage);

            assertErrorMessageDetail(postResponse, errorMessageDetail, value);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when application_type for coupon is set to
     * NTH_TIME_PER_SUBSCRIPTION and nth_time is set to a limit with false nth_time_recurring.
     * The user is try to request which is not in the limit of nth_time.
     * nth_time = 3  ; nth_time_recurring = false
     * In JSON payload PreviousPurchaseCount = 4 ; count = any(1,2,3...)
     * <p>
     * In JSON payload PreviousPurchaseCount = 1 ; Count = 1 expects failure
     * In JSON payload PreviousPurchaseCount = 2 ; Count = 2 expects true
     * In JSON payload PreviousPurchaseCount = 0 ; Count = 3 or above expects true
     * In JSON payload PreviousPurchaseCount = 0 ; Count = 2 or above expects failure
     * Failure Code : 409
     * Error Header Code : CPNE23::Discount cannot be applied as product is not being purchased nth time
     */
    @Test
    public void testApplicationTypeNpsWithFalseRecurring() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "codes";
            String changedValue = "A2-B2";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE23{0}Discount cannot be applied as product is not being purchased nth time";
            String expectedErrorMessageDetail = "This coupon having nth-time as 3 and recurring as false is not applicable for " +
                    "purchase count span: [4, 5]{0}3|false|[4, 5]";

            Response postResponse = updateJsonArrayAndPostAndAssertStatus(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when application type for a coupon is set to
     * NTH_TIME_PER_SUBSCRIPTION and nth_time with defined limit and nth_time_recurring is true.
     * User is try to request which is not in nth time limit.
     * Within Subscription = true
     * Nth_time = 3 ; nth_time_recurring = true
     * User gets discount for every 3rd, 6th, 9th... time.
     * PreviousPurchaseCount = 3 ; count = 2 expects failure
     * PreviousPurchaseCount = 3 ; count = 3 or above expects true
     * Failure Code :  409
     * Error Header Code : CPNE23::Discount cannot be applied as product is not being purchased nth time.
     */
    @Test
    public void testApplicationTypeNpsWithTrueRecurring() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "codes";
            String changedValue = "A3-B3";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE23{0}Discount cannot be applied as product is not being purchased nth time";
            String expectedErrorMessageDetail = "This coupon having nth-time as 3 and recurring as true is not applicable for " +
                    "purchase count span: [4, 5]{0}3|true|[4, 5]";

            Response postResponse = updateJsonArrayAndPostAndAssertStatus(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }

    }

    /**
     * This test case tests whether failure is returned when application_type for coupon is set to
     * NTH_TIME_AB_PER_SUBSCRIPTION with declared nth_time but in JSON payload within subscription is
     * false.
     * <p>
     * The expected failure code is 409
     * Error Header Code : CPNE25::Coupon discount request is not within subscription.
     */
    @Test
    public void testApplicationTypeNabpsOutsideSubscription() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String errorMessage = "CPNE25{0}Coupon discount request is not within subscription";
            String errorMessageDetail = "Not applicable as given cdr context {0} is not within subscription{1}{0}";
            WebTarget postTarget = client.target(baseURI + "/cdr");
            JSONObject jsonObject = new JSONObject(jsonString);
            String value = jsonObject.getString("contextType");
            jsonObject.put("withinSubscription", "false");
            JSONArray jsonCodeArray = jsonObject.getJSONArray("codes");
            jsonCodeArray.put(0, "A5-B5");
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.CONFLICT.getStatusCode(), postResponse.getStatus());

            assertErrorMessage(postResponse, errorMessage);

            assertErrorMessageDetail(postResponse, errorMessageDetail, value);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when application_type for coupon is set to
     * NTH_TIME_AB_PER_SUBSCRIPTION with declared nth_time within subscription as true.
     * Nth_time = 6
     * User gets discount for 6th, 7th, 8th... time.
     * PreviousPurchaseCount = 3 ; count = 2 expected failure i.e total nth_time is less then 6
     * PreviousPurchaseCount = 3 ; count = 3 or above expects true
     * i.e if NTH_TIME_AB_PER_SUBSCRIPTION > 6 always true.
     * Failure code : 409
     * Error Header Code : CPNE23::Discount cannot be applied as product is not being
     * purchased nth time/
     */
    @Test
    public void testApplicationTypeNabpsWithinSubscription() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "codes";
            String changedValue = "A5-B5";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE23{0}Discount cannot be applied as product is not being purchased nth time";
            String expectedErrorMessageDetail = "This coupon having nth-time as 6 and recurring as false is not applicable for purchase count span: [4, 5]{0}6|false|[4, 5]";

            Response postResponse = updateJsonArrayAndPostAndAssertStatus(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when application type for a coupon is set to
     * Nth_Time and nth_time attribute is also defined and nth_time_recurring is false.
     * User is try to request which is not in the nth time limit.
     * <p>
     * In JSON payload PreviousPurchaseCount = 0 ; Count = 2 or above expects failure
     * PreviousPurchaseCount = 4 ; count = 1,2,3... expects failure
     * PreviousPurchaseCount = 1 ; count = 1 expects failure
     * PreviousPurchaseCount = 2 ; count = 2 expects true
     * PreviousPurchaseCount = 0 ; Count = 3 or above expects true
     * Failure Code : 409
     * Error Header Code : CPNE23::discount cannot be applied as product is not being purchased nth time.
     */
    @Test
    public void testApplicationTypeNthTimeWithFalseRecurring() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "codes";
            String changedValue = "X1-Y2";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE23{0}Discount cannot be applied as product is not being purchased nth time";
            String expectedErrorMessageDetail = "This coupon having nth-time as 3 and recurring as false is not applicable for " +
                    "purchase count span: [4, 5]{0}3|false|[4, 5]";

            Response postResponse = updateJsonArrayAndPostAndAssertStatus(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when application type for a coupon is set to
     * NTH_TIME and nth_time with defined nth_time and nth_time_recurring is true.
     * User is trying to request which is not in nth time limit.
     * <p>
     * Nth_time = 3 ; nth_time_recurring = true
     * Coupon applicable for every 3rd, 6th, 9th... time.
     * PreviousPurchaseCount = 3 ; count = 2 expects failure
     * PreviousPurchaseCount = 3 ; count = 3 or above expects true
     * Failure Code : 409
     * Error Header Code : CPNE23::discount cannot be applied as product is not being purchased nth time.
     */
    @Test
    public void testApplicationTypeNthTimeWithTrueRecurring() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "codes";
            String changedValue = "X1-Y3";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE23{0}Discount cannot be applied as product is not being purchased nth time";
            String expectedErrorMessageDetail = "This coupon having nth-time as 3 and recurring as true is not applicable for " +
                    "purchase count span: [4, 5]{0}3|true|[4, 5]";

            Response postResponse = updateJsonArrayAndPostAndAssertStatus(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }

    }

    /**
     * This test case tests whether failure is returned when application type for a coupon is set to
     * ONE_TIME but previously this coupon code gets discount so this time request is not applicable
     * because it is ONE_TIME coupon.
     * <p>
     * The expected failure code is 409
     * Error Header Code : CPNE21::Coupon applicability count exceeded
     */
    @Test
    public void testApplicationTypeOneTime() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            WebTarget postTarget = client.target(baseURI + "/cdr");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonCodesArray = jsonObject.getJSONArray("codes");
            jsonCodesArray.put(0, "A6-B6");
            Object getCode = jsonCodesArray.get(0);
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
            JSONObject postResponseJsonObj = new JSONObject(postResponse.readEntity(String.class));
            String cdrId = postResponseJsonObj.get("cdrId").toString();

            assertEquals(Response.Status.OK.getStatusCode(), postResponse.getStatus());

            postResponse.close();

            WebTarget putTarget = client.target(baseURI + "/cdr/" + cdrId + "/apply?clientContextId=1");
            Response putResponse = putTarget.request().method("PUT");

            assertEquals(Response.Status.OK.getStatusCode(), putResponse.getStatus());

            putResponse.close();

            WebTarget rePostTarget = client.target(baseURI + "/cdr");
            Response rePostResponse = rePostTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.CONFLICT.getStatusCode(), rePostResponse.getStatus());

            String actualErrorMsg = (String) rePostResponse.getHeaders().get(COUPON_APP_ERROR_X_HEADER).get(0);
            String expectedErrorMsg = MessageFormat.format("CPNE21{0}Coupon applicability count exceeded",
                    ERROR_MSG_PART_DELIMITER);

            assertEquals(expectedErrorMsg, actualErrorMsg);

            String actualErrorDetail = (String) rePostResponse.getHeaders().get(COUPON_APP_DETAIL_X_HEADER).get(0);
            String expectedErrorDetail = MessageFormat.format(
                    "The maximum applicable count 1 has exhausted for this coupon{1}1|{0}",
                    getCode, ERROR_MSG_PART_DELIMITER);

            assertEquals(expectedErrorDetail, actualErrorDetail);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when application type for a coupon is set to
     * ONE_TIME_PER_USER but previously the user gets discount for requested coupon so this time the
     * request is not applicable because it is ONE_TIME_PER_USER coupon. The user can apply for the
     * coupon only once.
     * <p>
     * The expected failure code is 409
     * Error Header Code : CPNE21::Coupon applicability count exceeded
     */
    @Test
    public void testApplicationTypeOneTimePerUser() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            WebTarget postTarget = client.target(baseURI + "/cdr");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonCodesArray = jsonObject.getJSONArray("codes");
            jsonCodesArray.put(0, "A7-B7");
            Object getCode = jsonCodesArray.get(0);
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
            JSONObject postResponseJsonObj = new JSONObject(postResponse.readEntity(String.class));
            String cdrId = postResponseJsonObj.get("cdrId").toString();

            assertEquals(Response.Status.OK.getStatusCode(), postResponse.getStatus());

            postResponse.close();

            WebTarget putTarget = client.target(baseURI + "/cdr/" + cdrId + "/apply?clientContextId=1");
            Response putResponse = putTarget.request().method("PUT");

            assertEquals(Response.Status.OK.getStatusCode(), putResponse.getStatus());

            putResponse.close();

            WebTarget rePostTarget = client.target(baseURI + "/cdr");
            Response rePostResponse = rePostTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.CONFLICT.getStatusCode(), rePostResponse.getStatus());

            String actualErrorMsg = (String) rePostResponse.getHeaders().get(COUPON_APP_ERROR_X_HEADER).get(0);
            String expectedErrorMsg = MessageFormat.format("CPNE21{0}Coupon applicability count exceeded",
                    ERROR_MSG_PART_DELIMITER);

            assertEquals(expectedErrorMsg, actualErrorMsg);

            String actualErrorDetail = (String) rePostResponse.getHeaders().get(COUPON_APP_DETAIL_X_HEADER).get(0);
            String expectedErrorDetail = MessageFormat.format("The maximum applicable count 1 has exhausted for this coupon{1}1|{0}",
                    getCode, ERROR_MSG_PART_DELIMITER);

            assertEquals(expectedErrorDetail, actualErrorDetail);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when application_type for a coupon is set to
     * ONE_TIME_PER_USER_FIFO and application_use_count is set to a limit. This means coupon is applicable
     * per user once and maximum this coupon can apply to there upper limit only. So if previously this
     * coupon gets discount for its upper limit. This this time the request is not applicable.
     * <p>
     * The expected failure code is 409
     * Error Header Code : CPNE21::Coupon applicability count exceeded
     */
    @Test
    public void testApplicationTypeOneTimePerUserFifo() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            WebTarget firstPostTarget = client.target(baseURI + "/cdr");
            JSONObject firstPostJsonObj = new JSONObject(jsonString);
            JSONArray firstJsonCodesArray = firstPostJsonObj.getJSONArray("codes");
            firstJsonCodesArray.put(0, "A8-B8");
            Object couponCodeObj = firstJsonCodesArray.get(0);
            Response firstPostResponse = firstPostTarget.request().post(Entity.entity(firstPostJsonObj.toString(), MediaType.APPLICATION_JSON_TYPE));
            JSONObject firstPostResponseJsonObj = new JSONObject(firstPostResponse.readEntity(String.class));
            String firstCdrId = firstPostResponseJsonObj.get("cdrId").toString();

            assertEquals(Response.Status.OK.getStatusCode(), firstPostResponse.getStatus());

            firstPostResponse.close();

            WebTarget firstPutTarget = client.target(baseURI + "/cdr/" + firstCdrId + "/apply?clientContextId=1");
            Response firstPutResponse = firstPutTarget.request().method("PUT");


            assertEquals(Response.Status.OK.getStatusCode(), firstPutResponse.getStatus());

            firstPutResponse.close();

            JSONObject secondPostJsonObj = new JSONObject(firstPostJsonObj.toString()); // reusing JSON String from before
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

            JSONObject thirdPostJsonObj = new JSONObject(firstPostJsonObj.toString()); // Resuing the JSON String
            thirdPostJsonObj.put("requesterId", 3);
            thirdPostJsonObj.put("beneficiaryId", 3);
            WebTarget thirdPostTarget = client.target(baseURI + "/cdr");
            Response thirdPostResponse = thirdPostTarget.request().post(Entity.entity(thirdPostJsonObj.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.CONFLICT.getStatusCode(), thirdPostResponse.getStatus());

            String actualErrorMsg = (String) thirdPostResponse.getHeaders().get(COUPON_APP_ERROR_X_HEADER).get(0);
            String expectedErrorMsg = MessageFormat.format("CPNE21{0}Coupon applicability count exceeded",
                    ERROR_MSG_PART_DELIMITER);

            assertEquals(expectedErrorMsg, actualErrorMsg);

            String actualErrorDetail = (String) thirdPostResponse.getHeaders().get(COUPON_APP_DETAIL_X_HEADER).get(0);
            String expectedErrorDetail = MessageFormat.format(
                    "The maximum applicable count 2 has exhausted for this coupon{1}2|{0}",
                    couponCodeObj, ERROR_MSG_PART_DELIMITER);

            assertEquals(expectedErrorDetail, actualErrorDetail);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }


    /**
     * This test case tests whether failure is returned when an incomplete CDR is requested
     * in JSON payload. In this case RequesterId is not given in the request.
     * Failure Code : 409.
     * Coupon Error Header : CPNE20::Request is incomplete
     */
    @Test
    public void testNoRequesterIdInCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "requesterId";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE20{0}Request is incomplete";
            String expectedErrorMessageDetail = "Parameter requesterId is incomplete{0}requesterId";

            Response postResponse = removeJsonKeyAndPostAndAssertStatus(jsonString, key, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an incomplete CDR is requested
     * in JSON payload. In this case BeneficiaryId is not given in the request.
     * Failure Code : 409.
     * Coupon Error Header : CPNE20::Request is incomplete
     */
    @Test
    public void testNoBeneficiaryIdInCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "beneficiaryId";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE20{0}Request is incomplete";
            String expectedErrorMessageDetail = "Parameter beneficiaryId is incomplete{0}beneficiaryId";

            Response postResponse = removeJsonKeyAndPostAndAssertStatus(jsonString, key, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an incomplete CDR is requested
     * in JSON payload. In this case ProductBrandId is not given in the request.
     * Failure Code : 409.
     * Coupon Error Header : CPNE20::Request is incomplete
     */
    @Test
    public void testNoPatientBrandInCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "patientBrandId";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE20{0}Request is incomplete";
            String expectedErrorMessageDetail = "Parameter patientBrandId is incomplete{0}patientBrandId";

            Response postResponse = removeJsonKeyAndPostAndAssertStatus(jsonString, key, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an incomplete CDR is requested
     * in JSON payload. In this case areaId is not given in the request.
     * Failure Code : 409.
     * Coupon Error Header : CPNE20::Request is incomplete
     */
    @Test
    public void testNoAreaIdInCDR(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "areaId";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE20{0}Request is incomplete";
            String expectedErrorMessageDetail = "Parameter areaId is incomplete{0}areaId";

            Response postResponse = removeJsonKeyAndPostAndAssertStatus(jsonString, key, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an incomplete CDR is requested
     * in JSON payload. In this case referrerId is not given in the request.
     * Failure Code : 409.
     * Coupon Error Header : CPNE20::Request is incomplete
     */
    @Test
    public void testNoReferrerIdInCDR(){
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "referrerId";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE20{0}Request is incomplete";
            String expectedErrorMessageDetail = "Parameter referrerId is incomplete{0}referrerId";

            Response postResponse = removeJsonKeyAndPostAndAssertStatus(jsonString, key, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an incomplete CDR is requested
     * in JSON payload. In this case ContextType is not given in the request.
     * Failure Code : 409.
     * Coupon Error Header : CPNE20::Request is incomplete
     */
    @Test
    public void testNoContextTypeInCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "contextType";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE20{0}Request is incomplete";
            String expectedErrorMessageDetail = "Parameter contextType is incomplete{0}contextType";

            Response postResponse = removeJsonKeyAndPostAndAssertStatus(jsonString, key, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an incomplete CDR is requested
     * in JSON payload. In this case TotalCost is not given in the request.
     * Failure Code : 409.
     * Coupon Error Header : CPNE20::Request is incomplete
     */
    @Test
    public void testNoTotalCostInCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "totalCost";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE20{0}Request is incomplete";
            String expectedErrorMessageDetail = "Parameter totalCost is incomplete{0}totalCost";

            Response postResponse = removeJsonKeyAndPostAndAssertStatus(jsonString, key, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an incomplete CDR is requested
     * in JSON payload. In this case Codes is not given in the request.
     * Failure Code : 409.
     * Coupon Error Header : CPNE20::Request is incomplete
     */
    @Test
    public void testNoCodesInCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "codes";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE20{0}Request is incomplete";
            String expectedErrorMessageDetail = "Parameter codes is incomplete{0}codes";

            Response postResponse = removeJsonKeyAndPostAndAssertStatus(jsonString, key, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an incomplete CDR is requested
     * in JSON payload. In this case Products is not given in the request.
     * Failure Code : 409.
     * Coupon Error Header : CPNE20::Request is incomplete
     */
    @Test
    public void testNoProductsInCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "products";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE20{0}Request is incomplete";
            String expectedErrorMessageDetail = "Parameter products is incomplete{0}products";

            Response postResponse = removeJsonKeyAndPostAndAssertStatus(jsonString, key, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an incomplete CDR is requested
     * in JSON payload. In this case SourceName is not given in the request.
     * Failure Code : 409.
     * Coupon Error Header : CPNE20::Request is incomplete
     */
    @Test
    public void testNoSourceNameInCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "sourceName";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE20{0}Request is incomplete";
            String expectedErrorMessageDetail = "Parameter sourceName is incomplete{0}sourceName";

            Response postResponse = removeJsonKeyAndPostAndAssertStatus(jsonString, key, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an incomplete CDR is requested
     * in JSON payload. In this case WithinSubscription is not given in the request.
     * Failure Code : 409.
     * Coupon Error Header : CPNE20::Request is incomplete
     */
    @Test
    public void testNoWithinSubscriptionInCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "withinSubscription";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE20{0}Request is incomplete";
            String expectedErrorMessageDetail = "Parameter withinSubscription is incomplete{0}withinSubscription";

            Response postResponse = removeJsonKeyAndPostAndAssertStatus(jsonString, key, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an incomplete CDR is requested
     * in JSON payload. In this case ProductId is not given in the request.
     * Failure Code : 409.
     * Coupon Error Header : CPNE20::Request is incomplete
     */
    @Test
    public void testNoProductIdInCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "products";
            String changedValue = "id";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE20{0}Request is incomplete";
            String expectedErrorMessageDetail = "Parameter products.id is incomplete{0}products.id";

            Response postResponse = removeJsonArrayKeyAndPostAndAssert(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an incomplete CDR is requested
     * in JSON payload. In this case ProductType is not given in the request.
     * Failure Code : 409.
     * Coupon Error Header : CPNE20::Request is incomplete
     */
    @Test
    public void testNoProductTypeInCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "products";
            String changedValue = "productType";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE20{0}Request is incomplete";
            String expectedErrorMessageDetail = "Parameter products.productType is incomplete{0}products.productType";

            Response postResponse = removeJsonArrayKeyAndPostAndAssert(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an incomplete CDR is requested
     * in JSON payload. In this case ProductCount is not given in the request.
     * Failure Code : 409.
     * Coupon Error Header : CPNE20::Request is incomplete
     */
    @Test
    public void testNoProductCountInCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "products";
            String changedValue = "count";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE20{0}Request is incomplete";
            String expectedErrorMessageDetail = "Parameter products.count is incomplete{0}products.count";

            Response postResponse = removeJsonArrayKeyAndPostAndAssert(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an incomplete CDR is requested
     * in JSON payload. In this case ProductUnitCost is not given in the request.
     * Failure Code : 409.
     * Coupon Error Header : CPNE20::Request is incomplete
     */
    @Test
    public void testNoProductUnitCostInCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "products";
            String changedValue = "unitCost";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE20{0}Request is incomplete";
            String expectedErrorMessageDetail = "Parameter products.unitCost is incomplete{0}products.unitCost";

            Response postResponse = removeJsonArrayKeyAndPostAndAssert(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when an incomplete CDR is requested
     * in JSON payload. In this case ProductPurchaseCount is not given in the request.
     * Failure Code : 409.
     * Coupon Error Header : CPNE20::Request is incomplete
     */
    @Test
    public void testNoProductPurchaseCountInCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String key = "products";
            String changedValue = "purchaseCount";
            int expectedStatusCode = 409;
            String expectedErrorMessage = "CPNE20{0}Request is incomplete";
            String expectedErrorMessageDetail = "Parameter products.purchaseCount is incomplete{0}products.purchaseCount";

            Response postResponse = removeJsonArrayKeyAndPostAndAssert(jsonString, key, changedValue, expectedStatusCode);

            assertErrorMessage(postResponse, expectedErrorMessage);

            assertErrorMessageDetail(postResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether Success is returned when a valid CDR request is post
     * in JSON payload.
     * Success Code : 200 OK
     */
    @Test
    public void testSuccessfulCdrCreation() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        WebTarget postTarget = client.target(baseURI + "/cdr");
        Response postResponse = postTarget.request().post(Entity.entity(jsonString, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.OK.getStatusCode(), postResponse.getStatus());
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

    /**
     * This method asserts Header ErrorMessage
     * @param postResponse post response object
     * @param exceptedErrorMessage expected error message
     */
    private void assertErrorMessage(Response postResponse, String exceptedErrorMessage) {
        String actualErrorMsg = (String) postResponse.getHeaders().get(COUPON_APP_ERROR_X_HEADER).get(0);
        String expectedErrorMsg = MessageFormat.format(exceptedErrorMessage, ERROR_MSG_PART_DELIMITER);

        assertEquals(expectedErrorMsg, actualErrorMsg);
    }

    /**
     * This method asserts Header ErrorMessageDetail
     * @param postResponse       post response object
     * @param expectedErrorMessageDetail expected error message detail
     * @param changedValue       changed parameter value
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

    /**
     *
     * @param jsonString input JSON requesting for a CDR
     * @param arrayKey key to match with json array
     * @param valueToBeUpdated changed value associated with array key
     * @param statusToBeAsserted expected status code
     * @return HTTP Response for the POST request
     * @throws JSONException
     */
    private Response updateJsonArrayAndPostAndAssertStatus(String jsonString, String arrayKey, String valueToBeUpdated,
                                                           int statusToBeAsserted) throws JSONException {
        WebTarget postTarget = client.target(baseURI + "/cdr");
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonCodesArray = jsonObject.getJSONArray(arrayKey);
        jsonCodesArray.put(0, valueToBeUpdated);

        Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
        assertEquals(statusToBeAsserted, postResponse.getStatus());

        return postResponse;
    }

    /**
     *
     * @param jsonString input JSON requesting for a CDR
     * @param key key to be updated
     * @param valueToBeUpdated changed value associated with key
     * @param statusToBeAsserted expected status code
     * @return HTTP Response for the POST request
     * @throws JSONException
     */
    private Response updateJsonKeyAndPostAndAssertStatus(String jsonString, String key, String valueToBeUpdated,
                                                         int statusToBeAsserted) throws JSONException {
        WebTarget postTarget = client.target(baseURI + "/cdr");
        JSONObject jsonObject = new JSONObject(jsonString);
        jsonObject.put(key, valueToBeUpdated);

        Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
        assertEquals(statusToBeAsserted, postResponse.getStatus());

        return postResponse;
    }

    /**
     * This method returns a Post response object given a JSON and key/value.
     * @param jsonString   input JSON requesting for a CDR
     * @param arrayKey  key to find json array
     * @param valueToBeRemoved object to be removed within key specified
     * @param statusToBeAsserted expected status code
     * @return HTTP Response for the POST request
     * @throws JSONException
     */
    private Response removeJsonArrayKeyAndPostAndAssert(String jsonString, String arrayKey, String valueToBeRemoved, int statusToBeAsserted) throws JSONException {
        WebTarget postTarget = client.target(baseURI + "/cdr");
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray productsJsonArray = jsonObject.getJSONArray(arrayKey);
        for (int i = 0; i < productsJsonArray.length(); i++) {
            JSONObject productJsonObj = productsJsonArray.getJSONObject(i);
            productJsonObj.remove(valueToBeRemoved);
        }

        Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
        assertEquals(statusToBeAsserted, postResponse.getStatus());

        return postResponse;
    }

    /**
     *
     * @param jsonString input JSON requesting for a CDR
     * @param key key to be removed
     * @param statusToBeAsserted expected status code
     * @return HTTP Response for the POST request
     * @throws JSONException
     */
    private Response removeJsonKeyAndPostAndAssertStatus(String jsonString, String key, int statusToBeAsserted) throws JSONException {
        WebTarget postTarget = client.target(baseURI + "/cdr");
        JSONObject jsonObject = new JSONObject(jsonString);
        jsonObject.remove(key);

        Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));
        assertEquals(statusToBeAsserted, postResponse.getStatus());

        return postResponse;
    }

}