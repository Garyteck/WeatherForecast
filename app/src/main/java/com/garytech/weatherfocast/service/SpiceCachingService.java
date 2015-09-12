package com.garytech.weatherfocast.service;


import android.app.Application;

import com.octo.android.robospice.SpringAndroidSpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.springandroid.json.jackson.JacksonObjectPersisterFactory;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class SpiceCachingService extends SpringAndroidSpiceService {

    private RestTemplate restTemplate =  new RestTemplate();

    @Override
    public RestTemplate createRestTemplate() {
        MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        restTemplate.getMessageConverters().add(converter);
        return this.restTemplate;
    }

    @Override
    public CacheManager createCacheManager(Application application)
            throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();
        cacheManager.addPersister(new JacksonObjectPersisterFactory(application));
        return cacheManager;
    }

}
