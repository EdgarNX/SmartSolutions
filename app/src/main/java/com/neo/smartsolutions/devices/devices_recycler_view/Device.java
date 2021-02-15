package com.neo.smartsolutions.devices.devices_recycler_view;

public class Device {
    private int id;
    private String deviceName;
    private String description;
    private String status;
    private String type;
    private String code;

    public Device(int id, String deviceName, String description, String status, String type, String code) {
        this.id = id;
        this.deviceName = deviceName;
        this.description = description;
        this.status = status;
        this.type = type;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
