package com.bendaschel.lab5;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
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
    @Config(qualifiers = "land")
    public void testStartInLandscapeAndRotateToPortrait() throws Exception {
        MainActivity mainActivity = mActivityController.create().start().resume().visible().get();
        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
        Fragment buttonFrag = fragmentManager
                .findFragmentByTag(mainActivity.getString(R.string.tag_button_fragment));
        Fragment detailFragment = fragmentManager
                .findFragmentByTag(mainActivity.getString(R.string.tag_detail_fragment));
        assertThat(buttonFrag, instanceOf(ButtonFragment.class));
        assertThat(detailFragment, instanceOf(DetailFragment.class));

        RuntimeEnvironment.setQualifiers("");
        Bundle out = new Bundle();
        mActivityController.saveInstanceState(out).pause().stop().destroy();
        mainActivity = Robolectric.buildActivity(MainActivity.class).create(out)
                .restoreInstanceState(out).start().resume().visible().get();
        fragmentManager = mainActivity.getSupportFragmentManager();
        buttonFrag = fragmentManager
                .findFragmentByTag(mainActivity.getString(R.string.tag_button_fragment));
        detailFragment = fragmentManager
                .findFragmentByTag(mainActivity.getString(R.string.tag_detail_fragment));
        assertThat(buttonFrag, instanceOf(ButtonFragment.class));
        assertThat(detailFragment, nullValue());
    }

    @Test
    public void testButtonClickNormal() throws Exception {
        testButtonClick();
    }

    @Test
    @Config(qualifiers = "land")
    public void testButtonClickLandscape() throws Exception {
        testButtonClick();
    }

    private void testButtonClick(){
        MainActivity mainActivity = mActivityController.create().start().get();
        View button = mainActivity.findViewById(R.id.btn_clickme);
        long startTime = System.currentTimeMillis();
        button.performClick();
        long endTime = System.currentTimeMillis();
        TextView timeTextView = (TextView) mainActivity.findViewById(R.id.tv_time);
        assertThat(timeTextView, notNullValue());
        long displayTime = Long.parseLong(timeTextView.getText().toString());
        assertThat(displayTime, allOf(greaterThanOrEqualTo(startTime), lessThan(endTime)));
    }

    @Test
    public void testTextIsRetainedAcrossRotations() throws Exception {
        MainActivity mainActivityPort = mActivityController.create().start().resume().visible().get();
        View button = mainActivityPort.findViewById(R.id.btn_clickme);
        long startTime = System.currentTimeMillis();
        button.performClick();
        TextView timeTextView = (TextView) mainActivityPort.findViewById(R.id.tv_time);
        String originalText =  timeTextView.getText().toString();

        // Destroy and rebuild the activity the way android does
        Bundle out = new Bundle();
        mActivityController.saveInstanceState(out).pause().stop().destroy();
        RuntimeEnvironment.setQualifiers("land");
        // Need to explicitly create a new ActivityController object after changing runtime environment
        MainActivity mainActivityLand = Robolectric.buildActivity(MainActivity.class)
                .create(out).start().restoreInstanceState(out).resume().visible().get();
        timeTextView = (TextView) mainActivityLand.findViewById(R.id.tv_time);
        String newText = timeTextView.getText().toString();

        assertThat(Long.parseLong(originalText), greaterThanOrEqualTo(startTime));
        assertThat(originalText, equalTo(newText));
    }
}
