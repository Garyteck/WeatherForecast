package com.garytech.weatherfocast.requests;


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

    public FiveDaysRequest(){
        super(com.garytech.weatherfocast.model.WeatherForecast.class);
        this.restTemplate = getRestTemplate();

    }

    @Override
    public com.garytech.weatherfocast.model.WeatherForecast loadDataFromNetwork() throws Exception {
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Paris&units=metric&cnt=5";
        URI uri = new URI(url);
        String message;

        try {
            ResponseEntity<com.garytech.weatherfocast.model.WeatherForecast> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, com.garytech.weatherfocast.model.WeatherForecast.class);
            return response.getBody();
        }catch (HttpServerErrorException e) {
            message = e.getResponseBodyAsString();
            throw new SpiceException(message);
        }catch (HttpClientErrorException e) {
            message = e.getResponseBodyAsString();
            throw new SpiceException(message);
        }
    }



}
