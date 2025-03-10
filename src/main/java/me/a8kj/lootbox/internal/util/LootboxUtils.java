package me.a8kj.lootbox.internal.util;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

import lombok.experimental.UtilityClass;
import me.a8kj.config.ConfigFile;
import me.a8kj.lootbox.parent.entity.lootbox.Lootbox;
import me.a8kj.lootbox.parent.structure.Pair;
import me.a8kj.lootbox.parent.structure.Registry;

@UtilityClass
public class LootboxUtils {

    static final ThreadLocalRandom random = ThreadLocalRandom.current();

    public static ItemStack getRandomItem(Map<Float, ItemStack> itemsWithSpawnRate) {
        if (itemsWithSpawnRate == null || itemsWithSpawnRate.isEmpty()) {
            return null;
        }

        float chance = random.nextFloat();

        final float[] cumulativeRate = { 0.0f };
        return itemsWithSpawnRate.entrySet().stream()
                .filter(entry -> {
                    cumulativeRate[0] += entry.getKey();
                    return chance <= cumulativeRate[0];
                })
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    @Deprecated(forRemoval = true)
    public static ItemStack getRandomItem(Set<ItemStack> items) {
        if (items == null || items.isEmpty()) {
            return null;
        }

        return items.stream()
                .limit(1)
                .findFirst()
                .orElse(null);
    }

    public static Lootbox getRandomLootbox(Collection<? extends Lootbox> lootboxCollection) {
        if (lootboxCollection == null || lootboxCollection.isEmpty()) {
            return null;
        }

        float totalRate = (float) lootboxCollection.stream()
                .mapToDouble(Lootbox::getSpawnRate)
                .sum();

        if (totalRate == 0) {
            return null;
        }

        float randomValue = random.nextFloat() * totalRate;

        float cumulativeRate = 0.0f;
        for (Lootbox lootbox : lootboxCollection) {
            cumulativeRate += lootbox.getSpawnRate();
            if (randomValue <= cumulativeRate) {
                return lootbox;
            }
        }

        return null;
    }

    public static void removeLootboxes(Set<Location> locations, Registry<Lootbox> lootboxRegistry,
            ConfigFile configFile) {
        locations.stream()
                .flatMap(location -> location.getWorld().getNearbyEntities(location, 1, 1, 1).stream())
                .filter(entity -> entity instanceof ArmorStand)
                .map(entity -> (ArmorStand) entity)
                .filter(armorStand -> armorStand.getCustomName() != null
                        && lootboxRegistry.get(armorStand.getCustomName().toLowerCase()) != null)
                .forEach(armorStand -> {
                    String uniqueId = armorStand.getUniqueId().toString();
                    if (configFile.getValue(uniqueId) != null) {
                        configFile.removeValue(uniqueId);
                        try {
                            configFile.save();
                        } catch (IOException e) {
                            Bukkit.getLogger().warning(e.getMessage());
                        }
                    }
                    armorStand.remove();
                });
    }

    public static <T> Collection<Pair<String, T>> getEntriesAsCollection(Iterable<Pair<String, T>> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

}
