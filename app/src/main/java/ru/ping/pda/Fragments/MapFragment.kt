package ru.ping.pda.Fragments

import android.content.Context
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
import ru.ping.pda.Data_Model.GPS_Data_Track
import ru.ping.pda.R
/*
фрагмент отображения карты OpenStreetMap
предположительное использоване : отоброжение карты местности по полученым координатам gps c устройства,
отоброжение курсора на карте по полученным данным.
установка областей получнных от других источников, отрисовка трека. расположение объектов на карте создание ответных действий при приближении к заданному объекту.
сохранение геоданных на устройстве(трека)
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var fusedLocationClient: FusedLocationProviderClient
private lateinit var locationCallback: LocationCallback

class MapFragment : Fragment() {
    // это сгенерированный код

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
        //------------------------------------------------------------------------------------------
        val mMap : MapView = mView.findViewById(R.id.mapview)
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        //------------------------------------------------------------------------------------------
        val mapController : IMapController = mMap.controller //
        mapController.setZoom(16.5) // установка велечины зума при первоначальном запуске карты
        //------------------------------------------------------------------------------------------
        mMap.setMultiTouchControls(true) // управление зумом двумя пальцами
        //------------------------------------------------------------------------------------------
        var marker: Marker = Marker(mMap) // маркер точки на карте
        //изменение иконк маркера-------------------------------------------------------------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            marker.icon=resources.getDrawable(R.drawable.ic_position_point,null)
        }
        //------------------------------------------------------------------------------------------
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mView.context)
        //получение последних gps координат
        //и установка маркера на карту
        //отцентровка карты по маркеру
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                marker.position= GeoPoint(location.latitude,location.longitude)//получение последних gps данных, передача данных маркеру
                mMap.overlays.add(marker)//установка маркера
                mapController.setCenter(GeoPoint(location.latitude,location.longitude))//центровка карты по маркеру
            }
        }

        //изменение позиции маркера на карте после получения новых данных gps
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
    //функция запуска отслеживания gps позиции
    fun startLocationUpdate(){
        val request_GPS = LocationRequest.create()
        request_GPS.setInterval(50)
        request_GPS.setFastestInterval(25)
        fusedLocationClient.requestLocationUpdates(request_GPS, locationCallback, Looper.getMainLooper())
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
