package vlad.iweather2.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import vlad.iweather2.R;
import vlad.iweather2.View.MainActivity;
import vlad.iweather2.WeatherEntities.DayInfo;

import static vlad.iweather2.View.SplashActivity.LOG;

public class WeatherByDayAdapter extends RecyclerView.Adapter<WeatherByDayAdapter.ViewHolder> {

    private MainActivity view;
    private ArrayList<DayInfo> listData;

    public WeatherByDayAdapter(ArrayList<DayInfo> listData, MainActivity view){
        this.listData = listData;
        this.view = view;
    }


    @Override
    public int getItemCount() {
        return this.listData.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_weather_by_day, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        Time timedt = new Time();
        timedt.set(listData.get(pos).dt*1000);

            holder.textDate.setText(String.valueOf(timedt.format("%D")) + " ");
            holder.iconWeather.setImageDrawable(view.getIcon(listData.get(pos).weatherInfo.icon));
            holder.dayOfWeek.setText(String.valueOf(timedt.format("%A")) + " ");
            holder.weatherState.setText(listData.get(pos).weatherInfo.description);
            holder.dayMax.setText("max "+listData.get(pos).main.temp_max.toString());
            holder.dayMin.setText("min "+listData.get(pos).main.temp_min.toString());


           final Time item =  new Time();
            item.set(listData.get(pos).dt *1000);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.changeTime(item.monthDay);
            }
        });

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dayOfWeek;
        ImageView iconWeather;
        TextView textDate;
        TextView weatherState;
        TextView dayMin;
        TextView dayMax;
        public ViewHolder(View itemView) {
            super(itemView);
            dayOfWeek = itemView.findViewById(R.id.textDayOfWeek);
            iconWeather = itemView.findViewById(R.id.imageDayWeather);
            textDate = itemView.findViewById(R.id.textDateWeather);
            weatherState = itemView.findViewById(R.id.textWeatherState);
            dayMin = itemView.findViewById(R.id.textDayMin);
            dayMax = itemView.findViewById(R.id.textDayMax);
        }
    }
}