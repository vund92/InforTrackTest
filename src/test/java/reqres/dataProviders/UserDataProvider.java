package reqres.dataProviders;

import reqres.Payloads;
import reqres.pojos.CreateReqresUser;
import reqres.pojos.ReqresUser;
import utils.ExcelUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class UserDataProvider {
    public static List<LinkedHashMap<String,Object>> getCreateUserDataWithRandomStringData() throws IOException {
        List<LinkedHashMap<String, String>> excelDataAsListOfMap = ExcelUtils
                .getExcelDataAsListOfMapWithRandomStringData("CreateUserData", "Sheet1");
        List<LinkedHashMap<String, Object>> reqresUserData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : excelDataAsListOfMap) {
            String testCaseName = data.get("TestCaseName");
            Object reqresUser = null;
            if(data.get("Name").equalsIgnoreCase("DoNotSend") || data.get("Job").equalsIgnoreCase("DoNotSend")){
                reqresUser = Payloads.getEmptyPayload();
            }else{
                reqresUser = ReqresUser.builder()
                        .name(data.get("Name"))
                        .job(data.get("Job"))
                        .build();
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

    public static List<LinkedHashMap<String,Object>> getUpdateUserDataWithRandomStringData() throws IOException {
        List<LinkedHashMap<String, String>> excelDataAsListOfMap = ExcelUtils
                .getExcelDataAsListOfMapWithRandomStringData("UpdateUserData", "Sheet1");
        List<LinkedHashMap<String, Object>> reqresUserData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : excelDataAsListOfMap) {
            String testCaseName = data.get("TestCaseName");
            Object reqresUser = null;
            if(data.get("Name").equalsIgnoreCase("DoNotSend") || data.get("Job").equalsIgnoreCase("DoNotSend")){
                reqresUser = Payloads.getEmptyPayload();
            }else{
                reqresUser = ReqresUser.builder()
                        .name(data.get("Name"))
                        .job(data.get("Job"))
                        .build();
            }
            LinkedHashMap<String, Object> reqresUserDetails = new LinkedHashMap<>();
            reqresUserDetails.put(testCaseName, reqresUser);
            reqresUserData.add(reqresUserDetails);
        }
        return reqresUserData;
    }
}
