package com.steven.newshacker.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object RoomConverters {
        @TypeConverter
        fun fromString(value: String?): List<Int> {
            val listType  = object : TypeToken<ArrayList<String?>?>() {}.type
            return Gson().fromJson(value, listType)
        }

        @TypeConverter
        fun fromList(list: List<Int>?): String {
            val gson = Gson()
            return gson.toJson(list)
        }
}