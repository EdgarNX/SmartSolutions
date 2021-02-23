package com.neo.smartsolutions.locations.location_local_db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "location_table")
public class Location {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "street")
    private String street;

    @ColumnInfo(name = "number")
    private String number;

    public Location(@NonNull String name, String city, String street, String number) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getLocation() {
        return city + " " + street + " " + number;
    }
}
