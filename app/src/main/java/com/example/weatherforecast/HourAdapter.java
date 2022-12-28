package com.example.weatherforecast;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.ViewHolder> {
    private Context context;
    private static final String TAG = "HourAdapter";
    private ArrayList<WeatherItemNext7Days> mWeatherItems;

    public HourAdapter(Context context, ArrayList<WeatherItemNext7Days> weatherItems) {
        Log.i(TAG, "HourAdapter constructor");
        this.context = context;
        this.mWeatherItems = weatherItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder");
        LayoutInflater inflater = LayoutInflater.from(context);

        View heroView = inflater.inflate(R.layout.custom_horizontal_list, parent, false);

        HourAdapter.ViewHolder viewHolder = new HourAdapter.ViewHolder(heroView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder");
        WeatherItemNext7Days weatherItem = mWeatherItems.get(position);

        holder.timeHorizontal.setText(weatherItem.getNextDay());
        //holder.nhietDo.setText(weatherItem.getTemp());
        holder.anhMinhHoa.setImageResource(R.drawable.ic_baseline_cloud_24);
    }

    @Override
    public int getItemCount() {
        return mWeatherItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView timeHorizontal;
        private ImageView anhMinhHoa;
        private TextView nhietDo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeHorizontal = itemView.findViewById(R.id.time_horizontal_listview);
            anhMinhHoa = itemView.findViewById(R.id.anh_minh_hoa);
            nhietDo = itemView.findViewById(R.id.temp_example);
        }

        public TextView getTimeHorizontal() {
            return timeHorizontal;
        }

        public void setTimeHorizontal(TextView timeHorizontal) {
            this.timeHorizontal = timeHorizontal;
        }

        public ImageView getAnhMinhHoa() {
            return anhMinhHoa;
        }

        public void setAnhMinhHoa(ImageView anhMinhHoa) {
            this.anhMinhHoa = anhMinhHoa;
        }

        public TextView getNhietDo() {
            return nhietDo;
        }

        public void setNhietDo(TextView nhietDo) {
            this.nhietDo = nhietDo;
        }
    }
}
