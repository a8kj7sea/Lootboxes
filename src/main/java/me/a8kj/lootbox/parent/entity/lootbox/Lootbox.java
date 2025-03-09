package me.a8kj.lootbox.parent.entity.lootbox;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

import me.a8kj.lootbox.parent.entity.lootbox.properties.Container;
import me.a8kj.lootbox.parent.entity.lootbox.properties.Settings;

public interface Lootbox {

    default UUID getUniqueId() {
        return getEntity().getUniqueId();
    }

    public enum CollisionState {
        COLLISIONABLE, NON_COLLISIONABLE;
    }

    void setMetadata(String metadata);

    String getMetadata();

    default CollisionState getCollisionState() {
        return getMetadata().equalsIgnoreCase("COLLISIONABLE") ? CollisionState.COLLISIONABLE
                : CollisionState.NON_COLLISIONABLE;
    }

    Container getContainer();

    Settings getSettings();

    ArmorStand getEntity();

    String getName();

    float getSpawnRate();

    void onSpawn(Lootbox lootbox , Location location);

    void onDespawn(Lootbox lootbox, Location location);

    boolean spawn(Location location);

    boolean despawn();

    boolean move(Location location);

    boolean move(Entity entity);

}
