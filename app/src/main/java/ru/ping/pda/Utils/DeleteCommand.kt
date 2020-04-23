package ru.ping.pda.Utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.FirebaseDatabase
import ru.ping.pda.Data_Model.Command_info
import ru.ping.pda.R

class DeleteCommand: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val  builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater

        val view=inflater!!.inflate(R.layout.delete_command, null)
        builder.setView(view).setPositiveButton("Удалить"){ dialogInterface: DialogInterface, i: Int ->
            val settings = view?.context?.let { SettingsPda(it) }
            var list = ArrayList<String>()
            list.add("")
            val command = Command_info(settings?.getSettingsCommand_id().toString()
                ,settings?.getSettingsCommand_pin().toString()
                ,settings?.getSettingsCommand_Name().toString()
                ,settings?.getSettingsCommand_owner().toString(),
                list,
                ""
                )
            var fairebaseDB = FirebaseDatabase.getInstance()
            var ref = fairebaseDB.getReference("command/"+command.command_id)
            ref.removeValue()
            settings?.init_storage()
            settings?.savePda_command("00000000")
            settings?.saveCommandPin("000000")
            settings?.saveCommandOwner("000000000")
            settings?.saveCommandName("def")

        }
        return builder.create()
    }
}