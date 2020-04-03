package com.example.locomotioncommotion;

import android.app.Activity;
import android.widget.EditText;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.locomotioncommotion.activities.driver.DriverRequestsAll;
import com.example.locomotioncommotion.activities.driver.RequestFinderList;
import com.example.locomotioncommotion.activities.driver.ViewDriverRequest;
import com.example.locomotioncommotion.activities.loginRegistration.Registration;
import com.example.locomotioncommotion.activities.rider.CreateRequest;
import com.example.locomotioncommotion.activities.rider.RiderMain;
import com.example.locomotioncommotion.activities.shared.DriverOrRider;
import com.example.locomotioncommotion.activities.loginRegistration.MainActivity;
import com.example.locomotioncommotion.activities.shared.InspectProfile;
import com.example.locomotioncommotion.activities.shared.SelectLocationActivity;
import com.example.locomotioncommotion.activities.shared.UserProfile;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * To test if the MainActivity works by checking username properly moved around
 */
public class MainActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    @Test
    public void TestAll() {
        loginFailTest("TestUsername1");
        tryLogin("TestUsername1");
        userProfileTest("TestUsername1");
        createRequestTest("TestUsername1");
        logout();

        tryLogin("TestUsername2");
        driverTakeRequestTest("TestUsername1");
    }

    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

    // Login with invalid information should fail
    public void loginFailTest(String username){
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);

        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);

        solo.enterText((EditText)solo.getView(R.id.username),username);
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);

        solo.enterText((EditText)solo.getView(R.id.password),"Test");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
    }

    // attempts to login with user, if fail then create account for user
    public void tryLogin(String username){
        solo.clearEditText((EditText)solo.getView(R.id.username));
        solo.enterText((EditText)solo.getView(R.id.username),username);
        solo.clearEditText((EditText)solo.getView(R.id.password));
        solo.enterText((EditText)solo.getView(R.id.password),"TestPassword");
        solo.clickOnButton("Login");
        if(!solo.searchText("View Profile")){ // user does not already exist
            createUser(username);

            // Test login with new created user
            solo.clearEditText((EditText)solo.getView(R.id.password));
            solo.enterText((EditText)solo.getView(R.id.password),"Test");
            solo.clickOnButton("Login");
            solo.assertCurrentActivity("Wrong activity", MainActivity.class);

            solo.clearEditText((EditText)solo.getView(R.id.password));
            solo.enterText((EditText)solo.getView(R.id.password),"TestPassword");
            solo.clickOnButton("Login");
        }
        solo.assertCurrentActivity("Wrong activity", DriverOrRider.class);
    }

    // Creates user and tests registration
    public void createUser(String username){
        solo.clickOnButton("Register");
        solo.assertCurrentActivity("Wrong activity", Registration.class);

        solo.clickOnButton("Create Account");
        solo.assertCurrentActivity("Wrong activity", Registration.class);

        solo.enterText((EditText)solo.getView(R.id.registration_username),username);
        solo.enterText((EditText)solo.getView(R.id.registration_password),"TestPassword");
        solo.clickOnButton("Create Account");
        solo.assertCurrentActivity("Wrong activity", Registration.class);

        solo.enterText((EditText)solo.getView(R.id.registration_email),username + "@test.com");
        solo.enterText((EditText)solo.getView(R.id.registration_phone_number),"1112223333");
        solo.clickOnButton("Create Account");
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        /////////////////////////////////////////////////////////////////////////////////
    }

    // test viewing of own profile
    public void userProfileTest(String username){
        solo.clickOnButton("View Profile");
        solo.assertCurrentActivity("Wrong activity", UserProfile.class);

        assertTrue(solo.searchText(username));
        assertTrue(solo.searchText(username + "@test.com"));
        assertTrue(solo.searchText("1112223333"));

        assertTrue(solo.searchButton("Change Email"));
        assertTrue(solo.searchButton("Change Phone"));

        solo.clickOnButton("Back");
        solo.assertCurrentActivity("Wrong activity", DriverOrRider.class);
    }

    // Test the creation of a request
    public void createRequestTest(String username) {
        solo.clickOnButton("Rider");
        solo.assertCurrentActivity("Wrong activity", RiderMain.class);

        assertTrue(solo.searchText("Create Request"));
        solo.clickOnButton("Create Request");
        solo.assertCurrentActivity("Wrong activity", CreateRequest.class);

        solo.clickOnView(solo.getView(R.id.start_edit_create));
        solo.assertCurrentActivity("Wrong activity", SelectLocationActivity.class);

        solo.clickOnView(solo.getView(R.id.select_location_map));
        solo.clickOnScreen(200,800);
        solo.clickOnButton("Confirm");
        solo.assertCurrentActivity("Wrong activity", CreateRequest.class);

        solo.clickOnView(solo.getView(R.id.end_edit_create));
        solo.assertCurrentActivity("Wrong activity", SelectLocationActivity.class);

        solo.clickOnView(solo.getView(R.id.select_location_map));
        solo.clickOnScreen(400,600);
        solo.clickOnButton("Confirm");
        solo.assertCurrentActivity("Wrong activity", CreateRequest.class);

        solo.clickOnButton("Create Request");
        solo.assertCurrentActivity("Wrong activity", RiderMain.class);

        assertTrue(solo.searchText(username));
        solo.goBack();
        solo.assertCurrentActivity("Wrong activity", DriverOrRider.class);
    }

    // test logout
    public void logout(){
        solo.clickOnButton("Logout");
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
    }

    // test Driver finding a request
    public void driverTakeRequestTest(String rider) {
        solo.clickOnButton("Driver");
        solo.assertCurrentActivity("Wrong activity", DriverRequestsAll.class);

        solo.clickOnButton("Find Requests");
        solo.assertCurrentActivity("Wrong activity", RequestFinderList.class);

        solo.clickOnView(solo.getView(R.id.request_finder_list_location));
        solo.assertCurrentActivity("Wrong activity", SelectLocationActivity.class);

        solo.clickOnView(solo.getView(R.id.select_location_map));
        solo.clickOnScreen(200,805);
        solo.clickOnButton("Confirm");
        solo.assertCurrentActivity("Wrong activity", SelectLocationActivity.class);

        assertTrue(solo.searchText(rider));
        solo.clickOnText(rider);
        solo.assertCurrentActivity("Wrong activity", ViewDriverRequest.class);

        assertTrue(solo.searchText(rider));
        inspectProfileTest(rider);

        solo.clickOnButton("Accept");
        solo.assertCurrentActivity("Wrong activity", RequestFinderList.class);

        solo.clickOnButton("Back");
        solo.assertCurrentActivity("Wrong activity", DriverRequestsAll.class);
    }

    // test that inspect profile works
    public void inspectProfileTest(String username){
        solo.clickOnButton(username);
        solo.assertCurrentActivity("Wrong activity", InspectProfile.class);

        assertTrue(solo.searchText(username));
        assertTrue(solo.searchText(username + "@test.com"));
        assertTrue(solo.searchText("1112223333"));

        solo.goBack();
    }
}
