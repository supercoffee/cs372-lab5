package com.bendaschel.lab5;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DetailFragmentTest {

    @Test
    public void testCreateEmpty() throws Exception {
        Fragment detailFragment = new DetailFragment();
        SupportFragmentTestUtil.startFragment(detailFragment);
        TextView displayTextView = (TextView) detailFragment.getView().findViewById(R.id.tv_time);
        assertThat(displayTextView, notNullValue());
        assertThat(displayTextView.getText().toString().length(), lessThanOrEqualTo(0));
    }

    @Test
    public void testCreateWithValue() throws Exception {
        Fragment detailFragment = new DetailFragment();
        Bundle args = new Bundle();
        long time = 123456;
        args.putString(DetailFragment.ARG_STRING_DISPLAY_TEXT, String.format("%d", time));
        detailFragment.setArguments(args);
        SupportFragmentTestUtil.startFragment(detailFragment);
        TextView displayTextView = (TextView) detailFragment.getView().findViewById(R.id.tv_time);
        assertThat(displayTextView, notNullValue());
        assertThat(Long.parseLong(displayTextView.getText().toString()), equalTo(time));
    }
}
