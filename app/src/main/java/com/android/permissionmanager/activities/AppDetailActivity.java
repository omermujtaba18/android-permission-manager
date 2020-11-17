package com.android.permissionmanager.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.permissionmanager.R;

public class AppDetailActivity extends AppCompatActivity {

    TextView tv_appName, tv_version, tv_packageName, tv_appDelete;
    ImageView iv_appIcon;
    String packageName;
    PackageInfo packageInfo;
    PackageManager pm;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);

        init();

        try {
            packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_META_DATA);
            tv_appName.setText(pm.getApplicationLabel(pm
                    .getApplicationInfo(packageName, PackageManager.GET_META_DATA)));
            tv_packageName.setText(packageName);
            tv_version.setText(packageInfo.versionName);
            iv_appIcon.setImageDrawable(pm.getApplicationIcon(packageName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        tv_appDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri packageURI = Uri.parse("package:" + packageName);
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                startActivity(uninstallIntent);
            }
        });

//
//        try {
//            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
//            String[] requestedPermissions = packageInfo.requestedPermissions;
//
//            for(String permission: requestedPermissions){
//                PermissionInfo permissionInfo = pm.getPermissionInfo(permission,PackageManager.GET_META_DATA);
//                if(permissionInfo.protectionLevel == PermissionInfo.PROTECTION_DANGEROUS){
//                    PermissionGroupInfo permissionGroupInfo = pm.getPermissionGroupInfo(permissionInfo.group,PackageManager.GET_META_DATA);
//                    PermissionInfo pi = pm.getPermissionInfo(permission,PackageManager.GET_META_DATA);
//                }
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }


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
    }
}