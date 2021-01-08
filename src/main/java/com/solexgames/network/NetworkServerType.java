package com.solexgames.network;

import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public enum NetworkServerType {

    PRACTICE("Practice"),
    KITPVP("KitPvP"),
    HARDCORE_FACTIONS("HCF"),
    KITMAP("KitMap"),
    SKYWARS("SkyWars"),
    BEDWARS("BedWars"),
    MEETUP("UHC Meetup"),
    UHC_GAMES("UHC Games"),
    UHC("UHC"),
    HORSE_RACE("Horse Racing"),
    NOT_DEFINED("Booting..."),
    POTSG("PotSG");

    public final String serverTypeString;

    @ConstructorProperties("serverTypeString")
    NetworkServerType(String serverTypeString) {
        this.serverTypeString = serverTypeString;
    }
}
