package me.a8kj.lootbox.internal.manager;

import com.google.common.collect.Sets;

import me.a8kj.lootbox.parent.manager.LocationManager;

import org.bukkit.Location;

import java.util.Collection;
import java.util.Set;

public class LocationManagerImpl implements LocationManager {

    private final Set<Location> locations = Sets.newConcurrentHashSet();

    @Override
    public void addLocation(Location location) {
        locations.add(location);
    }

    @Override
    public void updateLocations(Collection<Location> locations) {
        this.locations.addAll(locations);
    }

    @Override
    public void removeLocation(Location location) {
        locations.remove(location);
    }

    @Override
    public boolean hasLocation(Location location) {
        return locations.contains(location);
    }

    @Override
    public Set<Location> getLocations() {
        return locations;
    }
}
