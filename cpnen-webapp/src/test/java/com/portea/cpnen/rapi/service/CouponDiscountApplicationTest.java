package com.portea.cpnen.rapi.service;

import com.portea.cpnen.rapi.domain.ApplicableDiscountResp;
import com.portea.cpnen.rapi.domain.CouponDiscountRequestCreateReq;
import com.portea.cpnen.rapi.domain.SelectedProduct;
import com.portea.cpnen.rapi.exception.ExceptionalCondition;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

public class CouponDiscountApplicationTest {

    private static final String COUPON_APP_ERROR_X_HEADER = "X-Cpnen-Error";
    private static final String ERROR_MSG_PART_DELIMITER = "::";
    private Client client;
    private String baseURI = "http://localhost:8080/cpnen/rapi";

    @Before
    public void init() {
        this.client = ClientBuilder.newClient();
    }


    /**
     * This test case checks whether system prevents invalid user to send a request
     * user Id : 1123 is not a registered user.
     * response status should be CONFLICT and error should be of type ExceptionalCondition.Error.CONSUMER_INVALID
     */
    @Test
    public void testInvalidConsumerException() {
        WebTarget target = client.target(baseURI + "/cdr");
        CouponDiscountRequestCreateReq request = new CouponDiscountRequestCreateReq();
        request.setRequesterId(1123);
        request.setCodes(new String[]{"C1-D1"});
        SelectedProduct sp1 = new SelectedProduct();
        SelectedProduct sp2 = new SelectedProduct();
        request.setProducts(Arrays.asList(sp1, sp2));

        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(request));
        Assert.assertTrue(response.getStatus() == Response.Status.CONFLICT.getStatusCode());
        validateErrorResponse(response, ExceptionalCondition.Error.CONSUMER_INVALID);
    }

    /**
     * Second Exception validation is to check if the given product id is not registered.
     * product Id: 1233 is not registered.
     * response status should be CONFLICT and error should be of type ExceptionalCondition.Error.PRODUCT_INVALID
     */
    @Test
    public void testInvalidProductException() {
        WebTarget target = client.target(baseURI + "/cdr");
        CouponDiscountRequestCreateReq request = new CouponDiscountRequestCreateReq();
        request.setRequesterId(1);
        request.setCodes(new String[]{"C1-D1"});
        SelectedProduct sp1 = new SelectedProduct();
        SelectedProduct sp2 = new SelectedProduct();
        request.setProducts(Arrays.asList(sp1, sp2));

        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(request));
        Assert.assertTrue(response.getStatus() == Response.Status.CONFLICT.getStatusCode());
        validateErrorResponse(response, ExceptionalCondition.Error.PRODUCT_INVALID);
    }

    /**
     * Third Exception validation is to check if the given coupon code is valid been deactivated.
     * coupon code: C1-D6 is deactivated.
     * response status should be CONFLICT and error should be of type ExceptionalCondition.Error.COUPON_INACTIVE
     */
    @Test
    public void testInactiveCouponCodeException() {
        WebTarget target = client.target(baseURI + "/cdr");
        CouponDiscountRequestCreateReq request = new CouponDiscountRequestCreateReq();
        request.setRequesterId(1);
        request.setCodes(new String[]{"C1-D6"});
        SelectedProduct sp1 = new SelectedProduct();
        SelectedProduct sp2 = new SelectedProduct();
        request.setProducts(Arrays.asList(sp1, sp2));

        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(request));
        Assert.assertTrue(response.getStatus() == Response.Status.CONFLICT.getStatusCode());
        validateErrorResponse(response, ExceptionalCondition.Error.COUPON_INACTIVE);
    }

    /**
     * Fourth Exception validation is to check if the given coupon code a valid code (registered in db).
     * coupon code: C1-D1235 is not registered.
     * response status should be CONFLICT and error should be of type ExceptionalCondition.Error.COUPON_INVALID
     */
    @Test
    public void testInvalidCouponException() {
        WebTarget target = client.target(baseURI + "/cdr");
        CouponDiscountRequestCreateReq request = new CouponDiscountRequestCreateReq();
        request.setRequesterId(1);
        request.setCodes(new String[]{"C1-D1235"});
        SelectedProduct sp1 = new SelectedProduct();
        SelectedProduct sp2 = new SelectedProduct();
        request.setProducts(Arrays.asList(sp1, sp2));

        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(request));
        Assert.assertTrue(response.getStatus() == Response.Status.CONFLICT.getStatusCode());
        validateErrorResponse(response, ExceptionalCondition.Error.COUPON_INVALID);
    }

    /**
     * Fifth Exception validation is to check if the given coupon validity expired.
     * coupon code C1-D9 belongs to expired coupon 9.
     * response status should be CONFLICT and error should be of type ExceptionalCondition.Error.COUPON_INVALID
     */
    @Test
    public void testCouponValidityExpiredException() {
        WebTarget target = client.target(baseURI + "/cdr");
        CouponDiscountRequestCreateReq request = new CouponDiscountRequestCreateReq();
        request.setRequesterId(1);
        request.setCodes(new String[]{"C1-D9"});
        SelectedProduct sp1 = new SelectedProduct();
        SelectedProduct sp2 = new SelectedProduct();
        request.setProducts(Arrays.asList(sp1, sp2));

        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(request));
        Assert.assertTrue(response.getStatus() == Response.Status.CONFLICT.getStatusCode());
        validateErrorResponse(response, ExceptionalCondition.Error.COUPON_VALIDITY_EXPIRED);

    }

    /**
     * Sixth Exception validation is to check if the given coupon is valid been deactivated.
     * coupon code C1-D8 belongs the deactivated coupon 8.
     * response status should be CONFLICT and error should be of type ExceptionalCondition.Error.COUPON_INACTIVE
     */
    @Test
    public void testInactiveCouponException() {
        WebTarget target = client.target(baseURI + "/cdr");
        CouponDiscountRequestCreateReq request = new CouponDiscountRequestCreateReq();
        request.setRequesterId(1);
        request.setCodes(new String[]{"C1-D8"});
        SelectedProduct sp1 = new SelectedProduct();
        SelectedProduct sp2 = new SelectedProduct();
        request.setProducts(Arrays.asList(sp1, sp2));

        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(request));
        Assert.assertTrue(response.getStatus() == Response.Status.CONFLICT.getStatusCode());
        validateErrorResponse(response, ExceptionalCondition.Error.COUPON_INACTIVE);
    }

    /**
     * Seventh Exception validation is to check if the there are multiple exclusive coupons.
     * coupon 1 inclusive property is false that are mapped to code C1-D1.
     * coupon 2 inclusive property is false that are mapped to code C1-D2.
     * response status should be CONFLICT and error should be of type ExceptionalCondition.Error.MULTIPLE_EXCLUSIVE_COUPONS
     */
    @Test
    public void testMultipleExclusiveCouponsException() {
        WebTarget target = client.target(baseURI + "/cdr");
        CouponDiscountRequestCreateReq request = new CouponDiscountRequestCreateReq();
        request.setRequesterId(1);
        request.setCodes(new String[]{"C1-D1","C1-D2"});
        SelectedProduct sp1 = new SelectedProduct();
        SelectedProduct sp2 = new SelectedProduct();
        request.setProducts(Arrays.asList(sp1, sp2));

        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(request));
        Assert.assertTrue(response.getStatus() == Response.Status.CONFLICT.getStatusCode());
        validateErrorResponse(response, ExceptionalCondition.Error.MULTIPLE_EXCLUSIVE_COUPONS);
    }

    /**
     * Eighth Exception validation is to check if the product span count is out of range.
     * coupon code C1-D7 belongs the coupon 7.
     * For coupon 7 span is true and product min count is 1 and max count is 15
     * total selected product count is 16 which is outside total product count.
     * response status should be CONFLICT and error should be of type ExceptionalCondition.Error.PRODUCT_SPAN_COUNT_OUT_OF_RANGE
     */
    @Test
    public void testProductSpanCountOutOfRangeException() {
        WebTarget target = client.target(baseURI + "/cdr");
        CouponDiscountRequestCreateReq request = new CouponDiscountRequestCreateReq();
        request.setRequesterId(1);
        request.setCodes(new String[]{"C1-D7"});
        SelectedProduct sp1 = new SelectedProduct();
        SelectedProduct sp2 = new SelectedProduct();
        request.setProducts(Arrays.asList(sp1, sp2));

        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(request));
        Assert.assertTrue(response.getStatus() == Response.Status.CONFLICT.getStatusCode());
