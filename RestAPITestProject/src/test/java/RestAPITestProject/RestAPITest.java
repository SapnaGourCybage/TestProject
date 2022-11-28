package RestAPITestProject;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.BaseClass;
import utils.ExcelReader;
import utils.PropFileReader;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;


public class RestAPITest extends BaseClass {
    Properties prop;
    public ExtentReports report;
    public static String baseURL;
    String value;
    Map<String, String>map1;


    @BeforeTest
    public void  initialize() throws IOException {
        prop = PropFileReader.getConfigData();
        String reportPath = new File(System.getProperty("user.dir")).getAbsolutePath().toString().trim()+"\\reports\\";
        report = new ExtentReports(reportPath+this.getClass().getSimpleName()+".html", true);
        map1 = ExcelReader.getMapData().get("DataSheet1");
        value = map1.get("test1");
        System.out.println(value);
    }

    @Test(enabled=true,priority = 1)
    public void doPost() throws IOException {
        test = report.startTest("MerchantE - Rest API Test - POST method");
        test.log(LogStatus.INFO, "Test case execution started - Capitalizes every letter for input set using body");
        File jsonData = new File(System.getProperty("user.dir")+"\\src\\main\\java\\resources\\payload.json");
        baseURL= prop.getProperty("baseURLForPostReq");
        Response resp = given().
                baseUri(baseURL).contentType(ContentType.JSON).body(jsonData).
                when().
                post().then().log().all().extract().response();
        test.log(LogStatus.INFO, "Response received successfully" + resp.body().asString());
        ResponseBody body = resp.body();
        String rbdy = body.asString();
        JsonPath jpath = new JsonPath(rbdy);
        String output = jpath.getString("OUTPUT");
        test.log(LogStatus.INFO, "output return from response is" + output);
        int statusCodeActual = resp.getStatusCode();
        test.log(LogStatus.INFO, "Response status code is " + statusCodeActual);
        Assert.assertEquals(statusCodeActual, Integer.parseInt("200"),"Status code Validation");
        test.log(LogStatus.INFO, "Capitalized every letter for input set using body is successful");
        test.log(LogStatus.PASS, "Test is Passed");
    }

    @Test(enabled=true,priority = 2)
    public void doGet()
    {
        test = report.startTest("MerchantE - Rest API Test - GET method");
        test.log(LogStatus.INFO, "Test case execution started - List all dog breeds");
        baseURL= prop.getProperty("baseURLForGetReq");
        Response resp = given().
                baseUri(baseURL).
                when().
                get().then().log().all().extract().response();

        test.log(LogStatus.INFO, "Response received successfully" + resp.body().asString());
        JsonPath jsonPathEvaluator = resp.jsonPath();
        test.log(LogStatus.INFO, "Extracting value from Bulldog json node " + jsonPathEvaluator.get("message.bulldog"));
        int statusCodeActual = resp.getStatusCode();
        test.log(LogStatus.INFO, "Response status code is " + statusCodeActual);
        Assert.assertEquals(statusCodeActual, Integer.parseInt("200"),"Status code Validation");
        test.log(LogStatus.INFO, "Listed all dog breeds successfully");
        test.log(LogStatus.PASS, "Test is Passed");

    }

    @Test(enabled=true,priority = 3)
    public void doDelete()
    {
        test = report.startTest("MerchantE - Rest API Test - Delete Method");
        test.log(LogStatus.INFO, "Test case execution started - Delete Request");
        baseURL= prop.getProperty("baseURLForDeleteReq");
        Response resp = given().
                baseUri(baseURL).
                when().
                delete().then().log().all().extract().response();

        test.log(LogStatus.INFO, "Response received successfully" + resp.body().asString());
        JsonPath jsonPathEvaluator = resp.jsonPath();
        int statusCodeActual = resp.getStatusCode();
        test.log(LogStatus.INFO, "Response status code is " + statusCodeActual);
        Assert.assertEquals(statusCodeActual, Integer.parseInt("200"),"Status code Validation");
        test.log(LogStatus.INFO, "Resource deleted successfully");
        test.log(LogStatus.PASS, "Test is Passed");

    }

    @AfterMethod
    public void endTestMethod()
    {
        report.endTest(test);
    }

    @AfterSuite
    public void tearDown()
    {
        report.endTest(test);
        report.flush();
        report.close();
    }

}
