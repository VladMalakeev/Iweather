package vlad.iweather2.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import vlad.iweather2.DataBase.AppDataBase;
import vlad.iweather2.DataBase.CitiesDao;
import vlad.iweather2.DataBase.DataBaseSingleton;
import vlad.iweather2.Adapter.CityAdapter;
import vlad.iweather2.R;
import vlad.iweather2.WeatherEntities.City;

public class CityListActivity extends AppCompatActivity {

    private AppDataBase db;
    private CitiesDao citiesDao;
    private List<City> list;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        Log.d("myLog", "cityList");
        db = DataBaseSingleton.getInstance(CityListActivity.this).getDatabase();
        citiesDao = db.citiesDao();


        recyclerView = (RecyclerView) findViewById(R.id.city_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(CityListActivity.this));

        Intent intent = getIntent();
        String state = intent.getStringExtra("state");

        citiesDao.getCityByState(state)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<City>>() {
                    @Override
                    public void accept(List<City> mList) throws Exception {
                        Log.d("myLog", "count = "+mList.size());
                        recyclerView.setAdapter(new CityAdapter(mList,CityListActivity.this));
                    }
                });

    }
}
