package com.android.permissionmanager.services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class PermissionService extends AccessibilityService {
    private boolean PERMISSION_CLICK = false;

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
//        if(getEventType(event) == "TYPE_VIEW_CLICKED"){
//            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//            Uri uri = Uri.fromParts("package", getEventText(event), null);
//            intent.setData(uri);
//            startActivity(intent);
//        }

//        if(getEventType(event).equals("TYPE_WINDOW_STATE_CHANGED")){
//            AccessibilityNodeInfo nodeInfo = event.getSource();
//            Log.v(TAG, "ACC::onAccessibilityEvent: nodeInfo=" + nodeInfo);
//            if (nodeInfo == null) {
//                return;
//            }
//
//            List<AccessibilityNodeInfo> permissions = nodeInfo.findAccessibilityNodeInfosByText("Permissions");
//            for (AccessibilityNodeInfo node : permissions) {
//                Log.v(TAG, "ACC::onAccessibilityEvent: Permission " + node.getText());
//                if(node.getText().equals("Permissions")){
//                    node.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                }
//            }
//
//            List<AccessibilityNodeInfo> permission = nodeInfo.findAccessibilityNodeInfosByText("Camera");
//            for (AccessibilityNodeInfo node : permission) {
//                Log.v(TAG, "ACC::onAccessibilityEvent: Camera " + node.getText());
//                if(node.getText().equals("Camera")){
//                    node.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                }
//            }
//            List<AccessibilityNodeInfo> deny = nodeInfo.findAccessibilityNodeInfosByText("Deny");
//            for (AccessibilityNodeInfo node : deny) {
//                Log.v(TAG, "ACC::onAccessibilityEvent: Deny " + node);
//                if(!PERMISSION_CLICK){
//                    if(node.getText().equals("Deny")){
//                        node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                        PERMISSION_CLICK = true;
//                    }
//                }
//                Intent intent = new Intent(this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//
//            nodeInfo.recycle();
//        }
    }

    public void clickPermissions(AccessibilityNodeInfo nodeInfo) {

    }

    public void clickCamera(AccessibilityNodeInfo nodeInfo) {

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