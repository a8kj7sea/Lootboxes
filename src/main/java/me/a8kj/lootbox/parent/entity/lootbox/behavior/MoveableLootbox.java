package me.a8kj.lootbox.parent.entity.lootbox.behavior;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface MoveableLootbox {

    boolean move(Location location);

    boolean move(Entity entity);
}
