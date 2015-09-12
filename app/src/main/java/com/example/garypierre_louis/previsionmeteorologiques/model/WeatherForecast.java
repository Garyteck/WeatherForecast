package com.example.garypierre_louis.previsionmeteorologiques.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Arrays;

@JsonIgnoreProperties({"message","cnt","city","cod"})
public class WeatherForecast implements Serializable
{


    public Forecast[] getList() {
        return list;
    }

    private Forecast[] list;


    public WeatherForecast() {} ;


    @Override
    public String toString() {
        return "WeatherForecast{" +
                "list=" + Arrays.toString(list) +
                '}';
    }

    public Forecast getList(int position) {
        return this.list[position];
    }
}