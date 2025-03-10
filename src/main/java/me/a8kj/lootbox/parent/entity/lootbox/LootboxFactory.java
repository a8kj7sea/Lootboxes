package me.a8kj.lootbox.parent.entity.lootbox;

import org.bukkit.inventory.ItemStack;
import java.util.Objects;

import me.a8kj.lootbox.parent.entity.lootbox.properties.Container;

public class LootboxFactory {

    public static LootboxPreview createPreviewLootbox(String name, ItemStack headItem, float spawnRate,
            Container container) {

        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(headItem, "Head item cannot be null");
        Objects.requireNonNull(container, "Container cannot be null");

        return new LootboxPreview(name, headItem, spawnRate, container);
    }

    public static LootboxPreview createPreviewLootbox(String displayName, Lootbox lootbox) {
        
        Objects.requireNonNull(displayName, "Display name cannot be null");
        Objects.requireNonNull(lootbox, "Lootbox cannot be null");
        Objects.requireNonNull(lootbox.getSettings(), "Lootbox settings cannot be null");
        Objects.requireNonNull(lootbox.getContainer(), "Lootbox container cannot be null");

        return new LootboxPreview(displayName, lootbox.getSettings().headItem(), lootbox.getSpawnRate(),
                lootbox.getContainer());
    }
}
