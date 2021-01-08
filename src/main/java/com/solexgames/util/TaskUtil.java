package com.solexgames.util;

import com.solexgames.DataPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskUtil {

    public static void run(Runnable runnable) {
        DataPlugin.getInstance().getServer().getScheduler().runTask(DataPlugin.getInstance(), runnable);
    }

    public static void runTimer(Runnable runnable, long delay, long timer) {
        DataPlugin.getInstance().getServer().getScheduler().runTaskTimer(DataPlugin.getInstance(), runnable, delay, timer);
    }

    public static void runTimer(BukkitRunnable runnable, long delay, long timer) {
        runnable.runTaskTimer(DataPlugin.getInstance(), delay, timer);
    }

    public static void runTimerAsync(BukkitRunnable runnable, long delay, long timer) {
        runnable.runTaskTimerAsynchronously(DataPlugin.getInstance(), delay, timer);
    }

    public static void runLater(Runnable runnable, long delay) {
        DataPlugin.getInstance().getServer().getScheduler().runTaskLater(DataPlugin.getInstance(), runnable, delay);
    }

    public static void runLaterAsync(Runnable runnable, long delay) {
        DataPlugin.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(DataPlugin.getInstance(), runnable, delay);
    }

    public static void runAsync(Runnable runnable) {
        DataPlugin.getInstance().getServer().getScheduler().runTaskAsynchronously(DataPlugin.getInstance(), runnable);
    }
}
