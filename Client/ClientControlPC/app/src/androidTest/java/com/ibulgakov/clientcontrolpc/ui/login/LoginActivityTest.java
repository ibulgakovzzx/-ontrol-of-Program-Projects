package com.ibulgakov.clientcontrolpc.ui.login;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.ibulgakov.clientcontrolpc.R;
import com.ibulgakov.clientcontrolpc.utils.LoginController;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @BeforeClass
    public static void init() {
        LoginController.INSTANCE.logout();
    }

    @Test
    public void loginActivityTest1() {
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.etCode), isDisplayed()));
        textInputEditText.perform(click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.etCode), isDisplayed()));
        textInputEditText2.perform(replaceText("Алексей"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btnSend), withText("Отправить"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction editText = onView(allOf(withId(R.id.etCode), isDisplayed()));
        editText.check(matches(withText("")));
    }

    @Test
    public void loginActivityTest2() {
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.etCode), isDisplayed()));
        textInputEditText.perform(click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.etCode), isDisplayed()));
        textInputEditText2.perform(replaceText(""), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btnSend), withText("Отправить"), isDisplayed()));
        //appCompatButton2.perform(click());

        ViewInteraction editText = onView(allOf(withId(R.id.etCode), isDisplayed()));
        editText.check(matches(withText("")));
    }

}
