package ru.ping.pda.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import ru.ping.pda.R
import ru.ping.pda.Utils.SettingsPda


class SettingsFragment : Fragment() {
    private var listener: OnFragmentSettingsListener? = null
    var chexBoxTreck: CheckBox? = null
    var checkBoxLine: CheckBox? = null
    lateinit var text_PDA_ID:TextView
    lateinit var text_COMMAND_ID:TextView
    lateinit var settings:SettingsPda


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_settings, container, false)
        initElement(mView)
        return mView
    }
    //инициализация элементов ----------------------------------------------------------------------
    fun initElement(view: View) {
        settings = SettingsPda(view.context) //инициализация класса для сохранения настроек
        settings.init_storage() // -----------------------------
        //инициализация chaexkBox и установка их в сохраненное положение
        checkBoxLine = view.findViewById(R.id.id_Settings_check_line)
        checkBoxLine?.isChecked = settings.getSettingsLine()
        chexBoxTreck = view.findViewById(R.id.id_Settings_check_track)
        chexBoxTreck?.isChecked=settings.getSetingsTreck()
        //иниациализация текста и установка значений из сохренненого
        text_PDA_ID= view.findViewById(R.id.id_settings_pda_id_text)
        text_COMMAND_ID=view.findViewById(R.id.id_settings_command_id_text)
        text_PDA_ID.text=resources.getString(R.string.pda_ip_text_forvard)+" "+settings.getSettingsPDA()
        text_COMMAND_ID.text=resources.getString(R.string.command_id_text_forvard)+" "+settings.getSettingsCommand()
    }
    //----------------------------------------------------------------------------------------------

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentSettingsListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        checkBoxLine?.isChecked?.let { settings.saveSettingsLine(it) }
        chexBoxTreck?.isChecked?.let { settings.saveSettingsTrack(it) }
    }


    interface OnFragmentSettingsListener {
        // TODO: Update argument type and name
        fun onFragmentSettings(id:Int, value:Boolean)
    }

}
