package tests;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import reporting.Setup;
import reqres.dataProviders.LoginDataProvider;
import reqres.pojos.Login;
import reqres.services.ReqresUserAPIs;
import restUtils.AssertionUtils;
import utils.JsonUtils;

import java.io.IOException;
import java.util.*;

import static org.testng.Assert.assertEquals;

@Listeners(reporting.Setup.class)
public class LoginTests extends ReqresUserAPIs {
    private static Map<String, Object> successfulLoginResults;
    private static Map<String, Object> unSuccessfulLoginResults;

    @BeforeMethod(groups = "login_tests")
    static void getExpectedResults() throws IOException {
        String env = System.getProperty("env") == null ? "stage" : System.getProperty("env");
        successfulLoginResults = JsonUtils.getJsonDataAsMap("reqres/" + env + "/SuccessfulLoginResults.json");
        unSuccessfulLoginResults = JsonUtils.getJsonDataAsMap("reqres/" + env + "/unSuccessfulLoginResults.json");
    }

    @Test(dataProvider = "loginDataWithTestCaseName", description = "Login Tests", groups = "login_tests")
    public void loginTests(LinkedHashMap<String, Login> login) throws IOException {
        for (Map.Entry<String, Login> entry : login.entrySet()) {
            String key = entry.getKey();
            Login loginData = entry.getValue();

            ExtentTest test = Setup.extentReports.createTest("Test Case: " + key);
            Setup.extentTest.set(test);

            Response response = createLogin(loginData);
            Map<String, Object> expectedValueMap = new HashMap<>();
            switch (key) {
                case "Login Successfully":
                    assertEquals(response.statusCode(),200);
                    expectedValueMap.put("token", successfulLoginResults.get("token"));
                    break;
                case "Login Unsuccessfully":
                    assertEquals(response.statusCode(),400);
                    expectedValueMap.put("error", unSuccessfulLoginResults.get("error"));
                    break;
            }
            AssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap);
            test.pass("Test passed!");
        }
    }

    @DataProvider(name = "loginDataWithTestCaseName")
    public Iterator<LinkedHashMap<String, Login>> getCreateLoginDataWithoutRandomData() throws IOException {
        return LoginDataProvider.getCreateLoginDataWithoutRandomData().iterator();
    }
}

