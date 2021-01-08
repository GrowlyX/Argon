package com.solexgames.redis;

import com.google.gson.Gson;
import com.solexgames.packet.DataPacket;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RedisMessage {

    private final DataPacket packet;
    private final Map<String, String> params;

    public RedisMessage(DataPacket packet) {
        this.packet = packet;
        this.params = new HashMap<>();
    }

    public RedisMessage setParam(String key, String value) {
        this.params.put(key, value);
        return this;
    }

    public String getPacketString() {
        return this.packet.packetDataName;
    }

    public String getParam(String key) {
        if (containsParam(key)) {
            return this.params.get(key);
        }
        return null;
    }

    public boolean containsParam(String key) {
        return this.params.containsKey(key);
    }

    public void removeParam(String key) {
        if (containsParam(key)) {
            this.params.remove(key);
        }
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
