package com.solexgames.util;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public final class ColorUtil {

    public static String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> translate(List<String> text) {
        return text.stream().map(ColorUtil::translate).collect(Collectors.toList());
    }
}
