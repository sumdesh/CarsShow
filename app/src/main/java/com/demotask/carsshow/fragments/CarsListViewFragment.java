package com.demotask.carsshow.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.demotask.carsshow.R;
import com.demotask.carsshow.activities.DetailsActivity;
import com.demotask.carsshow.adapters.CarsListAdapter;
import com.demotask.carsshow.core.ApplicationState;
import com.demotask.carsshow.events.CarsDownloadFinishedEvent;
import com.demotask.carsshow.utility.NetworkUtility;
import com.demotask.carsshow.webservice.Car;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class CarsListViewFragment extends BaseListFragment implements AdapterView.OnItemClickListener{

    private static List<Car> displayList;
    private CarsListAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CarsListViewFragment() {
    }

    public static CarsListViewFragment newInstance() {
        CarsListViewFragment fragment = new CarsListViewFragment();
        displayList = ApplicationState.getInstance().getCarInfo();
        return fragment;
    }

    public static CarsListViewFragment newInstance(Car displayItem) {
        CarsListViewFragment fragment = new CarsListViewFragment();
        List<Car>items = new ArrayList<Car>();
        items.add(displayItem);
        displayList = items;
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

        if (displayList != null){
            loadList(displayList);
        }else{
            Crouton.makeText(getActivity(), R.string.cars_info_unavailbale, Style.INFO).show();
        }
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
            setListAdapter(adapter);
        }

        //setListShown(true);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Car car = (Car) adapterView.getAdapter().getItem(i);
        Intent carDetailsActivity = new Intent(getActivity(), DetailsActivity.class);
        carDetailsActivity.putExtra("car_selection_position",i);
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
