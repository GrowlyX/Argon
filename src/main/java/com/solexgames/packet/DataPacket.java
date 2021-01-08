package com.solexgames.packet;

import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public enum DataPacket {

    SERVER_DATA_UPDATE("ServerDataUpdate"),
    SERVER_DATA_OFFLINE("ServerDataOffline");

    public String packetDataName;

    @ConstructorProperties("packetDataName")
    DataPacket(String packetDataName) {
        this.packetDataName = packetDataName;
    }
}
