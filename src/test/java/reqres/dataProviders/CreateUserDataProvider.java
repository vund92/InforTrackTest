package reqres.dataProviders;

import org.testng.annotations.DataProvider;
import reqres.Payloads;
import reqres.pojos.CreateReqresUser;
import reqres.pojos.Login;
import reqres.pojos.ReqresUser;
import utils.ExcelUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class CreateUserDataProvider {
    public static List<LinkedHashMap<String,Object>> getCreateReqresUserDataWithRandomStringData() throws IOException {
        List<LinkedHashMap<String, String>> excelDataAsListOfMap = ExcelUtils
                .getExcelDataAsListOfMapWithRandomStringData("CreateUserData", "Sheet1");
        List<LinkedHashMap<String, Object>> reqresUserData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : excelDataAsListOfMap) {
            String testCaseName = data.get("TestCaseName");
            Object reqresUser;
            if(!data.get("Name").isEmpty() || !data.get("Name").isBlank() || !data.get("Job").isEmpty() || !data.get("Job").isBlank()){
                reqresUser = ReqresUser.builder()
                        .name(data.get("Name"))
                        .job(data.get("Job"))
                        .build();
            }else{
                reqresUser = Payloads.getEmptyPayload();
            }
            LinkedHashMap<String, Object> reqresUserDetails = new LinkedHashMap<>();
            reqresUserDetails.put(testCaseName, reqresUser);
            reqresUserData.add(reqresUserDetails);
        }
        return reqresUserData;
    }

    private CreateReqresUser getCustomizedReqresData(LinkedHashMap<String, String> data) {
        CreateReqresUser createReqres = new CreateReqresUser();
        if(!data.get("Name").equalsIgnoreCase("NO_DATA"))
            createReqres.setName(data.get("Name"));
        if(!data.get("Job").equalsIgnoreCase("NO_DATA"))
            createReqres.setJob(data.get("Job"));
        return createReqres;
    }
}
