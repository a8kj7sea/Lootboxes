package me.a8kj.lootbox.parent.entity.lootbox;

import lombok.Getter;
import lombok.Setter;
import me.a8kj.config.ConfigFile;
import me.a8kj.lootbox.api.event.lootbox.LootboxDespawnEvent;
import me.a8kj.lootbox.api.event.lootbox.LootboxSpawnEvent;
import me.a8kj.lootbox.internal.plugin.LootboxPlugin;
import me.a8kj.lootbox.internal.util.StringUtils;
import me.a8kj.lootbox.parent.entity.lootbox.behavior.MoveableLootbox;
import me.a8kj.lootbox.parent.entity.lootbox.behavior.SpawnableLootbox;
import me.a8kj.lootbox.parent.entity.lootbox.enums.LootboxState;
import me.a8kj.lootbox.parent.entity.lootbox.properties.Container;
import me.a8kj.lootbox.parent.entity.lootbox.properties.Settings;
import me.a8kj.lootbox.parent.plugin.LootboxFacade;
import me.a8kj.lootbox.parent.task.HeadPoseMovementTask;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class LootboxBase implements Lootbox, SpawnableLootbox, MoveableLootbox {

    private final String name;
    private final Settings settings;

    private ArmorStand entity;
    private final float spawnRate;

    private String metadata;

    private final Container container;

    protected abstract LootboxFacade getFacade();

    @Override
    public boolean spawn(Location location) {
        if (entity != null) {
            Bukkit.getLogger().warning("Lootbox '" + name + "' is already spawned!");
            return false;
        }

        World world = location.getWorld();
        if (world == null) {
            Bukkit.getLogger().warning("World is null, cannot spawn Lootbox.");
            return false;
        }

        entity = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);
        entity.setCustomName(StringUtils.colorize(settings.displayName() == null ? name : settings.displayName()));
        entity.setCustomNameVisible(settings.nameVisible());
        entity.setGravity(settings.gravity());
        entity.setInvisible(!settings.visible());

        entity.getEquipment().setHelmet(settings.headItem());

        Bukkit.getLogger().info("Lootbox '" + name + "' spawned at: " + location);

        onSpawn(this, location);

        return true;
    }

    @Override
    public void setMetadata(String metadata) {
        if (metadata == null || metadata.isEmpty()) {
            Bukkit.getLogger().warning("Provided metadata is null or empty. Metadata not set.");
            return;
        }

        ConfigFile jsonConfig = LootboxPlugin.getInstance().getJsonConfig();
        jsonConfig.setValue(this.getUniqueId().toString(), metadata);

        try {
            jsonConfig.save();
            Bukkit.getLogger().info("Metadata for Lootbox '" + name + "' saved successfully.");
        } catch (IOException e) {
            Bukkit.getLogger().severe("Error saving metadata for Lootbox '" + name + "': " + e.getMessage());
        }
    }

    @Override
    public void onSpawn(Lootbox lootbox, Location location) {
        if (location == null || location.getWorld() == null) {
            Bukkit.getLogger().warning("Cannot spawn Lootbox at a null or invalid location.");
            return;
        }

        location.getWorld().spawnParticle(Particle.CLOUD, location, 30, 0.5, 1, 0.5, 0.1);
        getFacade().getTasksRegistry().register(lootbox.getUniqueId().toString(),
                new HeadPoseMovementTask(lootbox).runTaskTimer(getFacade(), 0, 18));

        getFacade().getLootboxStateManager().addLootbox(LootboxState.SPAWNED, location);

        Bukkit.getScheduler().runTask(getFacade(), () -> {
            new LootboxSpawnEvent(lootbox).callEvent();
        });
    }

    @Override
    public void onDespawn(Lootbox lootbox, Location location) {
        if (location == null || location.getWorld() == null) {
            Bukkit.getLogger().warning("Cannot despawn Lootbox at a null or invalid location.");
            return;
        }

        location.getWorld().spawnParticle(Particle.SMOKE, location, 30, 0.5, 1, 0.5, 0.1);
        getFacade().getTasksRegistry().unregister(lootbox.getUniqueId().toString());

        getFacade().getLootboxStateManager().removeLootboxByLocation(location);
        getFacade().getLootboxStateManager().addLootbox(LootboxState.EMPTY, location);

        Bukkit.getScheduler().runTask(getFacade(), () -> {
            new LootboxDespawnEvent(lootbox).callEvent();
        });
    }

    @Override
    public boolean despawn() {
        if (entity == null) {
            Bukkit.getLogger().warning("Lootbox '" + name + "' is not spawned!");
            return false;
        }

        Location location = entity.getLocation();
        entity.remove();
        entity = null;
        Bukkit.getLogger().info("Lootbox '" + name + "' despawned.");

        onDespawn(this, location);
        return true;
    }

    @Override
    public boolean move(Location location) {
        if (entity == null) {
            Bukkit.getLogger().warning("Lootbox '" + name + "' is not spawned, cannot move!");
            return false;
        }

        if (location == null || location.getWorld() == null) {
            Bukkit.getLogger().warning("Invalid location provided. Cannot move Lootbox.");
            return false;
        }

        entity.teleport(location);
        Bukkit.getLogger().info("Lootbox '" + name + "' moved to: " + location);
        return true;
    }

    @Override
    public boolean move(Entity targetEntity) {
        if (entity == null) {
            Bukkit.getLogger().warning("Lootbox '" + name + "' is not spawned, cannot move!");
            return false;
        }

        if (targetEntity == null) {
            Bukkit.getLogger().warning("Target entity is null, cannot move Lootbox.");
            return false;
        }

        entity.teleport(targetEntity.getLocation());
        Bukkit.getLogger().info("Lootbox '" + name + "' moved to entity: " + targetEntity.getName());
        return true;
    }

}
