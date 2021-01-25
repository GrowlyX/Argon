package com.solexgames.packet;

import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public enum DataPacket {

    SERVER_DATA_COMMAND,
    SERVER_DATA_ONLINE,
    SERVER_DATA_UPDATE,
    SERVER_DATA_OFFLINE

}
