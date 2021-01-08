package com.solexgames.network;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NetworkServerManager {

    private List<NetworkServer> networkServers = new ArrayList<>();

    public void removeEventServer(NetworkServer networkServer){
        networkServers.remove(networkServer);
    }

    public boolean existServer(String networkServer){
        return networkServers.contains(NetworkServer.getByName(networkServer));
    }
}
