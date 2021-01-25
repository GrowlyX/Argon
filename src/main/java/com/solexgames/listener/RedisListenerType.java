package com.solexgames.listener;

import com.google.gson.JsonObject;

public interface RedisListenerType {

    /**
     * Handle an incoming {@link JsonObject} message
     *
     * @param object the json object
     */
    void handle(JsonObject object);

    /**
     * Get the channel of the {@link RedisListenerType}
     *
     * @return the channel
     */
    String getChannel();

}
