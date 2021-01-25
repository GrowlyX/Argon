package com.solexgames.util;

import com.google.gson.JsonObject;
import com.solexgames.DataPlugin;
import com.solexgames.packet.DataPacket;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class RedisUtil {

    /**
     * Get the ticks per second of the server
     *
     * @return the formatted string of the tps
     */
    public static String getTicksPerSecondFormatted() {
        return ChatColor.GREEN + String.format("%.2f", Math.min(DataPlugin.getInstance().getTpsRunnable().getTPS(), 20.0));
    }

    /**
     * Get the update message in the format of a {@link JsonObject}
     *
     * @return the object
     */
    public static JsonObject getServerUpdateMessage() {
        return new JsonAppender()
                .append("packet", DataPacket.SERVER_DATA_UPDATE.name())
                .append("server", DataPlugin.getInstance().getConfig().getString("server-id"))
                .append("server_type", DataPlugin.getInstance().getConfig().getString("server-type"))
                .append("online_players", Bukkit.getOnlinePlayers().size())
                .append("max_players", Bukkit.getMaxPlayers())
                .append("whitelist", Bukkit.getServer().hasWhitelist())
                .append("tps", getTicksPerSecondFormatted()).get();
    }

    /**
     * Get the offline message in the format of a {@link JsonObject}
     *
     * @return the object
     */
    public static JsonObject getServerOfflineMessage() {
        return new JsonAppender()
                .append("packet", DataPacket.SERVER_DATA_OFFLINE.name())
                .append("server_type", DataPlugin.getInstance().getConfig().getString("server-type"))
                .append("server", DataPlugin.getInstance().getConfig().getString("server-id")).get();
    }

    /**
     * Get the online message in the format of a {@link JsonObject}
     *
     * @return the object
     */
    public static JsonObject getServerOnlineMessage() {
        return new JsonAppender()
                .append("packet", DataPacket.SERVER_DATA_ONLINE.name())
                .append("server_type", DataPlugin.getInstance().getConfig().getString("server-type"))
                .append("server", DataPlugin.getInstance().getConfig().getString("server-id")).get();
    }
}