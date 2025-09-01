package automation.dataproviders;

import automation.utils.CsvUtils;
import automation.utils.ExcelUtils;
import automation.utils.JsonUtils;
import automation.utils.UserDataFactory;
import automation.models.User;
import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.Map;

public class UserDataProviders {

    @DataProvider(name="usersFromCSV")
    public static Object[][] usersFromCSV(){
        List<Map<String,String>> rows = CsvUtils.read("testdata/users.csv");
        Object[][] data = new Object[rows.size()][1];
        for (int i=0;i<rows.size();i++){
            data[i][0] = UserDataFactory.fromMap(rows.get(i));
        }
        return data;
    }

    @DataProvider(name="usersFromJSON")
    public static Object[][] usersFromJSON(){
        List<Map<String,String>> rows = JsonUtils.readListOfMaps("testdata/users.json");
        Object[][] data = new Object[rows.size()][1];
        for (int i=0;i<rows.size();i++){
            data[i][0] = UserDataFactory.fromMap(rows.get(i));
        }
        return data;
    }

    @DataProvider(name="usersFromExcel")
    public static Object[][] usersFromExcel(){
        List<Map<String,String>> rows = ExcelUtils.readSheet("testdata/users.xlsx", "users");
        Object[][] data = new Object[rows.size()][1];
        for (int i=0;i<rows.size();i++){
            data[i][0] = UserDataFactory.fromMap(rows.get(i));
        }
        return data;
    }

    @DataProvider(name="invalidUsers")
    public static Object[][] invalidUsers(){
        return new Object[][]{
            { new User("Missing Email", null, "male", "active"), "email" },
            { new User("Bad Gender", automation.utils.Unique.email("badg."), "unknown", "active"), "gender" },
            { new User("Bad Status", automation.utils.Unique.email("bads."), "female", "unknown"), "status" }
        };
    }
}
