package me.a8kj.lootbox.api.event.lootbox;

import lombok.Getter;
import me.a8kj.lootbox.parent.entity.lootbox.Lootbox;

public class LootboxDespawnEvent extends LootboxEvent {

    public enum DespawnCause {
        RELOAD, INTERACT, REMOVE_MODE;
    }

    @Getter
    private final DespawnCause cause;

    public LootboxDespawnEvent(Lootbox lootbox, DespawnCause cause) {
        super(lootbox);
        this.cause = cause;
    }

}
