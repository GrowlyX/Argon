package com.solexgames.listener;

import com.google.gson.Gson;
import com.solexgames.DataPlugin;
import com.solexgames.network.NetworkServer;
import com.solexgames.network.NetworkServerStatus;
import com.solexgames.network.NetworkServerType;
import com.solexgames.redis.RedisMessage;
import com.solexgames.util.ColorUtil;
import org.bukkit.Bukkit;
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
                    case SERVER_DATA_ONLINE:
                        String bootingServerName = redisMessage.getParam("SERVER");

                        if (!DataPlugin.getInstance().getServerManager().existServer(bootingServerName)){
                            NetworkServer server = new NetworkServer(bootingServerName, NetworkServerType.valueOf(bootingServerName));

                            server.setServerStatus(NetworkServerStatus.BOOTING);
                            server.setWhitelistEnabled(false);
                            server.setOnlinePlayers(0);
                            server.setMaxPlayerLimit(0);
                            server.setTicksPerSecond("&a0.0&7, &a0.0&7, &a0.0");
                            server.setServerType(NetworkServerType.NOT_DEFINED);
                        }

                        Bukkit.getOnlinePlayers().forEach(player -> {
                            if (player.hasPermission(DataPlugin.getInstance().getConfig().getString("permissions.status-broadcasts"))) {
                                player.sendMessage(ColorUtil.translate(DataPlugin.getInstance().getConfig().getString("messages.online-broadcast").replace("<server>", bootingServerName)));
                            }
                        });
                        break;
                    case SERVER_DATA_UPDATE:
                        String serverName = redisMessage.getParam("SERVER");
                        String serverType = redisMessage.getParam("SERVER_TYPE");
                        String ticksPerSecond = redisMessage.getParam("TPS");

                        int maxPlayerLimit = Integer.parseInt(redisMessage.getParam("MAXPLAYERS"));
                        int onlinePlayers = Integer.parseInt(redisMessage.getParam("ONLINEPLAYERS"));

                        boolean whitelistEnabled = Boolean.parseBoolean(redisMessage.getParam("WHITELIST"));

                        if (!DataPlugin.getInstance().getServerManager().existServer(serverName)){
                            NetworkServer server = new NetworkServer(serverName, NetworkServerType.valueOf(serverType));

                            server.setTicksPerSecond(ticksPerSecond);
                            server.setMaxPlayerLimit(maxPlayerLimit);
                            server.setOnlinePlayers(onlinePlayers);
                            server.setWhitelistEnabled(whitelistEnabled);
                            server.updateServerStatus(true, whitelistEnabled);
                        }
                        NetworkServer.getByName(serverName).update(onlinePlayers, ticksPerSecond, maxPlayerLimit, whitelistEnabled, true);
                        if (DataPlugin.getInstance().getConfig().getBoolean("debug")) {
                            DataPlugin.getInstance().getLogger().info("[DEBUG] Message received of " + serverName + " being updated.");
                        }
                        break;
                    case SERVER_DATA_OFFLINE:
                        String offlineServerName = redisMessage.getParam("SERVER");

                        if (NetworkServer.getByName(offlineServerName) != null) {
                            NetworkServer.getByName(offlineServerName).update(0, "0.0", 100, false, false);
                            DataPlugin.getInstance().getServerManager().removeNetworkServer(NetworkServer.getByName(offlineServerName));
                        }

                        Bukkit.getOnlinePlayers().forEach(player -> {
                            if (player.hasPermission(DataPlugin.getInstance().getConfig().getString("permissions.status-broadcasts"))) {
                                player.sendMessage(ColorUtil.translate(DataPlugin.getInstance().getConfig().getString("messages.offline-broadcast").replace("<server>", offlineServerName)));
                            }
                        });

                        if (DataPlugin.getInstance().getConfig().getBoolean("debug")) {
                            DataPlugin.getInstance().getLogger().info("[DEBUG] Message received of " + offlineServerName + " going offline.");
                        }
                        break;
                    default:
                        if (DataPlugin.getInstance().getConfig().getBoolean("debug")) {
                            DataPlugin.getInstance().getLogger().info("[DEBUG] There was a message received but no response.");
                        }
                        break;
                }
            }
        }.runTaskAsynchronously(DataPlugin.getInstance());
    }
}
