package com.lab.hosaily.core.im.utils;

import com.google.gson.*;
import com.lab.hosaily.core.im.consts.UtilsException;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;


import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;

@NoArgsConstructor
public class GsonUtils {

    private static class DateDeserializer implements JsonDeserializer<Date>{
        @Override
        public Date deserialize(JsonElement element, Type typeOf, JsonDeserializationContext context) throws JsonParseException{
            return new Date(element.getAsJsonPrimitive().getAsLong());
        }
    }

    private static class DateSerializer implements JsonSerializer<Date>{
        @Override
        public JsonElement serialize(Date date, Type typeOf, JsonSerializationContext context){
            return new JsonPrimitive(date.getTime());
        }
    }

    private static GsonBuilder getBuilder(boolean serializeNulls, boolean escapeHtmlChars, boolean timestamp, String datePattern){
        GsonBuilder builder = new GsonBuilder();

        if(serializeNulls){
            builder.serializeNulls();
        }

        if(!escapeHtmlChars){
            builder.disableHtmlEscaping();
        }

        if(timestamp){
            builder.registerTypeAdapter(Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG)
                   .registerTypeAdapter(Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG);
        }

        if(!timestamp && !StringUtils.isBlank(datePattern)){
            builder.setDateFormat(datePattern);
        }

        return builder;
    }

    public static Gson getGson(boolean serializeNulls, boolean escapeHtmlChars, boolean timestamp, String datePattern){
        return getBuilder(serializeNulls, escapeHtmlChars, timestamp, datePattern).create();
    }

    public static Gson getGson(){
        return getBuilder(false, false, true, null).create();
    }

    public static String unpunctuation(String json){
        if(StringUtils.isBlank(json)){
            return json;
        }

        return json.replaceAll("\'|\"|\\{|\\}|\\[|\\]", "");
    }

    public static String serialize(Object object, boolean serializeNulls, boolean escapeHtmlChars, boolean timestamp, String datePattern){
        Gson gson = GsonUtils.getGson(serializeNulls, escapeHtmlChars, timestamp, datePattern);
        return gson.toJson(object);
    }

    public static String serialize(Object object){
        Gson gson = GsonUtils.getGson();
        return gson.toJson(object);
    }

    public static <T> T deserialize(String json, Class<T> clazz, boolean serializeNulls, boolean escapeHtmlChars, boolean timestamp, String datePattern){
        if(clazz == null){
            throw new UtilsException("对象类型不能为空");
        }

        Gson gson = GsonUtils.getGson(serializeNulls, escapeHtmlChars, timestamp, datePattern);
        return gson.fromJson(json, clazz);
    }

    public static <T> T deserialize(String json, Class<T> clazz){
        if(clazz == null){
            throw new UtilsException("对象类型不能为空");
        }

        Gson gson = GsonUtils.getGson();
        return gson.fromJson(json, clazz);
    }

    public static <T> T deserialize(String json, Type type,  boolean serializeNulls, boolean escapeHtmlChars, boolean timestamp, String datePattern){
        if(type == null){
            throw new UtilsException("对象类型不能为空");
        }

        Gson gson = GsonUtils.getGson(serializeNulls, escapeHtmlChars, timestamp, datePattern);
        return gson.fromJson(json, type);
    }

    public static <T> T deserialize(String json, Type type){
        if(type == null){
            throw new UtilsException("对象类型不能为空");
        }

        Gson gson = GsonUtils.getGson();
        return gson.fromJson(json, type);
    }
}
