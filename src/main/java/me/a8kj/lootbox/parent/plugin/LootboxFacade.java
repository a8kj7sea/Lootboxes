package me.a8kj.lootbox.parent.plugin;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import me.a8kj.config.ConfigFile;
import me.a8kj.lootbox.parent.entity.lootbox.Lootbox;
import me.a8kj.lootbox.parent.manager.LocationManager;
import me.a8kj.lootbox.parent.manager.LootboxStateManager;
import me.a8kj.lootbox.parent.structure.Registry;

public interface LootboxFacade extends Plugin {

    ConfigFile getJsonConfig();

    Registry<BukkitTask> getTasksRegistry();

    LootboxStateManager getLootboxStateManager();

    LocationManager getLocationManager();

    Registry<Lootbox> getLootboxRegistry();

}
