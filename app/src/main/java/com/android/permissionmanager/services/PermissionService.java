package com.android.permissionmanager.services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.android.permissionmanager.activities.MainActivity;
import com.google.gson.Gson;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class PermissionService extends AccessibilityService {
    private String permission;
    private String packageName;
    private boolean PERMISSION_CLICK;

    public PermissionService() {
    }

    static final String TAG = "PermissionService";

    private String getEventType(AccessibilityEvent event) {
        switch (event.getEventType()) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                return "TYPE_NOTIFICATION_STATE_CHANGED";
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                return "TYPE_VIEW_CLICKED";
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                return "TYPE_VIEW_FOCUSED";
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                return "TYPE_VIEW_LONG_CLICKED";
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                return "TYPE_VIEW_SELECTED";
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                return "TYPE_WINDOW_STATE_CHANGED";
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                return "TYPE_VIEW_TEXT_CHANGED";
        }
        return "default";
    }

    private String getEventText(AccessibilityEvent event) {
        StringBuilder sb = new StringBuilder();
        for (CharSequence s : event.getText()) {
            sb.append(s);
        }
        return sb.toString();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        String json = new Gson().toJson(event.getText());

        if (json.contains("Revoke")) {
            PERMISSION_CLICK = false;
            json = json.replace("[", "").replace("]", "");
            String[] text = json.split(" ");
            permission = text[1];

            AccessibilityNodeInfo nodeInfo = event.getSource();
            if (nodeInfo == null) {
                return;
            }

            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);

            Uri uri = Uri.fromParts("package", "" + packageName, null);
            intent.setData(uri);
            startActivity(intent);
        }

        if (getEventType(event).equals("TYPE_WINDOW_STATE_CHANGED")) {
            AccessibilityNodeInfo nodeInfo = event.getSource();
            if (nodeInfo == null) {
                return;
            }

            List<AccessibilityNodeInfo> name = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                name = nodeInfo.findAccessibilityNodeInfosByViewId(event.getPackageName() + ":id/app_package");
            }
            for (AccessibilityNodeInfo node : name) {
                packageName = node.getText().toString();
            }


            List<AccessibilityNodeInfo> permissions = nodeInfo.findAccessibilityNodeInfosByText("Permissions");
            for (AccessibilityNodeInfo node : permissions) {
                if (node.getText().equals("Permissions") && !node.getText().equals(permission) && !node.getText().equals("app")) {
                    node.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    Log.d("Service", "Click 1");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


            List<AccessibilityNodeInfo> p = nodeInfo.findAccessibilityNodeInfosByText(permission);
            for (AccessibilityNodeInfo innernode : p) {
                if (innernode.getText().equals(permission)) {
                    if (PERMISSION_CLICK)
                        return;

                    innernode.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    PERMISSION_CLICK = true;

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
            nodeInfo.recycle();
        }
    }

    @Override
    public void onInterrupt() {
        Log.v(TAG, "onInterrupt");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.v(TAG, "onServiceConnected");
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
    }
}