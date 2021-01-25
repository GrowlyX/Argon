package com.solexgames.util;

import com.google.gson.JsonObject;

public class JsonAppender {

    public JsonObject object;

    /**
     * Constructor for creating a new JsonAppender instance with a new JsonObject
     */
    public JsonAppender() {
        this(null);
    }

    /**
     * Constructor for creating a new JsonAppender instance
     *
     * @param object the preexisting JsonObject, if null it creates a new JsonObject
     */
    public JsonAppender(JsonObject object) {
        this.object = object == null ? new JsonObject() : object;
    }

    /**
     * Append an Object to the JsonObject
     *
     * @param key   the key where the Object is stored
     * @param value the value which is stored
     * @param <V>   the type of the value
     * @return the current instance of the JsonAppender
     */
    public <V> JsonAppender append(String key, V value) {
        if (value instanceof Number) {
            object.addProperty(key, (Number) value);
        } else if (value instanceof Boolean) {
            object.addProperty(key, (Boolean) value);
        } else if (value instanceof Character) {
            object.addProperty(key, (Character) value);
        } else if (value instanceof String) {
            object.addProperty(key, (String) value);
        } else {
            throw new IllegalArgumentException("Provided type of \"" + value.getClass().getName() + "\" which is not supported by Json");
        }

        return this;
    }

    /**
     * Get the JsonObject from the JsonAppender instance
     *
     * @return the object
     */
    public JsonObject get() {
        return this.object;
    }

}