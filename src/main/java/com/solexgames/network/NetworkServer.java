package com.solexgames.network;

import com.solexgames.DataPlugin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NetworkServer {

    private String serverName;
    private String ticksPerSecond;
    private NetworkServerType serverType;

    private int maxPlayerLimit;
    private int onlinePlayers;

    private boolean whitelistEnabled;

    public NetworkServer(String serverName) {
        this.serverName = serverName;
        DataPlugin.getInstance().getServerManager().getNetworkServers().add(this);
    }

    public static NetworkServer getByName(String name){
        return DataPlugin.getInstance().getServerManager().getNetworkServers().stream().filter(masters -> masters.getServerName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void update(int onlinePlayers, String ticksPerSecond, int maxPlayerLimit, boolean whitelistEnabled) {
        this.onlinePlayers = onlinePlayers;
        this.ticksPerSecond = ticksPerSecond;
        this.maxPlayerLimit = maxPlayerLimit;
        this.whitelistEnabled = whitelistEnabled;
    }
}
