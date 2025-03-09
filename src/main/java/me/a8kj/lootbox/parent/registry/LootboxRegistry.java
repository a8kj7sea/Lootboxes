package me.a8kj.lootbox.parent.registry;

import lombok.NonNull;
import me.a8kj.lootbox.parent.entity.lootbox.Lootbox;
import me.a8kj.lootbox.parent.structure.Pair;
import me.a8kj.lootbox.parent.structure.Registry;

import java.util.HashMap;
import java.util.Map;

public class LootboxRegistry implements Registry<Lootbox> {

    private final Map<String, Lootbox> lootboxMap = new HashMap<>();

    @Override
    public void register(@NonNull String key, @NonNull Lootbox value) {
        String lowerCaseKey = key.toLowerCase();
        if (lootboxMap.containsKey(lowerCaseKey)) {
            throw new IllegalArgumentException("Lootbox with this key already exists: " + lowerCaseKey);
        }
        lootboxMap.put(lowerCaseKey, value);
    }

    @Override
    public void unregister(@NonNull String key) {
        String lowerCaseKey = key.toLowerCase();
        if (!lootboxMap.containsKey(lowerCaseKey)) {
            throw new IllegalArgumentException("Lootbox with this key does not exist: " + lowerCaseKey);
        }
        lootboxMap.remove(lowerCaseKey);
    }

    @Override
    public boolean hasEntry(@NonNull String key) {
        return lootboxMap.containsKey(key.toLowerCase());
    }

    @Override
    public @NonNull Lootbox get(@NonNull String key) {
        String lowerCaseKey = key.toLowerCase();
        Lootbox lootbox = lootboxMap.get(lowerCaseKey);
        if (lootbox == null) {
            throw new IllegalArgumentException("Lootbox with this key does not exist: " + lowerCaseKey);
        }
        return lootbox;
    }

    @Override
    public @NonNull Iterable<Pair<String, Lootbox>> entries() {
        return () -> lootboxMap.entrySet().stream()
                .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
                .iterator();
    }
}
