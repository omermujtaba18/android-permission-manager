package com.android.permissionmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.permissionmanager.R;
import com.android.permissionmanager.holders.PermissionViewHolder;
import com.android.permissionmanager.model.Permissions;

import java.util.ArrayList;

public class PermissionAdapter extends RecyclerView.Adapter<PermissionViewHolder> {

    ArrayList<Permissions> permissions;
    Context context;

    public PermissionAdapter(Context context, ArrayList<Permissions> permissions) {
        this.context = context;
        this.permissions = permissions;
    }

    @NonNull
    @Override
    public PermissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_detail_item, parent, false);
        return new PermissionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PermissionViewHolder holder, final int position) {
        holder.appPermission.setText(permissions.get(position).getPermissionName());
        holder.appPermissionDesc.setText(permissions.get(position).getDescription());
        holder.appPermissionStatus.setText(permissions.get(position).isGranted() ? "Granted" : "Not Granted");
        holder.appPermissionProtection.setText(permissions.get(position).getProtectionLevel());
        holder.appPermissionModifiable.setText(permissions.get(position).isModifiable() ? "Yes" : "No");
        holder.revokeButton.setText("Revoke " + permissions.get(position).getPermissionGroup() + " Permission");

        if (!permissions.get(position).isGranted() || !permissions.get(position).isModifiable())
            holder.revokeButton.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return permissions.size();
    }
}
