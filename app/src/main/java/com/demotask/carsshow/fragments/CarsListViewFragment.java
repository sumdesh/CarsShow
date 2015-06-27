package com.demotask.carsshow.fragments;

import android.app.Activity;
import android.os.Bundle;

import com.demotask.carsshow.R;
import com.demotask.carsshow.adapters.CarsListAdapter;
import com.demotask.carsshow.core.ApplicationState;
import com.demotask.carsshow.events.CarsDownloadFinishedEvent;
import com.demotask.carsshow.webservice.Car;
import com.squareup.otto.Subscribe;

import java.util.List;

public class CarsListViewFragment extends BaseListFragment {

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
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListShown(false);
        setEmptyText(getString(R.string.loading));

        loadList(ApplicationState.getInstance().getCarInfo());
    }

     /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {

    }

    @Subscribe
    public void CarsInfoAvailbale(CarsDownloadFinishedEvent event) {
        loadList(event.downloadedCarsInfo);
    }

    private void loadList(List<Car> carsInfo) {
/*mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);*/

        if (adapter == null) {
            adapter = new CarsListAdapter(getActivity(), R.layout.fragment_item, carsInfo);
        }
        setListAdapter(adapter);
        setListShown(true);
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
