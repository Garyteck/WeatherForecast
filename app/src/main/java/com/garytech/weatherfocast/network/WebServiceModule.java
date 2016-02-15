package com.garytech.weatherfocast.network;


import android.content.Context;
import android.location.Location;

import com.garytech.weatherfocast.LocationModule;
import com.garytech.weatherfocast.activities.MainActivity;
import com.octo.android.robospice.SpiceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This module will inject a request manager and a request object to the main activity
 * This module is not complete because it uses the context provided by the app module
 */
@Module(injects = MainActivity.class, includes = LocationModule.class, complete = false)
public class WebServiceModule {


    @Provides
    @Singleton
    public Request provideRequestObject(Context context, Location location) {
        return new Request(context, location);
    }


    @Provides
    @Singleton
    public SpiceManager provideSpiceManager() {
        return new SpiceManager(com.garytech.weatherfocast.network.SpiceCachingService.class);
    }
}
