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

public class ReceiverAdapter extends RecyclerView.Adapter<ReceiverAdapter.ViewHolder> {
    MainActivity mainActivity;
    List<Receiver_list> receiver_lists;

    public ReceiverAdapter(MainActivity mainActivity, List<Receiver_list> receiver_lists) {
        this.mainActivity = mainActivity;
        this.receiver_lists = receiver_lists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reciever_list, parent, false);
        ReceiverAdapter.ViewHolder viewHolder = new ReceiverAdapter.ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Receiver_list list = receiver_lists.get(position);
        holder.nametv.setText(list.name);
        // holder.count.setText(""+list.count);
    }

    @Override
    public int getItemCount() {
        return receiver_lists.size();
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
