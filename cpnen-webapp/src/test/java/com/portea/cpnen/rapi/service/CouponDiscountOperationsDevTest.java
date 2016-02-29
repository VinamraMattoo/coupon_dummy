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
 * through its REST API interface after CDR Creation
 * To be able to execute these tests on localhost, add "127.0.0.1  coupons.localhost" (without quotes)
 * to hosts list in /etc/hosts file. Please note that the virtual host coupons.localhost must be
 * configured appropriately in the target WildFly server.
 */
public class CouponDiscountOperationsDevTest {

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
     * This test case tests whether failure is returned when user tries to apply a CDR which
     * is already canceled earlier.
     * Failure Code : 409
     * Coupon Error Header : CPNE27::Coupon discount request is already canceled
     */
    @Test
    public void testApplyCdrWithCanceledCdrStatus() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE27{0}Coupon discount request is already canceled";
            String expectedErrorMessageDetail = "Coupon discount request with id {0} is already canceled{1}{0}";
            String cdrId = getPostResponse(jsonString);

            WebTarget cancelTarget = client.target(baseURI + "/cdr/" + cdrId + "/cancel");
            Response cancelResponse = cancelTarget.request().method("PUT");

            assertEquals(Response.Status.OK.getStatusCode(), cancelResponse.getStatus());

            cancelResponse.close();

            WebTarget applyTarget = client.target(baseURI + "/cdr/" + cdrId + "/apply?clientContextId=1");
            Response applyResponse = applyTarget.request().method("PUT");

            assertEquals(Response.Status.CONFLICT.getStatusCode(), applyResponse.getStatus());

            assertErrorMessage(applyResponse, expectedErrorMessage);

            assertErrorMessageDetail(applyResponse, expectedErrorMessageDetail, cdrId);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when user tries to commit a CDR which
     * is already canceled earlier.
     * Failure Code : 409
     * Coupon Error Header : CPNE27::Coupon discount request is already canceled
     */
    @Test
    public void testCommitCdrWithCanceledCdrStatus() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE27{0}Coupon discount request is already canceled";
            String expectedErrorMessageDetail = "Coupon discount request with id {0} is already canceled{1}{0}";
            String cdrId = getPostResponse(jsonString);
            WebTarget cancelTarget = client.target(baseURI + "/cdr/" + cdrId + "/cancel");
            Response cancelResponse = cancelTarget.request().method("PUT");

            assertEquals(Response.Status.OK.getStatusCode(), cancelResponse.getStatus());

            cancelResponse.close();

            WebTarget commitTarget = client.target(baseURI + "/cdr/" + cdrId + "/commit?clientContextId=1");
            Response commitResponse = commitTarget.request().method("PUT");

            assertEquals(Response.Status.CONFLICT.getStatusCode(), commitResponse.getStatus());

            assertErrorMessage(commitResponse, expectedErrorMessage);

            assertErrorMessageDetail(commitResponse, expectedErrorMessageDetail, cdrId);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when user tries to cancel a CDR which
     * is already canceled earlier.
     * Failure Code : 409
     * Coupon Error Header : CPNE27::Coupon discount request is already canceled
     */
    @Test
    public void testCancelCdrWithCanceledCdrStatus() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE27{0}Coupon discount request is already canceled";
            String expectedErrorMessageDetail = "Coupon discount request with id {0} is already canceled{1}{0}";
            String cdrId = getPostResponse(jsonString);
            WebTarget cancelTarget = client.target(baseURI + "/cdr/" + cdrId + "/cancel");
            Response cancelResponse = cancelTarget.request().method("PUT");

            assertEquals(Response.Status.OK.getStatusCode(), cancelResponse.getStatus());

            cancelResponse.close();

            WebTarget reCancelTarget = client.target(baseURI + "/cdr/" + cdrId + "/cancel");
            Response reCancelResponse = reCancelTarget.request().method("PUT");

            assertEquals(Response.Status.CONFLICT.getStatusCode(), reCancelResponse.getStatus());

