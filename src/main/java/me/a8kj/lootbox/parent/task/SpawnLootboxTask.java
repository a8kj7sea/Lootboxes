package me.a8kj.lootbox.parent.task;

import java.util.Set;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.lootbox.internal.util.LootboxUtils;
import me.a8kj.lootbox.parent.entity.lootbox.Lootbox;
import me.a8kj.lootbox.parent.entity.lootbox.enums.LootboxState;
import me.a8kj.lootbox.parent.plugin.LootboxFacade;

@RequiredArgsConstructor
@Getter
public class SpawnLootboxTask extends BukkitRunnable {

    private final LootboxFacade facade;

    @Override
    public void run() {
        if (!facade.getLocationManager().getLocations().isEmpty()) {

            Set<Location> emptyLocations = facade.getLootboxStateManager().getLootboxLocations(LootboxState.EMPTY);

            for (Location location : emptyLocations) {

                boolean hasLootbox = location.getWorld().getNearbyEntities(location, 1.5, 1.5, 1.5).stream()
                        .filter(entity -> entity instanceof ArmorStand)
                        .map(entity -> (ArmorStand) entity)
                        .anyMatch(armorStand -> {
                            String customName = armorStand.getCustomName();
                            return customName != null && facade.getLootboxRegistry().hasEntry(customName.toLowerCase());
                        });

                if (!hasLootbox) {

                    spawnLootboxAt(location);
                }
            }
        }
    }

    private void spawnLootboxAt(Location location) {
        var lootbox = getRandomLootbox();
        lootbox.spawn(location);
        facade.getLogger().info("Spawning lootbox at location: " + location);
    }

    private Lootbox getRandomLootbox() {
        return LootboxUtils.getRandomLootbox(facade.getLootboxRegistry().getMap().values());
    }
}
