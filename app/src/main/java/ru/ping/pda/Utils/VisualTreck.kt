package ru.ping.pda.Utils

import io.realm.Realm
import io.realm.RealmResults
import org.osmdroid.util.GeoPoint
import ru.ping.pda.Data_Model.GPS_DATA_MODEL

class VisualTreck {

    fun getData():List<String>{
        var list=ArrayList<String>()
        var mRealm = Realm.getDefaultInstance()
        var listPoints:RealmResults<GPS_DATA_MODEL> = mRealm.where(GPS_DATA_MODEL::class.java).findAll()
        for (data in listPoints){
            if(list.isEmpty()) list.add(data.data_day)
            if(!data.data_day.equals(list.get(list.lastIndex))) list.add(data.data_day)
        }
        mRealm.close()
        return list
    }

    fun getPoints(day:String):ArrayList<GeoPoint>{
        var mRealm = Realm.getDefaultInstance()
        var listPoints:RealmResults<GPS_DATA_MODEL> = mRealm.where(GPS_DATA_MODEL::class.java).equalTo("data_day",day).findAll()
        var list:ArrayList<GeoPoint> = ArrayList()
        for(data in listPoints){
            var point= GeoPoint(data.latitude,data.longitude)
            if (point != null) {
                list.add(point)
            }

        }
        mRealm.close()
        return list
    }
}