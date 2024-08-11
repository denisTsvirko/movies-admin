package com.movies.admin.helper;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class JsonHelper {

    public static String toJson(Object object)
    {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static <T> T fromJson(String jsonString, Class<T> valueType) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, valueType);
    }

    public static <T> T fromJsonByType(String json, Type typeOfT) throws JsonSyntaxException {
        return (T) new Gson().fromJson(json, TypeToken.get(typeOfT));
    }
}
