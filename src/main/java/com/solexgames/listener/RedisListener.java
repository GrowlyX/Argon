package com.solexgames.listener;

import com.google.gson.Gson;
import com.solexgames.DataPlugin;
import com.solexgames.network.NetworkServer;
import com.solexgames.network.NetworkServerType;
import com.solexgames.redis.RedisMessage;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.JedisPubSub;

public class RedisListener extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        new BukkitRunnable() {
            @Override
            public void run() {
                RedisMessage redisMessage = new Gson().fromJson(message, RedisMessage.class);

                switch (redisMessage.getPacket()) {
                    case SERVER_DATA_UPDATE:
                        String serverName = redisMessage.getParam("SERVER");
                        String ticksPerSecond = redisMessage.getParam("TPS");

                        int maxPlayerLimit = Integer.parseInt(redisMessage.getParam("MAXPLAYERS"));
                        int onlinePlayers = Integer.parseInt(redisMessage.getParam("ONLINEPLAYERS"));

                        boolean whitelistEnabled = Boolean.parseBoolean(redisMessage.getParam("WHITELIST"));

                        if (!DataPlugin.getInstance().getServerManager().existServer(serverName)){
                            NetworkServer server = new NetworkServer(serverName);

                            server.setTicksPerSecond(ticksPerSecond);
                            server.setMaxPlayerLimit(maxPlayerLimit);
                            server.setOnlinePlayers(onlinePlayers);
                            server.setWhitelistEnabled(whitelistEnabled);
                        }

                        break;
                    case SERVER_DATA_OFFLINE:
                        break;
                    default:
                        DataPlugin.getInstance().getLogger().warning("[Redis] There was a response, but no message was received.");
                        break;
                }
            }
        }.runTaskAsynchronously(DataPlugin.getInstance());
    }
}
