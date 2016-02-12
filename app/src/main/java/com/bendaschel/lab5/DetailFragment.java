package com.bendaschel.lab5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {

    public static final String ARG_STRING_DISPLAY_TEXT = "display_text";
    public static final String TAG = "DetailFragment";

    @Bind(R.id.tv_time)
    TextView mTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_2, container, false);
        ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_STRING_DISPLAY_TEXT)){
            String displayText = args.getString(ARG_STRING_DISPLAY_TEXT);
            mTextView.setText(displayText);
        }
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        ButterKnife.unbind(this);
        super.onDestroyView();
    }
}
