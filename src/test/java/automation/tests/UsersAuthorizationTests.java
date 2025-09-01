package automation.tests;

import automation.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * NOTE: GoRest doesn't expose role-based access control in its public API.
 * This class documents the expected behavior and asserts that true RBAC is N/A.
 */
public class UsersAuthorizationTests extends BaseTest {

    @Test(groups = {"authorization","documentation"})
    public void role_based_access_control_not_applicable_for_gorest_public_api() {
        Assert.assertTrue(true, "RBAC not applicable for GoRest public sandbox; use enterprise API for real RBAC tests.");
    }
}
