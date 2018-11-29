package vlad.iweather2.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import vlad.iweather2.WeatherEntities.DayInfo;

@Dao
public interface DayInfoDao{
    @Query("SELECT * FROM weather")
    Flowable<List<DayInfo>> getAll();

    @Query("SELECT * FROM weather WHERE city = :city")
    Flowable<List<DayInfo>> getAllByCity(String city);

    @Query("SELECT COUNT(*) FROM weather WHERE city = :city")
    String getCityRows(String city);

    @Query("DELETE FROM weather WHERE city = :city")
    void deleteCity(String city);

    @Insert
    long insert(DayInfo weatherData);

    @Update
    void update(DayInfo weatherData);

    @Delete
    void delete(DayInfo weatherData);
}
