package com.solexgames.util;

// shoutout to LazyLemons on bukkit.org for utility class
public class TPSRunnable implements Runnable {

    public int TICK_COUNT = 0;
    public long[] TICKS = new long[600];

    /**
     * Get the ticks per second of the server
     * Calls TPSRunnable#getTPS(..) with 20 as default parameter
     *
     * @return the current ticks per second of the server
     */
    public double getTPS() {
        return getTPS(20);
    }

    /**
     * Get the ticks per second of the server
     *
     * @param ticks the amount of ticks to check for
     * @return the current ticks per second of the server
     */
    public double getTPS(int ticks) {
        if (TICK_COUNT < ticks) {
            return 20.0D;
        }

        final int target = (TICK_COUNT - 1 - ticks) % TICKS.length;
        final long elapsed = System.currentTimeMillis() - TICKS[target];

        return ticks / (elapsed / 1000.0D);
    }

    @Override
    public void run() {
        TICKS[(TICK_COUNT % TICKS.length)] = System.currentTimeMillis();
        TICK_COUNT += 1;
    }
}