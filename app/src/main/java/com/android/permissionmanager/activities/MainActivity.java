package com.android.permissionmanager.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.permissionmanager.R;
import com.android.permissionmanager.adapters.ApplicationAdapter;
import com.android.permissionmanager.model.ApplicationDetail;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "MainActivity";

    PackageManager packageManager;
    List<PackageInfo> packageInfoList;
    RecyclerView recyclerViewInstalled;
    RecyclerView recyclerViewSystem;
    LinearLayoutManager linearLayoutManagerSystem, linearLayoutManagerInstalled;
    ApplicationAdapter applicationAdapter;
    ArrayList<ApplicationDetail> packagesInstalled;
    ArrayList<ApplicationDetail> packagesSystem;
    String appName;
    String packageName;
    Drawable appIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        for (int i = 0; i < packageInfoList.size(); i++) {

            try {
                packageName = packageInfoList.get(i).packageName;
                appName = (String) packageManager.getApplicationLabel(packageManager
                        .getApplicationInfo(packageName, PackageManager.GET_META_DATA));
                appIcon = packageManager.getApplicationIcon(packageName);

                if (isSystemPackage(packageInfoList.get(i))) {
                    packagesSystem.add(new ApplicationDetail(appName, packageName, appIcon));
                } else {
                    packagesInstalled.add(new ApplicationDetail(appName, packageName, appIcon));
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        applicationAdapter = new ApplicationAdapter(MainActivity.this, packagesInstalled);
        recyclerViewInstalled.setLayoutManager(linearLayoutManagerInstalled);
        recyclerViewInstalled.setAdapter(applicationAdapter);

        applicationAdapter = new ApplicationAdapter(MainActivity.this, packagesSystem);
        recyclerViewSystem.setLayoutManager(linearLayoutManagerSystem);
        recyclerViewSystem.setAdapter(applicationAdapter);

        if (isAccessibilitySettingsOn(getApplicationContext()) == 0) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("This app works only if the accesibility settings are turned on.")
                    .setPositiveButton("Turn settings on", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                        }
                    })
                    .setNegativeButton("Close App", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }
                    }).show();
        }

    }

    private boolean isSystemPackage(PackageInfo packageInfo) {
        return ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    private int isAccessibilitySettingsOn(Context mContext) {
        try {
            return Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    void init() {
        packageManager = getPackageManager();
        packageInfoList = packageManager.getInstalledPackages(0);
        packagesInstalled = new ArrayList<>();
        packagesSystem = new ArrayList<>();
        recyclerViewInstalled = findViewById(R.id.rv_installed);
        recyclerViewSystem = findViewById(R.id.rv_system);
        linearLayoutManagerInstalled = new LinearLayoutManager(getApplicationContext());
        linearLayoutManagerSystem = new LinearLayoutManager(getApplicationContext());

    }

}
