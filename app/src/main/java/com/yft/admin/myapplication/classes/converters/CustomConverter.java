package com.yft.admin.myapplication.classes.converters;

import com.yft.admin.myapplication.classes.ExerciseClass;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Admin on 08.04.2017.
 */

public class CustomConverter implements JsonSerializer<ExerciseClass>, JsonDeserializer<ExerciseClass> {
    public JsonElement serialize(ExerciseClass src, Type type,
                                 JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("howManyTimes", src.howManyTimes);
        object.addProperty("name", src.name);
        object.addProperty("url1", src.url1);
        object.addProperty("url2", src.url2);
        return object;
    }

    public ExerciseClass deserialize(JsonElement json, Type type,
                              JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        String name=new String(object.get("name").getAsString());
        int howManyTimes=object.get("howManyTimes").getAsInt();
        int url1=object.get("url1").getAsInt();
        int url2=object.get("url2").getAsInt();
        return new ExerciseClass(name,howManyTimes,url1,url2);
    }
}