package me.a8kj.lootbox.parent.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import me.a8kj.lootbox.internal.util.ItemStackBuilder;
import me.a8kj.lootbox.internal.util.StringUtils;
import me.a8kj.lootbox.parent.entity.lootbox.properties.Container;

import java.util.Map;

public class LootboxPreviewMenu implements InventoryHolder {
    @Getter
    private final Inventory inventory;
    private final Container container;

    public LootboxPreviewMenu(String name, Container container, float spawnRate) {
        this.container = container;
        this.inventory = Bukkit.createInventory(this, calculateRows(container) * 9,
                StringUtils.format(name + "&b's Lootbox preview:"));
        addSpawnRateInfoItem(spawnRate);
        addItemsToInventory();
    }

    private int calculateRows(Container container) {
        int size = container.getContentsWithRate().size();
        return (int) Math.ceil(size / 9.0);
    }

    private void addSpawnRateInfoItem(float spawnRate) {
        inventory.setItem(inventory.getSize() - 1, new ItemStackBuilder(Material.PAPER)
                .setDisplayName("Spawn Rate: " + spawnRate + "%")
                .setAmount(1)
                .setEnchantment(Enchantment.AQUA_AFFINITY, 1)
                .setItemFlags(ItemFlag.HIDE_ENCHANTS)
                .setLore("This is the spawn rate of the lootbox.")
                .build());
    }

    private void addItemsToInventory() {
        Map<Float, ItemStack> itemsWithRates = container.getContentsWithRate();
        int index = 0;

        for (Map.Entry<Float, ItemStack> entry : itemsWithRates.entrySet()) {
            ItemStack item = entry.getValue();
            float spawnRate = entry.getKey();

            String displayName = item.getType().toString().replace("_", " ");
            displayName = displayName.length() > 15 ? displayName.substring(0, 15) + "..." : displayName;

            inventory.setItem(index, new ItemStackBuilder(item.getType())
                    .setAmount(item.getAmount())
                    .setDisplayName(StringUtils.format("&e" + displayName + " &7(Rate: " + spawnRate + "%)"))
                    .setLore(
                            StringUtils.format("&7Spawn rate: &f" + spawnRate + "%"),
                            StringUtils.format("&7This item is part of the lootbox."))
                    .build());
            index++;
        }
    }

}
