package com.demotask.carsshow.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demotask.carsshow.R;
import com.demotask.carsshow.core.ApplicationState;
import com.demotask.carsshow.events.MapReadyEvent;
import com.demotask.carsshow.utility.MapUtility;
import com.demotask.carsshow.webservice.Car;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends BaseFragment {


    private Car selectedCar;
    private GoogleMap googleMap;
    private MarkerOptions previousMarker;

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

    @InjectView(R.id.showHideDetails)
    public Button showHideDetails;

    @InjectView(R.id.car_other_details)
    public LinearLayout car_other_details;


    //car_other_details_layout
    @InjectView(R.id.numberPlateValue)
    public TextView numberPlateValue;

    @InjectView(R.id.locationValue)
    public TextView locationValue;

    @InjectView(R.id.fuelType)
    public TextView fuelType;

    @InjectView(R.id.fuelTypeValue)
    public TextView fuelTypeValue;

    @InjectView(R.id.carGroupValue)
    public TextView carGroupValue;

    @InjectView(R.id.carSeriesValue)
    public TextView carSeriesValue;

    @InjectView(R.id.innerCleanlinessValue)
    public TextView innerCleanlinessValue;


    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, null);
        ButterKnife.inject(getActivity(), v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        selectedCar = (Car) getActivity().getIntent().getParcelableExtra("car_selection");
    }

    private void showCarInfo() {
        Picasso.with(getActivity())
                .load(selectedCar.carImageUrl)
                .placeholder(R.drawable.ic_car)
                .error(R.drawable.ic_car)
                .into(carImage);

        modelName.setText(selectedCar.modelName);
        name.setText(selectedCar.name);

        double distance = MapUtility.getDistance(ApplicationState.HOME_LOCATION, new LatLng(selectedCar.latitude, selectedCar.longitude));
        if (distance > 1000.00) {
            distance = distance / 1000;
            carLocationValue.setText(String.format("%.2fkm", distance));
        } else {
            carLocationValue.setText(String.format("%.2fm", distance));
        }

        double fuelLevel = Math.round(selectedCar.fuelLevel * 100.00);
        fuelIndicatorValue.setText(String.format("%.0f%%", fuelLevel));

        if (selectedCar.transmission.equals("A")) {
            carTransmission.setImageResource(R.drawable.ic_automatic_transmission);
            carTransmissionValue.setText(R.string.auto_transmission);
        } else {
            carTransmission.setImageResource(R.drawable.ic_manual_transmission);
            carTransmissionValue.setText(R.string.manual_transmission);
        }


        numberPlateValue.setText(selectedCar.licensePlate);
        double lat = selectedCar.latitude;
        double lng = selectedCar.longitude;
        LatLng coordinate = new LatLng(lat, lng);
        locationValue.setText(MapUtility.getAddressFromCoordinates(getActivity(), coordinate));

        fuelType.setText("Fuel Type");
        if (selectedCar.fuelType.equals("P")) {
            fuelTypeValue.setText(R.string.fuel_type_petrol);
        } else {
            fuelTypeValue.setText(R.string.fuel_type_diesel);
        }

        carGroupValue.setText(selectedCar.group);

        carSeriesValue.setText(selectedCar.series);

        innerCleanlinessValue.setText(selectedCar.innerCleanliness);
    }

    private void adjustCameraAtCoordinates(LatLng coordinate) {
        final CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(coordinate)      // Sets the center of the map to Mountain View
                .zoom(13)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   //

        //googleMap.setInfoWindowAdapter(new CarInfoWindowAdapter(getActivity(), selectedCar));

        previousMarker = new MarkerOptions().position(coordinate)
                .title(selectedCar.modelName)
                .snippet(selectedCar.id)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        googleMap.addMarker(previousMarker);

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String newSelectionId = marker.getSnippet();
                if (newSelectionId != null) {
                    if (!newSelectionId.equals(selectedCar.id)) {

                        //hiding marker info
                        marker.hideInfoWindow();

                        //resetting previously selected marker color
                        previousMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                        googleMap.addMarker(previousMarker);
                        previousMarker = null;

                        //setting new selection
                        Car newSelection = ApplicationState.getInstance().getCarMapping().get(newSelectionId);
                        if (newSelection != null) {
                            selectedCar = ApplicationState.getInstance().getCarMapping().get(newSelectionId);
                            showCarInfo();
                            double lat = selectedCar.latitude;
                            double lng = selectedCar.longitude;
                            LatLng coordinate = new LatLng(lat, lng);
                            adjustCameraAtCoordinates(coordinate);
                        }
                    }
                }
                return false;
            }
        });

        //mapInstace.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 40));
        //mapInstace.animateCamera(CameraUpdateFactory.zoomTo(14));
    }

    @OnClick(R.id.showHideDetails)
    public void onShowDetailsClick(View view) {
        if (showHideDetails.getTag() == 0) {
            showHideDetails.setTag(1);
            car_other_details.setVisibility(View.GONE);
        } else {
            showHideDetails.setTag(0);
            car_other_details.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe
    public void mapIsReadyToUse(final MapReadyEvent event) {
        if (event.googleMap != null) {
            googleMap = event.googleMap;

            if (selectedCar != null) {
                showCarInfo();
                double lat = selectedCar.latitude;
                double lng = selectedCar.longitude;
                LatLng coordinate = new LatLng(lat, lng);
                adjustCameraAtCoordinates(coordinate);
            }
        }

    }
}
