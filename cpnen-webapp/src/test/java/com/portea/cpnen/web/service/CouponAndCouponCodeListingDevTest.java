package com.portea.cpnen.web.service;


import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.junit.*;

import static org.junit.Assert.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Calendar;
import java.util.Date;

/**
 * This class contains JUnit tests to test various functionality of Portea Coupon Management System through
 * its REST API interface. For Coupon and Coupon Codes Listing Only.
 * To be able to execute these tests on localhost, add "127.0.0.1  coupons.localhost" (without quotes)
 * to hosts list in /etc/hosts file. Please note that the virtual host coupons.localhost must be
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
public class CouponAndCouponCodeListingDevTest {

    private Client client;
    private String baseURI = "http://coupons.localhost:8080/web";
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();

    @BeforeClass
    public static void commonSetup() {
        CpnenTestDataDb testDataDb = new CpnenTestDataDb();
        testDataDb.populateTestData();
    }

    @Before
    public void setup() {
        this.client = ClientBuilder.newClient();
    }

    @Test
    public void testDefaultCouponListing() {
        assertStatusAndTotalRecords("/rws/coupon/list", Response.Status.OK.getStatusCode(), "12");
    }

    @Test
    public void testCouponListingGivenName() {
        assertStatusAndTotalRecords("/rws/coupon/list?name=health coupon", Response.Status.OK.getStatusCode(), "1");
    }

    @Test
    public void testCouponListingGivenInvalidName() {
        assertStatusAndTotalRecords("/rws/coupon/list?name=bla bla", Response.Status.OK.getStatusCode(), "0");
    }

    @Test
    public void testCouponListingGivenCreationFrom() {
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        Long startDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/list?from=" + startDate, Response.Status.OK.getStatusCode(), "12");

    }

    @Test
    public void testCouponListingGivenCreationTill() {
        Long epoch = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/list?till=" + epoch, Response.Status.OK.getStatusCode(), "12");
    }

    @Test
    public void testCouponListingWithinCreationDates() {
        Long endDate = date.getTime();
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        Long startDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/list?from=" + startDate + "&till=" + endDate, Response.Status.OK.getStatusCode(), "12");
    }

    @Test
    public void testCouponListingGivenUpdateFrom() {
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        Long startDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/list?updateFrom=" + startDate, Response.Status.OK.getStatusCode(), "12");
    }

    @Test
    public void testCouponListingGivenUpdateTill() {
        calendar.add(Calendar.DATE, +1);
        date = calendar.getTime();
        Long endDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/list?updateTill=" + endDate, Response.Status.OK.getStatusCode(), "12");
    }

    @Test
    public void testCouponListingGivenDeactivateFrom() {
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        Long startDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/list?deactivateFrom=" + startDate, Response.Status.OK.getStatusCode(), "1");
    }

    @Test
    public void testCouponListingGivenDeactivateTill() {
        calendar.add(Calendar.DATE, +3);
        date = calendar.getTime();
        Long endDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/list?deactivateTill=" + endDate, Response.Status.OK.getStatusCode(), "1");
    }

    @Test
    public void testCouponListingWithinDeactivationTime() {
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        Long startDate = date.getTime();
        calendar.add(Calendar.DATE, +3);
        date = calendar.getTime();
        Long endDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/list?deactivateFrom=" + startDate + "&deactivateTill=" + endDate, Response.Status.OK.getStatusCode(), "1");
    }

    @Test
    public void testCouponListingGivenApplicabilityFrom() {
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        Long startDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/list?appDurationFrom=" + startDate, Response.Status.OK.getStatusCode(), "12");
    }

    @Test
    public void testCouponListingGivenApplicabilityTill() {
        calendar.add(Calendar.DATE, +3);
        date = calendar.getTime();
        Long endDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/list?appDurationTill=" + endDate, Response.Status.OK.getStatusCode(), "11");
    }

    @Test
    public void testCouponListingWithinApplicabilityTime() {
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        Long startDate = date.getTime();
        calendar.add(Calendar.DATE, +1);
        date = calendar.getTime();
        Long endDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/list?appDurationFrom=" + startDate + "&appDurationTill=" + endDate, Response.Status.OK.getStatusCode(), "12");
    }

    @Test
    public void testCouponListingGivenPublishedFrom() {
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        Long startDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/list?publishedFrom=" + startDate, Response.Status.OK.getStatusCode(), "9");
    }

    @Test
    public void testCouponListingGivenPublishTill() {
        calendar.add(Calendar.DATE, +1);
        date = calendar.getTime();
        Long endDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/list?publishedTill=" + endDate, Response.Status.OK.getStatusCode(), "9");
    }

    @Test
    public void testCouponListingGivenTransactionFrom() {
        assertStatusAndTotalRecords("/rws/coupon/list?transactionFrom=500", Response.Status.OK.getStatusCode(), "7");
    }

    @Test
    public void testCouponListingGivenTransactionTill() {
        assertStatusAndTotalRecords("/rws/coupon/list?transactionTill=1000", Response.Status.OK.getStatusCode(), "5");
    }

    @Test
    public void testCouponListingWithinTransactionAmount() {
        assertStatusAndTotalRecords("/rws/coupon/list?transactionFrom=100&transactionTill=499", Response.Status.OK.getStatusCode(), "5");
    }

    @Test
    public void testCouponListingGivenGlobalAsTrue() {
        assertStatusAndTotalRecords("/rws/coupon/list?global=true", Response.Status.OK.getStatusCode(), "11");
    }

    @Test
    public void testCouponListingGivenGlobalAsFalse() {
        assertStatusAndTotalRecords("/rws/coupon/list?global=false", Response.Status.OK.getStatusCode(), "1");
    }

    @Test
    public void testCouponListingGivenInclusiveAsTrue() {
        assertStatusAndTotalRecords("/rws/coupon/list?inclusive=true", Response.Status.OK.getStatusCode(), "9");
    }

    @Test
    public void testCouponListingGivenInclusiveAsFalse() {
        assertStatusAndTotalRecords("/rws/coupon/list?inclusive=false", Response.Status.OK.getStatusCode(), "3");
    }

    @Test
    public void testAllCouponsInDraftStatus() {
        assertStatusAndTotalRecords("/rws/coupon/list?draft=true", Response.Status.OK.getStatusCode(), "3");
    }

    @Test
    public void testAllCouponsInPublishedStatus() {
        assertStatusAndTotalRecords("/rws/coupon/list?published=true", Response.Status.OK.getStatusCode(), "9");
    }

    @Test
    public void testAllCouponsInDeactivateStatus() {
        assertStatusAndTotalRecords("/rws/coupon/list?deactivated=true", Response.Status.OK.getStatusCode(), "1");
    }

    @Test
    public void testCouponListingGivenAppTypeOneTime() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=ONE_TIME", Response.Status.OK.getStatusCode(), "2");
    }

    @Test
    public void testCouponListingGivenAppTypeOneTimePerUser() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=ONE_TIME_PER_USER", Response.Status.OK.getStatusCode(), "1");
    }

    @Test
    public void testCouponListingGivenAppTypeOneTimePerUserFifo() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=ONE_TIME_PER_USER_FIFO", Response.Status.OK.getStatusCode(), "1");
    }

    @Test
    public void testCouponListingGivenAppTypeManyTime() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=MANY_TIMES", Response.Status.OK.getStatusCode(), "4");
    }

    @Test
    public void testCouponListingGivenAppTypeNthTime() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=NTH_TIME", Response.Status.OK.getStatusCode(), "2");
    }

    @Test
    public void testCouponListingGivenAppTypeNthTimePerSubscription() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=NTH_TIME_PER_SUBSCRIPTION", Response.Status.OK.getStatusCode(), "1");
    }

    @Test
    public void testCouponListingGivenAppTypeNthTimeAbPerSubscription() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=NTH_TIME_AB_PER_SUBSCRIPTION", Response.Status.OK.getStatusCode(), "1");
    }

    @Test
    public void testListAllStaffCouponsForAppTypeNth() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=NTH_TIME,NTH_TIME_PER_SUBSCRIPTION,NTH_TIME_AB_PER_SUBSCRIPTION&actor=STAFF"
                , Response.Status.OK.getStatusCode(), "2");
    }

    @Test
    public void testListAllCustomerCouponsForAppTypeNth() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=NTH_TIME,NTH_TIME_PER_SUBSCRIPTION,NTH_TIME_AB_PER_SUBSCRIPTION&actor=CUSTOMER"
                , Response.Status.OK.getStatusCode(), "2");
    }

    @Test
    public void testListAllStaffCouponsForAppTypeOneTime() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=ONE_TIME,ONE_TIME_PER_USER,ONE_TIME_PER_USER_FIFO&actor=STAFF"
                , Response.Status.OK.getStatusCode(), "2");
    }

    @Test
    public void testListAllCustomerCouponsForAppTypeOneTime() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=ONE_TIME,ONE_TIME_PER_USER,ONE_TIME_PER_USER_FIFO&actor=CUSTOMER"
                , Response.Status.OK.getStatusCode(), "2");
    }

    @Test
    public void testListAllStaffCouponsForAppTypeManyTime() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=MANY_TIMES&actor=STAFF"
                , Response.Status.OK.getStatusCode(), "1");
    }

    @Test
    public void testListAllCustomerCouponsForAppTypeManyTime() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=MANY_TIMES&actor=CUSTOMER"
                , Response.Status.OK.getStatusCode(), "3");
    }

    @Test
    public void testListOneTimeSubscriptionCoupons() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=ONE_TIME,ONE_TIME_PER_USER,ONE_TIME_PER_USER_FIFO&contextType=SUBSCRIPTION"
                , Response.Status.OK.getStatusCode(), "2");
    }

    @Test
    public void testListOneTimeAppointmentCoupons() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=ONE_TIME,ONE_TIME_PER_USER,ONE_TIME_PER_USER_FIFO&contextType=APPOINTMENT"
                , Response.Status.OK.getStatusCode(), "2");
    }

    @Test
    public void testListNthTimeSubscriptionCoupons() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=NTH_TIME,NTH_TIME_PER_SUBSCRIPTION,NTH_TIME_AB_PER_SUBSCRIPTION&contextType=SUBSCRIPTION"
                , Response.Status.OK.getStatusCode(), "2");
    }

    @Test
    public void testListNthTimeAppointmentCoupons() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=NTH_TIME,NTH_TIME_PER_SUBSCRIPTION,NTH_TIME_AB_PER_SUBSCRIPTION&contextType=APPOINTMENT"
                , Response.Status.OK.getStatusCode(), "2");
    }

    @Test
    public void testListManyTimeSubscriptionCoupons() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=MANY_TIMES&contextType=SUBSCRIPTION"
                , Response.Status.OK.getStatusCode(), "1");
    }

    @Test
    public void testListManyTimeAppointmentCoupons() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=MANY_TIMES&contextType=APPOINTMENT"
                , Response.Status.OK.getStatusCode(), "3");
    }

    @Test
    public void testCouponListingGivenActorTypeStaff() {
        assertStatusAndTotalRecords("/rws/coupon/list?actor=STAFF", Response.Status.OK.getStatusCode(), "5");
    }

    @Test
    public void testCouponListingGivenActorTypeCustomer() {
        assertStatusAndTotalRecords("/rws/coupon/list?actor=CUSTOMER", Response.Status.OK.getStatusCode(), "7");
    }

    @Test
    public void testCouponListingGivenContextTypeSubscription() {
        assertStatusAndTotalRecords("/rws/coupon/list?contextType=SUBSCRIPTION", Response.Status.OK.getStatusCode(), "5");
    }

    @Test
    public void testCouponListingGivenContextTypeAppointment() {
        assertStatusAndTotalRecords("/rws/coupon/list?contextType=APPOINTMENT", Response.Status.OK.getStatusCode(), "7");
    }

    @Test
    public void testListDraftAndPublishedCoupons() {
        assertStatusAndTotalRecords("/rws/coupon/list?active=true", Response.Status.OK.getStatusCode(), "11");
    }

    @Test
    public void testListAllOneTimePublishedCoupons() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=ONE_TIME,ONE_TIME_PER_USER,ONE_TIME_PER_USER_FIFO&published=true"
                , Response.Status.OK.getStatusCode(), "3");
    }

    @Test
    public void testListAllNthTimePublishedCoupons() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=NTH_TIME,NTH_TIME_PER_SUBSCRIPTION,NTH_TIME_AB_PER_SUBSCRIPTION&published=true"
                , Response.Status.OK.getStatusCode(), "3");
    }

    @Test
    public void testListAllManyTimePublishedCoupons() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=MANY_TIMES&published=true"
                , Response.Status.OK.getStatusCode(), "3");
    }

    @Test
    public void testListAllOneTimeDraftCoupons() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=ONE_TIME,ONE_TIME_PER_USER,ONE_TIME_PER_USER_FIFO&draft=true"
                , Response.Status.OK.getStatusCode(), "1");
    }

    @Test
    public void testListAllNthTimeDraftCoupons() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=NTH_TIME,NTH_TIME_PER_SUBSCRIPTION,NTH_TIME_AB_PER_SUBSCRIPTION&draft=true"
                , Response.Status.OK.getStatusCode(), "1");
    }

    @Test
    public void testListAllManyTimeDraftCoupons() {
        assertStatusAndTotalRecords("/rws/coupon/list?couponAppType=MANY_TIMES&draft=true"
                , Response.Status.OK.getStatusCode(), "1");
    }

    @Test
    public void testListingOneTimeCouponsByCreationTime() {
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        Long startDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/list?from=" + startDate + "&couponAppType=ONE_TIME,ONE_TIME_PER_USER,ONE_TIME_PER_USER_FIFO"
                , Response.Status.OK.getStatusCode(), "4");
    }

    @Test
    public void testListingNthTimeCouponsByCreationTime() {
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        Long startDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/list?from=" + startDate + "&couponAppType=NTH_TIME,NTH_TIME_PER_SUBSCRIPTION,NTH_TIME_AB_PER_SUBSCRIPTION"
                , Response.Status.OK.getStatusCode(), "4");
    }

    @Test
    public void testListingManyTimeCouponsByCreationTime() {
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        Long startDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/list?from=" + startDate + "&couponAppType=MANY_TIMES"
                , Response.Status.OK.getStatusCode(), "4");
    }

    /**
     * It must say no rows selected
     */
    @Test
    public void testListDraftCouponsWithinPublishedDates() {
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        Long startDate = date.getTime();
        calendar.add(Calendar.DATE, +1);
        date = calendar.getTime();
        Long endDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/list?publishedFrom=" + startDate + "&publishedTill=" + endDate + "&draft=true", Response.Status.OK.getStatusCode(), "0");
    }

    @Test
    public void testDefaultListingOfAllCodes() {
        assertStatusAndTotalRecords("/rws/coupon/codes", Response.Status.OK.getStatusCode(), "9");
    }

    @Test
    public void testListingOfAllActiveCodes() {
        assertStatusAndTotalRecords("/rws/coupon/codes?active=true", Response.Status.OK.getStatusCode(), "8");
    }

    @Test
    public void testListingOfAllDeactivateCodes() {
        assertStatusAndTotalRecords("/rws/coupon/codes?deactivated=true", Response.Status.OK.getStatusCode(), "1");
    }

    @Test
    public void testCodeListingGivenChannelName() {
        assertStatusAndTotalRecords("/rws/coupon/codes?channel=facebook", Response.Status.OK.getStatusCode(), "6");
    }

    @Test
    public void testCodeListingGivenCodeName() {
        assertStatusAndTotalRecords("/rws/coupon/codes?name=PORTEA-01", Response.Status.OK.getStatusCode(), "1");
    }

    @Test
    public void testCodeListingGivenCreatedFrom() {
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        Long startDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/codes?from=" + startDate, Response.Status.OK.getStatusCode(), "9");
    }

    @Test
    public void testCodeListingGivenCreatedTill() {
        calendar.add(Calendar.DATE, +1);
        date = calendar.getTime();
        Long endDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/codes?till=" + endDate, Response.Status.OK.getStatusCode(), "9");
    }

    @Test
    public void testCodeListingWithinCreatedDates() {
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        Long startDate = date.getTime();
        calendar.add(Calendar.DATE, +1);
        date = calendar.getTime();
        Long endDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/codes?from=" + startDate + "&till=" + endDate, Response.Status.OK.getStatusCode(), "9");
    }

    @Test
    public void testCodeListingGivenDeactivatedFrom() {
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        Long startDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/codes?deactivateFrom=" + startDate, Response.Status.OK.getStatusCode(), "1");
    }

    @Test
    public void testCodeListingGivenDeactivatedTill() {
        calendar.add(Calendar.DATE, +3);
        date = calendar.getTime();
        Long endDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/codes?deactivateTill=" + endDate, Response.Status.OK.getStatusCode(), "1");
    }

    @Test
    public void testCodeListingWithinDeactivatedDates() {
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        Long startDate = date.getTime();
        calendar.add(Calendar.DATE, +2);
        date = calendar.getTime();
        Long endDate = date.getTime();
        assertStatusAndTotalRecords("/rws/coupon/codes?deactivateFrom=" + startDate + "&deactivateTill=" + endDate, Response.Status.OK.getStatusCode(), "1");
    }

    @Test
    public void testListingOfAllProducts() {
        try {
            WebTarget getTarget = client.target(baseURI + "/rws/products");
            Response getResponse = getTarget.request().get();

            assertEquals(Response.Status.OK.getStatusCode(), getResponse.getStatus());
            JSONArray jsonArray = new JSONArray(getResponse.readEntity(String.class));

            assertEquals(8, jsonArray.length());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }

    @Test
    public void testListingOfAllBrands() {
        try {
            WebTarget getTarget = client.target(baseURI + "/rws/brands");
            Response getResponse = getTarget.request().get();

            assertEquals(Response.Status.OK.getStatusCode(), getResponse.getStatus());
            JSONArray jsonArray = new JSONArray(getResponse.readEntity(String.class));

            assertEquals(9, jsonArray.length());
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
     * Asserts HTTP status code for a URI and assert total no of records retrieve which is returned
     * in response.
     *
     * @param url                  target URI to be hit.
     * @param expectedStatus       excepted HTTP status code
     * @param expectedTotalRecords excepted number of records to retrieve.
     */
    private void assertStatusAndTotalRecords(String url, int expectedStatus, String expectedTotalRecords) {
        try {
            WebTarget getTarget = client.target(baseURI + url);
            Response getResponse = getTarget.request().get();

            assertEquals(expectedStatus, getResponse.getStatus());

            JSONObject getResponseJsonObj = new JSONObject(getResponse.readEntity(String.class));
            String totalRecords = getResponseJsonObj.get("total").toString();

            assertEquals(expectedTotalRecords, totalRecords);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with exception " + e.getMessage());
        }
    }
}

