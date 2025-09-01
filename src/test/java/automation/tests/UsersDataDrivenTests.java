package automation.tests;

import automation.base.BaseTest;
import automation.core.UsersService;
import automation.dataproviders.UserDataProviders;
import automation.models.User;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class UsersDataDrivenTests extends BaseTest {

    @Test(dataProvider = "usersFromCSV", dataProviderClass = UserDataProviders.class, groups={"post","datadriven"})
    public void create_users_from_csv(User user){
        new UsersService(reqSpec).createUser(user).then()
                .statusCode(201)
                .body("email", equalTo(user.getEmail()));
    }

    @Test(dataProvider = "usersFromJSON", dataProviderClass = UserDataProviders.class, groups={"post","datadriven"})
    public void create_users_from_json(User user){
        new UsersService(reqSpec).createUser(user).then()
                .statusCode(201)
                .body("email", equalTo(user.getEmail()));
    }

    @Test(dataProvider = "usersFromExcel", dataProviderClass = UserDataProviders.class, groups={"post","datadriven"})
    public void create_users_from_excel(User user){
        new UsersService(reqSpec).createUser(user).then()
                .statusCode(201)
                .body("email", equalTo(user.getEmail()));
    }

    @Test(dataProvider = "invalidUsers", dataProviderClass = UserDataProviders.class, groups={"post","negative","datadriven"})
    public void create_invalid_users_returns_422(User user, String field){
        new UsersService(reqSpec).createUser(user).then()
                .statusCode(422)
                .body("field", hasItem(field));
    }
}
