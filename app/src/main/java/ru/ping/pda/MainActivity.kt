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
import androidx.core.content.ContextCompat
import com.google.firebase.database.FirebaseDatabase
import io.realm.Realm
import io.realm.RealmConfiguration
import ru.ping.pda.Data_Model.Pda_info
import ru.ping.pda.Fragments.MapFragment
import ru.ping.pda.Fragments.MessageFragment
import ru.ping.pda.Fragments.SettingsFragment
import ru.ping.pda.Utils.SettingsPda
import kotlin.random.Random

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
    val FRAGMENT_MAP_MESSAGE = "message"
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
        setContentView(R.layout.activity_main)
        //установка в полноэкранный режим---------------
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        //----------------------------------------------
        check_permissions()
        check_Pda_ID()
        val save = SettingsPda(this)
        save.savePda_id("1111")
        //----------------------------------------------
       // setContentView(R.layout.activity_main)
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

            R.id.id_button_PDA_MAIL->{
                runFragment(FRAGMENT_MAP_MESSAGE)
            }
        }

    }

    //----------------------------------------------------------------------------------------------
    //функция проверки разрешений
    fun check_permissions() {
        // var param_add_string = "Для работы приложения необходимо включить разрешения."
        var check_gps_1 = false
        var check_write_storage = false
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
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            list_permission_guery.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            check_write_storage = true;
        }

        if (!check_gps_1 and !check_write_storage) {
            openApp()
        }

    }

    fun openApp() {
        var appSettings = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:" + getPackageName())
        )
        startActivityForResult(appSettings, PERMISSION_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun check_Pda_ID(){
        val settingsPda=SettingsPda(this)
      //  Toast.makeText(this,settingsPda.getSettingsPDA(),Toast.LENGTH_LONG).show()
        if(settingsPda.getSettingsPDA().equals("007")){
            var pda_id = Random.nextLong(1,2147483647)
            //--------------------------------------------------------------------------------------
            var fairebaseDB=FirebaseDatabase.getInstance()
            var ref=fairebaseDB.getReference("pda_info")
            var pda_info=Pda_info(pda_id.toString(),"user","0")
            ref.child(pda_id.toString()).setValue(pda_info)

            settingsPda.init_storage()
            settingsPda.savePda_id(pda_id.toString())
        }
    }
/*
    fun check_Pda_ID() {

        val settings = SettingsPda(this)
        val user = settings.getSettingsUser()
        val comand = settings.getSettingsCommand()
        val id = settings.getSettingsPDA()
        if (id.equals("007")) {
            var pda_id = Random.nextInt(1, 1000000)
            var readdb = FirebaseDatabase.getInstance().getReference()
            readdb.child("pda_id").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (ds: DataSnapshot in p0.children) {
                        val pda = ds.getValue(Pda_info::class.java)
                        if (pda?.pda_id.equals(pda_id.toString())) {
                            pda_id = Random.nextInt(1, 1000000)
                        }
                    }
                    var db: FirebaseDatabase = FirebaseDatabase.getInstance()
                    var ref = db.getReference("pda_id")
                    var id = Pda_info(pda_id.toString(), "007", "0")
                    ref.child(id.pda_id.toString()).setValue(id)

                }
            })


        }
        else{
        }


    }
*/
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
//проверка и генерация номера

    //----------------------------------------------------------------------------------------------
//запуск фрагментов-----------------------------------------------------------------------------
    public fun runFragment(run_parametr: String) {
        when (run_parametr) {
            FRAGMENT_MAP_NEW -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.id_Conteiner_Fragment, MapFragment()).commit()
            }
            FRAGMENT_MAP_REFRESH -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.id_Conteiner_Fragment, MapFragment()).commit()
            }
            FRAGMENT_MAP_MESSAGE->{
                supportFragmentManager.beginTransaction().replace(R.id.id_Conteiner_Fragment,MessageFragment()).commit()
            }
        }

    }
//----------------------------------------------------------------------------------------------
}
