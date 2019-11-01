package ru.ping.pda.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.TELEPHONY_SERVICE
import android.telephony.TelephonyManager

class UserID {
    @SuppressLint("MissingPermission", "NewApi")
    fun generateUSserID(context:Context):String{
        val telService:TelephonyManager= context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        val imei = telService.imei
        var data = imei.toInt()
        data = data*19830502/2000+3
        return data.toString()

    }
}