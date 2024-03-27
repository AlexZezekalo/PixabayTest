package com.zezekalo.pixabaytest.data.datasource.local.room.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.zezekalo.pixabaytest.data.datasource.local.room.entity.LocalTagList

@ProvidedTypeConverter
class RoomTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun convertTagListToJsonString(tagList: LocalTagList): String =
        gson.toJson(tagList)

    @TypeConverter
    fun convertJsonStringToTagList(jsonString: String): LocalTagList =
        gson.fromJson(jsonString, LocalTagList::class.java)
}