package com.garytech.weatherfocast.activities;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.garytech.weatherfocast.LocationModule;
import com.garytech.weatherfocast.base.BaseActivity;
import com.garytech.weatherfocast.model.WeatherForecast;
import com.garytech.weatherfocast.network.Request;
import com.garytech.weatherfocast.network.WebServiceModule;
import com.garytech.weatherforecast.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements RequestListener<WeatherForecast>, com.garytech.weatherfocast.interaction.OnListFragmentInteraction {

    /**
     * Rest Service
     */
    @Inject
    SpiceManager mSpiceManager;

    @Inject
    Request mRequest;

    @Inject
    Location mLocation;

    /**
     * key bundles
     */
    public final static String WEATHER_FORECAST_BUNDLE_KEY = "WEATHER_FORECAST_BUNDLE_KEY";
    public final static String REQUEST_SUCCEDED_BUNDLE_KEY = "REQUEST_SUCCEDED_BUNDLE_KEY";


    /**
     * data
     */
    WeatherForecast mWeatherForecast;

    /**
     * Views
     */
    ProgressBar mProgressBar;
    TextView mTextViewError;

    /**
     * item selected
     */
    int mCurrentPosition = -1;

    boolean requestSucceeded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.reload_circulair);
        mTextViewError = (TextView) findViewById(R.id.textView_error);

    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new WebServiceModule(), new LocationModule(this));
    }


    @Override
    protected void onStart() {
        super.onStart();
        mSpiceManager.start(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!requestSucceeded) {
            mSpiceManager.execute(mRequest, com.garytech.weatherfocast.utils.Utils.CACHE_NAME, DurationInMillis.ALWAYS_EXPIRED, this);
            mProgressBar.setVisibility(View.VISIBLE);
            mTextViewError.setVisibility(View.GONE);

        } else {
            resetUi(requestSucceeded);
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mWeatherForecast = (WeatherForecast) savedInstanceState.getSerializable(WEATHER_FORECAST_BUNDLE_KEY);
        requestSucceeded = savedInstanceState.getBoolean(REQUEST_SUCCEDED_BUNDLE_KEY);
    }

    @Override
    public void onListItemTouched(int position) {

        if (position != mCurrentPosition) {
            if (findViewById(R.id.slave) != null) {
                replaceFragment(R.id.slave, position, false);
                mCurrentPosition = position;
            } else {
                replaceFragment(R.id.master, position, true);
            }
        }
    }

    private void replaceFragment(int containerId, int position, boolean addToBackStack) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(containerId, com.garytech.weatherfocast.fragments.DetailedWeatherFragment.newInstance(mWeatherForecast.getList(position)));
        if (addToBackStack) {
            transaction.addToBackStack("");
        }
        transaction.commitAllowingStateLoss();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(WEATHER_FORECAST_BUNDLE_KEY, mWeatherForecast);
        outState.putBoolean(REQUEST_SUCCEDED_BUNDLE_KEY, requestSucceeded);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        if (mSpiceManager != null && mSpiceManager.isStarted()) {
            mSpiceManager.shouldStop();
        }
        super.onStop();
    }


    @Override
    public void onRequestFailure(SpiceException spiceException) {
        requestSucceeded = false;
        resetUi(requestSucceeded);
    }

    @Override
    public void onRequestSuccess(com.garytech.weatherfocast.model.WeatherForecast weatherForecast) {
        requestSucceeded = true;
        resetUi(requestSucceeded);
        this.mWeatherForecast = weatherForecast;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.master, com.garytech.weatherfocast.fragments.ForeCastListFragment.newInstance(weatherForecast.getList()))
                .commitAllowingStateLoss();

        if (findViewById(R.id.slave) != null) {
            onListItemTouched(0);
        }

    }

    private void resetUi(boolean requestSucceeded) {
        if (requestSucceeded) {
            mProgressBar.setVisibility(View.GONE);
            mTextViewError.setVisibility(View.GONE);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mTextViewError.setVisibility(View.VISIBLE);
        }
    }

}
