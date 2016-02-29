package com.portea.cpnen.web.service;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * This class contains automated tests to check coupon core aspects like
 * Coupon Creation, Code Creation, Coupon Validity Extension, Coupon
 * Deactivation etc making use of Selenium WebDriver API.
 * While running these test cases u should not minimize or close the browser.
 */
public class SeleniumWebDriverTest {

    private org.openqa.selenium.WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
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
            SeleniumWebDriverTest d = new SeleniumWebDriverTest();
            d.refreshDatabase();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed with Exception " + e.getMessage());
        }
    }

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://coupons.localhost:8080/web";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testPublishCpnAndAddCodeWithWebDriver() {
        try {
            driver.get(baseUrl);
            driver.manage().window().maximize();
            driver.findElement(By.id("username")).clear();
            driver.findElement(By.id("username")).sendKeys("tester");
            driver.findElement(By.id("psswd")).clear();
            driver.findElement(By.id("psswd")).sendKeys("pass");
            driver.findElement(By.id("loginButton")).click();
            driver.findElement(By.id("create_new_coupon")).click();
            driver.findElement(By.id("name")).clear();
            String couponName = generateRandomCouponName();
            driver.findElement(By.id("name")).sendKeys(couponName);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = new Date();
            String applicableFrom = dateFormat.format(date);
            Thread.sleep(2000);
            driver.findElement(By.id("applicableFrom")).clear();
            driver.findElement(By.id("applicableFrom")).sendKeys(applicableFrom);
            Thread.sleep(2000);
            date = new Date(System.currentTimeMillis() + 99999999);
            String applicableTill = dateFormat.format(date);
            driver.findElement(By.id("applicableTill")).clear();
            driver.findElement(By.id("applicableTill")).sendKeys(applicableTill);
            Thread.sleep(2000);
            driver.findElement(By.id("transactionValMax")).clear();
            driver.findElement(By.id("transactionValMax")).sendKeys("1000");
            Thread.sleep(2000);
            driver.findElement(By.id("transactionValMin")).clear();
            driver.findElement(By.id("transactionValMin")).sendKeys("200");
            Thread.sleep(2000);
            driver.findElement(By.id("discountAmountMax")).clear();
            driver.findElement(By.id("discountAmountMax")).sendKeys("100");
            Thread.sleep(2000);
            driver.findElement(By.id("discountAmountMin")).clear();
            driver.findElement(By.id("discountAmountMin")).sendKeys("10");
            Thread.sleep(2000);
            new Select(driver.findElement(By.id("contextType"))).selectByVisibleText("SUBSCRIPTION");
            Thread.sleep(2000);
            driver.findElement(By.id("description")).clear();
            driver.findElement(By.id("description")).sendKeys("Dummy coupon");
            Thread.sleep(2000);
            new Select(driver.findElement(By.id("couponCategory"))).selectByVisibleText("Sales");
            Thread.sleep(2000);
            new Select(driver.findElement(By.id("actorType"))).selectByVisibleText("STAFF");
            Thread.sleep(2000);
            new Select(driver.findElement(By.id("applicationType"))).selectByVisibleText("MANY TIMES");
            Thread.sleep(2000);
            driver.findElement(By.linkText("Mappings")).click();
            Thread.sleep(2000);
            driver.findElement(By.name("btSelectItem")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("(//input[@name='btSelectItem'])[2]")).click();
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("#mappingTable > tbody > tr > td.bs-checkbox > input[name=\"btSelectItem\"]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("(//input[@name='btSelectItem'])[17]")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("createBrandsReferralsLink")).click();
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("#referrersTable > tbody > tr > td.bs-checkbox > input[name=\"btSelectItem\"]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("(//input[@name='btSelectItem'])[26]")).click();
            Thread.sleep(2000);
            driver.findElement(By.linkText("Rules")).click();
            Thread.sleep(2000);
            new Select(driver.findElement(By.id("ruleType"))).selectByVisibleText("PERCENTAGE");
            driver.findElement(By.id("discountValue")).clear();
            driver.findElement(By.id("discountValue")).sendKeys("20");
            Thread.sleep(2000);
            driver.findElement(By.id("ruleDesc")).clear();
            driver.findElement(By.id("ruleDesc")).sendKeys("20 % discount");
            Thread.sleep(2000);
            driver.findElement(By.xpath("//submit[@onclick=\"submitCreateCoupon('publish')\"]")).click();
            Thread.sleep(2000);
            driver.findElement(By.linkText("here")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("generateCode")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("codeName")).clear();
            driver.findElement(By.id("codeName")).sendKeys("PorteaYeppi");
            Thread.sleep(2000);
            driver.findElement(By.id("codeChannel")).clear();
            driver.findElement(By.id("codeChannel")).sendKeys("Online");
            Thread.sleep(2000);
            driver.findElement(By.xpath("(//button[@type='button'])[34]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("(//a[contains(text(),'here')])[2]")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("showCouponBtn")).click();
            Thread.sleep(2000);
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("scroll(0, 800);");
            Thread.sleep(3000);
            jse.executeScript("scroll(0, -800);");
            Thread.sleep(4000);
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            Thread.sleep(2000);
            assertTrue(closeAlertAndGetItsText().matches("^Do you really want to logout[\\s\\S]$"));
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Message " + e.getMessage());
        }
    }

    @Test
    public void testQuickCouponCreationWithWebDriver() {
        try {
            driver.get(baseUrl);
            driver.manage().window().maximize();
            driver.findElement(By.id("username")).clear();
            driver.findElement(By.id("username")).sendKeys("tester");
            driver.findElement(By.id("psswd")).clear();
            driver.findElement(By.id("psswd")).sendKeys("pass");
            driver.findElement(By.id("loginButton")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//button[@onclick='hideOtherDivs(10)']")).click();
            driver.findElement(By.id("fast_code")).clear();
            String couponName = generateRandomCouponName();
            driver.findElement(By.id("fast_code")).sendKeys(couponName + "Portea");
            Thread.sleep(2000);
            driver.findElement(By.id("fast_name")).clear();
            driver.findElement(By.id("fast_name")).sendKeys(couponName);
            Thread.sleep(2000);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = new Date();
            String applicableFrom = dateFormat.format(date);
            Thread.sleep(2000);
            driver.findElement(By.id("fast_applicableFrom")).clear();
            driver.findElement(By.id("fast_applicableFrom")).sendKeys(applicableFrom);
            Thread.sleep(2000);
            date = new Date(System.currentTimeMillis() + 99999999);
            String applicableTill = dateFormat.format(date);
            driver.findElement(By.id("fast_applicableTill")).clear();
            driver.findElement(By.id("fast_applicableTill")).sendKeys(applicableTill);
            Thread.sleep(2000);
            driver.findElement(By.id("fast_discountAmountMax")).clear();
            driver.findElement(By.id("fast_discountAmountMax")).sendKeys("1000");
            Thread.sleep(2000);
            driver.findElement(By.id("fast_discountAmountMin")).clear();
            driver.findElement(By.id("fast_discountAmountMin")).sendKeys("500");
            Thread.sleep(2000);
            new Select(driver.findElement(By.id("fast_couponCategory"))).selectByVisibleText("Marketing");
            Thread.sleep(2000);
            driver.findElement(By.id("fast_inclusive")).click();
            new Select(driver.findElement(By.id("fast_actorType"))).selectByVisibleText("STAFF");
            Thread.sleep(2000);
            driver.findElement(By.id("fast_channel")).clear();
            driver.findElement(By.id("fast_channel")).sendKeys("Facebook");
            Thread.sleep(2000);
            new Select(driver.findElement(By.id("fast_contextType"))).selectByVisibleText("SUBSCRIPTION");
            Thread.sleep(2000);
            new Select(driver.findElement(By.id("fast_applicationType"))).selectByVisibleText("MANY TIMES");
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("#fastMappingsPanel > a[title=\"Click to select mappings\"]")).click();
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("#fastAreaTable > tbody > tr > td.bs-checkbox > input[name=\"btSelectItem\"]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("(//input[@name='btSelectItem'])[77]")).click();
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("#fastMappingTable > tbody > tr > td.bs-checkbox > input[name=\"btSelectItem\"]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("(//input[@name='btSelectItem'])[81]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("(//input[@name='btSelectItem'])[82]")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("fastBrandsReferralsLink")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("(//input[@name='btSelectItem'])[104]")).click();
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("#fastRulesPanel > a[title=\"Click to define discount rule\"]")).click();
            Thread.sleep(2000);
            new Select(driver.findElement(By.id("fast_ruleType"))).selectByVisibleText("PERCENTAGE");
            Thread.sleep(2000);
            driver.findElement(By.id("fast_discountValue")).clear();
            driver.findElement(By.id("fast_discountValue")).sendKeys("52");
            Thread.sleep(2000);
            driver.findElement(By.id("fastSubmitId")).click();
            driver.findElement(By.xpath("(//a[contains(text(),'here')])[2]")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("showCouponBtn")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//button[@onclick='hideOtherDivs(1)']")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            Thread.sleep(2000);
            assertTrue(closeAlertAndGetItsText().matches("^Do you really want to logout[\\s\\S]$"));
            Thread.sleep(4000);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure With Exception " + e.getMessage());
        }
    }

    /**
     * This method tests extending the coupon validity and deactivating the created coupon
     */
    @Test
    public void testCpnOperationsWithWebDriver() {
        try {
            driver.get(baseUrl);
            driver.manage().window().maximize();
            driver.findElement(By.id("username")).clear();
            driver.findElement(By.id("username")).sendKeys("tester");
            Thread.sleep(2000);
            driver.findElement(By.id("psswd")).clear();
            driver.findElement(By.id("psswd")).sendKeys("pass");
            Thread.sleep(2000);
            driver.findElement(By.id("loginButton")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("create_new_coupon")).click();
            driver.findElement(By.id("name")).clear();
            String couponName = generateRandomCouponName();
            driver.findElement(By.id("name")).sendKeys(couponName);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = new Date();
            String applicableFrom = dateFormat.format(date);
            Thread.sleep(2000);
            driver.findElement(By.id("applicableFrom")).clear();
            driver.findElement(By.id("applicableFrom")).sendKeys(applicableFrom);
            Thread.sleep(2000);
            date = new Date(System.currentTimeMillis() + 99999999);
            String applicableTill = dateFormat.format(date);
            driver.findElement(By.id("applicableTill")).clear();
            driver.findElement(By.id("applicableTill")).sendKeys(applicableTill);
            Thread.sleep(2000);
            driver.findElement(By.id("transactionValMax")).clear();
            driver.findElement(By.id("transactionValMax")).sendKeys("1000");
            Thread.sleep(2000);
            driver.findElement(By.id("transactionValMin")).clear();
            driver.findElement(By.id("transactionValMin")).sendKeys("200");
            Thread.sleep(2000);
            driver.findElement(By.id("discountAmountMax")).clear();
            driver.findElement(By.id("discountAmountMax")).sendKeys("100");
            Thread.sleep(2000);
            driver.findElement(By.id("discountAmountMin")).clear();
            driver.findElement(By.id("discountAmountMin")).sendKeys("10");
            Thread.sleep(2000);
            new Select(driver.findElement(By.id("contextType"))).selectByVisibleText("SUBSCRIPTION");
            Thread.sleep(2000);
            driver.findElement(By.id("description")).clear();
            driver.findElement(By.id("description")).sendKeys("Dummy coupon");
            Thread.sleep(2000);
            new Select(driver.findElement(By.id("couponCategory"))).selectByVisibleText("Sales");
            Thread.sleep(2000);
            driver.findElement(By.id("createGlobal")).click();
            Thread.sleep(2000);
            new Select(driver.findElement(By.id("actorType"))).selectByVisibleText("STAFF");
            Thread.sleep(2000);
            new Select(driver.findElement(By.id("applicationType"))).selectByVisibleText("MANY TIMES");
            Thread.sleep(2000);
            driver.findElement(By.linkText("Rules")).click();
            Thread.sleep(2000);
            new Select(driver.findElement(By.id("ruleType"))).selectByVisibleText("PERCENTAGE");
            driver.findElement(By.id("discountValue")).clear();
            driver.findElement(By.id("discountValue")).sendKeys("20");
            Thread.sleep(2000);
            driver.findElement(By.id("ruleDesc")).clear();
            driver.findElement(By.id("ruleDesc")).sendKeys("20 % discount");
            Thread.sleep(2000);
            driver.findElement(By.xpath("//submit[@onclick=\"submitCreateCoupon('publish')\"]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//button[@onclick='hideOtherDivs(1)']")).click();
            Thread.sleep(2000);
            driver.findElement(By.linkText(couponName)).click();
            Thread.sleep(2000);
            driver.findElement(By.id("extendAppTill")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("extendedApplicability")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@id='extendValidity']/div/div/div[2]/div[2]/div/ul/li/div/div/table/tbody/tr[3]/td[7]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("(//button[@type='button'])[31]")).click();
            Thread.sleep(2000);
            driver.findElement(By.linkText("here")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("deactivateCpn")).click();
            Thread.sleep(2000);
            assertTrue(closeAlertAndGetItsText().matches("^Do you really want to deactivate[\\s\\S]$"));
            Thread.sleep(2000);
            driver.findElement(By.linkText("here")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            Thread.sleep(2000);
            assertTrue(closeAlertAndGetItsText().matches("^Do you really want to logout[\\s\\S]$"));
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failure with Exception " + e.getMessage());
        }
    }

    private static String generateRandomCouponName() {
        return "Coupon" + (new Random()).nextInt();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
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

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
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
