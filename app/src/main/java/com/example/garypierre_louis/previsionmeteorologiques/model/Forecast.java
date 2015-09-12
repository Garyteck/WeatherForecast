package com.example.garypierre_louis.previsionmeteorologiques.model;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Arrays;

@JsonIgnoreProperties({"clouds","humidity","pressure","deg","speed"})
public class Forecast implements Serializable
{

    private String dt;


    private Weather[] weather;

    private Temp temp;

    public Forecast() {

    }
    private String rain;


    public String getDt ()
    {
        return dt;
    }

    public void setDt (String dt)
    {
        this.dt = dt;
    }


    public Weather[] getWeather ()
    {
        return weather;
    }

    public void setWeather (Weather[] weather)
    {
        this.weather = weather;
    }

    public Temp getTemp ()
    {
        return temp;
    }

    public void setTemp (Temp temp)
    {
        this.temp = temp;
    }

    public String getRain ()
    {
        return this.rain;
    }

    public void setRain (String rain)
    {
        this.rain = rain;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "dt='" + dt + '\'' +
                ", weather=" + Arrays.toString(weather) +
                ", temp=" + temp +
                ", rain='" + rain + '\'' +
                '}';
    }
}