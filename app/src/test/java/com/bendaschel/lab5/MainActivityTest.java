package com.bendaschel.lab5;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityTest {

    ActivityController<MainActivity> mActivityController;

    @Before
    public void setUp() throws Exception {
        mActivityController = Robolectric.buildActivity(MainActivity.class);
    }

    @Test
    public void testOnCreateNormal() throws Exception {
        MainActivity mainActivity = mActivityController.create().get();
        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
        Fragment buttonFrag = fragmentManager
                .findFragmentByTag(mainActivity.getString(R.string.tag_button_fragment));
        Fragment detailFragment = fragmentManager
                .findFragmentByTag(mainActivity.getString(R.string.tag_detail_fragment));
        assertThat(buttonFrag, instanceOf(ButtonFragment.class));
        assertThat(detailFragment, nullValue());
    }

    @Test
    @Config(qualifiers = "land")
    public void testOnCreateInLandscape() throws Exception {
        MainActivity mainActivity = mActivityController.create().get();
        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
        Fragment buttonFrag = fragmentManager
                .findFragmentByTag(mainActivity.getString(R.string.tag_button_fragment));
        Fragment detailFragment = fragmentManager
                .findFragmentByTag(mainActivity.getString(R.string.tag_detail_fragment));
        assertThat(buttonFrag, instanceOf(ButtonFragment.class));
        assertThat(detailFragment, instanceOf(DetailFragment.class));
    }

    @Test
    public void testButtonClickNormal() throws Exception {
        MainActivity mainActivity = mActivityController.create().start().get();
        View button = mainActivity.findViewById(R.id.btn_clickme);
        long startTime = System.currentTimeMillis();
        button.performClick();
        long endTime = System.currentTimeMillis();
        TextView timeTextView = (TextView) mainActivity.findViewById(R.id.tv_time);
        assertThat(timeTextView, notNullValue());
        long displayTime = Long.parseLong(timeTextView.getText().toString());
        assertThat(displayTime, allOf(greaterThan(startTime), lessThan(endTime)));
    }
}
