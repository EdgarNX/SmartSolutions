package com.neo.smartsolutions.location_recycler_view;

import androidx.annotation.NonNull;

public class Location {
    private int id;
    private String locationName;
    private String city;
    private String street;
    private int number;

    public Location(int id, String locationName, String city, String street, int number) {
        this.id = id;
        this.locationName = locationName;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) { this.street = street; }

    public int getNumber() { return number; }

    public void setNumber(int number) { this.number = number; }

    public String getLocation() { return city + " " + street + " " + number; }

    @NonNull
    @Override
    public String toString() {
        return "Location{" +
                "id='" + id + '\'' +
                ", locationName='" + locationName + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
