package com.example.garypierre_louis.previsionmeteorologiques.helpers;

public class TemperatureFormatter {

    public static String format(float temperature) {
        return String.valueOf(Math.round(temperature)) + "° C";
    }
}
