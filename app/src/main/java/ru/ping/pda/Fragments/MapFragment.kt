package ru.ping.pda.Fragments

import android.content.Context
import android.content.res.Resources
import android.content.res.Resources.Theme
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.*
import org.osmdroid.api.IMapController
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import ru.ping.pda.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var fusedLocationClient: FusedLocationProviderClient
private lateinit var locationCallback: LocationCallback

class MapFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdate()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_map, container, false)
        val mMap : MapView = mView.findViewById(R.id.mapview)
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        val mapController : IMapController = mMap.controller //
        mapController.setZoom(16.5) // установка велечины зума при первоначальном запуске карты
        mMap.setMultiTouchControls(true) // управление зумом двумя пальцами
        var marker: Marker = Marker(mMap) // маркер точка на карте
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            marker.icon=resources.getDrawable(R.drawable.ic_position_point,null)
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mView.context)
        //получение последних gps координат
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                marker.position= GeoPoint(location.latitude,location.longitude)
                mMap.overlays.add(marker)
                mapController.setCenter(GeoPoint(location.latitude,location.longitude))
            }
        }


        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationsResult: LocationResult?) {
                super.onLocationResult(locationsResult)
                if (locationsResult != null) {
                    for(location in locationsResult.locations){
                        marker.position= GeoPoint(location.latitude,location.longitude)
                        mMap.overlays.add(marker)
                        //центровать карту по маркеру
                        //mapController.setCenter(GeoPoint(location.latitude,location.longitude))
                    }

                }
            }
        }

        return mView
    }

    fun startLocationUpdate(){
        val request = LocationRequest.create().apply {
            interval =2000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())
    }



    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MapFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
