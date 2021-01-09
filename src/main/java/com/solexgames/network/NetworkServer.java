package com.solexgames.network;

import com.solexgames.DataPlugin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NetworkServer {

    private String serverName;
    private String ticksPerSecond;
    private String ticksPerSecondSimplified;

    private NetworkServerType serverType;
    private NetworkServerStatus serverStatus;

    private int maxPlayerLimit;
    private int onlinePlayers;

    private boolean whitelistEnabled;

    public NetworkServer(String serverName, NetworkServerType serverType) {
        this.serverName = serverName;
        this.serverType = serverType;
        DataPlugin.getInstance().getServerManager().addNetworkServer(this);
    }

    public static NetworkServer getByName(String name){
        return DataPlugin.getInstance().getServerManager().getNetworkServers().stream().filter(masters -> masters.getServerName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void update(int onlinePlayers, String ticksPerSecond, int maxPlayerLimit, boolean whitelistEnabled, String ticksPerSecondSimplified, boolean online) {
        this.onlinePlayers = onlinePlayers;
        this.ticksPerSecond = ticksPerSecond;
        this.maxPlayerLimit = maxPlayerLimit;
        this.whitelistEnabled = whitelistEnabled;
        this.ticksPerSecondSimplified = ticksPerSecondSimplified;
        updateServerStatus(online, whitelistEnabled);
    }

    public void updateServerStatus(boolean online, boolean whitelistEnabled) {
        if (whitelistEnabled && online) {
            this.serverStatus = NetworkServerStatus.WHITELISTED;
        } else if (online) {
            this.serverStatus = NetworkServerStatus.ONLINE;
        } else {
            this.serverStatus = NetworkServerStatus.OFFLINE;
        }
    }
}
