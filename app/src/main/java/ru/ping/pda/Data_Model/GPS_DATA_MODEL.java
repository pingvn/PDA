package ru.ping.pda.Data_Model;

import io.realm.RealmObject;

public class GPS_DATA_MODEL extends RealmObject {
    private String data_day;
    private String data_hours;
    private double latitude;
    private double longitude;
    private double altitude;

    public String getData_day() {
        return data_day;
    }

    public void setData_day(String data_day) {
        this.data_day = data_day;
    }

    public String getData_hours() {
        return data_hours;
    }

    public void setData_hours(String data_hours) {
        this.data_hours = data_hours;
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

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
}

