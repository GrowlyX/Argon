package com.solexgames.network;

import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public enum NetworkServerType {

    PRACTICE("Practice"),
    HARDCORE_FACTIONS("HCF"),
    KITPVP("KitPvP"),
    KITMAP("KitMap"),
    MEETUP("UHC Meetup"),
    UHC_GAMES("UHC Games"),
    UHC("UHC");

    public final String serverTypeString;

    @ConstructorProperties("serverTypeString")
    NetworkServerType(String serverTypeString) {
        this.serverTypeString = serverTypeString;
    }
}
