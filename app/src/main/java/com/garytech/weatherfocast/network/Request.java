package com.garytech.weatherfocast.network;


import android.content.Context;
import android.location.Location;

import com.garytech.weatherfocast.model.WeatherForecast;
import com.garytech.weatherforecast.R;
import com.octo.android.robospice.persistence.exception.SpiceException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 */
public class Request extends com.garytech.weatherfocast.network.SpiceWebService<com.garytech.weatherfocast.model.WeatherForecast> {
    private RestTemplate restTemplate;
    private Context mContext;
    private Location mLocation;
    private HttpEntity<?> requestEntity;

    public Request(Context context, Location location) {
        super(com.garytech.weatherfocast.model.WeatherForecast.class);
        this.restTemplate = getRestTemplate();
        this.mContext = context;
        this.mLocation = location;
    }

    @Override
    public WeatherForecast loadDataFromNetwork() throws Exception {
        String url;

        if (mLocation == null) {
            url = mContext.getString(R.string.default_url).concat(mContext.getString(R.string.APP_ID)).concat(mContext.getString((R.string.API_KEY)));
        } else {
            url = mContext.getString(R.string.ws_url, (int) mLocation.getLatitude(), (int) mLocation.getLongitude()).concat(mContext.getString(R.string.APP_ID)).concat(mContext.getString((R.string.API_KEY)));
        }

        URI uri = new URI(url);
        String message;

        try {
            ResponseEntity<WeatherForecast> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, WeatherForecast.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            message = e.getResponseBodyAsString();
            throw new SpiceException(message);
        }
    }


}
