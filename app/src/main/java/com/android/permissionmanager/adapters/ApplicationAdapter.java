package com.android.permissionmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.permissionmanager.R;
import com.android.permissionmanager.activities.AppDetailActivity;
import com.android.permissionmanager.holders.ApplicationViewHolder;
import com.android.permissionmanager.model.ApplicationDetail;

import java.util.ArrayList;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationViewHolder> {

    ArrayList<ApplicationDetail> applicationDetails;
    Context context;

    public ApplicationAdapter(Context context, ArrayList<ApplicationDetail> applicationDetails) {
        this.context = context;
        this.applicationDetails = applicationDetails;
    }

    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ApplicationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, final int position) {
        holder.name.setText(applicationDetails.get(position).getApplicationName());
        holder.icon.setImageDrawable(applicationDetails.get(position).appIcon);

        holder.itemView.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
                event.setEventType(AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED);
                return super.onRequestSendAccessibilityEvent(host, child, event);
            }
        });

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
