/*
 *
 *  *
 *  *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *  *
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  *
 *  *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *
 *
 */

package com.garytech.weatherfocast;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class LocationModule {

    Location mLocation;

    @Provides
    Location provideLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return mLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }

}
