package com.neo.smartsolutions.devices.device_local_db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "device_table")
public class Device {

    @PrimaryKey(autoGenerate = true)
    public Integer id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "code")
    private String code;

    public void setId(Integer id) { this.id = id; }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public Integer getId() { return id; }

    public String getLocation() { return location; }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public Device(@NonNull String name, String location, String description, String status, String type, String code) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.status = status;
        this.type = type;
        this.code = code;
    }
}
