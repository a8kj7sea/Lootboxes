package me.a8kj.lootbox.parent.registry;

import org.bukkit.scheduler.BukkitTask;
import lombok.NonNull;
import me.a8kj.lootbox.parent.structure.Pair;
import me.a8kj.lootbox.parent.structure.Registry;

import java.util.Map;
import java.util.HashMap;

public class BukkitTaskRegistry implements Registry<BukkitTask> {

    private static volatile Map<String, BukkitTask> taskMap = new HashMap<>();

    @Override
    public Map<String, BukkitTask> getMap() {
        return taskMap;
    }

    @Override
    public void register(@NonNull String key, @NonNull BukkitTask value) {
        taskMap.put(key, value);
    }

    @Override
    public void unregister(@NonNull String key) {
        taskMap.remove(key).cancel();
    }

    @Override
    public boolean hasEntry(@NonNull String key) {
        return taskMap.containsKey(key);
    }

    @Override
    public @NonNull BukkitTask get(@NonNull String key) {
        if (!taskMap.containsKey(key)) {
            throw new IllegalArgumentException("No task registered with key: " + key);
        }
        return taskMap.get(key);
    }

    @Override
    public @NonNull Iterable<Pair<String, BukkitTask>> entries() {
        return () -> taskMap.entrySet().stream()
                .map(entry -> Pair.of(entry.getKey(), entry.getValue()))
                .iterator();
    }
}
