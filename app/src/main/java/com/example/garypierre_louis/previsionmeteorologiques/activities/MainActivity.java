package com.example.garypierre_louis.previsionmeteorologiques.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.garypierre_louis.previsionmeteorologiques.R;
import com.example.garypierre_louis.previsionmeteorologiques.fragments.DetailedWeatherFragment;
import com.example.garypierre_louis.previsionmeteorologiques.fragments.ForeCastListFragment;
import com.example.garypierre_louis.previsionmeteorologiques.interaction.OnListFragmentInteraction;
import com.example.garypierre_louis.previsionmeteorologiques.model.WeatherForecast;
import com.example.garypierre_louis.previsionmeteorologiques.requests.FiveDaysRequest;
import com.example.garypierre_louis.previsionmeteorologiques.service.SpiceCachingService;
import com.example.garypierre_louis.previsionmeteorologiques.utils.Utils;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class MainActivity extends AppCompatActivity  implements RequestListener<WeatherForecast>,OnListFragmentInteraction {

    /**
     * Rest Service
     */
    public final static SpiceManager mSpiceManager = new SpiceManager(SpiceCachingService.class);

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

    boolean requestSucceeded ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.reload_circulair);
        mTextViewError = (TextView) findViewById(R.id.textView_error);

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
            mSpiceManager.execute(new FiveDaysRequest(), Utils.CACHE_NAME, DurationInMillis.ALWAYS_EXPIRED, this);
            mProgressBar.setVisibility(View.VISIBLE);
            mTextViewError.setVisibility(View.GONE);

        }else {
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

    private void replaceFragment(int containerId,int position, boolean addToBackStack) {
        android.support.v4.app.FragmentTransaction transaction  = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(containerId, DetailedWeatherFragment.newInstance(mWeatherForecast.getList(position)));
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
        if (mSpiceManager != null && mSpiceManager.isStarted() ) {
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
    public void onRequestSuccess(WeatherForecast weatherForecast) {
        requestSucceeded = true;
        resetUi(requestSucceeded);
        this.mWeatherForecast = weatherForecast;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.master, ForeCastListFragment.newInstance(weatherForecast.getList()))
                .commitAllowingStateLoss();

        if (findViewById(R.id.slave) != null) {
            onListItemTouched(0);
        }

    }

    private void resetUi(boolean requestSucceeded) {
        if (requestSucceeded) {
            mProgressBar.setVisibility(View.GONE);
            mTextViewError.setVisibility(View.GONE);
        }else {
            mProgressBar.setVisibility(View.GONE);
            mTextViewError.setVisibility(View.VISIBLE);
        }
    }

}
