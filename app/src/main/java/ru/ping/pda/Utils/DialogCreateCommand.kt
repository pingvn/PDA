package ru.ping.pda.Utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import ru.ping.pda.R

class DialogCreateCommand: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder=AlertDialog.Builder(activity)
        val inflater=activity?.layoutInflater

        val view=inflater!!.inflate(R.layout.create_command_dialog, null)
        val editeText:EditText
        editeText=view.findViewById(R.id.id_Edite_Text_Dialog_Crete_Command)
        builder.setView(view).setPositiveButton(R.string.Dialog_addUser_Button_add){ dialogInterface: DialogInterface, i: Int ->
            val settings = view?.context?.let { SettingsPda(it) }
            val command=editeText.text


        }
        return super.onCreateDialog(savedInstanceState)
    }
}