            assertErrorMessage(reCancelResponse, expectedErrorMessage);

            assertErrorMessageDetail(reCancelResponse, expectedErrorMessageDetail, cdrId);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when user tries to apply a CDR which
     * is already applied earlier.
     * Failure Code : 409
     * Coupon Error Header : CPNE24::Coupon discount request is already applied
     */
    @Test
    public void testApplyCdrWithAppliedCdrStatus() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE24{0}Coupon discount request is already applied";
            String expectedErrorMessageDetail = "Coupon discount request with id {0} is already applied {1}{0}";
            String cdrId = getPostResponse(jsonString);
            WebTarget applyTarget = client.target(baseURI + "/cdr/" + cdrId + "/apply?clientContextId=1");
            Response applyResponse = applyTarget.request().method("PUT");

            assertEquals(Response.Status.OK.getStatusCode(), applyResponse.getStatus());

            applyResponse.close();

            WebTarget reApplyTarget = client.target(baseURI + "/cdr/" + cdrId + "/apply?clientContextId=1");
            Response reApplyResponse = reApplyTarget.request().method("PUT");

            assertEquals(Response.Status.CONFLICT.getStatusCode(), reApplyResponse.getStatus());

            assertErrorMessage(reApplyResponse, expectedErrorMessage);

            assertErrorMessageDetail(reApplyResponse, expectedErrorMessageDetail, cdrId);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when user tries to cancel a CDR which
     * is already applied earlier.
     * Failure Code : 409
     * Coupon Error Header : CPNE24::Coupon discount request is already applied
     */
    @Test
    public void testCancelCdrWithAppliedCdrStatus() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE24{0}Coupon discount request is already applied";
            String exoectedErrorMessageDetail = "Coupon discount request with id {0} is already applied {1}{0}";
            String cdrId = getPostResponse(jsonString);
            WebTarget applyTarget = client.target(baseURI + "/cdr/" + cdrId + "/apply?clientContextId=1");
            Response applyResponse = applyTarget.request().method("PUT");

            assertEquals(Response.Status.OK.getStatusCode(), applyResponse.getStatus());

            applyResponse.close();

            WebTarget cancelTarget = client.target(baseURI + "/cdr/" + cdrId + "/cancel");
            Response cancelResponse = cancelTarget.request().method("PUT");

            assertEquals(Response.Status.CONFLICT.getStatusCode(), cancelResponse.getStatus());

            assertErrorMessage(cancelResponse, expectedErrorMessage);

            assertErrorMessageDetail(cancelResponse, exoectedErrorMessageDetail, cdrId);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when user tries to commit a CDR before
     * applying that CDR. Which is not allowed.
     * Failure Code : 409
     * Coupon Error Header : CPNE28::Coupon discount request has to be applied to be committed
     */
    @Test
    public void testCommitBeforeApplyingCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE28{0}Coupon discount request has to be applied to be committed";
            String expectedErrorMessageDetail = "Coupon discount request with id {0} should be applied to be committed{1}{0}";
            String cdrId = getPostResponse(jsonString);
            WebTarget commitTarget = client.target(baseURI + "/cdr/" + cdrId + "/commit?clientContextId=1");
            Response commitResponse = commitTarget.request().method("PUT");

            assertEquals(Response.Status.CONFLICT.getStatusCode(), commitResponse.getStatus());

            commitResponse.close();

            assertErrorMessage(commitResponse, expectedErrorMessage);

            assertErrorMessageDetail(commitResponse, expectedErrorMessageDetail, cdrId);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when clientContextId is already set for a CDR in
     * apply state and user tries to commit the CDR. If clientContextId is already set commit is invalid.
     * Failure Code : 409
     * Coupon Error Header : CPNE29::Context id for coupon discount request is already set
     */
    @Test
    public void testInvalidCommitGivenClientContextId() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE29{0}Context id for coupon discount request is already set";
            String expectedErrorMessageDetail = "Context id for coupon discount request with id {0} is already set{1}{0}";
            String cdrId = getPostResponse(jsonString);
            WebTarget applyTarget = client.target(baseURI + "/cdr/" + cdrId + "/apply?clientContextId=1");
            Response applyResponse = applyTarget.request().method("PUT");

