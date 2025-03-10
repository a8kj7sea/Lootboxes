package me.a8kj.lootbox.parent.entity.lootbox;

import lombok.Getter;
import lombok.Setter;
import me.a8kj.lootbox.parent.entity.lootbox.properties.Container;
import me.a8kj.lootbox.parent.entity.lootbox.properties.Settings;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import net.md_5.bungee.api.ChatColor;

@Getter
@Setter
public class LootboxPreview implements Lootbox {

    private final String name;
    private final Settings settings;
    private final float spawnRate;
    private final Container container;
    private ArmorStand entity;
    private String metadata;

    public LootboxPreview(String name, ItemStack headItem, float spawnRate, Container container) {
        this.name = ChatColor.stripColor(name);
        this.settings = Settings.builder()
                .ai(false)
                .gravity(false)
                .nameVisible(true)
                .small(false)
                .displayName(name)
                .headItem(headItem)
                .visible(true)
                .build();
        this.spawnRate = spawnRate;
        this.container = container;
        this.metadata = Lootbox.CollisionState.NON_COLLISIONABLE.toString();
    }

    @Override
    public boolean spawn(Location location) {
        if (entity != null) {
            return false;
        }

        entity = location.getWorld().spawn(location, ArmorStand.class);
        entity.setCustomName(name);
        entity.setCustomNameVisible(settings.nameVisible());
        entity.setGravity(settings.gravity());
        entity.setInvisible(!settings.visible());
        entity.getEquipment().setHelmet(settings.headItem());

        return true;
    }

    @Override
    public boolean despawn() {
        if (entity == null) {
            return false;
        }

        entity.remove();
        entity = null;
        return true;
    }
}
