package ru.ping.pda.Utils

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.ping.pda.R

class DialogAddUser:DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater= activity?.layoutInflater
        val view = inflater?.inflate(R.layout.adduserdialog,null)
        builder.setView(view)
        return builder.create()
    }
}