package me.a8kj.lootbox.parent.entity.lootbox;

import org.bukkit.inventory.ItemStack;

import me.a8kj.lootbox.internal.plugin.LootboxPlugin;
import me.a8kj.lootbox.parent.entity.lootbox.properties.Container;
import me.a8kj.lootbox.parent.entity.lootbox.properties.Settings;
import me.a8kj.lootbox.parent.plugin.LootboxFacade;
import net.md_5.bungee.api.ChatColor;

public class LootboxPreview extends LootboxBase {

    public LootboxPreview(String name, ItemStack headItem, float spawnRate, Container container) {
        super(ChatColor.stripColor(name), Settings.builder()
                .ai(false)
                .gravity(false)
                .nameVisible(true)
                .small(false)
                .displayName(name)
                .headItem(headItem)
                .visible(true)
                .build(), spawnRate, container);

        setMetadata(Lootbox.CollisionState.NON_COLLISIONABLE.toString());
    }

    @Override
    protected LootboxFacade getFacade() {
        return LootboxPlugin.getInstance();
    }

}
