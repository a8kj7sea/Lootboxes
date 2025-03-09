package me.a8kj.lootbox.internal.entity.lootbox.normal;

import me.a8kj.lootbox.parent.entity.lootbox.properties.BaseContainer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class NormalLootboxContainer extends BaseContainer {

    @Override
    protected void initializeContents() {
        addRandomItems();
    }

    private void addRandomItems() {
        Random random = new Random();
        addItemWithRate(0.5f, new ItemStack(Material.DIAMOND, random.nextInt(3) + 1));
        addItemWithRate(0.3f, new ItemStack(Material.EMERALD, random.nextInt(2) + 1));
        addItemWithRate(0.1f, new ItemStack(Material.NETHERITE_INGOT, 1));
        addItemWithRate(0.7f, new ItemStack(Material.IRON_INGOT, random.nextInt(5) + 1));
        addItemWithRate(0.2f, new ItemStack(Material.GOLDEN_APPLE, 1));
    }
}
