package me.a8kj.lootbox.internal.util;

import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.inventory.ItemStack;

import lombok.experimental.UtilityClass;
import me.a8kj.lootbox.parent.entity.lootbox.Lootbox;

@UtilityClass
public class LootboxUtils {

    public static ItemStack getRandomItem(Map<Float, ItemStack> itemsWithSpawnRate) {
        if (itemsWithSpawnRate == null || itemsWithSpawnRate.isEmpty()) {
            return null;
        }

        Random random = new Random();
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

    public static ItemStack getRandomItem(Set<ItemStack> items) {
        if (items == null || items.isEmpty()) {
            return null;
        }

        Random random = new Random();
        return items.stream()
                .skip(random.nextInt(items.size()))
                .findFirst()
                .orElse(null);
    }

    public static String getRandomLootbox(Map<String, Lootbox> lootboxMap) {
        if (lootboxMap == null || lootboxMap.isEmpty()) {
            return null;
        }

        Random random = new Random();
        float totalRate = (float) lootboxMap.values().stream()
                .mapToDouble(Lootbox::getSpawnRate)
                .sum();

        if (totalRate == 0) {
            return null;
        }

        float randomValue = random.nextFloat() * totalRate;

        float cumulativeRate = 0.0f;
        for (Map.Entry<String, Lootbox> entry : lootboxMap.entrySet()) {
            cumulativeRate += entry.getValue().getSpawnRate();
            if (randomValue <= cumulativeRate) {
                return entry.getKey();
            }
        }

        return null;
    }
}
