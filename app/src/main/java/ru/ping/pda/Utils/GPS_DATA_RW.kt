package ru.ping.pda.Utils

import android.content.Context
import io.realm.Realm
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


class GPS_DATA_RW(context: Context) {
    init {
        Realm.init(context)
    }

    fun put_GPS_data(latitude: Double, longitude: Double) {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        

    }
}
