package com.garytech.weatherfocast.model;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Arrays;

@JsonIgnoreProperties({"clouds", "humidity", "pressure", "deg", "speed"})
public class Forecast implements Serializable {

    private String dt;


    private com.garytech.weatherfocast.model.Weather[] weather;

    private Temp main;

    public Forecast() {

    }


    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }


    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public com.garytech.weatherfocast.model.Temp getMain() {
        return main;
    }

    public void setMain(com.garytech.weatherfocast.model.Temp main) {
        this.main = main;
    }


    @Override
    public String toString() {
        return "Forecast{" +
                "dt='" + dt + '\'' +
                ", weather=" + Arrays.toString(weather) +
                ", main=" + main +
                '}';
    }
}