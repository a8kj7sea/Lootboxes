package me.a8kj.lootbox.internal.listener;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.lootbox.parent.entity.lootbox.Lootbox;
import me.a8kj.lootbox.parent.entity.lootbox.Lootbox.CollisionState;
import me.a8kj.lootbox.parent.menu.LootboxPreviewMenu;
import me.a8kj.lootbox.parent.plugin.LootboxFacade;
import me.a8kj.lootbox.parent.structure.Registry;
import net.md_5.bungee.api.ChatColor;

@RequiredArgsConstructor
@Getter
public class PlayerInteractListener implements Listener {

    private final LootboxFacade facade;

    @EventHandler
    public void onPlayerInteractWithLootbox(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (entity instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) entity;

            if (armorStand.getCustomName() == null)
                return;

            String name = armorStand.getCustomName();
            Registry<Lootbox> lootboxRegistry = facade.getLootboxRegistry();
            Lootbox lootbox = lootboxRegistry.get(name);

            if (lootbox == null)
                return;

            event.setCancelled(true);

            if (facade.getPlayersInRemoveMode().contains(player.getUniqueId())) {
                handleRemoveMode(player, armorStand, lootbox);
            } else {
                handleLootboxPreview(player, armorStand, lootbox);
            }
        }
    }

    private void handleRemoveMode(Player player, ArmorStand armorStand, Lootbox lootbox) {
        if (CollisionState.NON_COLLISIONABLE.toString().equalsIgnoreCase(lootbox.getMetadata())) {
            armorStand.remove();
            player.sendMessage(ChatColor.YELLOW + "Lootbox preview removed!");
        } else {
            armorStand.remove();
            facade.getLocationManager().removeLocation(armorStand.getLocation());
            facade.getTasksRegistry().unregister(armorStand.getCustomName());
            facade.getLootboxStateManager().removeLootboxByLocation(armorStand.getLocation());
            player.sendMessage(ChatColor.YELLOW + "Lootbox removed!");
        }
    }

    private void handleLootboxPreview(Player player, ArmorStand armorStand, Lootbox lootbox) {
        if (CollisionState.NON_COLLISIONABLE.toString().equalsIgnoreCase(lootbox.getMetadata())) {
            LootboxPreviewMenu previewMenu = new LootboxPreviewMenu(
                    armorStand.getCustomName(),
                    lootbox.getContainer(),
                    lootbox.getSpawnRate());
            player.closeInventory();
            player.openInventory(previewMenu.getInventory());
            player.updateInventory();
            return;
        }
        lootbox.despawn();
    }
}
