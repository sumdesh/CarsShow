package com.demotask.carsshow.webservice;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by edrsoftware on 25.06.15.
 */
public class Car implements Parcelable{

    public String id;
    public String modelIdentifier;
    public String modelName;
    public String name;
    public String make;
    public String group;
    public String color;
    public String series;
    public String fuelType;
    public double fuelLevel;
    public String transmission;
    public String licensePlate;
    public double latitude;
    public double longitude;
    public String innerCleanliness;
    public String carImageUrl;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(id);
        parcel.writeString(modelIdentifier);
        parcel.writeString(modelName);
        parcel.writeString(name);
        parcel.writeString(make);
        parcel.writeString(group);
        parcel.writeString(color);
        parcel.writeString(series);
        parcel.writeString(fuelType);
        parcel.writeDouble(fuelLevel);
        parcel.writeString(transmission);
        parcel.writeString(licensePlate);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(innerCleanliness);
        parcel.writeString(carImageUrl);
    }

    public static  final Parcelable.Creator<Car> CREATOR = new Parcelable.Creator<Car>(){

        public Car createFromParcel(Parcel in){
            return new Car(in);
        }

        public Car[] newArray(int size){
            return new Car[size];
        }
    };

    private Car(Parcel in){
        id = in.readString();
        modelIdentifier = in.readString();
        modelName = in.readString();
        name = in.readString();
        make = in.readString();
        group = in.readString();
        color = in.readString();
        series = in.readString();
        fuelType = in.readString();
        fuelLevel = in.readDouble();
        transmission = in.readString();
        licensePlate = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        innerCleanliness = in.readString();
        carImageUrl = in.readString();
    }

}
