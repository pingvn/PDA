package ru.ping.pda.Fragments

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase
import org.osmdroid.api.IMapController
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.w3c.dom.Text
import ru.ping.pda.Data_Model.Pda_info
import ru.ping.pda.R
import ru.ping.pda.Utils.GPS
import ru.ping.pda.Utils.SettingsPda
import ru.ping.pda.Utils.VisualTreck
import kotlin.random.Random


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

    private var param_track: Boolean? = null
    private var param2: String? = null
    private var listener: MapFragment.OnFragmentInteractionListener? = null
    private lateinit var settings: SettingsPda
    private lateinit var mMap: MapView
    private lateinit var mapController: IMapController
    private lateinit var marker: Marker
    private lateinit var polylain: Polyline
    private lateinit var polylineSaved:Polyline
    lateinit var text_map_view: TextView

    val gps = GPS()
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
        initElements(mView)
        //------------------------------------------------------------------------------------------
        gps.getLocationUpdate(
            mView.context,
            mMap,
            marker,
            polylain,
            settings.getSetingsTreck(),
            settings.getSettingsLine()
        )
        if (fistrun) {
            fistrun = false
            gps.centerMapView(mapController)
        }
        return mView
    }

    fun initElements(view: View) {
        settings = SettingsPda(view.context)
        settings.init_storage()
        text_map_view = view.findViewById(R.id.id_text_add_info_MapView)
        text_map_view.text = "test"
        //------------------------------------------------------------------------------------------
        mMap = view.findViewById(R.id.mapview)
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        //------------------------------------------------------------------------------------------
        mapController = mMap.controller
        mapController.setZoom(16.5) // установка велечины зума при первоначальном запуске карты
        //------------------------------------------------------------------------------------------
        mMap.setMultiTouchControls(true) // управление зумом двумя пальцами
        //------------------------------------------------------------------------------------------
        marker = Marker(mMap) // маркер точки на карте
        //изменение иконк маркера-------------------------------------------------------------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            marker.icon = resources.getDrawable(R.drawable.ic_position_point, null)
        }
        //------------------------------------------------------------------------------------------
        //Polyline----------------------------------------------------------------------------------
        polylain = Polyline(mMap)
        polylain.color = resources.getColor(R.color.colorLine)
        if (settings.getSettingsShowTrack())
            drawTreck(mMap)
        //------------------------------------------------------------------------------------------
        //подключение к бд и запись значения
        //var db: FirebaseDatabase = FirebaseDatabase.getInstance()
        //var ref=db.getReference("pda")
        //ref.setValue(settings.getSettingsPDA()+"|"+settings.getSettingsCommand())
        //ref.child("dd").setValue("01011"+"|"+settings.getSettingsCommand())
        //check_Pda_ID(view)
    }

    fun drawTreck(map:MapView){
        polylineSaved = Polyline(map)
        polylineSaved.color=Color.CYAN
        var savedTrack = VisualTreck()
        var listDay=savedTrack.getData()
        polylineSaved.setPoints(settings.getSettingsDataTreck()?.let { savedTrack.getPoints(it) })
        map.overlays.add(polylineSaved)
        map.invalidate()
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
