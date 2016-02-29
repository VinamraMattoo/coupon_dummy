package com.portea.cpnen.web.service;


import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.junit.*;

import static org.junit.Assert.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

/**
 * This class contains JUnit test to test Coupon Dashboard charts population in Portea Coupon Management System through
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
public class CouponDashboardDevTest {

    private Client client;
    private String baseURL = "http://coupons.localhost:8080/web";
    private final static String CON_DRIVER = "com.mysql.jdbc.Driver";
    private final static String CON_DATABASE = "jdbc:mysql://localhost:3306/coupon_management";
    private final static String CON_USERNAME = "root";
    private final static String CON_PASSWORD = "root";
    private static Connection con = null;
    private static Statement statement = null;


    @BeforeClass
    public static void commonSetup() {
        try {
            Class.forName(CON_DRIVER);
            con = DriverManager.getConnection(CON_DATABASE, CON_USERNAME, CON_PASSWORD);
            CouponDashboardDevTest d = new CouponDashboardDevTest();
            d.refreshDatabase();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed with Exception " + e.getMessage());
        }
    }

    @Before
    public void setup() {
        this.client = ClientBuilder.newClient();
    }

    /*Pie Chart One*/
    @Test
    public void testNumOfActiveCpnsByActor() {
        try {
            WebTarget target = client.target(baseURL + "/rws/coupon/actor/details");
            Response response = target.request().get();
            JSONArray jsonArray = new JSONArray(response.readEntity(String.class));
            String exceptedActor = "CUSTOMER";
            String actualActor = null;
            String actualCount = null;
            String exceptedCount = "6";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                actualActor = jsonObject.getString("actorType");
                actualCount = jsonObject.getString("count");
            }
            assertEquals(exceptedActor, actualActor);
            assertEquals(exceptedCount, actualCount);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    /*Pie Chart One*/
    @Test
    public void testNumOfActiveCpnsByCategory() {
        try {
            WebTarget target = client.target(baseURL + "/rws/coupon/category/details");
            Response response = target.request().get();
            JSONArray jsonArray = new JSONArray(response.readEntity(String.class));
            String actualCategory = null;
            String actualCount = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                actualCategory = jsonObject.getString("couponCategory");
                actualCount = jsonObject.getString("count");
                assertEquals("ENGAGEMENT", actualCategory);
                assertEquals("2", actualCount);
                jsonObject = jsonArray.getJSONObject(3);
                actualCategory = jsonObject.getString("couponCategory");
                actualCount = jsonObject.getString("count");
                assertEquals("SALES", actualCategory);
                assertEquals("5", actualCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    /*Pie Chart One*/
    @Test
    public void testNumOfActiveCpnsByContextType() {
        try {
            WebTarget target = client.target(baseURL + "/rws/coupon/contextType/details");
            Response response = target.request().get();
            JSONArray jsonArray = new JSONArray(response.readEntity(String.class));
            String actualContextType = null;
            String actualCount = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                actualContextType = jsonObject.getString("contextType");
                actualCount = jsonObject.getString("count");
                assertEquals("APPOINTMENT", actualContextType);
                assertEquals("9", actualCount);
                jsonObject = jsonArray.getJSONObject(1);
                actualContextType = jsonObject.getString("contextType");
                actualCount = jsonObject.getString("count");
                assertEquals("SUBSCRIPTION", actualContextType);
                assertEquals("3", actualCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    /*Pie Chart One*/
    @Test
    public void testNumOfActiveCpnsByArea() {
        try {
            WebTarget target = client.target(baseURL + "/rws/coupon/area/details");
            Response response = target.request().get();
            JSONArray jsonArray = new JSONArray(response.readEntity(String.class));
            String actualArea = null;
            String actualCount = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                actualArea = jsonObject.getString("area");
                actualCount = jsonObject.getString("count");
                assertEquals("Hyderabad", actualArea);
                assertEquals("3", actualCount);
                jsonObject = jsonArray.getJSONObject(1);
                actualArea = jsonObject.getString("area");
                actualCount = jsonObject.getString("count");
                assertEquals("Bangalore", actualArea);
                assertEquals("1", actualCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    /*Pie Chart One*/
    @Test
    public void testNumOfActiveCpnsByDiscountRange() {
        try {
            WebTarget target = client.target(baseURL + "/rws/coupon/discountRange");
            Response response = target.request().get();
            JSONArray jsonArray = new JSONArray(response.readEntity(String.class));
            String actualDiscountRange = null;
            String actualCount = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                actualDiscountRange = jsonObject.getString("range");
                actualCount = jsonObject.getString("numberOfCoupons");
                assertEquals("0-250", actualDiscountRange);
                assertEquals("4", actualCount);
                jsonObject = jsonArray.getJSONObject(1);
                actualDiscountRange = jsonObject.getString("range");
                actualCount = jsonObject.getString("numberOfCoupons");
                assertEquals("250-500", actualDiscountRange);
                assertEquals("3", actualCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    /*Pie Chart Two*/
    @Test
    public void testDiscountCountByArea(){
        try {
            WebTarget target = client.target(baseURL + "/rws/couponDiscount/area/details");
            Response response = target.request().get();
            JSONArray jsonArray = new JSONArray(response.readEntity(String.class));
            String actualAreaDiscount = null;
            String actualCount = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                actualAreaDiscount = jsonObject.getString("area");
                actualCount = jsonObject.getString("count");
                assertEquals("Hyderabad", actualAreaDiscount);
                assertEquals("5", actualCount);
                jsonObject = jsonArray.getJSONObject(1);
                actualAreaDiscount = jsonObject.getString("area");
                actualCount = jsonObject.getString("count");
                assertEquals("Bangalore", actualAreaDiscount);
                assertEquals("3", actualCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    /*Pie Chart Two*/
    @Test
    public void testDiscountCountByReferrerType(){
        try {
            WebTarget target = client.target(baseURL + "/rws/couponDiscount/referrerType/details");
            Response response = target.request().get();
            JSONArray jsonArray = new JSONArray(response.readEntity(String.class));
            String actualReferrerDetails = null;
            String actualCount = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                actualReferrerDetails = jsonObject.getString("referrerType");
                actualCount = jsonObject.getString("count");
                assertEquals("B2B", actualReferrerDetails);
                assertEquals("6", actualCount);
                jsonObject = jsonArray.getJSONObject(1);
                actualReferrerDetails = jsonObject.getString("referrerType");
                actualCount = jsonObject.getString("count");
                assertEquals("B2C", actualReferrerDetails);
                assertEquals("5", actualCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    /*Pie Chart Two*/
    @Test
    public void testDiscountCountByBrand(){
        try {
            WebTarget target = client.target(baseURL + "/rws/couponDiscount/brand/details");
            Response response = target.request().get();
            JSONArray jsonArray = new JSONArray(response.readEntity(String.class));
            String actualBrandDetails = null;
            String actualCount = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                actualBrandDetails = jsonObject.getString("brand");
                actualCount = jsonObject.getString("count");
                assertEquals("Portea", actualBrandDetails);
                assertEquals("4", actualCount);
                jsonObject = jsonArray.getJSONObject(1);
                actualBrandDetails = jsonObject.getString("brand");
                actualCount = jsonObject.getString("count");
                assertEquals("Manipal", actualBrandDetails);
                assertEquals("2", actualCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    /*Pie Chart Three*/
    @Test
    public void testDiscountAmountGivenByArea(){
        try {
            WebTarget target = client.target(baseURL + "/rws/coupon/area/discountDetails");
            Response response = target.request().get();
            JSONArray jsonArray = new JSONArray(response.readEntity(String.class));
            String actualAreadDetails = null;
            String actualDiscountGiven = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                actualAreadDetails = jsonObject.getString("area");
                actualDiscountGiven = jsonObject.getString("discountGiven");
                assertEquals("Hyderabad", actualAreadDetails);
                assertEquals("99.0", actualDiscountGiven);
                jsonObject = jsonArray.getJSONObject(1);
                actualAreadDetails = jsonObject.getString("area");
                actualDiscountGiven = jsonObject.getString("discountGiven");
                assertEquals("Bangalore", actualAreadDetails);
                assertEquals("67.0", actualDiscountGiven);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    /*Pie Chart Three*/
    @Test
    public void testDiscountAmountGivenByBrand(){
        try {
            WebTarget target = client.target(baseURL + "/rws/coupon/brand/discountDetails");
            Response response = target.request().get();
            JSONArray jsonArray = new JSONArray(response.readEntity(String.class));
            String actualBrandDetails = null;
            String actualDiscountGiven = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                actualBrandDetails = jsonObject.getString("brand");
                actualDiscountGiven = jsonObject.getString("discountGiven");
                assertEquals("Portea", actualBrandDetails);
                assertEquals("90.0", actualDiscountGiven);
                jsonObject = jsonArray.getJSONObject(1);
                actualBrandDetails = jsonObject.getString("brand");
                actualDiscountGiven = jsonObject.getString("discountGiven");
                assertEquals("Manipal", actualBrandDetails);
                assertEquals("31.0", actualDiscountGiven);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    /*Histogram One*/
    @Test
    public void testCpnDiscMinMaxDetailsDayByDay(){
        try {
            WebTarget target = client.target(baseURL + "/rws/couponDiscount/details");
            Response response = target.request().get();
            JSONObject jsonObject = new JSONObject(response.readEntity(String.class));
            JSONArray jsonArray = jsonObject.getJSONArray("discountData");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                String actualMinDiscount = jsonObject1.getString("minDiscount");
                String actualMaxDiscount = jsonObject1.getString("maxDiscount");
                String actualAvgDiscount = jsonObject1.getString("avgDiscount");
                assertEquals("10.0", actualMinDiscount);
                assertEquals("45.0", actualMaxDiscount);
                assertEquals("21.454545454545453", actualAvgDiscount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    /*Histrogram Two*/
    @Test
    public void testCpnDiscStatusDetailsDayByDay(){
        try {
            WebTarget target = client.target(baseURL + "/rws/couponDiscount/status/details");
            Response response = target.request().get();
            JSONObject jsonObject = new JSONObject(response.readEntity(String.class));
            JSONArray jsonArray = jsonObject.getJSONArray("discountData");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                String actualAppliedCDR = jsonObject1.getString("applied");
                assertEquals("11", actualAppliedCDR);
                jsonObject1 = jsonArray.getJSONObject(1);
                String actualReqCDR = jsonObject1.getString("requested");
                String actualCndCDR = jsonObject1.getString("cancelled");
                assertEquals("6", actualReqCDR);
                assertEquals("4", actualCndCDR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    /*Histrogram Three*/
    @Test
    public void testCpnExpiryDistributionWeekByWeek (){
        try {
            WebTarget target = client.target(baseURL + "/rws/couponExpiry/details");
            Response response = target.request().get();
            JSONObject jsonObject = new JSONObject(response.readEntity(String.class));
            JSONArray jsonArray = jsonObject.getJSONArray("weeklyData");
            String actualCouponCount = null;
            String actualRange = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                actualCouponCount = jsonObject1.getString("couponsCount");
                actualRange = jsonObject1.getString("range");
                assertEquals("4", actualCouponCount);
                assertEquals("17/1 - 24/1", actualRange);
                JSONObject jsonObject2 = jsonArray.getJSONObject(1);
                actualCouponCount = jsonObject2.getString("couponsCount");
                actualRange = jsonObject2.getString("range");
                assertEquals("4", actualCouponCount);
                assertEquals("24/1 - 2/2", actualRange);
            }
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

    @AfterClass
    public static void shutDown() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Method contains the filepaths which has the SQL queries required for refreshing the database */
    private void refreshDatabase() {
        String filePathForRemoveTables = "/com/portea/cpnen/web/service/Remove-Complete-Data.DEV.sql";
        String filepathForPopulateTables = "/com/portea/cpnen/web/service/Populate-Test-Data.DEV.sql";

        updateDatabase(filePathForRemoveTables);
        updateDatabase(filepathForPopulateTables);
    }

    /*Method extracts the SQL queries from the files coming as the parameter and updates the database*/
    public void updateDatabase(String filePath) {
        String s;
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            statement = con.createStatement();
            br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(filePath)));
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            String[] inst = sb.toString().split(";");

            for (int i = 0; i < inst.length; i++) {
                if (!inst[i].trim().equals("")) {
                    statement.executeUpdate(inst[i]);
                    System.out.println(inst[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
