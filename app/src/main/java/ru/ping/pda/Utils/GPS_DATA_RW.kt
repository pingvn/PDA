package ru.ping.pda.Utils

import android.content.Context
import io.realm.Realm
import ru.ping.pda.Data_Model.GPS_DATA_MODEL
import java.text.SimpleDateFormat
import java.util.*

class GPS_DATA_RW(context: Context) {

    fun put_GPS_data(latitude: Double, longitude: Double) {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        var realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val gps_data= realm.createObject(GPS_DATA_MODEL::class.java)
        gps_data.data =sdf.format(Date())
        gps_data.latitude = latitude
        gps_data.longitude = longitude
        realm.commitTransaction()
        realm.close()

    }
}
