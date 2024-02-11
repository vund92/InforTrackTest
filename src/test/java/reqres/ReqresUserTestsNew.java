package reqres;

import com.poiji.bind.Poiji;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import reqres.pojos.ReqresUser;
import restUtils.AssertionUtils;
import utils.ExcelUtils;
import utils.RandomDataGenerator_Reqres;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Listeners(reporting.Setup.class)
public class ReqresUserTestsNew extends ReqresUserAPIs{

    @Test
    public void createReqresAndVerify() {
        ReqresUser request = Payloads.getCreateReqresUserPayloadFromPojo();
        Response response = createReqresUser(request);
        Map<String,Object> expectedValueMap = new HashMap<>();
        expectedValueMap.put("name", request.getName());
        expectedValueMap.put("job", request.getJob());
        AssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap);
    }


    @Test(dataProvider = "reqresData" )
    public void createReqresAndVerify(ReqresUser reqresUser) {
        Response response = createReqresUser(reqresUser);
        Map<String,Object> expectedValueMap = new HashMap<>();
//        expectedValueMap.put("id", reqresUser.getId());
        expectedValueMap.put("name", reqresUser.getName());
        expectedValueMap.put("job", reqresUser.getJob());
        AssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap);
    }


    @Test(dataProvider = "reqresDataPoiji" )
    public void createReqresAndVerifyPoiji(ReqresUser reqresUser) {

//        String cellValue = reqresUser.getIdValue();
        int size = 6;
//        if(cellValue.contains("RandomNumber")) {
//            // With size
//            if(cellValue.contains("_")) {
//                size = Integer.parseInt((cellValue.split("_"))[1]);
//            }
//            cellValue = RandomDataGenerator_Reqres.getRandomNumber(size);
//        }

//        reqresUser.setId(Integer.parseInt(cellValue));

        Response response = createReqresUser(reqresUser);
        Map<String,Object> expectedValueMap = new HashMap<>();
        expectedValueMap.put("name", reqresUser.getName());
        expectedValueMap.put("job", reqresUser.getJob());
        AssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap);
    }

    @DataProvider(name = "reqresData")
    public Iterator<ReqresUser> getCreateReqresData() throws IOException {
        List<LinkedHashMap<String, String>> excelDataAsListOfMap = ExcelUtils.getExcelDataAsListOfMapWithRandomStringData("CreateReqresData", "Sheet1");
        System.out.println(excelDataAsListOfMap);
        List<ReqresUser> reqresData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : excelDataAsListOfMap) {
            ReqresUser reqres = ReqresUser.builder()
                    .name(data.get("Name"))
                    .job(data.get("Job"))
                    .build();
            reqresData.add(reqres);
        }
        return reqresData.iterator();
    }

    @DataProvider(name = "reqresDataPoiji")
    public Iterator<ReqresUser> getCreateReqresDataPoiji() throws IOException {
        List<ReqresUser> reqresUsersData = Poiji.fromExcel(new File("src/test/resources/testdata/CreateReqresData.xlsx"),
                ReqresUser.class);
        return reqresUsersData.iterator();
    }

}
