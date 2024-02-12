package reqres;

import net.datafaker.Faker;
import reqres.pojos.Login;
import reqres.pojos.ReqresUser;
import utils.RandomDataGenerator_Reqres;
import utils.RandomDataTypeNames_Reqres;

import java.util.HashMap;
import java.util.Map;

public class Payloads {
    public static String getCreateReqresUserPayloadFromString(String name, String job) {
        String payload = "{\n" +
                "        \"name\": "+name+",\n" +
                "        \"job\": \""+job+"\",\n" +
                "}";
        return payload;
    }

    public static String getEmptyPayload() {
        String payload = "{}";
        return payload;
    }

    public static Map<String, Object> getCreateReqresUserPayloadFromMap(String name, String job) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", name);
        payload.put("job", job);
        return payload;
    }

    public static Map<String, Object> getCreateReqresUserPayloadFromMap() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", RandomDataGenerator_Reqres.getRandomDataFor(RandomDataTypeNames_Reqres.NAME));
        payload.put("job", RandomDataGenerator_Reqres.getRandomDataFor(RandomDataTypeNames_Reqres.JOB));
        return payload;
    }

    public static ReqresUser getCreateReqresUserPayloadFromPojo() {
        return ReqresUser
                .builder()
                .name(RandomDataGenerator_Reqres.getRandomDataFor(RandomDataTypeNames_Reqres.NAME))
                .job(RandomDataGenerator_Reqres.getRandomDataFor(RandomDataTypeNames_Reqres.JOB))
                .build();
    }

    /**
     * Draft section
     * **/
    public static Map<String, Object> getCreateReqresUserPayloadFromMapDraft() {
        Map<String, Object> payload = new HashMap<>();
        Faker faker = new Faker();
        payload.put("name", faker.name().name());
        payload.put("job", faker.job().position());
        return payload;
    }

    public static Login getCreateLoginPayloadFromPojo() {
        return Login
                .builder()
                .email(RandomDataGenerator_Reqres.getRandomDataFor(RandomDataTypeNames_Reqres.EMAIL))
                .email(RandomDataGenerator_Reqres.getRandomDataFor(RandomDataTypeNames_Reqres.PASSWORD))
                .build();
    }
}
