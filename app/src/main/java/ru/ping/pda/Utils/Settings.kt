package ru.ping.pda.Utils

import android.content.Context
import io.realm.Realm
import ru.ping.pda.Data_Model.Settings_data

class Settings (context: Context){
    fun saveSettings(user_id:String?,comand:String?,track:Boolean?,line:Boolean?){
        val realm :Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val settings=realm.createObject(Settings_data::class.java)
        if(user_id!=null) settings.user_id = user_id else settings.user_id = "007"
        if(comand!=null)settings.command = comand else settings.command = "0"
        if(track!=null)settings.isTrack_record = track else settings.isTrack_record = false
        if(line!=null)settings.isLine_track = line else settings.isLine_track = false
        realm.commitTransaction()
        realm.close()
    }

    fun readSettings(id:String): Settings_data? {
        val realm: Realm= Realm.getDefaultInstance()
        var settings =realm.where(Settings_data::class.java).equalTo("user_id",id).findFirst()
        realm.close()
        return settings
    }
}