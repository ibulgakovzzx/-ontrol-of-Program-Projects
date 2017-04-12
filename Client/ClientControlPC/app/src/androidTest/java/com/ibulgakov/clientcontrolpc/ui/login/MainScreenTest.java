package com.ibulgakov.clientcontrolpc.ui.login;


import android.location.Location;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.google.android.gms.maps.model.LatLng;
import com.ibulgakov.clientcontrolpc.Consts;
import com.ibulgakov.clientcontrolpc.R;
import com.ibulgakov.clientcontrolpc.ui.main.MainScreenActivity;
import com.ibulgakov.clientcontrolpc.utils.Prefs;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainScreenTest {

    @Rule
    public ActivityTestRule<MainScreenActivity> mActivityTestRule = new ActivityTestRule<>(MainScreenActivity.class);

    @Test
    public void mainScreenTestAtWork() {
        ViewInteraction tvStatus = onView(allOf(withId(R.id.tvStatus), isDisplayed()));

        Location myLocation = new Location("");
        myLocation.setLatitude(37.22);
        myLocation.setLongitude(40.22);
        Prefs.save(Consts.Prefs.KEY_MY_LAST_POSITION, myLocation);
        LatLng jobLatLng = new LatLng(37.22, 40.22);
        Prefs.save(Consts.Prefs.KEY_JOB_LOCATION, jobLatLng);
        Prefs.get().edit().putInt(Consts.Prefs.KEY_JOB_DISTANCE, 1000).apply();

        ViewInteraction btnUpdate = onView(allOf(withId(R.id.btnUpdate), isDisplayed()));
        btnUpdate.perform(click());
        tvStatus.check(matches(withText("Вы на рабочем месте")));
    }

    @Test
    public void mainScreenTestAtHome() {
        ViewInteraction tvStatus = onView(allOf(withId(R.id.tvStatus), isDisplayed()));

        Location myLocation = new Location("");
        myLocation.setLatitude(37.22);
        myLocation.setLongitude(40.22);
        Prefs.save(Consts.Prefs.KEY_MY_LAST_POSITION, myLocation);
        LatLng jobLatLng = new LatLng(37.22, 41.22);
        Prefs.save(Consts.Prefs.KEY_JOB_LOCATION, jobLatLng);
        Prefs.get().edit().putInt(Consts.Prefs.KEY_JOB_DISTANCE, 1000).apply();

        ViewInteraction btnUpdate = onView(allOf(withId(R.id.btnUpdate), isDisplayed()));
        btnUpdate.perform(click());
        tvStatus.check(matches(withText("Вы на рабочем месте")));
    }

}