            assertEquals(Response.Status.OK.getStatusCode(), applyResponse.getStatus());

            applyResponse.close();

            WebTarget commitTarget = client.target(baseURI + "/cdr/" + cdrId + "/commit?clientContextId=1");
            Response commitResponse = commitTarget.request().method("PUT");

            assertEquals(Response.Status.CONFLICT.getStatusCode(), commitResponse.getStatus());

            assertErrorMessage(commitResponse, expectedErrorMessage);

            assertErrorMessageDetail(commitResponse, expectedErrorMessageDetail, cdrId);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when clientContextId is not set in the apply
     * state for a CDR and user is tries to commit without clientContextId. If clientContextId is not set
     * in apply state then in commit state it is required to set clientContextId.
     * Failure Code : 409
     * Coupon Header Error : CPNE30::Context id for coupon discount request is needed to commit request
     */
    @Test
    public void testCommitWithoutClientContextId() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE30{0}Context id for coupon discount request is needed to commit request";
            String expectedErrorMessageDetail = "Context id for coupon discount request with id {0} is needed to commit the request{1}{0}";
            String cdrId = getPostResponse(jsonString);
            WebTarget applyTarget = client.target(baseURI + "/cdr/" + cdrId + "/apply");
            Response applyResponse = applyTarget.request().method("PUT");

            assertEquals(Response.Status.OK.getStatusCode(), applyResponse.getStatus());

            applyResponse.close();

            WebTarget commitTarget = client.target(baseURI + "/cdr/" + cdrId + "/commit");
            Response commitResponse = commitTarget.request().method("PUT");

            assertEquals(Response.Status.CONFLICT.getStatusCode(), commitResponse.getStatus());

            assertErrorMessage(commitResponse, expectedErrorMessage);

            assertErrorMessageDetail(commitResponse, expectedErrorMessageDetail, cdrId);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether Success is returned when user is try to apply a CDR.
     * Success Code : 200
     */
    @Test
    public void testSuccessfulApplyCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String cdrId = getPostResponse(jsonString);
            WebTarget applyTarget = client.target(baseURI + "/cdr/" + cdrId + "/apply?clientContextId=1");
            Response applyResponse = applyTarget.request().method("PUT");

            assertEquals(Response.Status.OK.getStatusCode(), applyResponse.getStatus());

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether Success is returned when user is try to Commit a CDR.
     * Success Code : 200
     */
    @Test
    public void testSuccessfulCommitCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String cdrId = getPostResponse(jsonString);
            WebTarget applyTarget = client.target(baseURI + "/cdr/" + cdrId + "/apply");
            Response applyResponse = applyTarget.request().method("PUT");

            assertEquals(Response.Status.OK.getStatusCode(), applyResponse.getStatus());

            applyResponse.close();

            WebTarget commitTarget = client.target(baseURI + "/cdr/" + cdrId + "/commit?clientContextId=1");
            Response commitResponse = commitTarget.request().method("PUT");

