package com.solexgames.listener.impl;

import com.google.gson.JsonObject;
import com.solexgames.DataPlugin;
import com.solexgames.listener.RedisListenerType;
import com.solexgames.network.NetworkServer;
import com.solexgames.network.NetworkServerStatus;
import com.solexgames.network.NetworkServerType;
import com.solexgames.util.ColorUtil;
import org.bukkit.Bukkit;

public class ServerDataOnlineListenerType implements RedisListenerType {

    @Override
    public void handle(JsonObject object) {
        final String serverName = object.get("server").getAsString();

        if (!DataPlugin.getInstance().getServerManager().existServer(serverName)) {
            new NetworkServer(serverName, NetworkServerType.NOT_DEFINED) {{
                setServerStatus(NetworkServerStatus.BOOTING);
                setWhitelistEnabled(false);
                setOnlinePlayers(0);
                setMaxPlayerLimit(0);
                setTicksPerSecond("&a0.0");
            }};
        }

        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.hasPermission(DataPlugin.getInstance().getConfig().getString("permissions.status-broadcasts")))
                .forEach(player -> player.sendMessage(ColorUtil.translate(DataPlugin.getInstance().getConfig().getString("messages.online-broadcast").replace("<server>", serverName))));

        if (DataPlugin.getInstance().getConfig().getBoolean("debug")) {
            DataPlugin.getInstance().getLogger().info("[DEBUG] Message received of " + serverName + " being booted.");
        }

    }

    @Override
    public String getChannel() {
        return "SERVER_DATA_ONLINE";
    }
}