package com.android.permissionmanager.holders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.permissionmanager.R;

public class PermissionViewHolder extends RecyclerView.ViewHolder {

    public TextView appPermission, appPermissionProtection, appPermissionDesc, appPermissionStatus, appPermissionModifiable;
    public Button revokeButton;

    public PermissionViewHolder(@NonNull View itemView) {
        super(itemView);

        appPermission = itemView.findViewById(R.id.app_permission);
        appPermissionProtection = itemView.findViewById(R.id.app_permission_protection);
        appPermissionDesc = itemView.findViewById(R.id.app_permission_desc);
        appPermissionStatus = itemView.findViewById(R.id.app_permission_status);
        appPermissionModifiable = itemView.findViewById(R.id.app_permission_modifiable);
        revokeButton = itemView.findViewById(R.id.revoke);
    }
}
