package ru.ping.pda


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.view.WindowManager
import android.widget.Button
import io.realm.Realm
import io.realm.RealmConfiguration
import ru.ping.pda.Fragments.MapFragment
import ru.ping.pda.Fragments.SettingsFragment

class MainActivity : AppCompatActivity(), MapFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentSettingsListener,
    View.OnClickListener {

    //кнопки----------------------------------------------------------------------------------------
    lateinit var menu_Button: Button
    lateinit var record_Button: Button
    lateinit var mail_Button: Button
    lateinit var position_Button: Button
    //----------------------------------------------------------------------------------------------
    //виды запуска фрагмента------------------------------------------------------------------------
    val FRAGMENT_MAP_NEW = "mew"
    val FRAGMENT_MAP_REFRESH = "refresh"
    //название базы Realm
    val NAME_REALM_BASE = "pda_db"
    //----------------------------------------------------------------------------------------------
    override fun onFragmentInteraction(uri: Uri) {
    }

    override fun onFragmentSettings(id: Int, value: Boolean){
    }
    //----------------------------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //установка в полноэкранный режим---------------
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        //----------------------------------------------
        setContentView(R.layout.activity_main)

        initElements()//инициализация элементов
        runFragment(FRAGMENT_MAP_NEW)//запуск фрагмета с картами
    }

    //Обработка нажатия кнопок----------------------------------------------------------------------
    override fun onClick(v: View?) {
        when (v?.id) {
            //нажатие кнопки меню
            R.id.id_button_PDA_MENU -> {
                supportFragmentManager.beginTransaction().replace(R.id.id_Conteiner_Fragment,SettingsFragment()).commit()
            }
            //нажатие кнопки определения позиции
            R.id.id_button_PDA_POSITION -> {
                runFragment(FRAGMENT_MAP_REFRESH)
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //здесь инициализируються компаненты главного экрана
    fun initElements() {
        //------------------------------------------------------------------------------------------
        //инициализация Realm
        Realm.init(this)
        //создание конфига Realm
        val configRealm = RealmConfiguration.Builder().name(NAME_REALM_BASE).build()
        Realm.setDefaultConfiguration(configRealm)
        //------------------------------------------------------------------------------------------
        org.osmdroid.config.Configuration.getInstance().load(
            applicationContext,
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
        )
        //инициализация кнопок
        menu_Button = findViewById(R.id.id_button_PDA_MENU)
        record_Button = findViewById(R.id.id_button_PDA_QUEST)
        mail_Button = findViewById(R.id.id_button_PDA_MAIL)
        position_Button = findViewById(R.id.id_button_PDA_POSITION)
        //подключение слушетеля
        menu_Button.setOnClickListener(this)
        record_Button.setOnClickListener(this)
        mail_Button.setOnClickListener(this)
        position_Button.setOnClickListener(this)

    }
    //----------------------------------------------------------------------------------------------
    //запуск фрагментов-----------------------------------------------------------------------------
    fun runFragment(run_parametr: String) {
        when (run_parametr) {
            FRAGMENT_MAP_NEW -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.id_Conteiner_Fragment, MapFragment()).commit()
            }
            FRAGMENT_MAP_REFRESH -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.id_Conteiner_Fragment, MapFragment()).commit()
            }
        }

    }
    //----------------------------------------------------------------------------------------------
}
