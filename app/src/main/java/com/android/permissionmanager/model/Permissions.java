package com.android.permissionmanager.model;

public class Permissions {

    public String permissionName;
    public String permissionGroup;
    public String protectionLevel;
    public boolean isModifiable;
    public String description;
    public boolean isGranted;

    public Permissions(String permissionName, String permissionGroup, String protectionLevel, boolean isModifiable, String description, boolean isGranted) {
        this.permissionName = permissionName;
        this.permissionGroup = permissionGroup;
        this.protectionLevel = protectionLevel;
        this.isModifiable = isModifiable;
        this.description = description;
        this.isGranted = isGranted;
    }

    public boolean isGranted() {
        return isGranted;
    }

    public void setGranted(boolean granted) {
        isGranted = granted;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionGroup() {
        return permissionGroup;
    }

    public void setPermissionGroup(String permissionGroup) {
        this.permissionGroup = permissionGroup;
    }

    public String getProtectionLevel() {
        return protectionLevel;
    }

    public void setProtectionLevel(String protectionLevel) {
        this.protectionLevel = protectionLevel;
    }

    public boolean isModifiable() {
        return isModifiable;
    }

    public void setModifiable(boolean modifiable) {
        isModifiable = modifiable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
