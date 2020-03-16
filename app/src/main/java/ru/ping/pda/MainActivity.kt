package ru.ping.pda


import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.realm.Realm
import io.realm.RealmConfiguration
import ru.ping.pda.Fragments.MapFragment
import ru.ping.pda.Fragments.SettingsFragment
import java.util.jar.Manifest
import javax.net.ssl.ManagerFactoryParameters

class MainActivity : AppCompatActivity(), MapFragment.OnFragmentInteractionListener,
    SettingsFragment.OnFragmentSettingsListener,
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
    var PERMISSION_REQUEST_CODE: Int = 0

    //название базы Realm
    val NAME_REALM_BASE = "pda_db"

    //----------------------------------------------------------------------------------------------
    override fun onFragmentInteraction(uri: Uri) {
    }

    override fun onFragmentSettings(id: Int, value: Boolean) {
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
        check_permissions()
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
                supportFragmentManager.beginTransaction()
                    .replace(R.id.id_Conteiner_Fragment, SettingsFragment()).commit()
            }
            //нажатие кнопки определения позиции
            R.id.id_button_PDA_POSITION -> {
                runFragment(FRAGMENT_MAP_REFRESH)
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //функция проверки разрешений
    fun check_permissions() {
        var param_add_string = "Для работы приложения необходимо включить разрешения."

        var check_gps_1 = false
        var check_gps_2 = false
        var check_wifi = false
        var check_write_storage = false
        var check_internet = false
        var check_networck = false
        var list_permission_guery = ArrayList<String>()
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            list_permission_guery.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        } else {
            check_gps_1 = true
        }
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            list_permission_guery.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            check_gps_2 = true
        }
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_WIFI_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            list_permission_guery.add(android.Manifest.permission.ACCESS_WIFI_STATE)
        } else {
            check_wifi = true
        }
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_NETWORK_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            list_permission_guery.add(android.Manifest.permission.ACCESS_NETWORK_STATE)
        } else {
            check_networck = true
        }
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            list_permission_guery.add(android.Manifest.permission.INTERNET)
        } else {
            check_internet = true
        }
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            list_permission_guery.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            check_write_storage = true;
        }
        //Toast.makeText(this,param_add_string,Toast.LENGTH_LONG).show()
        if (!check_gps_1 and !check_write_storage ) {
            openApp()
        }

       // openApp()
    }

    fun openApp() {
        var appSettings = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:" + getPackageName())
        )
       // Toast.makeText(this,"необходимо дать разрешения",Toast.LENGTH_LONG).show()
        startActivityForResult(appSettings, PERMISSION_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PERMISSION_REQUEST_CODE) {

            return;
        }
        super.onActivityResult(requestCode, resultCode, data)
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
