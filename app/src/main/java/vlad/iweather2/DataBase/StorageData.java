package vlad.iweather2.DataBase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;
import static vlad.iweather2.View.SplashActivity.LOG;


public class StorageData {

    private static final String LAST_UPDATE = "lastUpdate";
    private static final String LAST_CITY = "lastCity";
    private static final String STORAGE_DATA = "storageData";
    private static final String LATITUDE = "latitude";
    private static final String LONGETUDE = "longetude";
    private static final int MINUTES = 60000;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static StorageData instance;
    private static Context mcontext;

    private StorageData(){
        this.sharedPreferences = mcontext.getSharedPreferences(STORAGE_DATA, MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public static StorageData getInstance(Context context){
        mcontext = context;
        if(instance == null){
            instance = new StorageData();
        }
        return instance;
    }

    public String getLastUpdate(String lastCity) {
        return this.sharedPreferences.getString(LAST_UPDATE+lastCity,"");
    }

    public void setLastUpdate(String lastCity) {
        String unixTime = String.valueOf(System.currentTimeMillis());
        this.editor.putString(LAST_UPDATE+lastCity, unixTime).commit();
    }

    public String getLastCity(){
        return this.sharedPreferences.getString(LAST_CITY,"");
    }

    public void setLastCity(String lastCity){
        this.editor.putString(LAST_CITY, lastCity).commit();
        this.setLastUpdate(lastCity);
        Log.d(LOG,"записан новый город "+lastCity);
    }

    public void setUserCoordinates(Double latitude,Double longetude){
        Log.d(LOG,"Location success "+latitude+" "+longetude);
            this.editor.putString(LATITUDE, latitude.toString());
            this.editor.putString(LONGETUDE, longetude.toString());
    }

    public Double getLatitudeFromStorage(){
        return Double.valueOf(this.sharedPreferences.getString(LATITUDE,""));
    }

    public Double getLongetudeFromStorage(){
        return Double.valueOf(this.sharedPreferences.getString(LONGETUDE,""));
    }

    public long getTimeLastUpdate(String lastCity){
       long lastTime =  Long.valueOf(this.getLastUpdate(lastCity));
       long unixTime = System.currentTimeMillis();
       return (unixTime - lastTime)/MINUTES;
    }
}