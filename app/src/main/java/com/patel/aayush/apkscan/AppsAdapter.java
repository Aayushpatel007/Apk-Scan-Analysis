package com.patel.aayush.apkscan;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aayush on 02-01-2018.
 */

class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder> {
    MainActivity mainActivity;
    List<AppsListClass> appsListClasses;

    public AppsAdapter(MainActivity mainActivity, List<AppsListClass> appsListClasses) {
        this.mainActivity = mainActivity;
        this.appsListClasses = appsListClasses;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_apps, parent, false);
        AppsAdapter.ViewHolder viewHolder = new AppsAdapter.ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AppsListClass list = appsListClasses.get(position);
        holder.imageView.setImageDrawable(list.image);
        holder.nametv.setText(list.appname);
        Log.d("app", list.appname);
        Log.d("app", String.valueOf(list.dangerous));
        Log.d("app", String.valueOf(list.dangerous));
        holder.dangeroustv.setText(String.valueOf(list.dangerous));
        holder.normaltv.setText(String.valueOf(list.normal));
        holder.otherstv.setText(String.valueOf(list.others));
    }

    @Override
    public int getItemCount() {
        return appsListClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nametv, dangeroustv, normaltv, otherstv;
        ImageView imageView;

        public ViewHolder(View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.image_id);
            nametv = itemView.findViewById(R.id.app_nameid);
            dangeroustv = itemView.findViewById(R.id.dangerous);
            normaltv = itemView.findViewById(R.id.normal);
            otherstv = itemView.findViewById(R.id.others);


        }
    }
}
