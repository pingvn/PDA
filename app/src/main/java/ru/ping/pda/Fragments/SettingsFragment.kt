package ru.ping.pda.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import ru.ping.pda.R


class SettingsFragment : Fragment() {
    private var listener: OnFragmentSettingsListener? = null
    var chexBoxTreck: CheckBox? = null
    var checkBoxLine: CheckBox? = null


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

    fun initElement(view: View) {
        checkBoxLine = view.findViewById(R.id.id_Settings_check_line)
        chexBoxTreck = view.findViewById(R.id.id_Settings_check_track)
    }

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
    }


    interface OnFragmentSettingsListener {
        // TODO: Update argument type and name
        fun onFragmentSettings(id:Int, value:Boolean)
    }

}
