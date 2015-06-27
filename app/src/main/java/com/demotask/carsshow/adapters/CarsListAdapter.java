package com.demotask.carsshow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demotask.carsshow.R;
import com.demotask.carsshow.webservice.Car;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by edrsoftware on 26.06.15.
 */
public class CarsListAdapter extends ArrayAdapter<Car> {


    public CarsListAdapter(Context context, int resource, List<Car> objects) {
        super(context, resource, objects);
    }

    @Override
    public Car getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_item,null);
            v.setTag(new ViewHolder(v));
        }

        final ViewHolder vh = (ViewHolder) v.getTag();

        Car car = getItem(position);

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
        }

        Picasso.with(getContext())
                .load(car.carImageUrl)
                .placeholder(R.drawable.ic_car)
                .error(R.drawable.ic_car)
                .into(vh.carImage);

        return v;
    }


    public class ViewHolder{

        public ViewHolder(View view){
            ButterKnife.inject(this,view);
        }

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
    }
}
