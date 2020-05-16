package ru.ping.pda.Utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ru.ping.pda.Data_Model.Command_info
import ru.ping.pda.R

class GetOutCommand : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder= AlertDialog.Builder(activity)
        val inflater=activity?.layoutInflater
        
        val view =inflater!!.inflate(R.layout.get_out_command,null)
        builder.setPositiveButton(getText(R.string.delete)){dialog: DialogInterface?, which: Int ->
            val settings = view?.context?.let { SettingsPda(it) }
            val pda_id=settings?.getSettingsPDA()
            val command_id= settings?.getSettingsCommand_id()
            var read_id = FirebaseDatabase.getInstance().getReference()
            var list = ArrayList<String>()
            var command_a: Command_info
            read_id.child("command").addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for(ds :DataSnapshot in p0.children){
                        var command = ds.getValue(Command_info::class.java)
                        if(command?.command_id.equals(command_id)){
                            list = command?.command_fd as ArrayList<String>
                            list.remove(pda_id)
                            var fairebaseDB = FirebaseDatabase.getInstance()
                            var ref = fairebaseDB.getReference("command/"+command_id+"/command_fd/")
                            ref.setValue(list)
                            settings?.init_storage()
                            settings?.savePda_command("00000000")
                            settings?.saveCommandPin("000000")
                            settings?.saveCommandOwner("000000000")
                            settings?.saveCommandName("def")
                        }
                    }
                }
            })

            
        }
        builder.setNegativeButton(getText(R.string.cancel)){dialog: DialogInterface?, which: Int ->  
            
        }
        return builder.create()
    }
}