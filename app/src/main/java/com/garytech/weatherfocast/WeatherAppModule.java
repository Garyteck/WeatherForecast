package com.garytech.weatherfocast;

import com.garytech.weatherfocast.network.WebServiceModule;

import dagger.Module;

/***
 * At compile-time validation, this module , which includes all modules of the app , will tell us if there is any problems in the modules.
 */
@Module(includes = {ContextModule.class, LocationModule.class, WebServiceModule.class})
public class WeatherAppModule {
}
