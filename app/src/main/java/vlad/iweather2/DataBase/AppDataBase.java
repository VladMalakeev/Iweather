package vlad.iweather2.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import vlad.iweather2.WeatherEntities.City;
import vlad.iweather2.WeatherEntities.DayInfo;

@Database(entities = {DayInfo.class,City.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract DayInfoDao dayInfoDao();
    public abstract CitiesDao citiesDao();
}
