package me.a8kj.lootbox.internal.plugin;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.google.common.collect.Sets;

import lombok.Getter;
import me.a8kj.config.ConfigFile;
import me.a8kj.config.impl.JsonConfigFile;
import me.a8kj.lootbox.internal.command.LootboxCommand;
import me.a8kj.lootbox.internal.entity.lootbox.normal.NormalLootbox;
import me.a8kj.lootbox.internal.entity.lootbox.rare.RareLootbox;
import me.a8kj.lootbox.internal.listener.DespawnLootboxListener;
import me.a8kj.lootbox.internal.listener.InventoryProtectionListener;
import me.a8kj.lootbox.internal.listener.PlayerInteractListener;
import me.a8kj.lootbox.internal.listener.PlayerMoveListener;
import me.a8kj.lootbox.internal.listener.SpawnLootboxListener;
import me.a8kj.lootbox.internal.manager.LocationManagerImpl;
import me.a8kj.lootbox.internal.manager.LootboxStateManagerImpl;
import me.a8kj.lootbox.internal.util.LootboxUtils;
import me.a8kj.lootbox.parent.entity.lootbox.Lootbox;
import me.a8kj.lootbox.parent.manager.LocationManager;
import me.a8kj.lootbox.parent.manager.LootboxStateManager;
import me.a8kj.lootbox.parent.plugin.LootboxFacade;
import me.a8kj.lootbox.parent.registry.BukkitTaskRegistry;
import me.a8kj.lootbox.parent.registry.LootboxRegistry;
import me.a8kj.lootbox.parent.structure.Registry;
import me.a8kj.lootbox.parent.task.SpawnLootboxTask;

@Getter
public class LootboxPlugin extends JavaPlugin implements LootboxFacade {

    private static LootboxPlugin instance;

    private ConfigFile lootBoxesConfig;
    private ConfigFile locationsConfig;
    private Registry<BukkitTask> tasksRegistry;
    private LootboxStateManager lootboxStateManager;
    private LocationManager locationManager;
    private Registry<Lootbox> lootboxRegistry;
    private Set<UUID> playersInRemoveMode;
    private Logger logger;

    private BukkitTask spawnTask;

    public static LootboxPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        initializeFields();
        setupPlugin();
        registerCommands();
        registerListeners();
        loadConfigurations();
        registerLootboxes();

        if (!locationManager.getLocations().isEmpty())
            registerLootboxSpawnTask();
        else
            logger.warning("Spawn lootboxes task cannot start , please add locations and restart server");
    }

    @Override
    public void onDisable() {
        shutdownPlugin();
    }

    private void initializeFields() {
        tasksRegistry = new BukkitTaskRegistry();
        lootboxStateManager = new LootboxStateManagerImpl();
        locationManager = new LocationManagerImpl();
        lootboxRegistry = new LootboxRegistry();
        playersInRemoveMode = Sets.newHashSet();
        logger = getLogger();
    }

    private void registerLootboxSpawnTask() {
        spawnTask = new SpawnLootboxTask(this).runTaskTimer(instance, 0, 20 * 30);
    }

    private void setupPlugin() {
        logger.info("Lootbox Plugin is starting...");
    }

    private void registerCommands() {
        getCommand("lootbox").setExecutor(new LootboxCommand(this));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryProtectionListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new SpawnLootboxListener(), this);
        getServer().getPluginManager().registerEvents(new DespawnLootboxListener(this), this);
    }

    private void registerLootboxes() {
        lootboxRegistry.register("normal", new NormalLootbox());
        lootboxRegistry.register("rare", new RareLootbox());
    }

    private void loadConfigurations() {
        try {
            lootBoxesConfig = new JsonConfigFile("boxes.json", getDataFolder().getPath());
            lootBoxesConfig.load();
            locationsConfig = new JsonConfigFile("locations.json", getDataFolder().getPath());
            locationsConfig.load();
            logger.info("Configs loaded successfully.");

            locationManager.loadLocations((JsonConfigFile) locationsConfig);
            logger.info("Locations loaded successfully.");
        } catch (IOException e) {
            logger.severe("Failed to load config files: " + e.getMessage());
        }
    }

    private void shutdownPlugin() {
        logger.info("Lootbox Plugin is shutting down...");

        saveConfigurations();
        cancelAllTasks();
        cleanupLootboxes();

    }

    private void saveConfigurations() {
        try {
            lootBoxesConfig.save();
            locationManager.saveLocations((JsonConfigFile) locationsConfig);
            logger.info("Configs saved successfully.");
        } catch (IOException e) {
            logger.severe("Failed to save config files: " + e.getMessage());
        }
    }

    private void cancelAllTasks() {
        logger.info("Cancelling all tasks...");

        if (spawnTask != null) {
            spawnTask.cancel();
            logger.info("Cancelled spawn lootboxes task!");
        }

        tasksRegistry.entries().forEach(task -> {
            task.right().cancel();
            logger.info("Cancelled task: " + task.right().getTaskId());
        });

        Bukkit.getScheduler().cancelTasks(this);
        logger.info("All tasks cancelled.");
    }

    private void cleanupLootboxes() {
        LootboxUtils.removeLootboxes(locationManager.getLocations(), lootboxRegistry, lootBoxesConfig);
        logger.info("All lootbox instances destroyed and armor stands removed.");
    }
}