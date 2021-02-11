package com.neo.smartsolutions.devices_recycler_view;

public class Device {
    private int id;
    private String deviceName;
    private String status;
    private String type;
    private String code;

    public Device(int id, String deviceName, String status, String type, String code) {
        this.id = id;
        this.deviceName = deviceName;
        this.status = status;
        this.type = type;
        this.code = code;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

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

}
