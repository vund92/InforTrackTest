//package reqres;
//
//import com.aventstack.extentreports.ExtentTest;
//import io.restassured.response.Response;
//import org.testng.annotations.*;
//import reqres.dataProviders.CreateUserDataProvider;
//import reqres.pojos.ReqresUser;
//import reporting.Setup;
//import restUtils.AssertionUtils;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import static org.testng.Assert.assertEquals;
//
//@Listeners(reporting.Setup.class)
//public class ReadAUserTests extends ReqresUserAPIs {
//    public static String token;
//
//    static {
//        try {
//            token = getToken();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test(dataProvider = "createUserDataWithTestCaseName", description = "Create A User Tests", groups = "create_a_user_tests")
//    public void createAUserTests(LinkedHashMap<String, ReqresUser> reqresUser) throws IOException {
//        for (Map.Entry<String, ReqresUser> entry : reqresUser.entrySet()) {
//            String key = entry.getKey();
//            ReqresUser reqresUserData = entry.getValue();
//
//            ExtentTest test = Setup.extentReports.createTest("Test Case: " + key);
//            Setup.extentTest.set(test);
//
//            Response response = createUser(token, reqresUserData);
//            Map<String, Object> expectedValueMap = new HashMap<>();
//            switch (key) {
//                case "Create User Successful - Valid Values to Required Fields":
//                    assertEquals(response.statusCode(), 201);
//                    expectedValueMap.put("name", reqresUserData.getName());
//                    expectedValueMap.put("job", reqresUserData.getJob());
//                    break;
//                case "Create User Unsuccessful - Empty Required Field", "Create User Unsuccessful - Do not send Required Field":
//                    assertEquals(response.statusCode(), 400);
//                    expectedValueMap.put("name", reqresUserData.getName());
//                    expectedValueMap.put("job", reqresUserData.getJob());
//                    break;
//            }
//            AssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap);
//            test.pass("Test passed");
//        }
//    }
//
//    @DataProvider(name = "createUserDataWithTestCaseName")
//    public Iterator<LinkedHashMap<String, ReqresUser>> getCreateUserDataWithRandomStringData() throws IOException {
//        return CreateUserDataProvider.getCreateReqresUserDataWithRandomStringData().iterator();
//    }
//}
