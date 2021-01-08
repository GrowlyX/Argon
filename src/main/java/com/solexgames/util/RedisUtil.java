package com.solexgames.util;

import com.solexgames.DataPlugin;
import com.solexgames.packet.DataPacket;
import com.solexgames.redis.RedisMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class RedisUtil {

    private static String format(double tps) {
        return ( ( tps > 18.0 ) ? ChatColor.GREEN : ( tps > 16.0 ) ? ChatColor.YELLOW : ChatColor.RED ).toString()
                + ( ( tps > 20.0 ) ? "*" : "" ) + Math.min( Math.round( tps * 100.0 ) / 100.0, 20.0 );
    }

    public static String getTicksPerSecond() {
        double[] tps = Bukkit.spigot().getTPS();
        String[] tpsAvg = new String[tps.length];

        for (int i = 0; i < tps.length; i++) {
            tpsAvg[i] = format(tps[i]);
        }

        return StringUtils.join(tpsAvg, ChatColor.GRAY +  ", " + ChatColor.GREEN);
    }

    public static String onServerUpdate() {
        return new RedisMessage(DataPacket.SERVER_DATA_UPDATE)
                .setParam("SERVER", DataPlugin.getInstance().getConfig().getString("server-id"))
                .setParam("SERVER_TYPE", DataPlugin.getInstance().getConfig().getString("server-type"))
                .setParam("ONLINEPLAYERS", String.valueOf(Bukkit.getOnlinePlayers().size()))
                .setParam("MAXPLAYERS", String.valueOf(Bukkit.getMaxPlayers()))
                .setParam("WHITELIST", String.valueOf(Bukkit.getServer().hasWhitelist()))
                .setParam("TPS", getTicksPerSecond())
                .toJson();
    }

    public static String onServerOffline(){
        return new RedisMessage(DataPacket.SERVER_DATA_OFFLINE)
                .setParam("SERVER", DataPlugin.getInstance().getConfig().getString("server-id"))
                .toJson();
    }
}
