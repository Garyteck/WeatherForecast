package com.garytech.weatherfocast.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.garytech.weatherfocast.model.Forecast;
import com.garytech.weatherfocast.model.Temp;
import com.garytech.weatherforecast.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailedWeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailedWeatherFragment extends Fragment {
    private static final String FORECAST_SELECTED_BUNDLE_KEY = "FORECAST_SELECTED_BUNDLE_KEY";

    private com.garytech.weatherfocast.model.Forecast previsions;

    private TextView mMatin, mMidi, mSoir, mMain, mDescription;

    private ImageView mIcon;

    public DetailedWeatherFragment() {
    }

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
        mMatin = (TextView) view.findViewById(R.id.matin);
        mMidi = (TextView) view.findViewById(R.id.midi);
        mSoir = (TextView) view.findViewById(R.id.soir);

        mMain = (TextView) view.findViewById(R.id.main);
        mDescription = (TextView) view.findViewById(R.id.description);
        mIcon = (ImageView) view.findViewById(R.id.icon);

        Temp temperatures = previsions.getMain();
        com.garytech.weatherfocast.model.Weather[] weather = previsions.getWeather(); // le JSON Weather est un array avec un seul object JSON, nous sommes obligés de faire une liste

        mMatin.setText(com.garytech.weatherfocast.helpers.TemperatureFormatter.format(Float.valueOf(temperatures.getTemp_min())));
        mMidi.setText(com.garytech.weatherfocast.helpers.TemperatureFormatter.format(Float.valueOf(temperatures.getTemp())));
        mSoir.setText(com.garytech.weatherfocast.helpers.TemperatureFormatter.format(Float.valueOf(temperatures.getTemp_max())));

        mMain.setText(weather[0].getMain());
        mDescription.setText(weather[0].getDescription());

        Picasso.with(getActivity())
                .load(getString(R.string.icon_url).concat(previsions.getWeather()[0].getIcon().concat(getString(R.string.icon_extension))))
                .into(mIcon);

        return view;

    }


}
