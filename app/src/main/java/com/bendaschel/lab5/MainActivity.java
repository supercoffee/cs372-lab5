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
    private static final String KEY_CURRENT_TEXT = "key_current_text";
    private FragmentManager mFragmentManager;

    @Nullable
    @Bind(R.id.fragment_container)
    ViewParent mFragmentContainer;

    private String mCurrentDisplayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        if (mFragmentManager.getBackStackEntryCount() > 0){
            mFragmentManager.popBackStack();
        }
        if (mFragmentContainer != null){
            Fragment fragment = new ButtonFragment();
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment, getString(R.string.tag_button_fragment))
                    .commit();
        }
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_CURRENT_TEXT)){
            mCurrentDisplayText = savedInstanceState.getString(KEY_CURRENT_TEXT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        DetailFragment detailFragment = (DetailFragment) mFragmentManager
                .findFragmentByTag(getString(R.string.tag_detail_fragment));
        if (detailFragment != null){
            detailFragment.updateText(mCurrentDisplayText);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CURRENT_TEXT, mCurrentDisplayText);
    }

    private void updateTime() {
        DetailFragment detailFragment = (DetailFragment) mFragmentManager
                .findFragmentByTag(getString(R.string.tag_detail_fragment));
        long currentTime = System.currentTimeMillis();
        mCurrentDisplayText = String.format("%d", currentTime);
        if (detailFragment != null) {
            detailFragment.updateText(mCurrentDisplayText);
            return;
        }
        detailFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(DetailFragment.ARG_STRING_DISPLAY_TEXT, mCurrentDisplayText);
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
