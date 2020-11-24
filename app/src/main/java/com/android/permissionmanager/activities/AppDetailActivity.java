package com.android.permissionmanager.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.permissionmanager.R;
import com.android.permissionmanager.model.Permissions;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class AppDetailActivity extends AppCompatActivity {

    static final String TAG = "AppDetailActivity";

    TextView tv_appName, tv_version, tv_packageName, tv_appDelete;
    ImageView iv_appIcon;
    String packageName;
    PackageInfo packageInfo;
    PackageManager pm;
    Intent intent;
    ArrayList<Permissions> normalPermissions, signaturePermissions, dangerousPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);

        init();

        try {
            packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_META_DATA | PackageManager.GET_PERMISSIONS);
            tv_appName.setText(pm.getApplicationLabel(pm
                    .getApplicationInfo(packageName, PackageManager.GET_META_DATA)));
            tv_packageName.setText(packageName);
            tv_version.setText(packageInfo.versionName);
            iv_appIcon.setImageDrawable(pm.getApplicationIcon(packageName));

            String[] requestedPermissions = packageInfo.requestedPermissions;

            for (String permission : requestedPermissions) {
                PermissionInfo permissionInfo = pm.getPermissionInfo(permission, PackageManager.GET_META_DATA);

                switch (permissionInfo.protectionLevel) {
                    case PermissionInfo.PROTECTION_NORMAL:
                        normalPermissions.add(
                                new Permissions(permission,
                                        "",
                                        "Normal",
                                        false,
                                        "",
                                        pm.checkPermission(permission, packageName) == PERMISSION_GRANTED));
                        break;
                    case PermissionInfo.PROTECTION_SIGNATURE:
                        signaturePermissions.add(
                                new Permissions(permission,
                                        "",
                                        "Signature",
                                        false,
                                        "",
                                        pm.checkPermission(permission, packageName) == PERMISSION_GRANTED));
                        break;
                    case PermissionInfo.PROTECTION_DANGEROUS:
                        dangerousPermissions.add(
                                new Permissions(permission,
                                        pm.getPermissionGroupInfo(permissionInfo.group, 0).loadLabel(pm).toString(),
                                        "Dangerous",
                                        true,
                                        pm.getPermissionGroupInfo(permissionInfo.group, 0).loadDescription(pm).toString(),
                                        pm.checkPermission(permission, packageName) == PERMISSION_GRANTED));
                        break;
                }
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        String json = new Gson().toJson(dangerousPermissions);
        Log.d(TAG, json);

        tv_appDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri packageURI = Uri.parse("package:" + packageName);
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                startActivity(uninstallIntent);
            }
        });
    }

    private void init() {
        tv_appName = findViewById(R.id.app_name);
        tv_version = findViewById(R.id.app_version);
        tv_packageName = findViewById(R.id.app_package);
        iv_appIcon = findViewById(R.id.app_icon);
        tv_appDelete = findViewById(R.id.app_delete);
        pm = getPackageManager();
        intent = getIntent();
        packageName = intent.getStringExtra("packageName");

        normalPermissions = new ArrayList<>();
        signaturePermissions = new ArrayList<>();
        dangerousPermissions = new ArrayList<>();
    }
}