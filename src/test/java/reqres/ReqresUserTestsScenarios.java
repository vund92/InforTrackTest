package reqres;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import reporting.Setup;
import reqres.pojos.CreateReqresUser;
import restUtils.AssertionUtils;
import utils.ExcelUtils;

import java.io.IOException;
import java.util.*;

@Listeners(reporting.Setup.class)
public class ReqresUserTestsScenarios extends ReqresUserAPIs{

    @Test(dataProvider = "reqresData" )
    public void createReqresAndVerify(CreateReqresUser reqresUser) {

        ExtentTest test = Setup.extentReports.createTest("Test Name - " + reqresUser.getScenarioId(),
                reqresUser.getScenarioDesc());
        Setup.extentTest.set(test);

        Response response = createReqresUser(reqresUser);

        if(reqresUser.getExpectedStatusCode() != 200){
            if(reqresUser.getScenarioId().equalsIgnoreCase("CreateReqres_DuplicateID"))
                response = createReqresUser(reqresUser);
            Assert.assertEquals(response.getStatusCode(), reqresUser.getExpectedStatusCode());
            Assert.assertEquals(response.jsonPath().getString("message"), reqresUser.getExpectedErrorMessage());
        }
        else {
            Map<String,Object> expectedValueMap = new HashMap<>();
            expectedValueMap.put("name", reqresUser.getName());
            expectedValueMap.put("job", reqresUser.getJob());

            if(reqresUser.getScenarioId().equalsIgnoreCase("CreateReqres_WithoutID")) {
               expectedValueMap.remove("id");
            }
            AssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap);
        }

    }

    @DataProvider(name = "ReqresData")
    public Iterator<CreateReqresUser> getCreateReqresData() throws IOException {
        List<LinkedHashMap<String, String>> excelDataAsListOfMap = ExcelUtils
                .getExcelDataAsListOfMapWithRandomNumberData("CreateReqresDataScenarios", "Sheet1");
        List<CreateReqresUser> ReqresData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : excelDataAsListOfMap) {
            CreateReqresUser Reqres = getCustomizedReqresData(data);
            ReqresData.add(Reqres);
        }
        return ReqresData.iterator();
    }

    private CreateReqresUser getCustomizedReqresData(LinkedHashMap<String, String> data) {
        CreateReqresUser createReqres = new CreateReqresUser();
        createReqres.setScenarioId(data.get("ScenaroID"));
        createReqres.setScenarioDesc(data.get("ScenarioDesc"));
        createReqres.setExpectedStatusCode(Integer.parseInt(data.get("ExpectedStatusCode")));
        if(!data.get("ExpectedErrorMessage").equals("NO_DATA"))
            createReqres.setExpectedErrorMessage(data.get("ExpectedErrorMessage"));
        if(!data.get("Name").equalsIgnoreCase("NO_DATA"))
            createReqres.setName(data.get("Name"));
        if(!data.get("Job").equalsIgnoreCase("NO_DATA"))
            createReqres.setJob(data.get("Job"));

        return createReqres;
    }
}
