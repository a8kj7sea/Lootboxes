package me.a8kj.lootbox.parent.entity.lootbox.behavior;

import org.bukkit.Location;

import me.a8kj.lootbox.parent.entity.lootbox.Lootbox;

public interface SpawnableLootbox {
    void onSpawn(Lootbox lootbox, Location location);

    void onDespawn(Lootbox lootbox, Location location);
}