            assertEquals(Response.Status.OK.getStatusCode(), commitResponse.getStatus());

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }

    }

    /**
     * This test case tests whether failure is returned when a invalid coupon code is entered
     * to add in a existing valid cdrId.
     * Failure code : 409
     * Coupon Error Header : CPNE08:: Coupon code is not recognized
     */
    @Test
    public void testAddInvalidCodesForCdrId() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE08{0}Coupon code is not recognized";
            String expectedErrorMessageDetail = "Coupon code {0} is invalid{1}{0}";
            String cdrId = getPostResponse(jsonString);
            String addInvalidCode = "BLA-BLA";
            WebTarget putTarget = client.target(baseURI + "/cdr/" + cdrId + "/code/" + addInvalidCode);
            Response putResponse = putTarget.request().method("PUT");

            assertEquals(Response.Status.CONFLICT.getStatusCode(), putResponse.getStatus());

            assertErrorMessage(putResponse, expectedErrorMessage);

            assertErrorMessageDetail(putResponse, expectedErrorMessageDetail, addInvalidCode);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when a inactive coupon code is entered
     * to add in an existing CDR.
     * Failure Code : 409
     * Coupon Error Header : CPNE06::Coupon is deactivated
     */
    @Test
    public void testAddInactiveCodesForCdrId() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE06{0}Coupon is deactivated";
            String expectedErrorMessageDetail = "Coupon code {0} is deactivated{1}{0}";
            String cdrId = getPostResponse(jsonString);
            String addCode = "MMMM";
            WebTarget putTarget = client.target(baseURI + "/cdr/" + cdrId + "/code/" + addCode);
            Response putResponse = putTarget.request().method("PUT");

            assertEquals(Response.Status.CONFLICT.getStatusCode(), putResponse.getStatus());

            assertErrorMessage(putResponse, expectedErrorMessage);

            assertErrorMessageDetail(putResponse, expectedErrorMessageDetail, addCode);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when a coupon code is entered
     * to add in an existing CDR and Coupon is inactive for that Coupon Code.
     * Failure Code : 409
     * Coupon Error Header : CPNE06::Coupon is deactivated
     */
    @Test
    public void testAddCodeForInactiveCouponForCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE06::Coupon is deactivated";
            String expectedErrorMessageDetail = "Coupon code {0} is deactivated{1}{0}";
            String cdrId = getPostResponse(jsonString);
            String addCode = "NNNN";
            WebTarget putTarget = client.target(baseURI + "/cdr/" + cdrId + "/code/" + addCode);
            Response putResponse = putTarget.request().method("PUT");

            assertEquals(Response.Status.CONFLICT.getStatusCode(), putResponse.getStatus());

            assertErrorMessage(putResponse, expectedErrorMessage);

            assertErrorMessageDetail(putResponse, expectedErrorMessageDetail, addCode);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when coupon code entered to add for CDR is
     * already expired i.e the coupon validity is expired. In this case C3-D3 is
     * a Expired Coupon.
     * Failure code : 409
     * Error Header Code : CPNE02::Coupon validity has expired.
     */
    @Test
    public void testAddExpiredCodesForCdrId() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE02{0}Coupon validity has expired";
            String expectedErrorMessageDetail = "Coupon code C3-D3 was valid from [15-12-15:00:00:00] to " +
                    "[18-12-15:00:00:00]{1}{0}|15-12-15:00:00:00|18-12-15:00:00:00";
            String cdrId = getPostResponse(jsonString);
            String addCode = "C3-D3";
            WebTarget putTarget = client.target(baseURI + "/cdr/" + cdrId + "/code/" + addCode);
            Response putResponse = putTarget.request().method("PUT");

            assertEquals(Response.Status.CONFLICT.getStatusCode(), putResponse.getStatus());

            assertErrorMessage(putResponse, expectedErrorMessage);

            assertErrorMessageDetail(putResponse, expectedErrorMessageDetail, addCode);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when coupon code entered for CDR is for
     * same coupon that is for already
     * Failure Code : 409
     * Error Header Code : CPNE22::Multiple codes of the same coupon are not allowed.
     */
    @Test
    public void testAddCodeForSameCouponForCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE22{0}Multiple codes of the same coupon are not allowed";
            String cdrId = getPostResponse(jsonString);
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonCodesArray = jsonObject.getJSONArray("codes");
            Object firstCode = jsonCodesArray.get(0);
            String addCode = "C4-D4";
            WebTarget putTarget = client.target(baseURI + "/cdr/" + cdrId + "/code/" + addCode);
            Response putResponse = putTarget.request().method("PUT");

            assertEquals(Response.Status.CONFLICT.getStatusCode(), putResponse.getStatus());

            assertErrorMessage(putResponse, expectedErrorMessage);

            String actualErrorDetail = (String) putResponse.getHeaders().get(COUPON_APP_DETAIL_X_HEADER).get(0);
            String expectedErrorDetail = MessageFormat.format("Coupon codes {0} and {1} belong to same coupon{2}{0}|{1}",
                    firstCode, addCode, ERROR_MSG_PART_DELIMITER);

            assertEquals(expectedErrorDetail, actualErrorDetail);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when coupon code add for CDR is  exclusive
     * with coupon in Existing Cdr. In this case A1-B1 is exclusive with C1-D1.
     * Failure Code : 409
     * Error Header Code : CPNE03::Multiple exclusive coupons cannot be applied.
     */
    @Test
    public void testAddCodeForMultipleExclusiveCouponForCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE03{0}Multiple exclusive coupons cannot be applied";
            String cdrId = getPostResponse(jsonString);
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonCodesArray = jsonObject.getJSONArray("codes");
            Object firstCode = jsonCodesArray.get(0);
            String addCode = "A1-B1";
            WebTarget putTarget = client.target(baseURI + "/cdr/" + cdrId + "/code/" + addCode);
            Response putResponse = putTarget.request().method("PUT");

            assertEquals(Response.Status.CONFLICT.getStatusCode(), putResponse.getStatus());

            assertErrorMessage(putResponse, expectedErrorMessage);

            String actualErrorDetail = (String) putResponse.getHeaders().get(COUPON_APP_DETAIL_X_HEADER).get(0);
            String expectedErrorDetail = MessageFormat.format("Coupon code {1} is exclusive and in conflict with {0}{2}{1}|{0}",
                    firstCode, addCode, ERROR_MSG_PART_DELIMITER);

            assertEquals(expectedErrorDetail, actualErrorDetail);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when coupon code add for CDR has invalid
     * Product Mapping for that Non Global Coupon.
     * Failure Code : 409
     * Error Header Code : CPNE12::Coupon is not applicable on a specified product.
     */
    @Test
    public void testAddNonGlobalCouponWithInvalidProductForCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE12{0}Coupon is not applicable on a specified product";
            String expectedErrorMessageDetail = "Coupon code {0} is not applicable on the product 1 of type SERVICE{1}{0}|1|SERVICE";
            String cdrId = getPostResponse(jsonString);
            String addCode = "C5-D5";
            WebTarget putTarget = client.target(baseURI + "/cdr/" + cdrId + "/code/" + addCode);
            Response putResponse = putTarget.request().method("PUT");

            assertEquals(Response.Status.CONFLICT.getStatusCode(), putResponse.getStatus());

            assertErrorMessage(putResponse, expectedErrorMessage);

            assertErrorMessageDetail(putResponse, expectedErrorMessageDetail, addCode);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned user tries to delete a invalid code
     * from a existing CDR.
     * Failure Code : 409
     * Coupon Header Error : CPNE08::Coupon code is not recognized
     */
    @Test
    public void testDeleteInvalidCodeFromCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE08{0}Coupon code is not recognized";
            String expectedErrorMessageDetail = "Coupon code {0} is invalid{1}{0}";
            String cdrId = getPostResponse(jsonString);
            String code = "BLA-BLA";
            WebTarget deleteTarget = client.target(baseURI + "/cdr/" + cdrId + "/code/" + code);
            Response deleteResponse = deleteTarget.request().delete();

            assertEquals(Response.Status.CONFLICT.getStatusCode(), deleteResponse.getStatus());

            assertErrorMessage(deleteResponse, expectedErrorMessage);

            assertErrorMessageDetail(deleteResponse, expectedErrorMessageDetail, code);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether success in returned when user tries to delete a code from
     * a current CDR.
     * Successful Code : 200
     */
    @Test
    public void testSuccessfulDeleteCodeFromCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            WebTarget postTarget = client.target(baseURI + "/cdr");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonCodesArray = jsonObject.getJSONArray("codes");
            jsonCodesArray.put(0, "C1-D2");
            jsonObject.put("requesterId", 3);
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.OK.getStatusCode(), postResponse.getStatus());

            JSONObject postResponseJsonObj = new JSONObject(postResponse.readEntity(String.class));
            String cdrId = postResponseJsonObj.get("cdrId").toString();

            postResponse.close();

            String code = "E1-F1";
            WebTarget putTarget = client.target(baseURI + "/cdr/" + cdrId + "/code/" + code);
            Response putResponse = putTarget.request().method("PUT");

            assertEquals(Response.Status.OK.getStatusCode(), putResponse.getStatus());

            putResponse.close();

            WebTarget deleteTarget = client.target(baseURI + "/cdr/" + cdrId + "/code/" + code);
            Response deleteResponse = deleteTarget.request().delete();

            assertEquals(Response.Status.OK.getStatusCode(), deleteResponse.getStatus());

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether failure is returned when user tries to add a invalid
     * product for a current CDR.
     * Failure Code : 409
     * Coupon Error Header : CPNE10::Product code is not recognized
     */
    @Test
    public void testAddInvalidProductForCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE10{0}Product code is not recognized";
            String expectedErrorMessageDetail = "Product code 5 of type PACKAGE is invalid{0}5|PACKAGE";
            WebTarget postTarget = client.target(baseURI + "/cdr");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonCodesArray = jsonObject.getJSONArray("codes");
            jsonCodesArray.put(0, "E1-F1");
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.OK.getStatusCode(), postResponse.getStatus());

            JSONObject postResponseJsonObj = new JSONObject(postResponse.readEntity(String.class));
            String cdrId = postResponseJsonObj.get("cdrId").toString();

            postResponse.close();

            WebTarget putTarget = client.target(baseURI + "/cdr/" + cdrId + "/product");
            String addProduct = " {\n" +
                    " \t\"totalCost\": \"100\",\n" +
                    " \t\"products\": [{\n" +
                    " \t\t\"id\": 5,\n" +
                    " \t\t\"count\": 1,\n" +
                    " \t\t\"unitCost\": 20,\n" +
                    " \t\t\"productType\": \"PACKAGE\",\n" +
                    " \t\t\"purchaseCount\": 1,\n" +
                    " \t\t\"remarks\": \"Sample Remarks\"\n" +
                    " \t}]\n" +
                    " }";
            Response putResponse = putTarget.request().put(Entity.entity(addProduct, MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.CONFLICT.getStatusCode(), putResponse.getStatus());

            assertErrorMessage(putResponse, expectedErrorMessage);

            assertErrorMessageDetail(putResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether success is returned when user tries to add a valid product
     * for a current CDR.
     * Success Code : 200
     */
    @Test
    public void testSuccessfulAddProductForCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            WebTarget postTarget = client.target(baseURI + "/cdr");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonCodesArray = jsonObject.getJSONArray("codes");
            jsonCodesArray.put(0, "E1-F1");
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.OK.getStatusCode(), postResponse.getStatus());

            JSONObject postResponseJsonObj = new JSONObject(postResponse.readEntity(String.class));
            String cdrId = postResponseJsonObj.get("cdrId").toString();

            postResponse.close();

            WebTarget putTarget = client.target(baseURI + "/cdr/" + cdrId + "/product");
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
            Response putResponse = putTarget.request().put(Entity.entity(addProduct, MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.OK.getStatusCode(), putResponse.getStatus());

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }


    /**
     * This test case tests whether failure is returned when user tries to delete a invalid
     * product for a existing CDR.
     * Failure Code : 409
     * Coupon Error Header : CPNE10::Product code is not recognized
     */
    @Test
    public void testDeleteInvalidProductForCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String expectedErrorMessage = "CPNE10{0}Product code is not recognized";
            String expectedErrorMessageDetail = "Product code 1 of type null is invalid{0}1|null";
            WebTarget postTarget = client.target(baseURI + "/cdr");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonCodesArray = jsonObject.getJSONArray("codes");
            jsonCodesArray.put(0, "E1-F1");
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.OK.getStatusCode(), postResponse.getStatus());

            JSONObject postResponseJsonObj = new JSONObject(postResponse.readEntity(String.class));
            String cdrId = postResponseJsonObj.get("cdrId").toString();
            postResponse.close();

            WebTarget deleteTarget = client.target(baseURI + "/cdr/" + cdrId + "/product" + "/1/PACKAGE");
            String costJson = "{\n" +
                    "\t\"totalCost\": 50\n" +
                    "}";
            Response deleteResponse = deleteTarget.request().method("DELETE", Entity.entity(costJson, MediaType.APPLICATION_JSON_TYPE));
            assertEquals(Response.Status.CONFLICT.getStatusCode(), deleteResponse.getStatus());

            assertErrorMessage(deleteResponse, expectedErrorMessage);

            assertErrorMessageDetail(deleteResponse, expectedErrorMessageDetail, null);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether success is returned when user tries to delete a product
     * form a existing CDR.
     * Success Code : 200
     */
    @Test
    public void testSuccessfulDeleteProductForCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            WebTarget postTarget = client.target(baseURI + "/cdr");
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonCodesArray = jsonObject.getJSONArray("codes");
            jsonCodesArray.put(0, "E1-F1");
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.OK.getStatusCode(), postResponse.getStatus());

            JSONObject postResponseJsonObj = new JSONObject(postResponse.readEntity(String.class));
            String cdrId = postResponseJsonObj.get("cdrId").toString();
            postResponse.close();

            WebTarget deleteTarget = client.target(baseURI + "/cdr/" + cdrId + "/product" + "/1/SERVICE");
            String costJson = "{\n" +
                    "\t\"totalCost\": 50\n" +
                    "}";
            Response deleteResponse = deleteTarget.request().method("DELETE", Entity.entity(costJson, MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.OK.getStatusCode(), deleteResponse.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether success in returned when user tries to find actual
     * discount amount for a current CDR.
     * Success Code : 200
     */
    @Test
    public void testDiscountAmountForCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            WebTarget postTarget = client.target(baseURI + "/cdr");
            JSONObject jsonObject = new JSONObject(jsonString);
            Response postResponse = postTarget.request().post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON_TYPE));

            assertEquals(Response.Status.OK.getStatusCode(), postResponse.getStatus());

            JSONObject postResponseJsonObj = new JSONObject(postResponse.readEntity(String.class));
            String cdrId = postResponseJsonObj.get("cdrId").toString();
            String expectedDisc = postResponseJsonObj.get("discount").toString();

            postResponse.close();

            WebTarget getTarget = client.target(baseURI + "/cdr/" + cdrId + "/discountAmt");
            Response getResponse = getTarget.request().get();
            assertEquals(Response.Status.OK.getStatusCode(), getResponse.getStatus());

            JSONObject getResponseJsonObj = new JSONObject(getResponse.readEntity(String.class));
            String actualDisc = getResponseJsonObj.get("discountAmount").toString();

            assertEquals(expectedDisc, actualDisc);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    /**
     * This test case tests whether success is returned when user tries to get the current
     * status of a CDR.
     * Success Code : 200
     */
    @Test
    public void testStatusForCDR() {
        String jsonString = readFile("/com/portea/cpnen/rapi/service/test-InputCDR.json");

        try {
            String cdrId = getPostResponse(jsonString);

            WebTarget getTarget = client.target(baseURI + "/cdr/" + cdrId + "/status");
            Response getResponse = getTarget.request().get();
            JSONObject getResponseJsonObj = new JSONObject((getResponse.readEntity(String.class)));
            String actualCdrStatus = getResponseJsonObj.get("status").toString();

            assertEquals("REQUESTED", actualCdrStatus);
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
     * This method returns CdrId for Post response given a JSON
     * @param jsonString input JSON requesting for a CDR
     * @return postResponse
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
}
