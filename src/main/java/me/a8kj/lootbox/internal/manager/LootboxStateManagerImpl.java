package me.a8kj.lootbox.internal.manager;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.bukkit.Location;
import me.a8kj.lootbox.parent.entity.lootbox.enums.LootboxState;
import me.a8kj.lootbox.parent.manager.LootboxStateManager;

import java.util.Map;
import java.util.Set;

public class LootboxStateManagerImpl implements LootboxStateManager {

    private final Map<LootboxState, Set<Location>> lootboxesByState = Maps.newConcurrentMap();

    @Override
    public void addLootbox(LootboxState state, Location location) {
        lootboxesByState.computeIfAbsent(state, k -> Sets.newHashSet()).add(location);
    }

    @Override
    public void removeLootbox(LootboxState state) {
        lootboxesByState.remove(state);
    }

    @Override
    public boolean hasLootbox(LootboxState state) {
        return lootboxesByState.containsKey(state);
    }

    @Override
    public Set<Location> getLootboxLocations(LootboxState state) {
        return lootboxesByState.get(state);
    }

    @Override
    public void updateLootbox(LootboxState state, Set<Location> newLocations) {
        lootboxesByState.put(state, newLocations);
    }

    @Override
    public Map<LootboxState, Set<Location>> getAllLootboxes() {
        return lootboxesByState;
    }
}
