package vlad.iweather2.Retrofit;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vlad.iweather2.DataBase.AppDataBase;
import vlad.iweather2.DataBase.CitiesDao;
import vlad.iweather2.DataBase.DataBaseSingleton;
import vlad.iweather2.DataBase.DayInfoDao;
import vlad.iweather2.DataBase.StorageData;
import vlad.iweather2.WeatherEntities.City;
import vlad.iweather2.WeatherEntities.CityWeather;
import vlad.iweather2.WeatherEntities.DayInfo;


public class ServerRequest {

    public static final String LOG = "myLog";
    private static String API_KEY = "c5747f8920ee16b3c8aabbed8c0c9950";
    private static String UNITS = "metric";
    private RetrofitManager manager;
    private ServerAPI serverAPI;
    private CityWeather listOfData;
    private List<City> cityData;
    private Context context;
    private AppDataBase db;
    private DayInfoDao dayInfoDao;
    private CitiesDao citiesDao;

    public ServerRequest(Context context) {
        this.context = context;
        manager = new RetrofitManager();
        db = DataBaseSingleton.getInstance(context).getDatabase();
        dayInfoDao = db.dayInfoDao();
        citiesDao = db.citiesDao();
    }

    public void getWeatherDataByCoordinates(Double latitude, Double longetude) {
        serverAPI = manager.getServerAPI(context, "http://api.openweathermap.org");
        Call<CityWeather>  obj = serverAPI.getDataByLocation(latitude, longetude, UNITS, API_KEY);
        startEnqueue(obj);


    }

    public void getWeatherDataByCityName(String cityName) {
        serverAPI = manager.getServerAPI(context, "http://api.openweathermap.org");
        Call<CityWeather>  obj = serverAPI.getDataByCityName(cityName,UNITS, API_KEY);
        this.startEnqueue(obj);
    }

    public void startEnqueue(Call<CityWeather> obj) {
        try {
            obj.enqueue(new Callback<CityWeather>() {
                @Override
                public void onResponse(Call<CityWeather> call, Response<CityWeather> response) {
                    listOfData = response.body();
                    Log.d("myLog", "thread " + listOfData);
                    if (listOfData != null) {
                        Thread t = new Thread(new Runnable() {
                            public void run() {
                                String count = dayInfoDao.getCityRows(listOfData.city.name);
                                Log.d("myLog", "к-во записей города " + listOfData.city.name + " = " + count);

                                if (Integer.valueOf(count) > 0) {
                                    Log.d("myLog", "Чистим старые данные");
                                    dayInfoDao.deleteCity(listOfData.city.name);
                                    writeData(listOfData);
                                } else {
                                    Log.d("myLog", "Записываем новые данные");
                                    writeData(listOfData);
                                }

                            }
                        });
                        t.start();
                    } else {
                        Toast toast = Toast.makeText(context, "Нет данных!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

                public void writeData(CityWeather listOfData) {
                    StorageData sd = StorageData.getInstance(context);
                    sd.setLastUpdate(listOfData.city.name);
                    sd.setLastCity(listOfData.city.name);
                    for (DayInfo data : listOfData.list) {
                        data.city = listOfData.city.name;
                        data.weatherInfo = data.weather.get(0);
                        long weather_id = dayInfoDao.insert(data);
                        Log.d("myLog", "weather id = " + weather_id);

                        }
                }

                @Override
                public void onFailure(Call<CityWeather> call, Throwable t) {
                    Log.e(LOG, t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.e(LOG, e.getMessage());
        }
    }


    public void getCityData() {
        try {
            serverAPI = manager.getServerAPI(context, "http://my-study.zzz.com.ua");
            serverAPI.getCityData().enqueue(new Callback<List<City>>() {
                @Override
                public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                    cityData = response.body();
                    Log.d("myLog", "thread");
                    Thread t = new Thread(new Runnable() {
                        public void run() {
                            for (City data : cityData) {
                                long city_id = citiesDao.insert(data);
                                Log.d("myLog", "city id = " + city_id);
                            }
                        }
                    });
                    t.start();
                }

                @Override
                public void onFailure(Call<List<City>> call, Throwable t) {
                    Log.e(LOG, t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.e(LOG, e.getMessage());
        }
    }
}
