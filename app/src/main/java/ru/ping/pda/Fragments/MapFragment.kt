package ru.ping.pda.Fragments

import android.content.Context
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.*
import org.osmdroid.api.IMapController
import org.osmdroid.api.IMapView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import ru.ping.pda.R
import java.util.*
import kotlin.concurrent.schedule


/*
фрагмент отображения карты OpenStreetMap
предположительное использоване : отоброжение карты местности по полученым координатам gps c устройства,
отоброжение курсора на карте по полученным данным.
установка областей получнных от других источников, отрисовка трека. расположение объектов на карте создание ответных действий при приближении к заданному объекту.
сохранение геоданных на устройстве(трека)
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
//глобальные переменные для геопозиции--------------------------------------------------------------
private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
private lateinit var locationRequest: LocationRequest
private lateinit var locationCallback: LocationCallback



class MapFragment : Fragment() {


    // это сгенерированный код
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    var fistrun: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_map, container, false)
        //------------------------------------------------------------------------------------------
        val mMap: MapView = mView.findViewById(R.id.mapview)
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        //------------------------------------------------------------------------------------------
        val mapController: IMapController = mMap.controller
        mapController.setZoom(16.5) // установка велечины зума при первоначальном запуске карты
        //------------------------------------------------------------------------------------------
        mMap.setMultiTouchControls(true) // управление зумом двумя пальцами
        //------------------------------------------------------------------------------------------
        var marker: Marker = Marker(mMap) // маркер точки на карте
        //изменение иконк маркера-------------------------------------------------------------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            marker.icon = resources.getDrawable(R.drawable.ic_position_point, null)
        }
        getLocationUpdate(mView.context,mMap,marker,mapController)
        return mView
    }

    private fun getLocationUpdate(context: Context,map :MapView, marker:Marker,mapController:IMapController) {
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
                    marker.position = GeoPoint(p0.lastLocation.latitude,p0.lastLocation.longitude)
                    map.overlays.add(marker)
                    map.invalidate()
                   // map.refreshDrawableState()
                    if (fistrun){
                        fistrun = false
                        mapController.setCenter(GeoPoint(p0.lastLocation.latitude,p0.lastLocation.longitude))
                    }

                }
            }
        }
    }

    private fun startLocationUpdate() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

    private fun stopLocationUpdate() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }


    override fun onResume() {
        super.onResume()
        startLocationUpdate()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdate()
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
