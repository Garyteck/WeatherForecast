package com.example.garypierre_louis.previsionmeteorologiques.requests;


import com.example.garypierre_louis.previsionmeteorologiques.model.WeatherForecast;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.example.garypierre_louis.previsionmeteorologiques.service.SpiceWebService;
import org.springframework.http.HttpEntity;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Created by Gary PIERRE-LOUIS on 02/06/2015.
 */
public class FiveDaysRequest extends SpiceWebService<WeatherForecast> {
    protected RestTemplate restTemplate;
    private HttpEntity<?> requestEntity;

    public FiveDaysRequest(){
        super(WeatherForecast.class);
        this.restTemplate = getRestTemplate();

    }

    @Override
    public WeatherForecast loadDataFromNetwork() throws Exception {
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Paris&units=metric&cnt=5";
        URI uri = new URI(url);
        String message;

        try {
            ResponseEntity<WeatherForecast> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, WeatherForecast.class);
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
