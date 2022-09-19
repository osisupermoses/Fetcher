package com.crop2cash.fetcher.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.osisupermoses.dictionaryapp.feature_dictionary.data.util.JsonParser

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromStringsJson(json: String): List<String> {
        return jsonParser.fromJson<ArrayList<String>>(
            json,
            object : TypeToken<ArrayList<String>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toStringsJson(Strings: List<String>): String {
        return jsonParser.toJson(
            Strings,
            object : TypeToken<ArrayList<String>>(){}.type
        ) ?: ""
    }
}