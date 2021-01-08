package com.solexgames;

import com.solexgames.command.CommandManager;
import com.solexgames.network.NetworkServerManager;
import com.solexgames.redis.RedisClient;
import com.solexgames.task.ServerUpdateTask;
import com.solexgames.util.RedisUtil;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class DataPlugin extends JavaPlugin {

    @Getter
    public static DataPlugin instance;

    public RedisClient redisClient;

    public NetworkServerManager serverManager;
    public CommandManager commandManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        this.redisClient = new RedisClient();
        this.serverManager = new NetworkServerManager();
        this.commandManager = new CommandManager();

        new ServerUpdateTask();
    }

    @Override
    public void onDisable() {
        DataPlugin.getInstance().getRedisClient().write(RedisUtil.onServerOffline());
        this.redisClient.destroyClient();
        instance = null;
    }
}
