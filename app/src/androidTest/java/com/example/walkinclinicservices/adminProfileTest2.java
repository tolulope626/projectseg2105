package com.example.walkinclinicservices;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class adminProfileTest2 {

    @Rule
    public ActivityTestRule<adminProfile> mActivityTestRule = new ActivityTestRule<adminProfile>(adminProfile.class);

    private adminProfile mActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(Accounts.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchOfActivityActBtn(){
        assertNotNull(mActivity.findViewById(R.id.actBtn));

        onView(withId(R.id.actBtn)).perform(click());

        Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,7000);

        assertNotNull(secondActivity);

        secondActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}