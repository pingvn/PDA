package ru.ping.pda.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import ru.ping.pda.MainActivity
import ru.ping.pda.R
import ru.ping.pda.Utils.*


class SettingsFragment : Fragment(),View.OnClickListener {
    private var listener: OnFragmentSettingsListener? = null
    var chexBoxTreck: CheckBox? = null
    var checkBoxLine: CheckBox? = null
    var checkBoxShowTreck: CheckBox? = null
    lateinit var text_PDA_ID: TextView
    lateinit var text_COMMAND_ID: TextView
    lateinit var settings: SettingsPda
    lateinit var spinner: Spinner
    lateinit var buttonAddUser: Button
    lateinit var buttonCreateCommand: Button
    lateinit var buttonAddToCommand: Button
    var list = ArrayList<String>()


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
        settings.init_storage() // -----------------------------------------------------------------
        spinner = view.findViewById(R.id.id_spinner_chuse_data)
        spinner.isEnabled = settings.getSettingsShowTrack()
        var trackData = VisualTreck()
        val spinerAdapter = ArrayAdapter(
            view.context,
            android.R.layout.simple_spinner_item,
            trackData.getData().toTypedArray()
        )
        list = trackData.getData() as ArrayList<String>
        spinerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinerAdapter
        //инициализация chaexkBox и установка их в сохраненное положение
        checkBoxLine = view.findViewById(R.id.id_Settings_check_line)
        checkBoxLine?.isChecked = settings.getSettingsLine()
        chexBoxTreck = view.findViewById(R.id.id_Settings_check_track)
        chexBoxTreck?.isChecked = settings.getSetingsTreck()
        checkBoxShowTreck = view.findViewById(R.id.id_Settings_show_track)
        checkBoxShowTreck?.isChecked = settings.getSettingsShowTrack()
        checkBoxShowTreck?.setOnCheckedChangeListener { buttonView, isChecked ->
            spinner.isEnabled = isChecked
        }
        //инициализация кнопок
        buttonAddUser=view.findViewById(R.id.id_settings_button_User_name)
        buttonCreateCommand=view.findViewById(R.id.id_settings_button_create_command)
        buttonAddToCommand=view.findViewById(R.id.id_settings_button_add_to_command)
        if((!settings.getSettingsCommand_id().equals("00000000"))&&settings.getSettingsCommand_owner().equals(settings.getSettingsPDA())){
            buttonCreateCommand.text= getString(R.string.Delete_Command_Text)
            buttonAddToCommand.isEnabled=false
        }else{
            buttonAddToCommand.isEnabled=true
            if(!settings.getSettingsCommand_id().equals("00000000")){
                buttonCreateCommand.isEnabled=false
                buttonAddToCommand.text=getText(R.string.get_out_command)
            }else{
                buttonCreateCommand.isEnabled=true
            }

        }

        buttonCreateCommand.setOnClickListener(this)
        buttonAddUser.setOnClickListener(this)
        buttonAddToCommand.setOnClickListener(this)
        //иниациализация текста и установка значений из сохренненого
        text_PDA_ID = view.findViewById(R.id.id_settings_pda_id_text)
        text_COMMAND_ID = view.findViewById(R.id.id_settings_command_id_text)
        text_PDA_ID.text =
            resources.getString(R.string.pda_ip_text_forvard) + " " + settings.getSettingsPDA()
        text_COMMAND_ID.text =
            resources.getString(R.string.command_id_text_forvard) + " " +settings.getSettingsCommand_Name()+"\n"+settings.getSettingsCommand_pin()+"  "+settings.getSettingsCommand_id()

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
        checkBoxShowTreck?.isChecked?.let { settings.saveShowTrack(it) }
        val position = spinner.selectedItemPosition
        if ((position >= 0) and (position < list.size)) { //проверка чтобы не вылезать за длину списка.
            settings.savedataTreck(list.get(spinner.selectedItemPosition))
        }

    }


    interface OnFragmentSettingsListener {
        // TODO: Update argument type and name
        fun onFragmentSettings(id: Int, value: Boolean)
    }
    //обработка нажатия на кнопку
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.id_settings_button_User_name->{
                val dialogUser=DialogAddUser()
                val dialogManager = fragmentManager
                if (dialogManager != null) {
                    dialogUser.show(dialogManager,"test")
                }

            }
            R.id.id_settings_button_create_command->{
                if((!settings.getSettingsCommand_id().equals("00000000"))&&settings.getSettingsCommand_owner().equals(settings.getSettingsPDA())){
                    val dialogDeleteCommand=DeleteCommand()
                    val dialogManager=fragmentManager
                    if(dialogManager!=null){
                        dialogDeleteCommand.show(dialogManager,"command")
                    }

                }else{
                    val dialogCommand=DialogCreateCommand()
                    val dialogManager= fragmentManager
                    if(dialogManager!= null){
                        dialogCommand.show(dialogManager,"Command")
                    }
                }


            }
            R.id.id_settings_button_add_to_command-> {
                if(!settings.getSettingsCommand_id().equals("00000000")) {
                    val dialogCommand = GetOutCommand()
                    val dialogManager = fragmentManager
                    if (dialogManager != null) {
                        dialogCommand.show(dialogManager, "Command")
                    }
                }else{
                    val dialogCommand = AddCommad()
                    val dialogManager = fragmentManager
                    if (dialogManager != null) {
                        dialogCommand.show(dialogManager, "Command")
                    }
                }


            }
        }
    }

}
