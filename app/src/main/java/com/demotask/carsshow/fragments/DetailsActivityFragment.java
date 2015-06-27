package com.demotask.carsshow.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demotask.carsshow.R;
import com.demotask.carsshow.events.CarSelectedEvent;
import com.demotask.carsshow.webservice.Car;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {

    private Car selectedCar;
private MapsFragment fragment;

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details,null);

        fragment = (MapsFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.maps_fragment);

        ButterKnife.inject(this,v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        selectedCar = (Car) getActivity().getIntent().getParcelableExtra("car_selection");
        if (selectedCar!= null){
            double lat =  selectedCar.latitude;
            double lng = selectedCar.longitude;
            LatLng coordinate = new LatLng(lat, lng);
            fragment.moveCameraTo(coordinate);
        }
    }

    @Subscribe
    public void selectedCarAvailbale(CarSelectedEvent event){

    }
}