//        validateErrorResponse(response, ExceptionalCondition.Error.PRODUCT_SPAN_COUNT_OUT_OF_RANGE);
    }

    /**
     * Ninth Exception validation is to check if the product count is out of range.
     * coupon code C1-D1 belongs the coupon 1.
     * For coupon 1 span is false and product min count is 1 and max count is 10
     * total individual selected product count is 0 and 5 so product 1 which has 0 count, min count validation fails.
     * response status should be CONFLICT and error should be of type ExceptionalCondition.Error.PRODUCT_COUNT_OUT_OF_RANGE.
     */
    @Test
    public void testProductCountOutOfRangeException() {
        WebTarget target = client.target(baseURI + "/cdr");
        CouponDiscountRequestCreateReq request = new CouponDiscountRequestCreateReq();
        request.setRequesterId(1);
        request.setCodes(new String[]{"C1-D1"});
        SelectedProduct sp1 = new SelectedProduct();
        SelectedProduct sp2 = new SelectedProduct();
        request.setProducts(Arrays.asList(sp1, sp2));

        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(request));
        Assert.assertTrue(response.getStatus() == Response.Status.CONFLICT.getStatusCode());
//        validateErrorResponse(response, ExceptionalCondition.Error.PRODUCT_COUNT_OUT_OF_RANGE);
    }

    /**
     * Tenth Exception validation is to check if transaction value is out of range.
     * coupon code C1-D1 belongs the coupon 1 and the transaction min value is 100 and max count is 1000
     * response status should be CONFLICT and error should be of type ExceptionalCondition.Error.TRANSACTION_VALUE_OUT_OF_RANGE
     * unit cost of product 1 : 100
     * unit cost of product 2 : 250
     * total transaction cost : 100*5 + 250*3 = 1250 is outside acceptable range.
     * response status should be CONFLICT and error should be of type ExceptionalCondition.Error.TRANSACTION_VALUE_OUT_OF_RANGE
     */
    @Test
    public void testTransactionValueOutOfRangeException() {
        WebTarget target = client.target(baseURI + "/cdr");
        CouponDiscountRequestCreateReq request = new CouponDiscountRequestCreateReq();
        request.setRequesterId(1);
        request.setCodes(new String[]{"C1-D1"});
        SelectedProduct sp1 = new SelectedProduct();
        SelectedProduct sp2 = new SelectedProduct();
        request.setProducts(Arrays.asList(sp1, sp2));

        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(request));
        Assert.assertTrue(response.getStatus() == Response.Status.CONFLICT.getStatusCode());
        validateErrorResponse(response, ExceptionalCondition.Error.TRANSACTION_VALUE_OUT_OF_RANGE);
    }

    /**
     * Eleventh Exception validation check if No existing Discount rules are found for the coupon.
     * coupon code C1-D11 belongs to the coupon 11 and it has no Discounting rules.
     * response status should be INTERNAL_SERVER_ERROR.
     */
    @Test
    public void testInternalServerErrorExceptionWhenRulesDontExist() {
        WebTarget target = client.target(baseURI + "/cdr");
        CouponDiscountRequestCreateReq request = new CouponDiscountRequestCreateReq();
        request.setRequesterId(1);
        request.setCodes(new String[]{"C1-D11"});
        SelectedProduct sp1 = new SelectedProduct();
        SelectedProduct sp2 = new SelectedProduct();
        request.setProducts(Arrays.asList(sp1, sp2));

        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(request));
        Assert.assertTrue(response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }

    /**
     * Twelfth Exception validation check if No applicable rules are found for the coupon.
     * coupon code C1-D2 belongs to the coupon 2 and it has no applicable Discounting rules.
     * response status should be INTERNAL_SERVER_ERROR.
     */
    @Test
    public void testInternalServerErrorExceptionWhenRulesDontApply() {
        WebTarget target = client.target(baseURI + "/cdr");
        CouponDiscountRequestCreateReq request = new CouponDiscountRequestCreateReq();
        request.setRequesterId(1);
        request.setCodes(new String[]{"C1-D2"});
        SelectedProduct sp1 = new SelectedProduct();
        SelectedProduct sp2 = new SelectedProduct();
        request.setProducts(Arrays.asList(sp1, sp2));

        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(request));
        Assert.assertTrue(response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }

    /**
     * Thirteenth Exception validation check if No selected product and selected coupon are applicable.
     * coupon code C1-D1 belongs to the coupon 1.
     * As product 8 parent product is 11 and coupon 1 is not applicable to 8 or 11.
     * product 8 and 11's categories are 3,2 and coupon 1 is not applicable to 3 or 2.
     * response status should be CONFLICT and error should be of type ExceptionalCondition.Error.COUPON_INAPPLICABLE
     */
    @Test
    public void testInapplicableCouponException() {
        WebTarget target = client.target(baseURI + "/cdr");
        CouponDiscountRequestCreateReq request = new CouponDiscountRequestCreateReq();
        request.setRequesterId(1);
        request.setCodes(new String[]{"C1-D1"});
        SelectedProduct sp1 = new SelectedProduct();
        request.setProducts(Arrays.asList(sp1));

        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(request));
        Assert.assertTrue(response.getStatus() == Response.Status.CONFLICT.getStatusCode());
        validateErrorResponse(response, ExceptionalCondition.Error.COUPON_INAPPLICABLE);
    }

    /**
     * Fourteen Exception validation check if possible circular hierarchy detected for product.
     * coupon code C1-D1 belongs to the coupon 1.
     * product 10 has circular dependency.
     * response status should be INTERNAL_SERVER_ERROR.
     */
    @Test
    public void testInternalServerErrorExceptionWhenCircularHierarchyDetected() {
        WebTarget target = client.target(baseURI + "/cdr");
        CouponDiscountRequestCreateReq request = new CouponDiscountRequestCreateReq();
        request.setRequesterId(1);
        request.setCodes(new String[]{"C1-D1"});
        SelectedProduct sp1 = new SelectedProduct();
        request.setProducts(Arrays.asList(sp1));

        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(request));
        Assert.assertTrue(
                "Expected status code:" + Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() +
                        "; Received status code: " + response.getStatus(),
                response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }

    /**
     * Fifteenth Exception check if the the coupon discount request is invalid.
     * Coupon discount request id  : 1 has code id : 4 which was rejected.
     * response status should be CONFLICT and error should be of type ExceptionalCondition.Error.COUPON_DISCOUNT_REQUEST_INVALID
     */
    @Test
    public void testInvalidCouponDiscountRequestException() {
        // TODO This test case can be extended to cover the scenario when a request is created, and then updated
        // to use invaid code. Subsequent requested for discount amount will result in the exception
        WebTarget target = client.target(baseURI + "/cdr/1/discountAmt");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();
        Assert.assertTrue(response.getStatus() == Response.Status.CONFLICT.getStatusCode());
        validateErrorResponse(response, ExceptionalCondition.Error.COUPON_DISCOUNT_REQUEST_INVALID);
    }


    private void validateErrorResponse(Response response, ExceptionalCondition.Error expectedEnum) {
        for(String header : response.getHeaders().keySet()) {
            System.out.println(header + " : " + response.getHeaders().get(header));
            if(header.equals(COUPON_APP_ERROR_X_HEADER)) {
                String error = (String) response.getHeaders().get(header).get(0);
                String errorCode = error.split(ERROR_MSG_PART_DELIMITER)[0];
                ExceptionalCondition.Error errorEnum = ExceptionalCondition.Error.codeToEnum(errorCode);
                Assert.assertTrue(
                        "Expected :" + expectedEnum + ", Received :" + errorEnum,
                        errorEnum.equals(expectedEnum));
            }
        }
    }

    /**
     * This test case runs the scenario when a CDR is created successfully and subsequent discount amount
     * retrieved is as expected
     *
     * Following is the test data used
     * User selects a product '8' (association through category mapping), count '2', code 'C1-D1'
     * and gets appropriate discount
     * Discount intervals are for transaction value
     * transaction range (100 - 300) discount is 50
     * transaction range (301 - 900) discount is 100
     * transaction range (901 - 1000) discount is 150
     * product 1 unit price is 100
     * selected product count = 2; total cost = 100*2 = 200;
     * Discount should be 50
     */
    @Test
    public void testSuccessfulCouponApplication1() {

        WebTarget creationTarget = client.target(baseURI + "/cdr");

        String jsonCreationRequest =
                "{'userId':'1','products':[{'id':'8','count':8}],'codes':['C1-D1']}";

        CouponDiscountRequestCreateReq request = new CouponDiscountRequestCreateReq();
        request.setRequesterId(1);
        request.setCodes(new String[]{"C1-D1"});
        SelectedProduct sp4 = new SelectedProduct();
        request.setProducts(Arrays.asList(sp4));

        Response response = creationTarget.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(request));
        Assert.assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());

        for(String header:response.getHeaders().keySet()) {
            System.out.println(header +" : "+response.getHeaders().get(header));
            if(header.equals("Location")) {
                String location = (String) response.getHeaders().get(header).get(0);
                testApplicableDiscountRetrievalAvailability(location, 50);
            }
        }
    }

    private void testApplicableDiscountRetrievalAvailability(String location, int expectedDiscount) {
        close();
        init();
        WebTarget target = client.target(location+"/discountAmt");
        ApplicableDiscountResp resp = target.request(MediaType.APPLICATION_JSON_TYPE).get(ApplicableDiscountResp.class);
        Assert.assertTrue("Expected Discount :" + resp.getDiscountAmount() + ", Received Discount :" + expectedDiscount,
                resp.getDiscountAmount() == expectedDiscount);
    }

    /**
     * This test case runs the scenario when a CDR is created successfully and subsequent discount amount
     * retrieved is as expected
     *
     * User selects a product '8' (association through category mapping), count '2', code 'C1-D1'
     * and gets appropriate discount
     * Discount intervals are for transaction value
     * transaction range (100 - 300) discount is 50
     * transaction range (301 - 900) discount is 100
     * transaction range (901 - 1000) discount is 150
     * product 1 unit price is 100
     * selected product count = 6; total cost = 100*6 = 600;
     * Discount should be 100
     */
    @Test
    public void testSuccessfulCouponApplication2() {

        WebTarget creationTarget = client.target(baseURI + "/cdr");

        String jsonCreationRequest =
                "{'userId':'1','products':[{'id':'8','count':8}],'codes':['C1-D1']}";

        CouponDiscountRequestCreateReq request = new CouponDiscountRequestCreateReq();
        request.setRequesterId(1);
        request.setCodes(new String[]{"C1-D1"});
        SelectedProduct sp4 = new SelectedProduct();
        request.setProducts(Arrays.asList(sp4));

        Response response = creationTarget.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(request));
        Assert.assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());

        for(String header:response.getHeaders().keySet()) {
            System.out.println(header +" : "+response.getHeaders().get(header));
            if(header.equals("Location")) {
                String location = (String) response.getHeaders().get(header).get(0);
                testApplicableDiscountRetrievalAvailability(location, 100);
            }
        }
    }

    /**
     * This test case runs the scenario when a CDR is created successfully and subsequent discount amount
     * retrieved is as expected
     *
     * User selects a product '8' (association through category mapping), count '2', code 'C1-D1'
     * and gets appropriate discount
     * Discount intervals are for transaction value
     * transaction range (100 - 300) discount is 50
     * transaction range (301 - 900) discount is 100
     * transaction range (901 - 1000) discount is 150
     * product 1 unit price is 100
     * selected product count = 10; total cost = 100*10 = 1000;
     * Discount should be 150
     */
    @Test
    public void testSuccessfulCouponApplication3() {

        WebTarget creationTarget = client.target(baseURI + "/cdr");

        String jsonCreationRequest =
                "{'userId':'1','products':[{'id':'8','count':8}],'codes':['C1-D1']}";

        CouponDiscountRequestCreateReq request = new CouponDiscountRequestCreateReq();
        request.setRequesterId(1);
        request.setCodes(new String[]{"C1-D1"});
        SelectedProduct sp4 = new SelectedProduct();
        request.setProducts(Arrays.asList(sp4));

        Response response = creationTarget.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(request));
        Assert.assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());

        for(String header:response.getHeaders().keySet()) {
            System.out.println(header +" : "+response.getHeaders().get(header));
            if(header.equals("Location")) {
                String location = (String) response.getHeaders().get(header).get(0);
                testApplicableDiscountRetrievalAvailability(location, 150);
            }
        }
    }

    @After
    public void close() {
        if (client != null) {
            client.close();
        }
    }
}