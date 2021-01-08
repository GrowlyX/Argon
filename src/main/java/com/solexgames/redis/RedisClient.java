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
    private RedisListener redisListener;

    private final String redisAddress;
    private final String redisPassword;
    private final int redisPort;

    private boolean redisAuthentication;
    private boolean isClientActive;

    public RedisClient() {
        this.redisAddress = DataPlugin.getInstance().getConfig().getString("redis.address");
        this.redisPassword = DataPlugin.getInstance().getConfig().getString("redis.password");
        this.redisPort = DataPlugin.getInstance().getConfig().getInt("redis.port");
        this.redisAuthentication = DataPlugin.getInstance().getConfig().getBoolean("redis.authentication");

        try {
            this.jedisPool = new JedisPool(redisAddress, redisPort);
            Jedis jedis = this.jedisPool.getResource();

            if (redisAuthentication){
                jedis.auth(this.redisPassword);
            }
            (new Thread(() -> jedis.subscribe(this.redisListener, "ARGON-DATA"))).start();
            jedis.connect();
            this.setClientActive(true);
            DataPlugin.getInstance().getLogger().info("[Redis] Connected to Redis backend.");
        } catch (Exception e) {
            DataPlugin.getInstance().getLogger().severe("[Redis] Could not connect to Redis backend.");
            this.setClientActive(false);
        }
    }

    public void destroyClient() {
        try {
            jedisPool.destroy();
            this.redisListener.unsubscribe();
        } catch (Exception e) {
            System.out.println("[Redis] Could not destroy Redis Pool.");
        }
    }

    public void write(String json){
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.auth(this.redisPassword);
            jedis.publish("ARGON-DATA", json);
        }
    }
}
