package com.garytech.weatherfocast.requests;


import android.content.Context;

import com.garytech.weatherfocast.model.WeatherForecast;
import com.garytech.weatherforecast.R;
import com.octo.android.robospice.persistence.exception.SpiceException;

import org.springframework.http.HttpEntity;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 */
public class FiveDaysRequest extends com.garytech.weatherfocast.service.SpiceWebService<com.garytech.weatherfocast.model.WeatherForecast> {
    protected RestTemplate restTemplate;
    private HttpEntity<?> requestEntity;
    Context mContext;

    public FiveDaysRequest(Context context){
        super(com.garytech.weatherfocast.model.WeatherForecast.class);
        this.restTemplate = getRestTemplate();
        this.mContext = context;

    }

    @Override
    public WeatherForecast loadDataFromNetwork() throws Exception {
        //String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Paris&units=metric&cnt=5";
        String url = mContext.getString(R.string.ws_url).concat(mContext.getString(R.string.APP_ID)).concat(mContext.getString((R.string.API_KEY)));
        URI uri = new URI(url);
        String message;

        try {
            ResponseEntity<WeatherForecast> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, WeatherForecast.class);
            return response.getBody();
        }catch (HttpClientErrorException e) {
            message = e.getResponseBodyAsString();
            throw new SpiceException(message);
        }catch (HttpServerErrorException e) {
            message = e.getResponseBodyAsString();
            throw new SpiceException(message);
        }
    }



}
