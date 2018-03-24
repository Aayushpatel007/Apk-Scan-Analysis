package com.patel.aayush.apkscan;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aayush on 01-12-2017.
 */

public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ViewHolder> {
    MainActivity mainActivity;
    List<Providers_list> providers_lists;

    public ProviderAdapter(MainActivity mainActivity, List<Providers_list> providers_lists) {
        this.mainActivity = mainActivity;
        this.providers_lists = providers_lists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.provider_list, parent, false);
        ProviderAdapter.ViewHolder viewHolder = new ProviderAdapter.ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Providers_list list = providers_lists.get(position);
        holder.nametv.setText(list.name);
        //holder.count.setText(""+list.count);
    }

    @Override
    public int getItemCount() {
        return providers_lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView click, nametv, count;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            nametv = itemView.findViewById(R.id.nametvid);
        }
    }
}
