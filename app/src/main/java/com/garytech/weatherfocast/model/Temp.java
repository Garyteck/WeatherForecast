package com.garytech.weatherfocast.model;

import java.io.Serializable;

/*
generé par http://pojo.sodhanalibrary.com/
 */

public class Temp implements Serializable
{
    private String min;

    private String eve;

    private String temp_max;

    private String temp_min;

    private String night;

    private String temp;

    public String getMin ()
    {
        return min;
    }

    public void setMin (String min)
    {
        this.min = min;
    }

    public String getEve ()
    {
        return eve;
    }

    public void setEve (String eve)
    {
        this.eve = eve;
    }

    public String getTemp_max()
    {
        return temp_max;
    }

    public void setTemp_max(String temp_max)
    {
        this.temp_max = temp_max;
    }

    public String getTemp_min()
    {
        return temp_min;
    }

    public void setTemp_min(String temp_min)
    {
        this.temp_min = temp_min;
    }

    public String getNight ()
    {
        return night;
    }

    public void setNight (String night)
    {
        this.night = night;
    }

    public String getTemp()
    {
        return temp;
    }

    public void setTemp(String temp)
    {
        this.temp = temp;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [min = "+min+", eve = "+eve+", temp_max = "+ temp_max +", temp_min = "+ temp_min +", night = "+night+", temp = "+ temp +"]";
    }
}