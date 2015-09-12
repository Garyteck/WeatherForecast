package com.garytech.weatherfocast.helpers;


import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Format a Unix timestamp into a human readable week day String such as "Today", "Tomorrow"
 * and "Wednesday"
 */
public class DayFormatter {
    private final static long MILLISECONDS_IN_SECONDS = 1000;

    public String format(final long unixTimestamp) {
        final long milliseconds = unixTimestamp * MILLISECONDS_IN_SECONDS;
        return  getDayOfWeek(milliseconds);

    }

    private String getDayOfWeek(final long milliseconds) {
        return new SimpleDateFormat("EEEE").format(new Date(milliseconds));
    }


}
