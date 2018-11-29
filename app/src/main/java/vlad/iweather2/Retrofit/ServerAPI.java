package vlad.iweather2.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vlad.iweather2.WeatherEntities.City;
import vlad.iweather2.WeatherEntities.CityWeather;

public interface ServerAPI {

    @GET("/data/2.5/forecast")
    Call<CityWeather> getDataByLocation(@Query("lat") Double lat, @Query("lon") Double lon, @Query("units") String units, @Query("appid") String appid);

    @GET("/data/2.5/forecast")
    Call<CityWeather> getDataByCityName(@Query("q") String name, @Query("units") String units, @Query("appid") String appid);

    @GET("/my")
    Call<List<City>> getCityData();
}

