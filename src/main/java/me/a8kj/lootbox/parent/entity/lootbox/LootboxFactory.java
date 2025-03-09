package me.a8kj.lootbox.parent.entity.lootbox;

@Deprecated(forRemoval = true)
public interface LootboxFactory {

    LootboxBase createPreviewLootbox();

    LootboxBase createLootbox();
}
