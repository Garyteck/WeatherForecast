package com.garytech.weatherfocast.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.garytech.weatherfocast.helpers.DayFormatter;
import com.garytech.weatherfocast.model.Forecast;
import com.garytech.weatherfocast.model.Temp;
import com.garytech.weatherfocast.model.Weather;
import com.garytech.weatherforecast.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailedWeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailedWeatherFragment extends Fragment {
    private static final String FORECAST_SELECTED_BUNDLE_KEY = "FORECAST_SELECTED_BUNDLE_KEY";

    private Forecast previsions;

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
            previsions = (Forecast) getArguments().getSerializable(FORECAST_SELECTED_BUNDLE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_weather, container, false);

        TextView mMin, mAverage, mMax, mMain, mDescription;

        ImageView mIcon;

        mMin = (TextView) view.findViewById(R.id.min);
        mAverage = (TextView) view.findViewById(R.id.average);
        mMax = (TextView) view.findViewById(R.id.max);

        mMain = (TextView) view.findViewById(R.id.main);
        mDescription = (TextView) view.findViewById(R.id.description);
        mIcon = (ImageView) view.findViewById(R.id.icon);

        Temp temperatures = previsions.getMain();
        Weather[] weather = previsions.getWeather();

        mMin.setText(com.garytech.weatherfocast.helpers.TemperatureFormatter.format(Float.valueOf(temperatures.getTemp_min())));
        mAverage.setText(com.garytech.weatherfocast.helpers.TemperatureFormatter.format(Float.valueOf(temperatures.getTemp())));
        mMax.setText(com.garytech.weatherfocast.helpers.TemperatureFormatter.format(Float.valueOf(temperatures.getTemp_max())));

        mMain.setText(weather[0].getMain());
        mDescription.setText(weather[0].getDescription());


        Picasso.with(getActivity())
                .load(getString(R.string.icon_url).concat(previsions.getWeather()[0].getIcon().concat(getString(R.string.icon_extension))))
                .into(mIcon);

        return view;

    }


}
