package me.a8kj.lootbox.internal.util;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class StringUtils {

    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
