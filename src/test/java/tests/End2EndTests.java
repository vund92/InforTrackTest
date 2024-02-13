package tests;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import reporting.Setup;
import reqres.pojos.ReqresUser;
import reqres.pojos.ReqresUserResponse;
import reqres.services.Base;
import reqres.services.ReqresUserAPIs;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Listeners(reporting.Setup.class)
public class End2EndTests extends ReqresUserAPIs {
    public static String token = Base.token;
    public static ReqresUserResponse reqresUserResponse = Base.reqresUserResponse;

    @Test(description = "create_read_delete_a_user", groups = "CRUD")
    public void deleteUserAfterBeingCreated() throws IOException {
        String testMethodName = new Object(){}.getClass().getEnclosingMethod().getName();
        ExtentTest test = Setup.extentReports.createTest("Test Case: " + testMethodName);
        Setup.extentTest.set(test);

        Response creResponse = createUser(token, ReqresUser.builder().name(new Faker().name().fullName()).job(new Faker().job().title()).build());
        Assert.assertEquals(creResponse.statusCode(),201);

        String id = creResponse.jsonPath().get("id");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token + "Unauthorized");
        Response reaResponse = getUser(id,new HashMap<>(),new HashMap<>(),headers);
        Assert.assertEquals(reaResponse.statusCode(),200);

        Response delResponse = deleteUser(id, new HashMap<>(),new HashMap<>(),headers);
        Assert.assertEquals(delResponse.statusCode(),204);

        Response reReadResponse = getUser(id,new HashMap<>(),new HashMap<>(),headers);
        Assert.assertEquals(reReadResponse.statusCode(),404);

        test.pass("Test passed!");
    }

    @Test(description = "create_read_update_a_user", groups = "CRUD")
    public void updateUserAfterBeingCreated() throws IOException {
        String testMethodName = new Object(){}.getClass().getEnclosingMethod().getName();
        ExtentTest test = Setup.extentReports.createTest("Test Case: " + testMethodName);
        Setup.extentTest.set(test);

        Response creResponse = createUser(token, ReqresUser.builder().name(new Faker().name().fullName()).job(new Faker().job().title()).build());
        Assert.assertEquals(creResponse.statusCode(),201);

        String id = creResponse.jsonPath().get("id");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token + "Unauthorized");
        Response reaResponse = getUser(id,new HashMap<>(),new HashMap<>(),headers);
        Assert.assertEquals(reaResponse.statusCode(),200);

        Response updResponse = updateUser(id, token, ReqresUser.builder().name(new Faker().name().fullName()).job(new Faker().job().title()).build());
        Assert.assertEquals(updResponse.statusCode(),200);

        Response reReadResponse = getUser(id,new HashMap<>(),new HashMap<>(),headers);
        Assert.assertEquals(reReadResponse.statusCode(),200);

        test.pass("Test passed!");
    }
}
