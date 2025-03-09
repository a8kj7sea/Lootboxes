package me.a8kj.lootbox.api.event.player;

import org.bukkit.entity.Player;

import lombok.Getter;
import me.a8kj.lootbox.parent.entity.lootbox.Lootbox;

@Getter
public class PlayerLootboxCollisionEvent extends PlayerEvent {

    private final Lootbox lootbox;

    public PlayerLootboxCollisionEvent(Player player, Lootbox lootbox) {
        super(player);
        this.lootbox = lootbox;
    }
}
