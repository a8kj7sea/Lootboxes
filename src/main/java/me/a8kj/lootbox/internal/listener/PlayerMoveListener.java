package me.a8kj.lootbox.internal.listener;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerCollision(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        double distanceThreshold = 1.5;

        for (ArmorStand armorStand : player.getWorld().getEntitiesByClass(ArmorStand.class)) {
            if (player.getLocation().distance(armorStand.getLocation()) < distanceThreshold) {
                if (armorStand.getCustomName() != null && !armorStand.getCustomName().isEmpty()) {
                        
                }
            }

        }
    }

}
