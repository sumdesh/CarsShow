package com.demotask.carsshow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

import com.demotask.carsshow.R;
import com.demotask.carsshow.backgroundtasks.CarsInfoDownloadService;
import com.demotask.carsshow.core.ApplicationState;
import com.demotask.carsshow.fragments.CarsListViewFragment;
import com.demotask.carsshow.fragments.MapsFragment;
import com.demotask.carsshow.fragments.NavigationDrawerFragment;
import com.demotask.carsshow.utility.NetworkUtility;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_main);
        ApplicationState.getInstance().getEventBus().register(this);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        if (NetworkUtility.isNetworkAvailable(this)){
            //Starting cars info download on background
            setProgressBarIndeterminateVisibility(true);
            Intent service = new Intent(MainActivity.this, CarsInfoDownloadService.class);
            startService(service);
        }else{
            Crouton.makeText(this, R.string.internet_connection_error, Style.ALERT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!NetworkUtility.isNetworkAvailable(this)){
            Crouton.makeText(this, R.string.internet_connection_error, Style.ALERT).show();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (position) {

            case 0:
                onSectionAttached(1);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, MapsFragment.newInstance())
                        .commit();
                break;

            case 1:
                onSectionAttached(2);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, CarsListViewFragment.newInstance())
                        .commit();
                break;

            default:
                onSectionAttached(1);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, MapsFragment.newInstance())
                        .commit();
                break;
        }
        restoreActionBar();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.map_view);
                break;
            case 2:
                mTitle = getString(R.string.list_view);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }
}
