package com.patel.aayush.apkscan;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aayush on 01-12-2017.
 */

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    MainActivity mainActivity;
    List<Service_list> service_lists;
    public ServiceAdapter(MainActivity mainActivity, List<Service_list> service_lists) {
        this.mainActivity=mainActivity;
        this.service_lists=service_lists;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.services_list, parent, false);
        ServiceAdapter.ViewHolder viewHolder = new ServiceAdapter.ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Service_list list=service_lists.get(position);
        holder.nametv.setText(list.name);
        //holder.count.setText(""+list.count);
    }

    @Override
    public int getItemCount() {
        return service_lists.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView nametv,count,click;
        LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            nametv=itemView.findViewById(R.id.nametvid);
           // count=itemView.findViewById(R.id.countid);
          //  click=itemView.findViewById(R.id.detailsid);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.lineardescid);
            //linearLayout.setVisibility(View.GONE);
//            click.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    linearLayout.setVisibility(View.VISIBLE);
//                }
//            });
        }
    }
}
