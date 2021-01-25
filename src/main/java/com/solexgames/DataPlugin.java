package com.solexgames;

import com.solexgames.command.CommandManager;
import com.solexgames.network.NetworkServerManager;
import com.solexgames.redis.RedisClient;
import com.solexgames.task.ServerUpdateTask;
import com.solexgames.util.RedisUtil;
import com.solexgames.util.TPSRunnable;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Getter
public final class DataPlugin extends JavaPlugin {

    @Getter
    private static DataPlugin instance;

    private final TPSRunnable tpsRunnable = new TPSRunnable();
    private final Executor redisThread = Executors.newFixedThreadPool(1);

    private NetworkServerManager serverManager;
    private CommandManager commandManager;

    private RedisClient redisClient;


    @Override
    public void onEnable() {
        instance = JavaPlugin.getPlugin(DataPlugin.class);

        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);

        // setup managers
        this.serverManager = new NetworkServerManager();
        this.commandManager = new CommandManager();

        // redis
        this.redisClient = new RedisClient();

        this.startTasks();
        this.getRedisThread().execute(() -> DataPlugin.getInstance().getRedisClient().write(RedisUtil.getServerOnlineMessage().toString()));

        if (DataPlugin.getInstance().getConfig().getBoolean("debug")) {
            DataPlugin.getInstance().getLogger().info("[DEBUG] Sent server online message to all connected servers.");
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelAllTasks();
        DataPlugin.getInstance().getRedisClient().write(RedisUtil.getServerOfflineMessage().toString());

        this.redisClient.destroyClient();
    }

    /**
     * Start required tasks for Argon
     */
    private void startTasks() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TPSRunnable(), 0L, 1L);
        new ServerUpdateTask();
    }
}