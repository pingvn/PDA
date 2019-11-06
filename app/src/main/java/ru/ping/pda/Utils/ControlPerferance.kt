package ru.ping.pda.Utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.view.Display

class ControlPerferance(context:Context) {
    val PREFERENCE = "sittings"
    val TRACE_ON = "trace"
    val LINE_ON = "line"
    var sharedPreferences:SharedPreferences
    var editor:SharedPreferences.Editor

    init {
        sharedPreferences = context.getSharedPreferences(PREFERENCE,MODE_PRIVATE)
        editor=sharedPreferences.edit()
    }

    public fun savetrack(on:Boolean){
        editor.putBoolean(TRACE_ON,on)
        editor.apply()
    }
    public fun saveline(on:Boolean){
        editor.putBoolean(LINE_ON,on)
        editor.apply()
    }

    fun gettrack():Boolean{
        if(sharedPreferences.contains(TRACE_ON)){
            return sharedPreferences.getBoolean(TRACE_ON,false)
        }
        return false
    }
    fun getline():Boolean{
        if(sharedPreferences.contains(LINE_ON)){
            return sharedPreferences.getBoolean(LINE_ON,false)
        }
        return false
    }



}