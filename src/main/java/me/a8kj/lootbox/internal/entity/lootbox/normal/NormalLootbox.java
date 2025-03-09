package me.a8kj.lootbox.internal.entity.lootbox.normal;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import me.a8kj.lootbox.internal.plugin.LootboxPlugin;
import me.a8kj.lootbox.parent.entity.lootbox.Lootbox;
import me.a8kj.lootbox.parent.entity.lootbox.LootboxBase;
import me.a8kj.lootbox.parent.entity.lootbox.properties.Settings;
import me.a8kj.lootbox.parent.plugin.LootboxFacade;

public class NormalLootbox extends LootboxBase {

    public NormalLootbox() {
        super("normal", Settings.builder()
                .ai(false)
                .gravity(false)
                .nameVisible(false)
                .small(false)
                .headItem(new ItemStack(Material.IRON_BLOCK))
                .visible(true)
                .build(), 60.0f, new NormalLootboxContainer());

        setMetadata(Lootbox.CollisionState.COLLISIONABLE.toString());

    }

    @Override
    protected LootboxFacade getFacade() {
        return LootboxPlugin.getInstance();
    }

}
