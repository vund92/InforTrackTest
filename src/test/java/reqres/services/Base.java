package reqres.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import reqres.pojos.ReqresUserResponse;
import utils.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static reqres.services.ReqresUserAPIs.getToken;

public class Base {

    static String PROJECT_PATH = System.getProperty("user.dir");
    public static String token;
    public static ReqresUserResponse reqresUserResponse;

    public static Map<String,Object> dataFromJsonFile;

    public static String env = System.getProperty("env") == null ? "stage" : System.getProperty("env");

    static {
        try {
            dataFromJsonFile = JsonUtils.getJsonDataAsMap("reqres/" + env + "/reqresApiDataEndPoints.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            token = getToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            String jsonFileName = "reqres/" + env + "/existingUserData.json";
            String completeJsonFilePath = PROJECT_PATH + "/src/test/resources/" + jsonFileName;
            ObjectMapper objectMapper = new ObjectMapper();
            reqresUserResponse = objectMapper.readValue(new File(completeJsonFilePath), ReqresUserResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
