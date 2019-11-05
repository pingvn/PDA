package ru.ping.pda.Utils

import android.content.Context
import android.location.Location
import com.google.android.gms.location.*
import org.osmdroid.api.IMapController
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline
import ru.ping.pda.Fragments.fistrun

lateinit var fusedLocationProviderClient: FusedLocationProviderClient
lateinit var locationRequest: LocationRequest
lateinit var locationCallback: LocationCallback


class GPS {

    fun getLocationUpdate(context: Context,map:MapView,marker:org.osmdroid.views.overlay.Marker,mapController:IMapController,track:Polyline) {

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
                    //gps_data_rw.put_GPS_data(p0.lastLocation.latitude,p0.lastLocation.longitude)

                    marker.position = GeoPoint(p0.lastLocation.latitude,p0.lastLocation.longitude)
                    map.overlays.add(marker)
                    track.addPoint(GeoPoint(p0.lastLocation.latitude,p0.lastLocation.longitude))
                    map.overlays.add(track)
                    map.invalidate()
                        //if(fistrun){
                        //mapController.setCenter(GeoPoint(p0.lastLocation.latitude,p0.lastLocation.longitude))
                        //fistrun=false
                    //}


                }
            }
        }
    }

    fun centerMapView(mapController:IMapController){
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location!=null){
                mapController.setCenter(GeoPoint(location.latitude,location.longitude))
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