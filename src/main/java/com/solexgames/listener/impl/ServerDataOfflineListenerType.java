package com.solexgames.listener.impl;

import com.google.gson.JsonObject;
import com.solexgames.DataPlugin;
import com.solexgames.listener.RedisListenerType;
import com.solexgames.network.NetworkServer;
import com.solexgames.network.NetworkServerType;
import com.solexgames.util.ColorUtil;
import org.bukkit.Bukkit;

public class ServerDataOfflineListenerType implements RedisListenerType {

    @Override
    public void handle(JsonObject object) {
        final String serverName = object.get("server").getAsString();
        final NetworkServerType type = NetworkServerType.valueOf(object.get("server_type").getAsString());

        final NetworkServer server = DataPlugin.getInstance().getServerManager().find(serverName)
                .orElseGet(() -> new NetworkServer(serverName, type));

        server.setOnline(false);

        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.hasPermission(DataPlugin.getInstance().getConfig().getString("permissions.status-broadcasts")))
                .forEach(player -> player.sendMessage(ColorUtil.translate(DataPlugin.getInstance().getConfig().getString("messages.offline-broadcast").replace("<server>", serverName))));

        if (DataPlugin.getInstance().getConfig().getBoolean("debug")) {
            DataPlugin.getInstance().getLogger().info("[DEBUG] Message received of " + serverName + " going offline.");
        }
    }

    @Override
    public String getChannel() {
        return "SERVER_DATA_OFFLINE";
    }
}
