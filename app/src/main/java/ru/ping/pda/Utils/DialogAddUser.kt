package ru.ping.pda.Utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.FirebaseDatabase
import ru.ping.pda.Data_Model.Pda_info
import ru.ping.pda.R

class DialogAddUser:DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater= activity?.layoutInflater

        val view = inflater?.inflate(R.layout.adduserdialog,null)
        val editText:EditText
        editText= view!!.findViewById(R.id.id_edittext_dialog_Add_User)
        builder.setView(view).setPositiveButton(R.string.Dialog_addUser_Button_add){ dialogInterface: DialogInterface, i: Int ->
            val settings = view?.context?.let { SettingsPda(it) }
            val user=editText.text
            var pda_id=Pda_info(settings?.getSettingsPDA(),user.toString(),settings?.getSettingsCommand_id())
            settings?.savePda_User(user.toString())
            var fairebaseDB= FirebaseDatabase.getInstance()
            var ref=fairebaseDB.getReference("pda_info")
            settings?.getSettingsPDA()?.let { ref.child(it).setValue(pda_id) }
        }
        return builder.create()
    }


}
