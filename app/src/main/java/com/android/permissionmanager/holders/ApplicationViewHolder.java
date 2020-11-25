package com.android.permissionmanager.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.permissionmanager.R;

public class ApplicationViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public ImageView icon;

    public ApplicationViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        icon = itemView.findViewById(R.id.appIcon);
    }
}
