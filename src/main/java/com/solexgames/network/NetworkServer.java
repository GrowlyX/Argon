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
        setupServerType();
        DataPlugin.getInstance().getServerManager().getNetworkServers().add(this);
    }

    private void setupServerType() {
        if (serverName.contains("hcf") || serverName.contains("infernal") || serverName.contains("horshcf")) {
            this.serverType = NetworkServerType.HARDCORE_FACTIONS;
        }
        if (serverName.contains("bw") || serverName.contains("bedwars") || serverName.contains("horswaresz")) {
            this.serverType = NetworkServerType.BEDWARS;
        }
        if (serverName.contains("sw") || serverName.contains("skywars") || serverName.contains("skyhors")) {
            this.serverType = NetworkServerType.SKYWARS;
        }
        if (serverName.contains("kp") || serverName.contains("kitpvp") || serverName.contains("kithorspvp")) {
            this.serverType = NetworkServerType.KITMAP;
        }
        if (serverName.contains("uhc") || serverName.contains("nauhc") || serverName.contains("horsuhc")) {
            this.serverType = NetworkServerType.UHC;
        }
        if (serverName.contains("uhcg") || serverName.contains("nauhcg") || serverName.contains("horsuhcgae-ms")) {
            this.serverType = NetworkServerType.UHC_GAMES;
        }
        if (serverName.contains("horse-racing") || serverName.contains("hr") || serverName.contains("HORSEGAMESS")) {
            this.serverType = NetworkServerType.HORSE_RACE;
        }
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
