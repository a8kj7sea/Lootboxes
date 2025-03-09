package me.a8kj.lootbox.internal.entity.lootbox.rare;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import me.a8kj.lootbox.internal.plugin.LootboxPlugin;
import me.a8kj.lootbox.parent.entity.lootbox.Lootbox;
import me.a8kj.lootbox.parent.entity.lootbox.LootboxBase;
import me.a8kj.lootbox.parent.entity.lootbox.properties.Settings;
import me.a8kj.lootbox.parent.plugin.LootboxFacade;

public class RareLootbox extends LootboxBase {

    public RareLootbox() {
        super("rare", Settings.builder()
                .ai(false)
                .gravity(false)
                .nameVisible(false)
                .small(false)
                .headItem(new ItemStack(Material.IRON_BLOCK))
                .visible(true)
                .build(), 60.0f, new RareLootboxContainer());

        setMetadata(Lootbox.CollisionState.COLLISIONABLE.toString());

    }

    @Override
    protected LootboxFacade getFacade() {
        return LootboxPlugin.getInstance();
    }

}
