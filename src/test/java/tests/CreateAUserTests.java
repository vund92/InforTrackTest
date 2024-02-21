package tests;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import reporting.Setup;
import reqres.dataProviders.UserDataProvider;
import reqres.pojos.ReqresUser;
import reqres.services.Base;
import reqres.services.ReqresUserAPIs;
import restUtils.AssertionUtils;
import io.restassured.module.jsv.JsonSchemaValidator;
import utils.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

@Listeners(reporting.Setup.class)
public class CreateAUserTests extends ReqresUserAPIs {
    public static String token = Base.token;
    @Test(dataProvider = "createUserDataWithTestCaseName", description = "Create A User Tests", groups = "create_a_user_tests")
    public void createAUserTests(LinkedHashMap<String, Object> reqresUser) throws IOException {
        File schemaFile = new File(JsonUtils.getSchemaFileByName(Base.env,"schema-create-user-response-body"));
        for (Map.Entry<String, Object> entry : reqresUser.entrySet()) {
            String key = entry.getKey();
            Object reqresUserData = entry.getValue();

            ExtentTest test = Setup.extentReports.createTest("Test Case: " + key);
            Setup.extentTest.set(test);

            Response response = createUser(token, reqresUserData);
            Map<String, Object> expectedValueMap = new HashMap<>();
            switch (key) {
                case "Create User Successful - Valid Values to Required Fields":
                    assertEquals(response.statusCode(), 201);
                    assertThat(response.jsonPath().getString("id"), Matchers.notNullValue());
                    assertThat(response.jsonPath().getString("createdAt"), Matchers.matchesRegex(AssertionUtils.createdAtRegexPattern));
                    response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
                    expectedValueMap.put("name", ((ReqresUser) reqresUserData).getName());
                    expectedValueMap.put("job", ((ReqresUser) reqresUserData).getJob());
                    break;
                case "Create User Unsuccessful - Empty Required Field", "Create User Unsuccessful - Do not send Required Field":
                    assertEquals(response.statusCode(), 400);
                    break;
            }
            AssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap);
            test.pass("Test passed!");
        }
    }

    @DataProvider(name = "createUserDataWithTestCaseName")
    public Iterator<LinkedHashMap<String, Object>> getCreateUserDataWithRandomStringData() throws IOException {
        return UserDataProvider.getCreateUserDataWithRandomStringData().iterator();
    }
}
