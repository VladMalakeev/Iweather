package vlad.iweather2.View;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vlad.iweather2.Adapter.WeatherByDayAdapter;
import vlad.iweather2.Adapter.WeatherByTimeAdapter;
import vlad.iweather2.Presenter.MainPresenter;
import vlad.iweather2.R;
import vlad.iweather2.WeatherEntities.DayInfo;

import static vlad.iweather2.View.SplashActivity.LOG;

public class MainActivity extends AppCompatActivity{

    private RecyclerView recyclerViewTime;
    private RecyclerView recyclerViewDay;
    private ImageView mainIcon;
    private TextView mainTemperature;
    private TextView mainDescription;
    private TextView mainMin;
    private TextView mainMax;
    private TextView mainDate;
    private TextView pressure;
    private TextView humidity;
    private TextView windDeg;
    private TextView windSpeed;
    private MainPresenter mainPresenter;
    private Button moreButton;
    private Boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG,"запуск mainActivity");
        showMore(false);

        mainIcon = (ImageView) findViewById(R.id.weatherImage);
        mainDescription = (TextView) findViewById(R.id.dayDescription);
        mainTemperature = (TextView) findViewById(R.id.dayTemperature);
        mainMin = (TextView) findViewById(R.id.minDayTemperature);
        mainMax = (TextView) findViewById(R.id.maxDayTemperature);
        mainDate = (TextView) findViewById(R.id.mainDate);
        moreButton = (Button) findViewById(R.id.moreButton);
        pressure = new TextView(this);
        pressure.setGravity(Gravity.CENTER);
        pressure.setTextAppearance(this,R.style.TextAppearance_AppCompat_Light_SearchResult_Title);
        humidity = new TextView(this);
        humidity.setGravity(Gravity.CENTER);
        humidity.setTextAppearance(this,R.style.TextAppearance_AppCompat_Light_SearchResult_Title);
        windSpeed = new TextView(this);
        windSpeed.setGravity(Gravity.CENTER);
        windSpeed.setTextAppearance(this,R.style.TextAppearance_AppCompat_Light_SearchResult_Title);
        windDeg = new TextView(this);
        windDeg.setGravity(Gravity.CENTER);
        windDeg.setTextAppearance(this,R.style.TextAppearance_AppCompat_Light_SearchResult_Title);


        recyclerViewTime = (RecyclerView) findViewById(R.id.weatherByTime);
        recyclerViewTime.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        recyclerViewDay = (RecyclerView) findViewById(R.id.weatherByDay);
        recyclerViewDay.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        mainPresenter = new MainPresenter(getIntent(),this, this);
        mainPresenter.getData();

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == false){
                    showMore(true);
                    flag=true;
                }
                else{
                    showMore(false);
                    flag=false;
                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.change_city){
            Intent intent = new Intent(MainActivity.this, StateListActivity.class);
            startActivity(intent);
        }
        return true;
    }

    public void viewDays(ArrayList<DayInfo> list){
        recyclerViewDay.setAdapter(new WeatherByDayAdapter(list, this));
    }

    public void viewTime(ArrayList<DayInfo> list){
        recyclerViewTime.setAdapter(new WeatherByTimeAdapter(list, this));
    }

    public void mainWeather(DayInfo dayInfo){
        Time timedt = new Time();
        timedt.set(dayInfo.dt*1000);
        mainIcon.setImageDrawable(getIcon(dayInfo.weatherInfo.icon));
        mainTemperature.setText(dayInfo.main.temp_max+"°C");
        mainDescription.setText(dayInfo.weatherInfo.description);
        mainMax.setText("max  "+dayInfo.main.temp_max+"°C");
        mainMin.setText("min  "+dayInfo.main.temp_min+"°C");
        mainDate.setText(dayInfo.city+", "+timedt.format("%D"));
        pressure.setText("pressure - "+ (dayInfo.main.pressure*0.75)+"mm");
        humidity.setText("humidity - "+ dayInfo.main.humidity+"%");
        windDeg.setText("wind degree - "+ dayInfo.wind.deg+"°");
        windSpeed.setText("wind speed- "+ dayInfo.wind.speed+"m/s");
    }


    public void changeTime(int day){
        mainPresenter.newTime(day);
    }

    public void showMore(boolean show) {
        Button  moreButton = (Button) findViewById(R.id.moreButton);
        LinearLayout  moreLayout = (LinearLayout) findViewById(R.id.moreInfo);

        if (show){
            moreLayout.setVisibility(View.VISIBLE);
            moreLayout.setBackground(getDrawable(R.color.colorWhite));
            moreLayout.addView(this.pressure);
            moreLayout.addView(this.humidity);
            moreLayout.addView(this.windDeg);
            moreLayout.addView(this.windSpeed);
            moreButton.setText("cкрыть");
        }
        else {
            moreLayout.setVisibility(View.INVISIBLE);
            moreLayout.removeAllViews();
            moreButton.setText("подробнее");
        }
    }

    public Drawable getIcon(String resourse){
        switch (resourse) {
            case "01d":
                return getResources().getDrawable(R.drawable.img_01d);
            case "01n":
                return getResources().getDrawable(R.drawable.img_01n);
            case "02n":
                return getResources().getDrawable(R.drawable.img_02d);
            case "02d":
                return getResources().getDrawable(R.drawable.img_02n);
            case "03d":
                return getResources().getDrawable(R.drawable.img_03c);
            case "03n":
                return getResources().getDrawable(R.drawable.img_03c);
            case "04d":
                return getResources().getDrawable(R.drawable.img_03c);
            case "04n":
                return getResources().getDrawable(R.drawable.img_03c);
            case "09d":
                return getResources().getDrawable(R.drawable.img_09d);
            case "09n":
                return getResources().getDrawable(R.drawable.img_09n);
            case "10d":
                return getResources().getDrawable(R.drawable.img_10c);
            case "10n":
                return getResources().getDrawable(R.drawable.img_10c);
            case "11d":
                return getResources().getDrawable(R.drawable.img_11d);
            case "11n":
                return getResources().getDrawable(R.drawable.img_11n);
            case "13d":
                return getResources().getDrawable(R.drawable.img_13d);
            case "13n":
                return getResources().getDrawable(R.drawable.img_13n);
            case "50d":
                return getResources().getDrawable(R.drawable.img_50c);
            case "50n":
                return getResources().getDrawable(R.drawable.img_50c);
                default:return getResources().getDrawable(R.drawable.ic_launcher_background);
        }
    }

}
