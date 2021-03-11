package com.neo.smartsolutions.devices.device_local_db;

public interface ClickListener<T> {
    void onItemClick(T data);
    void onItemLongClick(T data);
}