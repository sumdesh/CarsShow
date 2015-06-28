package com.demotask.carsshow.adapters;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.demotask.carsshow.R;
import com.demotask.carsshow.webservice.Car;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Sumedh on 28/06/15.
 */
public class CarInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Car car;
    private Context context;

    public CarInfoWindowAdapter(Context context, Car selectedCar){
        this.context = context;
        this.car = selectedCar;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    private String getAddressFromCoordinates(LatLng coordinate){
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(coordinate.latitude, coordinate.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

            return address;
        } catch (IOException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    @Override
    public View getInfoContents(Marker marker) {
        View v = null;
        if (v == null){
            v = LayoutInflater.from(context).inflate(R.layout.car_info_windowadapter,null);
            v.setTag(new ViewHolder(v));
        }

        final ViewHolder vh = (ViewHolder) v.getTag();

        double lat = car.latitude;
        double lng = car.longitude;
        LatLng coordinate = new LatLng(lat, lng);

        /*Picasso.with(context)
                .load(car.carImageUrl)
                .placeholder(R.drawable.ic_car)
                .error(R.drawable.ic_car)
                .into(vh.carImage);

        vh.modelName.setText(car.modelName);
        vh.name.setText(car.name);

        vh.carLocationValue.setText("600m");

        double fuelLevel = Math.round(car.fuelLevel*100.00);
        vh.fuelIndicatorValue.setText(String.valueOf(fuelLevel));

        if (car.transmission.equals("A")){
            vh.carTransmission.setImageResource(R.drawable.ic_automatic_transmission);
            vh.carTransmissionValue.setText("Automatic");
        }else{
            vh.carTransmission.setImageResource(R.drawable.ic_manual_transmission);
            vh.carTransmissionValue.setText("Manual");
        }*/

        vh.numberPlateValue.setText(car.licensePlate);
        vh.locationValue.setText(getAddressFromCoordinates(coordinate));

        vh.fuelType.setText("Fuel Type");
        if (car.fuelType.equals("P")){
            vh.fuelTypeValue.setText("Petrol");
        }else {
            vh.fuelTypeValue.setText("Diesel");
        }

        vh.carGroupValue.setText(car.group);

        vh.carSeriesValue.setText(car.series);

        vh.innerCleanlinessValue.setText(car.innerCleanliness);

        return v;
    }


    public class ViewHolder{

        public ViewHolder(View view){
            ButterKnife.inject(this, view);
        }

        /*@InjectView(R.id.icon1)
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
        public TextView carTransmissionValue;*/

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
    }

}
