package ru.ping.pda.GPS
// основа класса взяьта https://habr.com/ru/post/201648/

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle


class GPS_Location :LocationListener{
    //настройки GPS Слушетеля
    var myPoint: Location? = null
    @SuppressLint("MissingPermission")
    fun SeptupLocationListener(context:Context){
        var locationManager:LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var locationListener: GPS_Location = GPS_Location()
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            1f,
            locationListener
        )
        myPoint=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    }

    override fun onLocationChanged(location: Location?) {
        if (location != null) {
            myPoint=location
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}