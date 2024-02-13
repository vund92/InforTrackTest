package tests;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import reporting.Setup;
import reqres.pojos.ReqresUserResponse;
import reqres.services.Base;
import reqres.services.ReqresUserAPIs;
import utils.RandomDataGenerator_Reqres;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Listeners(reporting.Setup.class)
public class DeleteAUserTests extends ReqresUserAPIs {
    public static String token = Base.token;
    public static ReqresUserResponse reqresUserResponse = Base.reqresUserResponse;

    @BeforeMethod(groups = "read_a_user_tests")
    static void setUp() throws IOException {
    }

    @Test(description = "204 - Delete an existing user", groups = "create_a_user_tests")
    public void deleteAnExistingUserTests() throws IOException {
        String testMethodName = new Object(){}.getClass().getEnclosingMethod().getName();
        ExtentTest test = Setup.extentReports.createTest("Test Case: " + testMethodName);
        Setup.extentTest.set(test);

        String userId = String.valueOf(reqresUserResponse.getData().getId());
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);

        Response response = deleteUser(userId,new HashMap<>(),new HashMap<>(),headers);
        System.out.println(response.prettyPrint());

        Assert.assertEquals(response.statusCode(),204);
        test.pass("Test passed!");
    }

    @Test(description = "404 - Delete non-existing user", groups = "create_a_user_tests")
    public void deleteNonExistingUserTests() throws IOException {
        String testMethodName = new Object(){}.getClass().getEnclosingMethod().getName();
        ExtentTest test = Setup.extentReports.createTest("Test Case: " + testMethodName);
        Setup.extentTest.set(test);

        String userId = RandomDataGenerator_Reqres.getRandomAlphabets(2);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);

        Response response = deleteUser(userId,new HashMap<>(),new HashMap<>(),headers);

        Assert.assertEquals(response.statusCode(),404);
        test.pass("Test passed!");
    }

    @Test(description = "401 - Unauthorized error response when authentication token is missing/wrong", groups = "create_a_user_tests")
    public void deleteUserWithUnauthorizedPermission() throws IOException {
        String testMethodName = new Object(){}.getClass().getEnclosingMethod().getName();
        ExtentTest test = Setup.extentReports.createTest("Test Case: " + testMethodName);
        Setup.extentTest.set(test);

        String userId = String.valueOf(reqresUserResponse.getData().getId());
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token + "Unauthorized");

        Response response = deleteUser(userId,new HashMap<>(),new HashMap<>(),headers);

        Assert.assertEquals(response.statusCode(),401);
        test.pass("Test passed!");
    }
}
