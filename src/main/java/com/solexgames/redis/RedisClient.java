package com.solexgames.redis;

import com.solexgames.DataPlugin;
import com.solexgames.listener.RedisListener;
import lombok.Getter;
import lombok.Setter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Getter
@Setter
public class RedisClient {

    private JedisPool jedisPool;

    public RedisClient() {
        this.jedisPool = new JedisPool(DataPlugin.getInstance().getConfig().getString("redis.address"), DataPlugin.getInstance().getConfig().getInt("redis.port"));
        Jedis jedis = this.jedisPool.getResource();

        if (DataPlugin.getInstance().getConfig().getBoolean("redis.authentication")) {
            jedis.auth(DataPlugin.getInstance().getConfig().getString("redis.password"));
        }

        new Thread(() -> jedis.subscribe(new RedisListener(), "ARGON-DATA")).start();

        DataPlugin.getInstance().getLogger().info("[Redis] Connected to Redis backend.");
    }

    public void destroyClient() {
        jedisPool.destroy();
    }

    public void write(String json) {
        try (Jedis jedis = this.jedisPool.getResource()) {

            if (DataPlugin.getInstance().getConfig().getBoolean("redis.authentication")) {
                jedis.auth(DataPlugin.getInstance().getConfig().getString("redis.password"));
            }

            jedis.publish("ARGON-DATA", json);
        }
    }
}