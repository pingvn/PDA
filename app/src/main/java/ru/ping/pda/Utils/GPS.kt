package ru.ping.pda.Utils

import android.content.Context
import android.location.Location
import com.google.android.gms.location.*
import io.realm.Realm
import io.realm.RealmObject
import org.osmdroid.api.IMapController
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline
import ru.ping.pda.Data_Model.GPS_DATA_MODEL
import ru.ping.pda.Fragments.fistrun
import java.text.SimpleDateFormat
import java.util.*

lateinit var fusedLocationProviderClient: FusedLocationProviderClient
lateinit var locationRequest: LocationRequest
lateinit var locationCallback: LocationCallback


class GPS {

    fun getLocationUpdate(
        context: Context,
        map: MapView,
        marker: org.osmdroid.views.overlay.Marker,
        track: Polyline,
        track_on:Boolean,
        line_on:Boolean
    ) {
        var lRealm = Realm.getDefaultInstance()
        val sdd = SimpleDateFormat("dd/M/yyyy")
        val sdh = SimpleDateFormat("hh:mm:ss")
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        locationRequest = LocationRequest()
        locationRequest.interval = 500
        locationRequest.fastestInterval = 500
        locationRequest.smallestDisplacement = 10f
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)
                if (p0 != null) {
                    if(track_on){
                        //что то написать для работы с realm
                        lRealm.executeTransaction { realm ->
                            var gps_save = realm.createObject(GPS_DATA_MODEL::class.java)
                            gps_save.data_day = sdd.format(Date())
                            gps_save.data_hours=sdh.format(Date())
                            gps_save.longitude=p0.lastLocation.longitude
                            gps_save.latitude=p0.lastLocation.latitude
                            gps_save.altitude=p0.lastLocation.altitude
                        }
                    }
                    marker.position = GeoPoint(p0.lastLocation.latitude, p0.lastLocation.longitude)
                    map.overlays.add(marker)
                    if(line_on){
                        track.addPoint(GeoPoint(p0.lastLocation.latitude, p0.lastLocation.longitude))
                        map.overlays.add(track)
                    }
                    map.invalidate()

                }
            }
        }
    }


    fun centerMapView(mapController: IMapController) {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                mapController.setCenter(GeoPoint(location.latitude, location.longitude))
            }

        }
    }

    fun startLocationUpdate() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

    fun stopLocationUpdate() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}