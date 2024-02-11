package reqres.dataProviders;

import org.testng.annotations.DataProvider;
import reqres.pojos.Login;
import utils.ExcelUtils;

import java.io.IOException;
import java.util.*;

public class LoginDataProvider {

//    public static List<Login> getCreateLoginData() throws IOException {
//        List<LinkedHashMap<String, String>> excelDataAsListOfMap = ExcelUtils.getExcelDataAsListOfMapWithoutRandomData("LoginData", "Sheet1");
//        List<Login> loginData = new ArrayList<>();
//        for (LinkedHashMap<String, String> data : excelDataAsListOfMap) {
//            Login login = Login.builder()
//                    .email(data.get("Email"))
//                    .password(data.get("Password"))
//                    .build();
//            loginData.add(login);
//        }
//        return loginData;
//    }

    public static List<LinkedHashMap<String, Login>> getCreateLoginDataWithoutRandomData() throws IOException {
        List<LinkedHashMap<String, String>> excelDataAsListOfMap = ExcelUtils.getExcelDataAsListOfMapWithoutRandomData("LoginData", "Sheet1");
        List<LinkedHashMap<String, Login>> loginData = new ArrayList<>();
        for (LinkedHashMap<String, String> data : excelDataAsListOfMap) {
            String testCaseName = data.get("TestCaseName");
            Login login = Login.builder()
                    .email(data.get("Email"))
                    .password(data.get("Password"))
                    .build();
            LinkedHashMap<String, Login> loginDetails = new LinkedHashMap<>();
            loginDetails.put(testCaseName, login);
            loginData.add(loginDetails);
        }
        return loginData;
    }
}
