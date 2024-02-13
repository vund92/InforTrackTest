package tests;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import reporting.Setup;
import reqres.dataProviders.UserDataProvider;
import reqres.pojos.ReqresUser;
import reqres.pojos.ReqresUserResponse;
import reqres.services.Base;
import reqres.services.ReqresUserAPIs;
import restUtils.AssertionUtils;
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
public class UpdateAUserTests extends ReqresUserAPIs {
    public static String token = Base.token;
    public static ReqresUserResponse reqresUserResponse = Base.reqresUserResponse;

    @Test(dataProvider = "updateUserDataWithTestCaseName", description = "Create A User Tests", groups = "create_a_user_tests")
    public void updateAUserTests(LinkedHashMap<String, Object> reqresUser) throws IOException {
        File schemaFile = new File(JsonUtils.getSchemaFileByName(Base.env,"schema-create-user-response-body"));
        for (Map.Entry<String, Object> entry : reqresUser.entrySet()) {
            String key = entry.getKey();
            Object reqresUserData = entry.getValue();

            ExtentTest test = Setup.extentReports.createTest("Test Case: " + key);
            Setup.extentTest.set(test);

            String userId = String.valueOf(reqresUserResponse.getData().getId());
            Response response = updateUser(userId, token, reqresUserData);
            Map<String, Object> expectedValueMap = new HashMap<>();
            switch (key) {
                case "Update User Successful - Valid Values to Required Fields":
                    assertEquals(response.statusCode(), 200);
                    assertThat(response.jsonPath().getString("updatedAt"), Matchers.matchesRegex(AssertionUtils.createdAtRegexPattern));
                    JsonSchemaValidator.matchesJsonSchema(schemaFile);
                    expectedValueMap.put("name", ((ReqresUser) reqresUserData).getName());
                    expectedValueMap.put("job", ((ReqresUser) reqresUserData).getJob());
                    break;
                case "Update User Unsuccessful - Empty Required Field", "Update User Unsuccessful - Do not send Required Field":
                    assertEquals(response.statusCode(), 400);
                    break;
            }
            AssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap);
            test.pass("Test passed!");
        }
    }

    @DataProvider(name = "updateUserDataWithTestCaseName")
    public Iterator<LinkedHashMap<String, Object>> getUpdateUserDataWithRandomStringData() throws IOException {
        return UserDataProvider.getUpdateUserDataWithRandomStringData().iterator();
    }
}
