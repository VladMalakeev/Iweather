package vlad.iweather2.Presenter;

import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import vlad.iweather2.DataBase.AppDataBase;
import vlad.iweather2.DataBase.DataBaseSingleton;
import vlad.iweather2.DataBase.DayInfoDao;
import vlad.iweather2.Retrofit.ServerRequest;
import vlad.iweather2.DataBase.StorageData;
import vlad.iweather2.View.MainActivity;
import vlad.iweather2.WeatherEntities.DayInfo;

import static vlad.iweather2.View.SplashActivity.LOG;

public class MainPresenter {
    private AppDataBase db;
    private DayInfoDao dayInfoDao;
    private List<DayInfo> list;
    private ServerRequest request;
    private Context context;
    private String state;
    private StorageData sd;
    private MainActivity view;
    private static final long TIME_UPDATE = 120;
    private long time;
    private String lastCity;

    public MainPresenter(Intent intent, Context context, MainActivity view){
        this.state = intent.getStringExtra("state");
        this.db = DataBaseSingleton.getInstance(context).getDatabase();
        this.dayInfoDao = db.dayInfoDao();
        this.sd = StorageData.getInstance(context);
        this.request = new ServerRequest(context);
        this.view = view;
        this.lastCity = sd.getLastCity();
    }

    public void getData(){
                Log.d(LOG,"последний город = "+lastCity);

                dayInfoDao.getAllByCity(lastCity)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<DayInfo>>() {
                            @Override
                            public void accept(List<DayInfo> mList) throws Exception {
                                Log.d("myLog", "к-во данных о погоде = "+mList.size());
                                list = mList;
                                time = sd.getTimeLastUpdate(sd.getLastCity());
                                Log.d(LOG, "время последнего обновления - "+time+" минут");
                                if((mList.size()==0) || (time > TIME_UPDATE)){
                                    switch (state){
                                        case "connectToServerByGPS":
                                            request.getWeatherDataByCoordinates(sd.getLatitudeFromStorage(),sd.getLongetudeFromStorage());
                                            Log.d(LOG,"данные по координатах");
                                            break;
                                        case "connectToServerByCityName":
                                            Log.d(LOG,"запрос данных к серверу");
                                            request.getWeatherDataByCityName(sd.getLastCity());
                                            break;
                                        default:  Log.d(LOG,"просто грузим из бд");
                                    }
                                }
                                ArrayList<DayInfo> dList = new ArrayList<>();
                                ArrayList<DayInfo> tList = new ArrayList<>();
                                DayInfo dayInfo = null;
                                for(int i = 0; i < mList.size();i++){
                                    Time timeDate = new Time();
                                    Time timePrev = new Time();
                                    Time currentTime = new Time();
                                    currentTime.setToNow();
                                    long now = (mList.get(i).dt - (currentTime.toMillis(true)/1000))/3600;
                                    if(now>=0 && now<=3){
                                        dayInfo = mList.get(i);
                                    }
                                    else if(now<0 && i==mList.size()-1){
                                        dayInfo = mList.get(i);
                                    }

                                    timeDate.set(mList.get(i).dt*1000);
                                    if(i!=0) {
                                        timePrev.set(mList.get(i-1).dt * 1000);
                                    }


                                    if((currentTime.monthDay!=timeDate.monthDay) && (timeDate.monthDay!=timePrev.monthDay)) {
                                        dList.add(mList.get(i));
                                    }
                                    if(currentTime.monthDay==timeDate.monthDay) {
                                        tList.add(mList.get(i));
                                    }
                                }
                                view.viewDays(dList);
                                view.viewTime(tList);
                                if(mList.size()>38) {
                                     view.mainWeather(dayInfo);
                                }
                            }
                        });
    }

    public void newTime(int day){
        final ArrayList<DayInfo> newDay = new ArrayList<>();
        for(int i = 0; i<list.size();i++){
            Time newItem =  new Time();
            newItem.set(list.get(i).dt*1000);
            if (newItem.monthDay ==day) {
                newDay.add(list.get(i));
            }
            view.viewTime(newDay);
            if(newDay.size()!=0)
            view.mainWeather(newDay.get(newDay.size()/2));
        }
    }
}
