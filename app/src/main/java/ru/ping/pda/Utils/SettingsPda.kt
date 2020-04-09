package ru.ping.pda.Utils

import android.content.Context
import android.content.SharedPreferences

class SettingsPda(context: Context) {

    //----------------------------------------------------------------------------------------------
    //название хранилища для хранения настроек
    val STORAGE_NAME = "PDA_SETTINGS"
    //название параметра для хранения состояния записи координат
    val TRACK_RECORD = "track_record"
    //название параметра для хранения состояния отоброжения линии пройденого пут
    val TRECK_LINE = "track_line"
    //название параметра для хранения уникального номера устройства
    val PDA_ID = "id_pda"
    //название параметра для хранения индентефикатора комманды, по умолчанию 0 (одиночка)
    val COMMAND_ID = "command_id"
    //название настройки сохранения отображения записанного трека
    val SHOW_TRECK = "show_track"
    //параметр сохраненной даты для отоброения трека
    val SELECTED_TRACK_DATA = "treck_data"
    val COMMAND_NAME = "command_name"
    val USAER_NAME="user"

    val COMMAND_PIN="c_pin"
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    var sharedPreferences: SharedPreferences? = null
    var editor  :SharedPreferences.Editor? = null
    var context = context
    //---инициализация хранения настроек------------------------------------------------------------
    //скорее всего частое использование для включния отключения записи координат и показа линии
    // пройденного пути
    fun init_storage() {
        sharedPreferences = context.getSharedPreferences(STORAGE_NAME,Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
    }
    //----------------------------------------------------------------------------------------------
    //----сохранение настроек-----------------------------------------------------------------------
    fun saveSettingsTrack(track:Boolean){
        if(sharedPreferences == null) init_storage() //если не проинициализированно
        editor?.putBoolean(TRACK_RECORD,track) //запись значения трека
        editor?.apply() // внести изменение в хранилище
    }
    //----------------------------------------------------------------------------------------------
    fun saveSettingsLine(line:Boolean){
        if(sharedPreferences == null) init_storage() //если не проинициализированно
        editor?.putBoolean(TRECK_LINE,line) //запись значения линии
        editor?.apply() // внести изменение в хранилище
    }
    //----------------------------------------------------------------------------------------------
    //сохранение настройки отображения сохраненного трека
    fun saveShowTrack(show:Boolean){
        if(sharedPreferences == null) init_storage() //если не проинициализированно то проинициализировать
        editor?.putBoolean(SHOW_TRECK,show)
        editor?.apply()
    }
    //сохранение данных о выбранной дате трека
    fun savedataTreck(dataTreck:String){
        if (sharedPreferences == null) init_storage()
        editor?.putString(SELECTED_TRACK_DATA, dataTreck)
        editor?.apply()
    }
    //--сохранение данных о PDA --------------------------------------------------------------------
    //предположительно будет редко изменяться при первом запуске или при смене комманды
    /*
    fun savePDASettings(id_PDA: String, user:String ,command_id:String){
        if(sharedPreferences!=null)init_storage() //если хранилище не инициализированно
        editor?.putString(PDA_ID,id_PDA) //внести значение pda
        editor?.putString(USAER_NAME,user)
        editor?.putString(COMMAND_ID,command_id) //внести значение комманды
        editor?.commit()//записать в хранилище
    }
    */
    fun savePda_id(id_PDA:String){
        if(sharedPreferences!=null)init_storage() //если хранилище не инициализированно
        editor?.putString(PDA_ID,id_PDA) //внести значение pda
        editor?.apply()
    }
    fun savePda_User(user:String){
        if(sharedPreferences!=null)init_storage() //если хранилище не инициализированно
        editor?.putString(USAER_NAME,user)
        editor?.apply()
    }
    fun savePda_command(command_id:String){
        if(sharedPreferences!=null)init_storage() //если хранилище не инициализированно
        editor?.putString(COMMAND_ID,command_id) //внести значение комманды
        editor?.apply()//записать в хранилище
    }
    fun saveCommandPin(command_pin:String){
        if(sharedPreferences!=null)init_storage() //если хранилище не инициализированно
        editor?.putString(COMMAND_PIN,command_pin) //внести значение комманды
        editor?.apply()//записать в хранилище
    }

    fun saveCommandName(command_Name:String){
        if(sharedPreferences!=null)init_storage() //если хранилище не инициализированно
        editor?.putString(COMMAND_NAME,command_Name) //внести значение комманды
        editor?.apply()//записать в хранилище
    }
    //----------------------------------------------------------------------------------------------
    //--получение значений изхранилища--------------------------------------------------------------
    //получения состояния чек боксов трека и линии--------------------------------------------------
    fun getSetingsTreck():Boolean{
        val pref = context.getSharedPreferences(STORAGE_NAME,0)
        return pref.getBoolean(TRACK_RECORD,false)
    }
    fun getSettingsLine(): Boolean {
        val pref = context.getSharedPreferences(STORAGE_NAME,0)
        return pref.getBoolean(TRECK_LINE,false)
    }
    //получение PDA_ID и COMMAND_ID-----------------------------------------------------------------
    fun getSettingsPDA(): String? {
        val pref = context.getSharedPreferences(STORAGE_NAME,0)
        return pref.getString(PDA_ID,"007")
    }
    //данные о команде
    fun getSettingsCommand_Name():String?{
        val pref = context.getSharedPreferences(STORAGE_NAME,0)
        return pref.getString(COMMAND_NAME,"0")
    }
    //показывать сохраненный трек
    fun getSettingsShowTrack():Boolean{
        val pref=context.getSharedPreferences(STORAGE_NAME,0)
        return  pref.getBoolean(SHOW_TRECK,false)
    }
    fun getSettingsDataTreck(): String? {
        val pref = context.getSharedPreferences(STORAGE_NAME,0)
        return pref.getString(SELECTED_TRACK_DATA,"0")
    }

    fun getSettingsUser(): String? {
        val pref = context.getSharedPreferences(STORAGE_NAME,0)
        return pref.getString(USAER_NAME,"0")
    }

    fun getSettingsCommand_id():String?{
        val pref = context.getSharedPreferences(STORAGE_NAME,0)
        return pref.getString(COMMAND_ID,"00000000")
    }

    fun getSettingsCommand_pin():String?{
        val pref = context.getSharedPreferences(STORAGE_NAME,0)
        return pref.getString(COMMAND_PIN,"000000")
    }
}