package com.example.locomotioncommotion;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;


public class createRequestTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<CreateRequest>rule =
            new ActivityTestRule<>(CreateRequest.class, true , true);

    /**
     * runs before all tests and creates solo instance.
     */

    @Before
    public void setUp () throws Exception{
        //solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }
}

