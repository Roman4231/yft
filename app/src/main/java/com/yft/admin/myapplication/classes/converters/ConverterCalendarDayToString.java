package com.yft.admin.myapplication.classes.converters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.lang.reflect.Type;

/**
 * Created by Admin on 29.04.2017.
 */

public class ConverterCalendarDayToString implements JsonSerializer<CalendarDay>, JsonDeserializer<CalendarDay> {
    public JsonElement serialize(CalendarDay src, Type type,
                                 JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("year", src.getYear());
        object.addProperty("month", src.getMonth());
        object.addProperty("day", src.getDay());
        return object;
    }

    public CalendarDay deserialize(JsonElement json, Type type,
                                     JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        int year=object.get("year").getAsInt();
        int month=object.get("month").getAsInt();
        int day=object.get("day").getAsInt();
        return CalendarDay.from(year,month,day);
    }
}