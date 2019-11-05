package ru.ping.pda.Fragments

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.osmdroid.api.IMapController
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import ru.ping.pda.R
import ru.ping.pda.Utils.GPS



/*
фрагмент отображения карты OpenStreetMap
предположительное использоване : отоброжение карты местности по полученым координатам gps c устройства,
отоброжение курсора на карте по полученным данным.
установка областей получнных от других источников, отрисовка трека. расположение объектов на карте создание ответных действий при приближении к заданному объекту.
сохранение геоданных на устройстве(трека)
 */
private const val ARG_PARAM_ON_TRACK = "track"
private const val ARG_PARAM2 = "param2"
//глобальные переменные для геопозиции--------------------------------------------------------------


var fistrun: Boolean = true

class MapFragment : Fragment() {

    //var track:GPS_Data_Track = GPS_Data_Track("",0.0,0.0)
    // это сгенерированный код
    private var param_track: Boolean? = null
    private var param2: String? = null
    private var listener: MapFragment.OnFragmentInteractionListener? = null

    val gps=GPS()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param_track = it.getBoolean(ARG_PARAM_ON_TRACK)
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
        //------------------------------------------------------------------------------------------
        //Polyline----------------------------------------------------------------------------------
        var polylain: Polyline = Polyline(mMap)
        polylain.color=resources.getColor(R.color.colorLine)
        //------------------------------------------------------------------------------------------
        gps.getLocationUpdate(mView.context,mMap,marker,mapController,polylain)
        if(fistrun){
            fistrun = false
            gps.centerMapView(mapController)
        }
        return mView
    }




    override fun onResume() {
        super.onResume()
        gps.startLocationUpdate()
    }

    override fun onPause() {
        super.onPause()
       gps.stopLocationUpdate()
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
        fistrun = true
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(ptrack: Boolean, param2: String) =
            MapFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_PARAM_ON_TRACK, ptrack)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
