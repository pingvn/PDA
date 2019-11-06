package ru.ping.pda.Data_Model;

import io.realm.RealmObject;

public class Settings_data extends RealmObject {
    String user_id; //уникальный номер пда
    String command; //номер комманды
    boolean track_record; //включина ли запись передвижения
    boolean line_track; // показывать линию трекера на карте

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public boolean isTrack_record() {
        return track_record;
    }

    public void setTrack_record(boolean track_record) {
        this.track_record = track_record;
    }

    public boolean isLine_track() {
        return line_track;
    }

    public void setLine_track(boolean line_track) {
        this.line_track = line_track;
    }
}
