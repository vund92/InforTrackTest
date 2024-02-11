package reqres;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import reqres.pojos.ReqresUser;
import restUtils.RestUtils;
import utils.JsonUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Listeners(reporting.Setup.class)
public class ReqresUserTests extends  ReqresUserAPIs {

    @Test()
    public void createReqresDraft1() throws IOException {
        Response response = RestAssured.given().log().all()
                .baseUri("https://reqres.in/api/users")
                .body("{\n" +
                        "\"name\": \"Nguyen Trinh\",\n" +
                        "\"job\": \"Leader\",\n" +
                        "}")
                .post()
                .then().log().all().extract().response();
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void createReqresDraft2() throws IOException {
        String endPoint = "https://reqres.in/api/users";
        String payLoad = "{\n" +
                "\"name\": \"Nguyen Tuan\",\n" +
                "\"job\": \"Member\",\n" +
                "}";
        Response response = RestUtils.performPostDraft(endPoint, payLoad, new HashMap<>());
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void createReqresDraft3() throws IOException {
        String endPoint = "https://reqres.in/api/users";
        String payLoad = Payloads.getCreateReqresUserPayloadFromString("Tran Tuan", "Manager");
        Response response = RestUtils.performPostDraft(endPoint, payLoad, new HashMap<>());
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void createReqresDraft4() throws IOException {
        String endPoint = "https://reqres.in/api/users";
        Map<String, Object> payLoad = Payloads.getCreateReqresUserPayloadFromMap( "Huynh Van Banh", "Developer");
        Response response = RestUtils.performPost(endPoint, payLoad, new HashMap<>());
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void createReqresDraft5() throws IOException {
        String env = System.getProperty("env") == null ? "qa" : System.getProperty("env");
        Map<String, String> data = JsonUtils.getJsonDataAsMapDraft("reqres/qa/reqresApiData.json");
        String endPoint = data.get("api.user");
        Map<String, Object> payLoad = Payloads.getCreateReqresUserPayloadFromMap("Nguyen Ngoc Ngan", "Author");
        Response response = RestUtils.performPost(endPoint, payLoad, new HashMap<>());
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void createReqresDraft6() throws IOException {
        System.out.println(System.getProperty("evn") + " Evn value"); //command line: mvn clean test -Devn=qa
        String env = System.getProperty("env") == null ? "qa" : System.getProperty("env");
        Map<String, String> data = JsonUtils.getJsonDataAsMapDraft("reqres/" + env + "/reqresApiData.json");
        String endPoint = data.get("api.user");
        Map<String, Object> payLoad = Payloads.getCreateReqresUserPayloadFromMap("Ho Ngoc Ha", "Singer");
        Response response = RestUtils.performPost(endPoint, payLoad, new HashMap<>());
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void createReqresDraft7() throws IOException {
        System.out.println(System.getProperty("evn") + " Evn value"); //command line: mvn clean test -Devn=qa
        String env = System.getProperty("env") == null ? "qa" : System.getProperty("env");
        Map<String, String> data = JsonUtils.getJsonDataAsMapDraft("reqres/" + env + "/reqresApiData.json");
        String endPoint = data.get("api.user");
        Map<String, Object> payLoad = Payloads.getCreateReqresUserPayloadFromMap("Ailee", "Singer");
        Response response = RestUtils.performPost(endPoint, payLoad, new HashMap<>());
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void createReqresDraft8() throws IOException {
        Map<String, Object> payLoad = Payloads.getCreateReqresUserPayloadFromMap("Hung Dao", "Engineer");
        Response response = createReqresUser(payLoad);
        Assert.assertEquals(response.statusCode(), 201);
    }


    @Test()
    public void createReqresDraft9() throws IOException {
        Map<String, Object> payload = Payloads.getCreateReqresUserPayloadFromMapDraft();
        Response response = createReqresUser(payload);
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void createReqresDraft10() throws IOException {
        ReqresUser payload = Payloads.getCreateReqresUserPayloadFromPojo();
        Response response = createReqresUser(payload);
        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test()
    public void createReqresDraft11() throws IOException {
        ReqresUser payload = new ReqresUser().toBuilder().build();
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(payload));
    }

    @Test()
    public void createReqresUserAndVerifyResponseDraft1() throws IOException {
        ReqresUser payload = new ReqresUser();
        Response response = createReqresUser(payload);

        // first way
        Assert.assertEquals(response.jsonPath().getString("name"), payload.getName());

        ObjectMapper objectMapper = new ObjectMapper();
        ReqresUser createReqresResponse = objectMapper.readValue(response.getBody().asString(), ReqresUser.class);

        Assert.assertEquals(createReqresResponse, payload);
    }

}
