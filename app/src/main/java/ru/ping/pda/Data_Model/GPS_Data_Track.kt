package ru.ping.pda.Data_Model

import io.realm.RealmModel

class GPS_Data_Track :RealmModel{

     var data :String = "" //для хранения даты
    //для хранения координт
     var latitude: Double = 0.0
     var longitude:Double = 0.0

}