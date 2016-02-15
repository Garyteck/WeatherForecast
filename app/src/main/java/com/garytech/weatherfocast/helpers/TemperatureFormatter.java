package com.garytech.weatherfocast.helpers;

public class TemperatureFormatter {

    public static String format(float temperature) {

        return String.format("%02d ° C", (int) (Math.round(temperature) - 273.15)) + "";
    }
}
