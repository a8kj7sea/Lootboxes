package me.a8kj.lootbox.internal.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import lombok.Getter;
import me.a8kj.config.ConfigFile;
import me.a8kj.lootbox.internal.manager.LocationManagerImpl;
import me.a8kj.lootbox.internal.manager.LootboxStateManagerImpl;
import me.a8kj.lootbox.parent.entity.lootbox.Lootbox;
import me.a8kj.lootbox.parent.manager.LocationManager;
import me.a8kj.lootbox.parent.manager.LootboxStateManager;
import me.a8kj.lootbox.parent.plugin.LootboxFacade;
import me.a8kj.lootbox.parent.registry.BukkitTaskRegistry;
import me.a8kj.lootbox.parent.registry.LootboxRegistry;
import me.a8kj.lootbox.parent.structure.Registry;

@Getter
public class LootboxPlugin extends JavaPlugin implements LootboxFacade {

    @Getter
    private static LootboxPlugin instance;
    private ConfigFile jsonConfig;

    private final Registry<BukkitTask> tasksRegistry = new BukkitTaskRegistry();

    private final LootboxStateManager lootboxStateManager = new LootboxStateManagerImpl();

    private final LocationManager locationManager = new LocationManagerImpl();

    private final Registry<Lootbox> lootboxRegistry = new LootboxRegistry();

    @Override
    public void onEnable() {

    }

    

}
