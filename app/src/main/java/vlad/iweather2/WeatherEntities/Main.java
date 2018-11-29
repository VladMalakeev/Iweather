package vlad.iweather2.WeatherEntities;

import android.arch.persistence.room.Entity;

@Entity
public class Main{
    public Double temp_min;
    public Double temp_max;
    public Double pressure;
    public int humidity;
}