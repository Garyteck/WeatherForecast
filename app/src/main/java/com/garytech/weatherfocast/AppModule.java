package com.garytech.weatherfocast;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * This module is a library because it does not inject dependencies , its dependencies are used in other modules
 */
@Module(library = true)
public class AppModule {

    Context mContext;

    public AppModule(Application app) {
        mContext = app.getApplicationContext();
    }

    @Provides
    public Context provideContext() {
        return mContext;
    }
}
