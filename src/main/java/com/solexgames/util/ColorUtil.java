package com.solexgames.util;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public final class ColorUtil {

    /**
     * Translate a {@link String} using ChatColor#translateAlternateColorCodes
     *
     * @param text the text to translate
     * @return the translated text
     */
    public static String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Translate a {@link List} of strings using ChatColor#translateAlternateColorCodes
     *
     * @param text the text to translate
     * @return the translated text
     */
    public static List<String> translate(List<String> text) {
        return text.stream()
                .map(ColorUtil::translate)
                .collect(Collectors.toList());
    }
}
