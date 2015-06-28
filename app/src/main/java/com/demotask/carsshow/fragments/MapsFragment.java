package com.demotask.carsshow.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demotask.carsshow.R;
import com.demotask.carsshow.backgroundtasks.CarLocationsLoader;
import com.demotask.carsshow.core.ApplicationState;
import com.demotask.carsshow.events.CarsDownloadFinishedEvent;
import com.demotask.carsshow.events.MapReadyEvent;
import com.demotask.carsshow.pojos.CarLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends BaseFragment implements OnMapReadyCallback, LocationListener, LoaderManager.LoaderCallbacks<List<CarLocation>> {

    private static final String GPS_PROVIDER = LocationManager.GPS_PROVIDER;
    private static final LatLng HOME_LOCATION = new LatLng(48.1333, 11.5667);
    private static final String NETWORK_LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;

    private MapView mMapView;
    private GoogleMap googleMap;
    private LocationRequest locationRequest;
    private LocationManager locationManager;

    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;

    private int LOCATION_LOADER = 1;

    public MapsFragment() {
        // Required empty public constructor
    }

    public static MapsFragment newInstance() {
        MapsFragment map = new MapsFragment();
        return map;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        locationManager = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate and return the layout
        View v = inflater.inflate(R.layout.fragment_maps, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);// needed to get the map to display immediately
        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
            mMapView.getMapAsync(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        // set map type
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setMyLocationEnabled(true);

        addMarker(HOME_LOCATION);
        locationManager.requestLocationUpdates(NETWORK_LOCATION_PROVIDER, 10, 100, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng location_LatLang = new LatLng(location.getLatitude(),
                location.getLongitude());
        addMarker(location_LatLang);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void addMarker(LatLng location) {

        googleMap.addMarker(new MarkerOptions()
                .position(location)
                .title("You")
                .snippet("You are here!!")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        // Zoom in the Google Map
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        getLoaderManager().initLoader(LOCATION_LOADER, null, this);
    }

    private void addMarker(LatLng location, String title, String snippet) {
        // map.setMyLocationEnabled(true);
        // Zoom in the Google Map
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));

        googleMap.addMarker(new MarkerOptions()
                .position(location)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_maps_distance)));
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(14));
    }

    public void moveCameraTo(GoogleMap mapInstace, LatLng location) {
        final CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(location)      // Sets the center of the map to Mountain View
                .zoom(13)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   //
        //mapInstace.addMarker(new MarkerOptions().position(new LatLng(companyDetail.getLatitude(), companyDetail.getLongitude())).title("Marker"));
        mapInstace.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        //mapInstace.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 40));
        //mapInstace.animateCamera(CameraUpdateFactory.zoomTo(14));
    }

    @Override
    public Loader<List<CarLocation>> onCreateLoader(int id, Bundle args) {
        CarLocationsLoader loader = new CarLocationsLoader(getActivity());
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<CarLocation>> loader, List<CarLocation> data) {
        if (data != null) {
            for (CarLocation location : data) {
                double lat = location.latitude;
                double lng = location.longitude;
                LatLng coordinate = new LatLng(lat, lng);
                addMarker(coordinate, location.carModel, "");
            }

            //To access GoogleMaps instance in details fragment
            ApplicationState.getInstance().getEventBus().post(new MapReadyEvent(googleMap));
        }
    }

    @Override
    public void onLoaderReset(Loader<List<CarLocation>> loader) {

    }

    @Subscribe
    public void carsInfoAvailbale(CarsDownloadFinishedEvent event) {
        ApplicationState.getInstance().setCarInfo(event.downloadedCarsInfo);
        getLoaderManager().restartLoader(LOCATION_LOADER, null, this);
    }
}