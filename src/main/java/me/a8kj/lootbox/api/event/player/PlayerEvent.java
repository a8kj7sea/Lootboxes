package me.a8kj.lootbox.api.event.player;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class PlayerEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final @Nonnull Player player;

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