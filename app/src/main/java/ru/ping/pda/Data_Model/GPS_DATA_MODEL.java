package ru.ping.pda.Data_Model;

import io.realm.RealmObject;

public class GPS_DATA_MODEL extends RealmObject {
    private String data;
    private double latitude;
    private double longitude;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
