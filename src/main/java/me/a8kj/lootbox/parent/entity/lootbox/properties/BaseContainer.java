package me.a8kj.lootbox.parent.entity.lootbox.properties;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class BaseContainer implements Container {

    protected final Set<ItemStack> contents = new HashSet<>();
    protected final Map<Float, ItemStack> contentsWithRate = new HashMap<>();

    public BaseContainer() {
        initializeContents();
    }

    protected abstract void initializeContents();

    @Override
    public Set<ItemStack> getContents() {
        return new HashSet<>(contents);
    }

    @Override
    public Map<Float, ItemStack> getContentsWithRate() {
        return new HashMap<>(contentsWithRate);
    }

    public void addItem(ItemStack item) {
        contents.add(item);
    }

    public void addItemWithRate(float rate, ItemStack item) {
        contentsWithRate.put(rate, item);
    }

    public boolean removeItem(ItemStack item) {
        return contents.remove(item);
    }

    public boolean removeItemWithRate(float rate) {
        return contentsWithRate.remove(rate) != null;
    }

    public boolean updateContainer(ItemStack oldItem, ItemStack newItem) {
        if (contents.contains(oldItem)) {
            contents.remove(oldItem);
            contents.add(newItem);
            return true;
        }
        return false;
    }

    public boolean updateContainer(float rate, ItemStack newItem) {
        if (contentsWithRate.containsKey(rate)) {
            contentsWithRate.put(rate, newItem);
            return true;
        }
        return false;
    }

    public void updateAllContents(Set<ItemStack> newContents) {
        contents.clear();
        contents.addAll(newContents);
    }

    public void updateAllContentsWithRate(Map<Float, ItemStack> newContentsWithRate) {
        contentsWithRate.clear();
        contentsWithRate.putAll(newContentsWithRate);
    }

    public void clearAll() {
        contents.clear();
        contentsWithRate.clear();
    }
}
