package com.bendaschel.lab5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewParent;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ButtonFragment.ButtonFragmentListener{

    private static final String TAG = "MainActivity";
    private FragmentManager mFragmentManager;

    @Nullable
    @Bind(R.id.fragment_container)
    ViewParent mFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentManager.enableDebugLogging(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        int numFragments = mFragmentManager.getFragments() != null ?
                mFragmentManager.getFragments().size() : 0;
        Log.d(TAG, String.format("Num fragments %d", numFragments));
        if (savedInstanceState == null && mFragmentContainer != null){
            Fragment fragment = new ButtonFragment();
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment, getString(R.string.tag_button_fragment))
                    .commit();
        }
    }

    private void updateTime() {
        DetailFragment detailFragment = (DetailFragment) mFragmentManager
                .findFragmentByTag(getString(R.string.tag_detail_fragment));
        long currentTime = System.currentTimeMillis();
        String displayText = String.format("%d", currentTime);
        if (detailFragment != null) {
            detailFragment.updateText(displayText);
            return;
        }
        detailFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(DetailFragment.ARG_STRING_DISPLAY_TEXT, displayText);
        detailFragment.setArguments(args);
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,
                        detailFragment,
                        getString(R.string.tag_detail_fragment))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onButtonClicked() {
        updateTime();
    }
}
