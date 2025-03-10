package me.a8kj.lootbox.internal.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.lootbox.api.event.lootbox.LootboxDespawnEvent;
import me.a8kj.lootbox.internal.util.LootboxUtils;
import me.a8kj.lootbox.parent.plugin.LootboxFacade;

@Getter
@RequiredArgsConstructor
public class DespawnLootboxListener implements Listener {

    private final LootboxFacade facade;

    @EventHandler
    public void onDespawnLootbox(LootboxDespawnEvent event) {
        var lootbox = event.getLootbox();
        if (event.getCause() == LootboxDespawnEvent.DespawnCause.INTERACT) {

            var container = lootbox.getContainer();

            var location = lootbox.getEntity().getLocation();

            var itemDrop = LootboxUtils.getRandomItem(container.getContentsWithRate());

            location.getWorld().dropItemNaturally(location, itemDrop);

        }

        
    }
}
