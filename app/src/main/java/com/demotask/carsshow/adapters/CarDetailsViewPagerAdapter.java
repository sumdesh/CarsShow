package com.demotask.carsshow.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.demotask.carsshow.core.ApplicationState;
import com.demotask.carsshow.fragments.CarsListViewFragment;

/**
 * Created by Sumedh on 28/06/15.
 */
public class CarDetailsViewPagerAdapter extends FragmentPagerAdapter {

    public CarDetailsViewPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }


    @Override
    public Fragment getItem(int position) {
        CarsListViewFragment fragment = CarsListViewFragment.newInstance(ApplicationState.getInstance().getCarInfo().get(position));
        return fragment;
    }

    @Override
    public int getCount() {
        int count = ApplicationState.getInstance().getCarInfo().size();
        return count;
    }
}
