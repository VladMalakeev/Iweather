package vlad.iweather2.WeatherEntities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

@Entity(tableName = "weather")
public class DayInfo{
    @PrimaryKey(autoGenerate = true)
    public long table_id;
    public long dt;
    @Embedded
    public Main main;
    @Ignore
    public List<Weather> weather;
    @Embedded
    public Weather weatherInfo;
    @Embedded
    public Wind wind;
    public String city;


}
