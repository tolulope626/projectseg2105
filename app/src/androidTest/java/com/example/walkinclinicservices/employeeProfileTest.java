package com.example.walkinclinicservices;

import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNotEquals;

public class employeeProfileTest {
    @Rule
    public ActivityTestRule<employeeProfile> mActivityTestRule = new ActivityTestRule<>(employeeProfile.class);
    private employeeProfile mActivity = null;
    private TextView text;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }


    @Test
    @UiThreadTest
    public void checkStreetName() throws Exception {
        assertNotNull(mActivity.findViewById(R.id.textView13));
        text = mActivity.findViewById(R.id.strText);
        text.setText("User1");
        String name = text.getText().toString();
        assertNotEquals("user", name);
    }
}