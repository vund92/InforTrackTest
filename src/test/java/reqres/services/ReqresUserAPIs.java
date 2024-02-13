package reqres.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import reqres.pojos.CreateReqresUser;
import reqres.pojos.Login;
import reqres.pojos.ReqresUser;
import restUtils.RestUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReqresUserAPIs {

    private static final String PROJECT_PATH = System.getProperty("user.dir");

    public Response createReqresUser(Map<String,Object> createReqresPayload) {
        String endPoint = (String) Base.dataFromJsonFile.get("api.users");
        return RestUtils.performPost(endPoint,createReqresPayload, new HashMap<>());
    }

    public Response createReqresUser(ReqresUser createReqresPayload) {
        String endPoint = (String) Base.dataFromJsonFile.get("api.users");
        return RestUtils.performPost(endPoint,createReqresPayload, new HashMap<>());
    }

    public Response createReqresUser(CreateReqresUser createReqresPayload) {
        String endPoint = (String) Base.dataFromJsonFile.get("api.users");
        return RestUtils.performPost(endPoint,createReqresPayload, new HashMap<>());
    }

    public static Response createLogin(Login loginPayload) {
        String endPoint = (String) Base.dataFromJsonFile.get("api.login");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept","application/json");
        headers.put("Content-Type","application/json");
        return RestUtils.performPost(endPoint,loginPayload,headers);
    }

    public static Response createUser(String token, ReqresUser reqresUserPayload) {
        String endPoint = (String) Base.dataFromJsonFile.get("api.users");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        return RestUtils.performPost(endPoint,reqresUserPayload, headers);
    }

    public static Response createUser(String token, Object payload) {
        String endPoint = (String) Base.dataFromJsonFile.get("api.users");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        return RestUtils.performPost(endPoint, payload, headers);
    }

    public static String getToken() throws IOException {
        String env = System.getProperty("env") == null ? "stage" : System.getProperty("env");
        String jsonFile = "reqres/" + env + "/successfulLoginResults.json";
        String jsonFilePath = PROJECT_PATH + "/src/test/resources/" + jsonFile;
        String token = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Login login = objectMapper.readValue(new File(jsonFilePath), Login.class);

            Response response = createLoginToken(login);
            token = response.jsonPath().get("token");
            System.out.println("\nToken: " + token + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return token;
    }

    public static Response createLoginToken(Login loginPayload) {
        String endPoint = (String) Base.dataFromJsonFile.get("api.login");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept","application/json");
        headers.put("Content-Type","application/json");
        return RestUtils.performPostWithoutLogs(endPoint,loginPayload,headers);
    }

    public Response getUser(String endPointValue, Map<String,String>pathParams, Map<String,String>queryParams, Map<String,String>headers) {
        String endPoint = (String) Base.dataFromJsonFile.get("api.users") + "/" + endPointValue;
        return RestUtils.performGet(endPoint,pathParams,queryParams,headers);
    }

    public Response deleteUser(String endPointValue, Map<String,String>pathParams, Map<String,String>queryParams, Map<String,String>headers) {
        String endPoint = (String) Base.dataFromJsonFile.get("api.users") + "/" + endPointValue;
        return RestUtils.performDelete(endPoint,pathParams,queryParams,headers);
    }

    public static Response updateUser(String endPointValue, String token, Object payload) {
        String endPoint = (String) Base.dataFromJsonFile.get("api.users") + "/" + endPointValue;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        return RestUtils.performPut(endPoint, payload, headers);
    }
}
