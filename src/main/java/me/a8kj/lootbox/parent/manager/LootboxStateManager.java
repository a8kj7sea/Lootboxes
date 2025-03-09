package me.a8kj.lootbox.parent.manager;

import java.util.Map;
import java.util.Set;
import org.bukkit.Location;
import me.a8kj.lootbox.parent.entity.lootbox.enums.LootboxState;

public interface LootboxStateManager {

    void addLootbox(LootboxState state, Location location);

    void removeLootbox(LootboxState state);

    boolean hasLootbox(LootboxState state);

    Set<Location> getLootboxLocations(LootboxState state);

    void updateLootbox(LootboxState state, Set<Location> newLocations);

    Map<LootboxState, Set<Location>> getAllLootboxes();
}
