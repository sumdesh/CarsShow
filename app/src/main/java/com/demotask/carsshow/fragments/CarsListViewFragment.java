package com.demotask.carsshow.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.demotask.carsshow.R;
import com.demotask.carsshow.activities.DetailsActivity;
import com.demotask.carsshow.adapters.CarsListAdapter;
import com.demotask.carsshow.core.ApplicationState;
import com.demotask.carsshow.events.CarSelectedEvent;
import com.demotask.carsshow.events.CarsDownloadFinishedEvent;
import com.demotask.carsshow.webservice.Car;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class CarsListViewFragment extends BaseListFragment implements AdapterView.OnItemClickListener{

    private CarsListAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CarsListViewFragment() {
    }

    public static CarsListViewFragment newInstance() {
        CarsListViewFragment fragment = new CarsListViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListShown(false);
        setEmptyText(getString(R.string.loading));

        getListView().setOnItemClickListener(this);

        loadList(ApplicationState.getInstance().getCarInfo());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);


    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {

    }

    @Subscribe
    public void carsInfoAvailbale(CarsDownloadFinishedEvent event){
        ApplicationState.getInstance().setCarInfo(event.downloadedCarsInfo);
        loadList(event.downloadedCarsInfo);
    }

    private void loadList(List<Car> carsInfo) {
        if (adapter == null) {
            adapter = new CarsListAdapter(getActivity(), R.layout.fragment_item, carsInfo);
        }
        setListAdapter(adapter);
        setListShown(true);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Car car = (Car) adapterView.getAdapter().getItem(i);
        ApplicationState.getInstance().getEventBus().post(new CarSelectedEvent(car));

        Intent carDetailsActivity = new Intent(getActivity(), DetailsActivity.class);
        carDetailsActivity.putExtra("car_selection",car);
        startActivity(carDetailsActivity);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }
}
