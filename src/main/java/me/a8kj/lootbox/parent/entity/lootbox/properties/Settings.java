package me.a8kj.lootbox.parent.entity.lootbox.properties;

import org.bukkit.inventory.ItemStack;

import lombok.Builder;

@Builder
public record Settings(
        boolean small,
        boolean gravity,
        boolean ai,
        boolean visible, boolean nameVisible, String displayName,
        ItemStack headItem) {
}
