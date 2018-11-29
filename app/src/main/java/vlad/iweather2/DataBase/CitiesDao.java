package vlad.iweather2.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import vlad.iweather2.WeatherEntities.City;

@Dao
public interface CitiesDao{
    @Query("SELECT * FROM cities")
    Flowable<List<City>> getAll();

    @Query("SELECT * FROM cities GROUP BY state_name")
    Flowable<List<City>> getStates();

    @Query("SELECT * FROM cities WHERE id = :id")
    City getById(long id);

    @Query("SELECT * FROM cities WHERE state_name = :state")
    Flowable<List<City>> getCityByState(String state);

    @Insert
    long insert(City city);

    @Update
    void update(City city);

    @Delete
    void delete(City city);
}
