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

import vlad.iweather2.View.CityListActivity;
import vlad.iweather2.DataBase.AppDataBase;
import vlad.iweather2.DataBase.CitiesDao;
import vlad.iweather2.DataBase.DataBaseSingleton;
import vlad.iweather2.R;
import vlad.iweather2.WeatherEntities.City;

import static vlad.iweather2.View.SplashActivity.LOG;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {

    private List<City> state;
    private Context context;
    public StateAdapter(List<City> state, Context context){
        this.state = state;
        this.context = context;
    }


    @Override
    public int getItemCount() {
        return this.state.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_state, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        AppDataBase db = DataBaseSingleton.getInstance(this.context).getDatabase();
        CitiesDao citiesDao = db.citiesDao();
        viewHolder.stateText.setText(state.get(i).state_name);
        final String element = state.get(i).state_name;
        viewHolder.stateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG,"клик по области");
                Intent intent = new Intent(context, CityListActivity.class);
                Log.d(LOG,"putExtra");
                intent.putExtra("state",element);
                Log.d(LOG,"startActivity");
                context.startActivity(intent);
            }
        });

    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {
      TextView stateText;
        public ViewHolder(View itemView) {
            super(itemView);
            stateText = (TextView) itemView.findViewById(R.id.state_item);
        }
    }
}