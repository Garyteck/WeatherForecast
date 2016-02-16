package com.garytech.weatherfocast.activities;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.garytech.weatherfocast.base.BaseActivity;
import com.garytech.weatherfocast.helpers.DayFormatter;
import com.garytech.weatherfocast.interaction.OnListFragmentInteraction;
import com.garytech.weatherfocast.model.WeatherForecast;
import com.garytech.weatherfocast.network.Request;
import com.garytech.weatherfocast.network.WebServiceModule;
import com.garytech.weatherfocast.utils.Utils;
import com.garytech.weatherforecast.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements RequestListener<WeatherForecast>, OnListFragmentInteraction {

    /**
     * Rest Service
     */
    @Inject
    SpiceManager mSpiceManager;

    @Inject
    Request mRequest;


    /**
     * key bundles
     */
    public final static String WEATHER_FORECAST_BUNDLE_KEY = "WEATHER_FORECAST_BUNDLE_KEY";
    public final static String REQUEST_SUCCEDED_BUNDLE_KEY = "REQUEST_SUCCEDED_BUNDLE_KEY";


    /**
     * data
     */
    private WeatherForecast mWeatherForecast;

    /**
     * Views
     */
    private ProgressBar mProgressBar;
    private TextView mTextViewError;
    private View mSlaveView;
    /**
     * item selected
     */
    private int mCurrentPosition = -1;

    private boolean mRequestSucceeded;
    private static final DayFormatter dayFormatter = new DayFormatter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.reload_circulair);
        mTextViewError = (TextView) findViewById(R.id.textView_error);
        mSlaveView = findViewById(R.id.slave);

        mTextViewError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeRequest();
            }
        });


    }

    @Override
    protected List<Object> getModules() {
        return Collections.<Object>singletonList(new WebServiceModule());
    }


    @Override
    protected void onStart() {
        super.onStart();
        mSpiceManager.start(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!mRequestSucceeded) {
            executeRequest();

        } else {
            resetUi();
        }
    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mWeatherForecast = (WeatherForecast) savedInstanceState.getSerializable(WEATHER_FORECAST_BUNDLE_KEY);
        mRequestSucceeded = savedInstanceState.getBoolean(REQUEST_SUCCEDED_BUNDLE_KEY);
    }

    @Override
    public void onListItemTouched(int position) {

        if (position != mCurrentPosition) {
            if (mSlaveView != null) {
                replaceFragment(R.id.slave, position, false);
                mCurrentPosition = position;
            } else {
                replaceFragment(R.id.master, position, true);
                mActionBar.setDisplayHomeAsUpEnabled(true);
                mActionBar.setTitle(dayFormatter.format(Long.valueOf(mWeatherForecast.getList(position).getDt())));

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(WEATHER_FORECAST_BUNDLE_KEY, mWeatherForecast);
        outState.putBoolean(REQUEST_SUCCEDED_BUNDLE_KEY, mRequestSucceeded);
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
        mRequestSucceeded = false;
        resetUi();
    }

    @Override
    public void onRequestSuccess(com.garytech.weatherfocast.model.WeatherForecast weatherForecast) {
        mRequestSucceeded = true;
        resetUi();
        mWeatherForecast = weatherForecast;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.master, com.garytech.weatherfocast.fragments.ForeCastListFragment.newInstance(weatherForecast.getList()))
                .commitAllowingStateLoss();

        if (mSlaveView != null) {
            onListItemTouched(0);
        }

    }

    private void executeRequest() {
        mSpiceManager.execute(mRequest, Utils.CACHE_NAME, DurationInMillis.ALWAYS_EXPIRED, this);
        mProgressBar.setVisibility(View.VISIBLE);
        mTextViewError.setVisibility(View.GONE);
    }

    private void resetUi() {
        if (mRequestSucceeded) {
            mProgressBar.setVisibility(View.GONE);
            mTextViewError.setVisibility(View.GONE);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mTextViewError.setVisibility(View.VISIBLE);
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
    public void onBackPressed() {
        super.onBackPressed();
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setTitle(R.string.app_name);
    }
}
