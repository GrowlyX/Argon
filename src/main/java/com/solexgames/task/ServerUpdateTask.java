package com.solexgames.task;

import com.solexgames.DataPlugin;
import com.solexgames.util.RedisUtil;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServerUpdateTask extends BukkitRunnable {
    
    public Executor executor;

    public ServerUpdateTask() {
        this.runTaskTimer(DataPlugin.getInstance(), 0,DataPlugin.getInstance().getConfig().getInt("refresh-time") * 20L);
        this.executor = Executors.newFixedThreadPool(1);
    }

    @Override
    public void run() {
        executor.execute(() -> DataPlugin.getInstance().getRedisClient().write(RedisUtil.onServerUpdate()));
    }
}
