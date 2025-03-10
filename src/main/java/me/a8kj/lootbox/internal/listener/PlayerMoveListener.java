package me.a8kj.lootbox.internal.listener;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.lootbox.parent.entity.lootbox.Lootbox;
import me.a8kj.lootbox.parent.plugin.LootboxFacade;

@Getter
@RequiredArgsConstructor
public class PlayerMoveListener implements Listener {

    private final LootboxFacade facade;

    @EventHandler
    public void onPlayerCollision(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        double distanceThreshold = 1.5;

        player.getNearbyEntities(distanceThreshold, distanceThreshold, distanceThreshold).stream()
                .filter(entity -> entity instanceof ArmorStand)
                .map(entity -> (ArmorStand) entity)
                .filter(armorStand -> armorStand.getCustomName() != null && !armorStand.getCustomName().isEmpty())
                .forEach(armorStand -> {
                    var lootbox = getFacade().getLootboxRegistry().get(armorStand.getCustomName());
                    if (lootbox == null) {
                        return;
                    }

                    if (!Lootbox.CollisionState.COLLISIONABLE.toString().equalsIgnoreCase(lootbox.getMetadata())) {
                        return;
                    }

                    lootbox.despawn();
                });
    }
}
