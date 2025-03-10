package me.a8kj.lootbox.internal.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class StringUtils {

    @NonNull
    public static String[] format(String... strings) {
        return Arrays.stream(strings).map(StringUtils::format).toArray(String[]::new);
    }

    @NonNull
    public static String format(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    @NonNull
    public static List<String> format(List<String> strings) {
        return strings.stream().map(StringUtils::format).collect(Collectors.toList());
    }
}
