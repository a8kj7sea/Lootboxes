package me.a8kj.lootbox.internal.entity.lootbox;

import me.a8kj.lootbox.parent.entity.lootbox.properties.BaseContainer;

public class NormalLootboxContainer extends BaseContainer {

    @Override
    protected void initializeContents() {
        this.addItemWithRate(0, null);
    }

}
