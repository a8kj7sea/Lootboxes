package me.a8kj.lootbox.parent.structure;

import java.util.Map;

import lombok.NonNull;

/**
 * Simple registry interface
 *
 * @param <T> Generic type argument
 */
public interface Registry<T> {

    /**
     * Register a new entry with the provided string key and value
     *
     * @param key   key as a string
     * @param value value
     * @throws IllegalArgumentException when there is already an entry for the
     *                                  provided key
     */
    void register(@NonNull String key, @NonNull T value);

    /**
     * Unregister the entry for the provided string key if present
     *
     * @param key key as a string
     * @throws IllegalArgumentException when there is no entry for the provided key
     */
    void unregister(@NonNull String key);

    /**
     * Check whether an entry is present for the provided string key
     *
     * @param key key as a string
     * @return whether an entry is present
     */
    boolean hasEntry(@NonNull String key);

    /**
     * Get the registered value for a string key
     *
     * @param key key as a string
     * @return value
     * @throws IllegalArgumentException when there is no value for the provided key
     */
    @NonNull
    T get(@NonNull String key);

    /**
     * Get the registered entries
     *
     * @return the registered entries
     */
    @NonNull
    Iterable<Pair<String, T>> entries();

    /**
     * 
     * Get the register map
     * 
     * @return the registered map
     * 
     */
    Map<String, T> getMap();

}