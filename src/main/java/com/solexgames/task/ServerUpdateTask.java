package com.solexgames.task;

import com.solexgames.DataPlugin;
import com.solexgames.util.RedisUtil;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServerUpdateTask extends BukkitRunnable {

    public ServerUpdateTask() {
        this.runTaskTimer(DataPlugin.getInstance(), 0,DataPlugin.getInstance().getConfig().getInt("refresh-time") * 20L);
    }

    @Override
    public void run() {
        Executor executor = Executors.newFixedThreadPool(1);
        executor.execute(() -> DataPlugin.getInstance().getRedisClient().write(RedisUtil.onServerUpdate()));
    }
}
