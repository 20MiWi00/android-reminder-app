package com.example.z3.RoomDatabase;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

public class convertDate {

    @TypeConverter
    public static String dateToString(multiDate date){
        Gson converter = new Gson();
        String jsonConverter = converter.toJson(date);
        return jsonConverter;
    }

    @TypeConverter
    public static multiDate stringToDate(String json){
        Gson converter = new Gson();
        multiDate date = converter.fromJson(json,multiDate.class);
        return date;
    }

}
