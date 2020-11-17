package com.android.permissionmanager.model;

import android.graphics.drawable.Drawable;

public class ApplicationDetail {

    public String applicationName, packageName;
    public Drawable appIcon;

    public ApplicationDetail(String applicationName, String packageName, Drawable appIcon) {
        this.applicationName = applicationName;
        this.packageName = packageName;
        this.appIcon = appIcon;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

}
