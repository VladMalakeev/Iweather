package vlad.iweather2.View;

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
import vlad.iweather2.Adapter.StateAdapter;
import vlad.iweather2.R;
import vlad.iweather2.Retrofit.ServerRequest;
import vlad.iweather2.WeatherEntities.City;

public class StateListActivity extends AppCompatActivity {

   private AppDataBase db;
   private CitiesDao citiesDao;
   private ServerRequest request;
   private List<City> list;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_list);
        Log.d("myLog", "stateList");
        request = new ServerRequest(getApplicationContext());
        db = DataBaseSingleton.getInstance(getApplicationContext()).getDatabase();
        citiesDao = db.citiesDao();


        recyclerView = (RecyclerView) findViewById(R.id.state_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(StateListActivity.this ));

        citiesDao.getStates()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<City>>() {
                    @Override
                    public void accept(List<City> mList) throws Exception {
                        Log.d("myLog", "элементов в таблице cities = "+mList.size());
                        if(mList.size() == 0){
                            request.getCityData();
                            Log.d("myLog", "ожидаем");
                        }else{
                                recyclerView.setAdapter(new StateAdapter(mList,StateListActivity.this));
                        }

                    }
                });

    }
}
