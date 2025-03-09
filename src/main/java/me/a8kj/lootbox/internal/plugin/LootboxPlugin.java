package me.a8kj.lootbox.internal.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import me.a8kj.config.ConfigFile;

@Getter
public class LootboxPlugin extends JavaPlugin {

    @Getter
    private static LootboxPlugin instance;
    private ConfigFile jsonConfig;

    @Override
    public void onEnable() {
        

    }
}
