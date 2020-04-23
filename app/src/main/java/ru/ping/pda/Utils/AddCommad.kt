package ru.ping.pda.Utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ru.ping.pda.Data_Model.Command_info
import ru.ping.pda.R

class AddCommad : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder=AlertDialog.Builder(activity)
        val inflater=activity?.layoutInflater

        val view=inflater!!.inflate(R.layout.in_to_command, null)
        val editPin:EditText
        val editId:EditText
        editId=view.findViewById(R.id.id_edittext_id_command_dialog_to_command)
        editPin=view.findViewById(R.id.id_edittext_pin_dialog_to_command)
        builder.setView(view).setPositiveButton("Вступить"){dialog: DialogInterface?, which: Int ->
            val settings = view?.context?.let { SettingsPda(it) }
            val pda_id=settings?.getSettingsPDA()
            val command_pin = editPin.text.toString()
            val command_id = editId.text.toString()
            var eqilID = false
            var read_id = FirebaseDatabase.getInstance().getReference()
            var list = ArrayList<String>()
            var command_a:Command_info
            read_id.child("command").addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (ds: DataSnapshot in p0.children){
                        var command = ds.getValue(Command_info::class.java)
                        if (command?.command_id.equals(command_id)){
                            if(command?.command_pin.equals(command_pin)){
                                // eqilID=true
                                list = command?.command_fd as ArrayList<String>
                                list.add(pda_id.toString())
                                var fairebaseDB = FirebaseDatabase.getInstance()
                                var ref = fairebaseDB.getReference("command/"+command_id+"/command_fd/")
                                ref.setValue(list)
                                settings?.init_storage()
                                settings?.saveCommandPin(command.command_pin)
                                settings?.savePda_command(command.command_id)
                                settings?.saveCommandName(command.Command_name)
                            }
                        }
                    }

                }

            })

        }


        return builder.create()
    }
}