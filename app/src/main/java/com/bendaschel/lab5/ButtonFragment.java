package com.bendaschel.lab5;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ButtonFragment extends Fragment {

    public interface ButtonFragmentListener {
        void onButtonClicked();
    }

    public static final String TAG = "ButtonFragment";

    private ButtonFragmentListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ButtonFragmentListener) {
            mListener = (ButtonFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @OnClick(R.id.btn_clickme)
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onButtonClicked();
        }
    }
}
