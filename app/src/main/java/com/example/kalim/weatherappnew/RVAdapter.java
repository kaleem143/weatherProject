package com.example.kalim.weatherappnew;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.TipsViewHolder> {

    List<WeatherUi> weatherList;
    public  class TipsViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView day_Name,weather_description,minTemp,maxTemp;



        ImageView weatherPhoto;

        TipsViewHolder(final View itemView) {
            super(itemView);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent=new Intent(v.getContext(),DetailActivity.class);
//                    intent.putExtra("cardValue",getAdapterPosition());
//                    v.getContext().startActivity(intent);
//
//
//
//                }
//            });
            cv = (CardView)itemView.findViewById(R.id.cv);
            day_Name = (TextView)itemView.findViewById(R.id.weather_day);
            weather_description=(TextView)itemView.findViewById(R.id.weather_description);
            maxTemp= (TextView) itemView.findViewById(R.id.max_temp);
            minTemp=(TextView)itemView.findViewById(R.id.min_temp);
            weatherPhoto = (ImageView)itemView.findViewById(R.id.weather_photo);

        }
    }



    RVAdapter(List<WeatherUi> weathers){
        this.weatherList = weathers;

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public TipsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        TipsViewHolder pvh = new TipsViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(TipsViewHolder continentsViewHolder, int i) {
        continentsViewHolder.day_Name.setText(weatherList.get(i).dayName);
        continentsViewHolder.weather_description.setText(weatherList.get(i).weather_description);
        continentsViewHolder.maxTemp.setText(weatherList.get(i).maxTemp);
        continentsViewHolder.minTemp.setText(weatherList.get(i).minTemp);
        continentsViewHolder.weatherPhoto.setImageResource(weatherList.get(i).photoId);

    }

    @Override
    public int getItemCount() {
       return weatherList.size();
    }

    /**
     * Created by Kalim on 4/15/2016.
     */

}
