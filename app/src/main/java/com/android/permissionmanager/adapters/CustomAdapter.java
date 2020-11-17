package com.android.permissionmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.permissionmanager.R;
import com.android.permissionmanager.activities.AppDetailActivity;
import com.android.permissionmanager.holders.MyViewHolder;
import com.android.permissionmanager.model.ApplicationDetail;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<MyViewHolder> {

    ArrayList<ApplicationDetail> applicationDetails;
    Context context;

    public CustomAdapter(Context context, ArrayList<ApplicationDetail> applicationDetails) {
        this.context = context;
        this.applicationDetails = applicationDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.name.setText(applicationDetails.get(position).getApplicationName());
        holder.icon.setImageDrawable(applicationDetails.get(position).appIcon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AppDetailActivity.class);
                intent.putExtra("packageName", applicationDetails.get(position).getPackageName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return applicationDetails.size();
    }
}
