package com.example.locomotioncommotion;

import android.widget.EditText;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * To test if the MainActivity works by checking username s properly moved around
 * currently broken
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
    public void loginRegisterProfileTest() {
        // Login with invalid information should fail
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);

        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);

        solo.enterText((EditText)solo.getView(R.id.username),"TestUsername");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);

        solo.enterText((EditText)solo.getView(R.id.password),"TestPassword");
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        ///////////////////////////////////////////////////////////////////////////

        // Tests Registration
        solo.clickOnButton("Register");
        solo.assertCurrentActivity("Wrong activity", Registration.class);

        solo.clickOnButton("Create Account");
        solo.assertCurrentActivity("Wrong activity", Registration.class);

        solo.enterText((EditText)solo.getView(R.id.registration_username),"TestUsername");
        solo.enterText((EditText)solo.getView(R.id.registration_password),"TestPassword");
        solo.clickOnButton("Create Account");
        solo.assertCurrentActivity("Wrong activity", Registration.class);

        solo.enterText((EditText)solo.getView(R.id.registration_email),"test@test.com.");
        solo.enterText((EditText)solo.getView(R.id.registration_phone_number),"1112223333");
        solo.clickOnButton("Create Account");
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        /////////////////////////////////////////////////////////////////////////////////

        // Test login with new created user
        solo.clearEditText((EditText)solo.getView(R.id.password));
        solo.enterText((EditText)solo.getView(R.id.password),"Test");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);

        solo.clearEditText((EditText)solo.getView(R.id.password));
        solo.enterText((EditText)solo.getView(R.id.password),"TestPassword");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong activity", DriverOrRider.class);
        /////////////////////////////////////////////////////////////////////////////////

        // Test Profile page
        solo.clickOnButton("View Profile");
        solo.assertCurrentActivity("Wrong activity", UserProfile.class);

        assertTrue(solo.searchText("TestUsername"));
        assertTrue(solo.searchText("test@test.com"));
        assertTrue(solo.searchText("1112223333"));

        solo.clickOnButton("Back");
        solo.assertCurrentActivity("Wrong activity", DriverOrRider.class);
        /////////////////////////////////////////////////////////////////////////////////

        createRequestTest();
    }

    public void createRequestTest() {
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
    }

    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }


}
