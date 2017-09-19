package com.yft.admin.myapplication.classes.converters;

import com.yft.admin.myapplication.classes.CompletedExercises;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Admin on 26.04.2017.
 */

public class CompletedExercisesConverter implements
        JsonSerializer<CompletedExercises>,
        JsonDeserializer<CompletedExercises> {

    public JsonElement serialize(CompletedExercises src, Type type,
                                 JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("name", src.name);
        object.addProperty("reps", src.reps);
        return object;
    }

    public CompletedExercises deserialize(JsonElement json, Type type,
                                     JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        String name=new String(object.get("name").getAsString());
        int sets=object.get("sets").getAsInt();
        int reps=object.get("reps").getAsInt();
        return new CompletedExercises(name,reps);
    }

}
