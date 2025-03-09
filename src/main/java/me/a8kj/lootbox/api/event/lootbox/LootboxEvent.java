package me.a8kj.lootbox.api.event.lootbox;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.lootbox.parent.entity.lootbox.Lootbox;

@RequiredArgsConstructor
@Getter
public abstract class LootboxEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final @Nonnull Lootbox lootbox;

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public void callEvent() {
        Bukkit.getPluginManager().callEvent(this);
    }
}