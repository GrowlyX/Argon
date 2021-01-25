package com.solexgames.network;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class NetworkServerManager {

    private final List<NetworkServer> networkServers = new ArrayList<>();

    /**
     * Find a {@link NetworkServer} by a {@link String}
     *
     * @param name the name of the server
     * @return the optional of the server
     */
    public Optional<NetworkServer> find(String name) {
        return this.networkServers.stream()
                .filter(server -> server.getServerName().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     * Check if a {@link NetworkServer} exists by a name
     *
     * @param networkServer the name of the server
     * @return whether it exists or not
     */
    public boolean existServer(String networkServer){
        return networkServers.contains(NetworkServer.getByName(networkServer));
    }
}
