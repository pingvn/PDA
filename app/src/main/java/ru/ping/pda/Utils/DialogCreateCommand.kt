package ru.ping.pda.Utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import ru.ping.pda.Data_Model.Command_info
import ru.ping.pda.R
import kotlin.random.Random

class DialogCreateCommand: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder=AlertDialog.Builder(activity)
        val inflater=activity?.layoutInflater

        val view=inflater!!.inflate(R.layout.create_command_dialog, null)
        val editeText:EditText
        editeText=view.findViewById(R.id.id_Edite_Text_Dialog_Crete_Command)
        builder.setView(view).setPositiveButton(R.string.Dialog_addUser_Button_add){ dialogInterface: DialogInterface, i: Int ->
            val settings = view?.context?.let { SettingsPda(it) }
            val command_name=editeText.text //получение имени комманды
            val command_id= Random.nextInt(0,99999999) //получение id команды
            val command_pin= Random.nextInt(0,999999) // получение пинкода команды для подключения
            var command = Command_info(command_id.toString(),command_pin.toString(),command_name.toString()) // crete command from generate date
            settings?.saveCommandName(command_name.toString())
            settings?.saveCommandPin(command_pin.toString())
            settings?.savePda_command(command_id.toString())


        }
        return builder.create()
    }
}