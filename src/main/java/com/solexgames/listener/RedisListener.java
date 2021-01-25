package com.solexgames.listener;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.solexgames.DataPlugin;
import com.solexgames.listener.impl.ServerDataOfflineListenerType;
import com.solexgames.listener.impl.ServerDataOnlineListenerType;
import com.solexgames.listener.impl.ServerDataUpdateListenerType;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.JedisPubSub;

import java.util.Arrays;

public class RedisListener extends JedisPubSub {

    private final JsonParser parser = new JsonParser();
    private final RedisListenerType[] listenerTypes = new RedisListenerType[]{
            new ServerDataOfflineListenerType(),
            new ServerDataOnlineListenerType(),
            new ServerDataUpdateListenerType(),
    };

    @Override
    public void onMessage(String channel, String message) {
        new BukkitRunnable() {
            @Override
            public void run() {
                final JsonObject object = parser.parse(message).getAsJsonObject();

                Arrays.stream(listenerTypes)
                        .filter(type -> type.getChannel().equalsIgnoreCase(object.get("packet").getAsString()))
                        .findFirst().ifPresent(listener -> listener.handle(object));

            }
        }.runTaskAsynchronously(DataPlugin.getInstance());
    }
}