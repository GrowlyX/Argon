package com.solexgames;

import com.solexgames.network.NetworkServerManager;
import com.solexgames.redis.RedisClient;
import com.solexgames.task.ServerUpdateTask;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class DataPlugin extends JavaPlugin {

    @Getter
    public static DataPlugin instance;

    public RedisClient redisClient;

    public NetworkServerManager serverManager;

    @Override
    public void onEnable() {
        instance = this;

        this.redisClient = new RedisClient();
        this.serverManager = new NetworkServerManager();

        new ServerUpdateTask();
    }

    @Override
    public void onDisable() {
        this.redisClient.destroyClient();
        instance = null;
    }
}
