package me.a8kj.lootbox.parent.entity.lootbox;

import lombok.Getter;
import lombok.Setter;
import me.a8kj.config.ConfigFile;
import me.a8kj.lootbox.internal.plugin.LootboxPlugin;
import me.a8kj.lootbox.parent.entity.lootbox.properties.Container;
import me.a8kj.lootbox.parent.entity.lootbox.properties.Settings;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class LootboxBase implements Lootbox {

    private final String name;
    private final Settings settings;

    private ArmorStand entity;
    private final float spawnRate;

    private String metadata;

    private final Container container;

    @Override
    public boolean spawn(Location location) {
        if (entity != null) {
            Bukkit.getLogger().warning("Lootbox '" + name + "' is already spawned!");
            return false;
        }

        World world = location.getWorld();
        if (world == null)
            return false;

        entity = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);
        entity.setCustomName(name);
        entity.setCustomNameVisible(settings.nameVisible());
        entity.setGravity(settings.gravity());
        entity.setInvisible(!settings.visible());

        entity.getEquipment().setHelmet(settings.headItem());

        Bukkit.getLogger().info("Lootbox '" + name + "' spawned at: " + location);

        onSpawn(this);

        return true;
    }

    @Override
    public void setMetadata(String metadata) {
        ConfigFile jsonConfig = LootboxPlugin.getInstance().getJsonConfig();
        jsonConfig.setValue(this.getUniqueId().toString(), metadata);
        try {
            jsonConfig.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean despawn() {
        if (entity == null) {
            Bukkit.getLogger().warning("Lootbox '" + name + "' is not spawned!");
            return false;
        }

        entity.remove();
        entity = null;
        Bukkit.getLogger().info("Lootbox '" + name + "' despawned.");

        onDespawn(this);
        return true;
    }

    @Override
    public boolean move(Location location) {
        if (entity == null) {
            Bukkit.getLogger().warning("Lootbox '" + name + "' is not spawned, cannot move!");
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

        entity.teleport(targetEntity.getLocation());
        Bukkit.getLogger().info("Lootbox '" + name + "' moved to entity: " + targetEntity.getName());
        return true;
    }

}
