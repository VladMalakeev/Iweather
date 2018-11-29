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
import vlad.iweather2.R;
import vlad.iweather2.View.MainActivity;
import vlad.iweather2.WeatherEntities.DayInfo;

import static vlad.iweather2.View.SplashActivity.LOG;

public class WeatherByTimeAdapter extends RecyclerView.Adapter<WeatherByTimeAdapter.ViewHolder> {

    private MainActivity view;
    private ArrayList<DayInfo> listData;

    public WeatherByTimeAdapter(ArrayList<DayInfo> listData, MainActivity view){
        this.listData = listData;
        this.view = view;
    }


    @Override
    public int getItemCount() {
        return this.listData.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_weather_by_time, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        Time timedt = new Time();
        timedt.set(listData.get(pos).dt*1000);
            holder.time.setText(String.valueOf(timedt.format("%H:%M")));
            holder.icon.setImageDrawable(view.getIcon(listData.get(pos).weatherInfo.icon));
            holder.temp.setText(listData.get(pos).main.temp_max.toString()+"°C");
            final int position = pos;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.mainWeather(listData.get(position));
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        ImageView icon;
        TextView temp;
        public ViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.text_time);
            icon = itemView.findViewById(R.id.image_time);
            temp = itemView.findViewById(R.id.text_temp);

        }
    }
}