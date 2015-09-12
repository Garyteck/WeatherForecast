package com.garytech.weatherfocast.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.garytech.weatherfocast.model.Forecast;
import com.garytech.weatherforecast.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailedWeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailedWeatherFragment extends Fragment {
    private static final String FORECAST_SELECTED_BUNDLE_KEY = "FORECAST_SELECTED_BUNDLE_KEY";

    private com.garytech.weatherfocast.model.Forecast previsions;

    private TextView mMatin, mMidi, mSoir, mMain, mDescription;


    public DetailedWeatherFragment() {}

    public static DetailedWeatherFragment newInstance(Forecast param1) {
        DetailedWeatherFragment fragment = new DetailedWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(FORECAST_SELECTED_BUNDLE_KEY, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            previsions = (com.garytech.weatherfocast.model.Forecast) getArguments().getSerializable(FORECAST_SELECTED_BUNDLE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_weather, container, false);
        mMatin = (TextView)view.findViewById(R.id.matin);
        mMidi = (TextView) view.findViewById(R.id.midi);
        mSoir = (TextView) view.findViewById(R.id.soir);

        mMain = (TextView) view.findViewById(R.id.main);
        mDescription = (TextView) view.findViewById(R.id.description) ;


        com.garytech.weatherfocast.model.Temp temperatures =  previsions.getTemp();
        com.garytech.weatherfocast.model.Weather[] weather =  previsions.getWeather(); // le JSON Weather est un array avec un seul object JSON, nous sommes obligés de faire une liste
        mMatin.setText(com.garytech.weatherfocast.helpers.TemperatureFormatter.format(Float.valueOf(temperatures.getMorn())));
        mMidi.setText(com.garytech.weatherfocast.helpers.TemperatureFormatter.format(Float.valueOf(temperatures.getDay())));
        mSoir.setText(com.garytech.weatherfocast.helpers.TemperatureFormatter.format(Float.valueOf(temperatures.getNight())));
        mMain.setText(weather[0].getMain());
        mDescription.setText(weather[0].getDescription());

        return view;

    }



}
