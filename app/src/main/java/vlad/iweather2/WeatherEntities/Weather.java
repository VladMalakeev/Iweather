package vlad.iweather2.WeatherEntities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
@Entity
public class Weather{
    @Ignore
    public long id;
    @Ignore
    public String main;
    public String description;
    public String icon;
}