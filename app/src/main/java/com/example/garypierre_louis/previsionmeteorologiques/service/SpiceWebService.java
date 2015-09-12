package com.example.garypierre_louis.previsionmeteorologiques.service;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.octo.android.robospice.retry.DefaultRetryPolicy;
import com.octo.android.robospice.retry.RetryPolicy;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


public abstract class SpiceWebService<T> extends SpringAndroidSpiceRequest<T> {

    private RestTemplate restTemplate =  new RestTemplate();;

    public SpiceWebService(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public RestTemplate getRestTemplate() {
        MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        converter.getObjectMapper().disable(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS);
        restTemplate.getMessageConverters().add(converter);
        return this.restTemplate;
    }



    @Override
    public RetryPolicy getRetryPolicy() {
        return new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_DELAY_BEFORE_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }



}
