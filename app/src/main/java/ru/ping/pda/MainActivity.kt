package ru.ping.pda

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.FragmentManager
import ru.ping.pda.Fragments.MapFragment

class MainActivity : AppCompatActivity() , MapFragment.OnFragmentInteractionListener{
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        org.osmdroid.config.Configuration.getInstance().load(applicationContext,PreferenceManager.getDefaultSharedPreferences(applicationContext))

        setContentView(R.layout.activity_main)

        runFragment()

    }
    fun runFragment(){
        supportFragmentManager.beginTransaction().add(R.id.id_Conteiner_Fragment,MapFragment()).commit()
    }
}
