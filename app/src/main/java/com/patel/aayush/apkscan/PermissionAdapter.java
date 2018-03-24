package com.patel.aayush.apkscan;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aayush on 30-11-2017.
 */

class PermissionAdapter extends RecyclerView.Adapter<PermissionAdapter.ViewHolder> {
    MainActivity mainActivity;
    List<Permissions_List> permissions_lists;

    public PermissionAdapter(MainActivity mainActivity, List<Permissions_List> permissions_lists) {
        this.mainActivity = mainActivity;
        this.permissions_lists = permissions_lists;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.permissions_list, parent, false);
        PermissionAdapter.ViewHolder viewHolder = new PermissionAdapter.ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Permissions_List list = permissions_lists.get(position);
        holder.pname.setText(list.name);
        holder.protectionlevel.setText(list.protectionlevel);
        if(list.protectionlevel.equals("Normal")){ holder.protectionlevel.setTextColor(Color.GREEN);}
        if(list.protectionlevel.equals("Dangerous")){holder.protectionlevel.setTextColor(Color.RED);}
        if(list.protectionlevel.equals("Signature")){holder.protectionlevel.setTextColor(Color.BLUE);}
        if(list.protectionlevel.equals("SignatureOrSystem")){holder.protectionlevel.setTextColor(Color.MAGENTA);}
        holder.info.setText(list.info);

    }

    @Override
    public int getItemCount() {
        return permissions_lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pname, protectionlevel, info,click,hidedesc;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.lineardescid);
            linearLayout.setVisibility(View.GONE);
            hidedesc=itemView.findViewById(R.id.hidedesc);
            hidedesc.setVisibility(View.GONE);
            pname = (TextView) itemView.findViewById(R.id.perid);
            protectionlevel = itemView.findViewById(R.id.protectionid);
            info = (TextView) itemView.findViewById(R.id.descriptionid);
            click=itemView.findViewById(R.id.descclickid);
            click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    linearLayout.setVisibility(View.VISIBLE);
                    hidedesc.setVisibility(View.VISIBLE);
                }
            });
            hidedesc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    linearLayout.setVisibility(View.GONE);
                    hidedesc.setVisibility(View.GONE);
                }
            });
        }
    }
}
