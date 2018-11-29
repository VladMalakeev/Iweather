package vlad.iweather2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import vlad.iweather2.DataBase.AppDataBase;
import vlad.iweather2.DataBase.CitiesDao;
import vlad.iweather2.DataBase.DataBaseSingleton;
import vlad.iweather2.DataBase.StorageData;
import vlad.iweather2.View.MainActivity;
import vlad.iweather2.R;
import vlad.iweather2.Retrofit.ServerRequest;
import vlad.iweather2.WeatherEntities.City;

import static vlad.iweather2.View.SplashActivity.LOG;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private List<City> city;
    private Context context;

    public CityAdapter(List<City> city, Context context) {
        this.city = city;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return this.city.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_city, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        AppDataBase db = DataBaseSingleton.getInstance(this.context).getDatabase();
        CitiesDao citiesDao = db.citiesDao();
        final String cityName = city.get(i).name;
        final String cityCode = city.get(i).country_code;

        viewHolder.cityText.setText(cityName);
        viewHolder.cityText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG, "клик по городу " + cityName);
//                ServerRequest request = new ServerRequest(context);
//                request.getWeatherDataByCityName(cityName + "," + cityCode);
                StorageData.getInstance(context).setLastCity(cityName);
                Intent intent = new Intent(context, MainActivity.class);
                Log.d(LOG, "putExtra");
                intent.putExtra("state", "connectToServerByCityName");
                Log.d(LOG, "startActivity");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityText;

        public ViewHolder(View itemView) {
            super(itemView);
            cityText = (TextView) itemView.findViewById(R.id.city_item);
        }
    }
}