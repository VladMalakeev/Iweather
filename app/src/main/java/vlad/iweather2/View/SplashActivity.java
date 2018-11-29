package vlad.iweather2.View;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import vlad.iweather2.Location.LocationSingleton;
import vlad.iweather2.R;
import vlad.iweather2.DataBase.StorageData;


public class SplashActivity extends AppCompatActivity {

    public static String LOG = "myLog";
    StorageData storageInstance;
    String lastCity;
    private LocationSingleton singleton;
    TextView textInfo;
    ProgressBar gpsBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        storageInstance = StorageData.getInstance(getApplicationContext());
        lastCity = storageInstance.getLastCity();

        this.textInfo = (TextView) findViewById(R.id.textError);
        Button refresh = (Button) findViewById(R.id.buttonRefresh);
        this.gpsBar = (ProgressBar) findViewById(R.id.gpsBar);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG,"пересоздание астивити");
                recreate();
            }
        });

        loadGps(false);
        showButton(false);

        if (hasInternet(getApplicationContext()) == true) {
            Log.d(LOG, "Интернет включен");
            if (lastCity == "") {
                Log.d(LOG, "нет данных о последем городе");
                toCitiesActivity();
//                if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
//                    Log.d(LOG, "GPS отключен");
//                    toCitiesActivity();
//                }
//                else{
//                    Log.d(LOG, "GPS работает");
//                    singleton = LocationSingleton.getInstance(this,this);
//                    loadGps(true);
//                }

            } else {
                toMainActivity("connectToServerByCityName");
            }
        } else {
            Log.d(LOG, "интернет выключен");
            if (lastCity == "") {
                Log.d(LOG, "нет данных о последем городе");
                showButton(true);
            } else {
                Log.d(LOG, "есть данные о последнем городе");
                toMainActivity("connectToDataBase");
            }
        }

    }

    public void toMainActivity(String state) {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.putExtra("state",state);
        SplashActivity.this.startActivity(intent);
        finish();
    }

    public void toCitiesActivity() {
        Intent intent = new Intent(SplashActivity.this, StateListActivity.class);
        SplashActivity.this.startActivity(intent);
        finish();
    }

    public static boolean hasInternet(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private void showButton(boolean show) {
        Button button = (Button) findViewById(R.id.buttonRefresh);
        this.textInfo.setText("Нет соединения с интернетом");
        if (show) button.setVisibility(View.VISIBLE); else button.setVisibility(View.INVISIBLE);
        if (show) this.textInfo.setVisibility(View.VISIBLE); else this.textInfo.setVisibility(View.INVISIBLE);
    }

    public void loadGps(boolean show){
        this.textInfo.setText("Определение gps координат");
        if (show) this.gpsBar.setVisibility(View.VISIBLE); else this.gpsBar.setVisibility(View.INVISIBLE);
        if (show) this.textInfo.setVisibility(View.VISIBLE); else this.textInfo.setVisibility(View.INVISIBLE);
    }



}

