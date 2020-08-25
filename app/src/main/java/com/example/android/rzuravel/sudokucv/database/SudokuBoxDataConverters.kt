package com.example.android.rzuravel.sudokucv.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class SudokuBoxDataConverters {
    @TypeConverter
    fun fromString(value: String?): BooleanArray? {
        val listType: Type = object : TypeToken<BooleanArray?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: BooleanArray?): String? {
        val text = Gson()
        return text.toJson(list)
    }
}