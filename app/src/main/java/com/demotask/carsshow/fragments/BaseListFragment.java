package com.demotask.carsshow.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;

import com.demotask.carsshow.core.ApplicationState;

import butterknife.ButterKnife;

/**
 * Created by edrsoftware on 26.06.15.
 */
public class BaseListFragment extends ListFragment {

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
        ApplicationState.getInstance().getEventBus().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ApplicationState.getInstance().getEventBus().register(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
    }
}