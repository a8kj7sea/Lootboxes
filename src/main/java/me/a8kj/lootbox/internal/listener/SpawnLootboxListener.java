package me.a8kj.lootbox.internal.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.a8kj.lootbox.api.event.lootbox.LootboxSpawnEvent;

public class SpawnLootboxListener implements Listener {

    @EventHandler
    public void onSpawnLootbox(LootboxSpawnEvent event) {
        Bukkit.broadcastMessage(event.getLootbox().getName() + " spawned successfully in "
                + event.getLootbox().getEntity().getLocation());
    }
}
