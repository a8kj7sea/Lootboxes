package me.a8kj.lootbox.parent.entity.lootbox.properties;

import org.bukkit.inventory.ItemStack;
import java.util.Map;
import java.util.Set;

public interface Container {

    Set<ItemStack> getContents();

    Map<Float, ItemStack> getContentsWithRate();

    void addItem(ItemStack item);

    void addItemWithRate(float rate, ItemStack item);

    boolean removeItem(ItemStack item);

    boolean removeItemWithRate(float rate);

    boolean updateContainer(ItemStack oldItem, ItemStack newItem);

    boolean updateContainer(float rate, ItemStack newItem);

    void updateAllContents(Set<ItemStack> newContents);

    void updateAllContentsWithRate(Map<Float, ItemStack> newContentsWithRate);

    void clearAll();
}
