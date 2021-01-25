package com.solexgames.listener.impl;

import com.google.gson.JsonObject;
import com.solexgames.DataPlugin;
import com.solexgames.listener.RedisListenerType;
import com.solexgames.network.NetworkServer;
import com.solexgames.network.NetworkServerStatus;
import com.solexgames.network.NetworkServerType;

public class ServerDataUpdateListenerType implements RedisListenerType {

    @Override
    public void handle(JsonObject object) {
        final String serverName = object.get("server").getAsString();
        final NetworkServerType type = NetworkServerType.valueOf(object.get("server_type").getAsString());

        final NetworkServer server = DataPlugin.getInstance().getServerManager().find(serverName)
                .orElseGet(() -> new NetworkServer(serverName, type));

        server.setServerType(type);
        server.setTicksPerSecond(object.get("tps").getAsString());
        server.setMaxPlayerLimit(object.get("max_players").getAsInt());
        server.setOnlinePlayers(object.get("online_players").getAsInt());
        server.setWhitelistEnabled(object.get("whitelist").getAsBoolean());
        server.setOnline(true);

        if (DataPlugin.getInstance().getConfig().getBoolean("debug")) {
            DataPlugin.getInstance().getLogger().info("[DEBUG] Message received of " + serverName + " being updated.");
        }
    }


    @Override
    public String getChannel() {
        return "SERVER_DATA_UPDATE";
    }
}
