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
    private NetworkServerStatus serverStatus;

    private int maxPlayerLimit;
    private int onlinePlayers;

    private boolean whitelistEnabled;
    private boolean online;

    /**
     * Constructor for making a new {@link NetworkServer} object
     *
     * @param serverName the name of the server
     * @param serverType the type of the server
     */
    public NetworkServer(String serverName, NetworkServerType serverType) {
        this.serverName = serverName;
        this.serverType = serverType;

        DataPlugin.getInstance().getServerManager().getNetworkServers().add(this);
    }

    /**
     * Find a {@link NetworkServer} by a name
     *
     * @param name the name of the server
     * @return the server
     */
    public static NetworkServer getByName(String name) {
        return DataPlugin.getInstance().getServerManager().getNetworkServers().stream()
                .filter(masters -> masters.getServerName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    /**
     * Set the online state of the server
     *
     * @param online the online state of the server
     */
    public void setOnline(boolean online) {
        this.online = online;
        this.updateServerStatus();
    }

    /**
     * Set the state of the whitelist of the server
     *
     * @param enabled the state of the whitelist
     */
    public void setWhitelistEnabled(boolean enabled) {
        this.whitelistEnabled = enabled;
        this.updateServerStatus();
    }

    /**
     * Update the status of the server
     */
    public void updateServerStatus() {
        this.serverStatus = online
                ? (whitelistEnabled ? NetworkServerStatus.WHITELISTED : NetworkServerStatus.ONLINE)
                : NetworkServerStatus.OFFLINE;
    }
}
