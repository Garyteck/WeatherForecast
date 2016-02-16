package com.garytech.weatherfocast;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * This module is a library because it does not provide dependencies ( there is no inject annotation ) , its dependencies are used in other modules
 */
@Module(library = true)
public class ContextModule {


    Context mContext;

    public ContextModule(Application app) {
        mContext = app.getApplicationContext();
    }

    @Provides
    public Context provideContext() {
        return mContext;
    }
}
