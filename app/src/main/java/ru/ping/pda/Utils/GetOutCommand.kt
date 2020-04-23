package ru.ping.pda.Utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.ping.pda.R

class GetOutCommand : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder= AlertDialog.Builder(activity)
        val inflater=activity?.layoutInflater
        
        val view =inflater!!.inflate(R.layout.get_out_command,null)
        builder.setPositiveButton(getText(R.string.delete)){dialog: DialogInterface?, which: Int ->  
            
        }
        builder.setNegativeButton(getText(R.string.cancel)){dialog: DialogInterface?, which: Int ->  
            
        }
        return builder.create()
    }
}