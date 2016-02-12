package com.bendaschel.lab5;

import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import java_cup.Main;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
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
        Fragment buttonFrag = fragmentManager.findFragmentByTag(ButtonFragment.TAG);
        Fragment detailFragment = fragmentManager.findFragmentByTag(DetailFragment.TAG);
        assertThat(buttonFrag, instanceOf(ButtonFragment.class));
        assertThat(detailFragment, nullValue());
    }

    @Test
    @Config(qualifiers = "land")
    public void testOnCreateInLandscape() throws Exception {
        MainActivity mainActivity = mActivityController.create().get();
        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
        Fragment buttonFrag = fragmentManager.findFragmentByTag(ButtonFragment.TAG);
        Fragment detailFragment = fragmentManager.findFragmentByTag(DetailFragment.TAG);
        assertThat(buttonFrag, instanceOf(ButtonFragment.class));
        assertThat(detailFragment, instanceOf(DetailFragment.class));
    }
}
