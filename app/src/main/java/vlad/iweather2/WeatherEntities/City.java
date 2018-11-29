package vlad.iweather2.WeatherEntities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "cities")
public class City{

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public String country_code;
}