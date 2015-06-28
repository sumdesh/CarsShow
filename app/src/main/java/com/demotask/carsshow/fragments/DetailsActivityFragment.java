package com.demotask.carsshow.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demotask.carsshow.R;
import com.demotask.carsshow.adapters.CarDetailsViewPagerAdapter;
import com.demotask.carsshow.adapters.CarInfoWindowAdapter;
import com.demotask.carsshow.events.CarSelectedEvent;
import com.demotask.carsshow.events.MapReadyEvent;
import com.demotask.carsshow.webservice.Car;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends BaseFragment {

    private ViewPager pager;
    private Car selectedCar;
    private int selectedCarPosition;
    private MapsFragment fragment;

    @InjectView(R.id.icon1)
    public ImageView carImage;

    @InjectView(R.id.modelName)
    public TextView modelName;

    @InjectView(R.id.name)
    public TextView name;

    @InjectView(R.id.carLocationValue)
    public TextView carLocationValue;

    @InjectView(R.id.fuelIndicatorValue)
    public TextView fuelIndicatorValue;

    @InjectView(R.id.carTransmission)
    public ImageView carTransmission;

    @InjectView(R.id.carTransmissionValue)
    public TextView carTransmissionValue;

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, null);
        //pager = (ViewPager) v.findViewById(R.id.pager);
        fragment = (MapsFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.maps_fragment);
        ButterKnife.inject(getActivity(),v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        selectedCar = (Car) getActivity().getIntent().getParcelableExtra("car_selection");
        selectedCarPosition = getActivity().getIntent().getIntExtra("car_selection_position",0);

        //pager.setAdapter(new CarDetailsViewPagerAdapter(getFragmentManager()));
        //pager.setCurrentItem(selectedCarPosition, true);
    }

    @Subscribe
    public void mapIsReadyToUse(final MapReadyEvent event) {
        if (event.googleMap!=null){
            if (selectedCar != null) {
                double lat = selectedCar.latitude;
                double lng = selectedCar.longitude;
                LatLng coordinate = new LatLng(lat, lng);


                modelName.setText(selectedCar.modelName);
                name.setText(selectedCar.name);

                carLocationValue.setText("600m");

                double fuelLevel = Math.round(selectedCar.fuelLevel*100.00);
                fuelIndicatorValue.setText(String.valueOf(fuelLevel));

                if (selectedCar.transmission.equals("A")){
                    carTransmission.setImageResource(R.drawable.ic_automatic_transmission);
                    carTransmissionValue.setText("Automatic");
                }else{
                    carTransmission.setImageResource(R.drawable.ic_manual_transmission);
                    carTransmissionValue.setText("Manual");
                }

                Picasso.with(getActivity())
                        .load(selectedCar.carImageUrl)
                        .placeholder(R.drawable.ic_car)
                        .error(R.drawable.ic_car)
                        .into(carImage);

                final CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(coordinate)      // Sets the center of the map to Mountain View
                        .zoom(13)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   //

                event.googleMap.setInfoWindowAdapter(new CarInfoWindowAdapter(getActivity(), selectedCar));
                event.googleMap.addMarker(new MarkerOptions().position(coordinate).title(selectedCar.modelName)).showInfoWindow();
                event.googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                event.googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
                        event.googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        event.googleMap.setInfoWindowAdapter(new CarInfoWindowAdapter(getActivity(), selectedCar));
                        return true;
                    }
                });

                //mapInstace.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 40));
                //mapInstace.animateCamera(CameraUpdateFactory.zoomTo(14));

                //fragment = MapsFragment.newInstance();
                //fragment.moveCameraTo(event.googleMap, coordinate);
            }
        }

    }
}
