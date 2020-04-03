package com.example.locomotioncommotion;

import android.widget.EditText;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.locomotioncommotion.activities.shared.DriverOrRider;
import com.example.locomotioncommotion.activities.loginRegistration.MainActivity;
import com.example.locomotioncommotion.activities.shared.UserProfile;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;


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

    // Before
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }
    //tests
   // @Test
    public void testChangeContactInfo() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);

        solo.enterText((EditText)solo.getView(R.id.username),"TestUsername");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("wrong activity", DriverOrRider.class);
        solo.clickOnButton("VIEW PROFILE");
        solo.assertCurrentActivity("Wrong activity", UserProfile.class );
        assertTrue(solo.waitForText("TestUsername",1,2000));
    }


    //After
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }


}
