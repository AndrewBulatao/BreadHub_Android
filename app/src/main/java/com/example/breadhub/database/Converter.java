package com.example.breadhub.database;

import androidx.room.TypeConverter;

import java.util.List;

public class Converter {
    // Convert List<String> to one string
    @TypeConverter
    public static String fromList(List<String> list){
        if(list == null){return null;}
        return String.join(",", list);
    }

    @TypeConverter
    public static String toList(List<String> data){
        if(data == null || data.isEmpty()){return null;}
        return String.join(",", data);
    }

}
