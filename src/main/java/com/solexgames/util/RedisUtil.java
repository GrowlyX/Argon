package com.solexgames.util;

import com.solexgames.DataPlugin;
import com.solexgames.packet.DataPacket;
import com.solexgames.redis.RedisMessage;
import org.bukkit.Bukkit;

public class RedisUtil {

    public static String onServerUpdate() {
        return new RedisMessage(DataPacket.SERVER_DATA_UPDATE)
                .setParam("PLAYERS", String.valueOf(Bukkit.getOnlinePlayers().size()))
                .toJson();
    }

    public static String onServerOffline(){
        return new RedisMessage(DataPacket.SERVER_DATA_OFFLINE)
                .toJson();
    }
}
