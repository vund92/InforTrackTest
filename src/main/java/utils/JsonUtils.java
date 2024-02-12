package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JsonUtils {

    static String PROJECT_PATH = System.getProperty("user.dir");
    private static ObjectMapper objectMapper = new ObjectMapper();
    public static Map<String,Object> getJsonDataAsMap(String jsonFileName) throws IOException {
        String completeJsonFilePath = PROJECT_PATH + "/src/test/resources/" + jsonFileName;
        Map<String,Object> data = objectMapper.readValue(new File(completeJsonFilePath), new TypeReference<>(){});
        return data;
    }

    public static Map<String,String> getJsonDataAsMapDraft(String jsonFileName) throws IOException {
        String completeJsonFilePath = PROJECT_PATH + "/src/test/resources/" + jsonFileName;
        Map<String,String> data = objectMapper.readValue(new File(completeJsonFilePath), new TypeReference<>(){});
        return data;
    }

    public static String getSchemaFileByName(String env, String jsonFileName) {
        return PROJECT_PATH + "/src/test/resources/reqres/testData/schema-code/"+env+"/"+jsonFileName+".json";
    }
}
